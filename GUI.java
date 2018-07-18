import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.util.ArrayList;
import java.util.Locale;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;

/**
 * This is the GUI of the software "AlgraviS".
 * It provides tools for drawing a graph, reading a graph from a text file, editing a graph 
 * (add vertices and edges, delete vertices and edges, move vertices) and running different algorithms
 * (DFS, find connected components, maximal matching, BUILD) and visualizing them stepwise.
 * 
 * @author Anica, Sonja
 */

public class GUI {

	/**
	 * Containers for the GUI.
	 */
	private JFrame frame;
	private static JLabel lblStatus;
	private JButton btnVisualizeAlg;
	private JButton btnShowResult;
	private static DrawPanel drawPanel;
	private static DrawPanel auxiliaryDrawPanel;
	
	/**
	 * Container for the status.
	 */
	private static Status status;
	
	/**
	 * 
	 */
	private static Graph graph;
	private static String typeOfGraph;
	
	/**
	 * Containers for file reading.
	 */
	private Reader reader;
	private static ReaderBUILD readerBuild;	
	
	/**
	 * Containers for the algorithms.
	 */
	private MaximalMatching maximalMatching;
	private static DFS dfs;
	private ConnectedComponents findConCom;
	private static Build build;	
	private static Graph resultGraph;
	private UndirectedGraph auxiliaryGraph;
	
	/**
	 * Containers for the visualization of an algorithm.
	 */
	private static ArrayList<Operation> operations;
	private Operation operation;
	private EdgeOperation edgeOperation;
	private VertexOperation vertexOperation;
	private String operationName;
	private Timer timer;
	private int i;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		graph = new UndirectedGraph();
		typeOfGraph = "u";
		status = new Status();
		reader = new Reader();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("AlgraviS");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		drawPanel = new DrawPanel(784,491);
		drawPanel.setBorder(null);
		drawPanel.setLocation(0,23);
		drawPanel.setSize(784,491);
		frame.getContentPane().add(drawPanel);
		
		/**
		 * This label shows the current status of the software.
		 */
		lblStatus = new JLabel(status.getStatus(0));
		lblStatus.setBounds(0, 515, 784, 25);
		frame.getContentPane().add(lblStatus);
		
		/**
		 * Pressing this button starts the visualization of the current algorithm.
		 */
		btnVisualizeAlg = new JButton("visualize algorithm");
		btnVisualizeAlg.setBounds(0, 0, 140, 23);
		frame.getContentPane().add(btnVisualizeAlg);
		btnVisualizeAlg.setVisible(false);
		btnVisualizeAlg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(getStatusNumber()==6) {
					visualizeAlgorithmBuild();
				}
				else {
					if(resultGraph==null) {
						return;
					}
					visualizeAlgorithm();
				}	
			}
		});
		
		/**
		 * Pressing this button shows the result of the current algorithm.
		 */
		btnShowResult = new JButton("show result");
		btnShowResult.setBounds(141, 0, 104, 23);
		frame.getContentPane().add(btnShowResult);
		btnShowResult.setVisible(false);
		btnShowResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(resultGraph==null) {
					return;
				}
				if(getStatusNumber()==6) {
					Graphics g = drawPanel.getGraphics();
					drawPanel.drawCompleteGraph(g, Color.BLUE, Color.BLACK);
				}
				else {
					showResult();
				}				
			}
		});
	
		/**
		 * Produces the menu bar of the GUI.
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(135, 206, 250));
		frame.setJMenuBar(menuBar);
		
		/**
		 * This menu contains all algorithms.
		 */
		JMenu mnAlgorithm = new JMenu("Algorithm");
		menuBar.add(mnAlgorithm);
		
		/**
		 * Pressing this menu item starts the BFS.
		 * (Not yet implemented.)
		 */
		JMenuItem mntmBreadthFirstSearch = new JMenuItem("Breadth-First Search");
		mntmBreadthFirstSearch.setEnabled(false);
		mnAlgorithm.add(mntmBreadthFirstSearch);
		mntmBreadthFirstSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(1));
			}
		});
		
		/**
		 * Pressing this menu item starts the DFS.
		 */
		JMenuItem mntmDepthfirstsearch = new JMenuItem("Depth-First Search");
		//mntmDepthfirstsearch.setEnabled(false);
		mnAlgorithm.add(mntmDepthfirstsearch);
		mntmDepthfirstsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(2));
				startAlgorithm();	
				createDfsStartpointFrame();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(true);
			}
		});
		
		/**
		 * Pressing this menu item starts the finding connected components.
		 */
		JMenuItem mntmFindConnectedComponents = new JMenuItem("Find Connected Components");
		mnAlgorithm.add(mntmFindConnectedComponents);
		mntmFindConnectedComponents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(15));
				startAlgorithm();	
				findConCom = new ConnectedComponents();
				try {
					operations = findConCom.execute(graph);
					resultGraph = findConCom.getResult(graph);
				} catch (Exception e1) {
					lblStatus.setText(status.getStatus(17));
					return;
				}
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(true);
			}
		});
		
		/**
		 * Pressing this menu item starts the topological sort.
		 * (Not yet implemented.)
		 */
		JMenuItem mntmTopologicalSort = new JMenuItem("Topological Sort");
		mntmTopologicalSort.setEnabled(false);
		mnAlgorithm.add(mntmTopologicalSort);
		mntmTopologicalSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(3));
			}
		});
		
		/**
		 * Pressing this menu item starts the minimum-spanning-tree-algorithm.
		 * (Not yet implemented.)
		 */
		JMenuItem mntmMinimumSpanningTree = new JMenuItem("Minimum Spanning Tree");
		mntmMinimumSpanningTree.setEnabled(false);
		mnAlgorithm.add(mntmMinimumSpanningTree);
		mntmMinimumSpanningTree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(4));
			}
		});
		
		/**
		 * Pressing this menu item starts the maximal-matching-algorithm.
		 */
		JMenuItem mntmMaximalMatching = new JMenuItem("Maximal Matching");
		mnAlgorithm.add(mntmMaximalMatching);
		mntmMaximalMatching.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lblStatus.setText(status.getStatus(5));
				startAlgorithm();	
				maximalMatching = new MaximalMatching();
				try {
					operations = maximalMatching.execute(graph);
					resultGraph = maximalMatching.getResult(graph);
				} catch (Exception e1) {
					lblStatus.setText(status.getStatus(17));
					return;
				}
				btnShowResult.setVisible(true);
				btnVisualizeAlg.setVisible(true);
			}
		});
		
		/**
		 * Pressing this menu item starts the BUILD-Algorithm.
		 */
		JMenuItem mntmBuild = new JMenuItem("BUILD");
		mnAlgorithm.add(mntmBuild);
		mntmBuild.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(6));
				createStartBuildFrame();
			}
		});
		
		/**
		 * This menu contains menu items for editing the graph.
		 */
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		/**
		 * This menu contains menu items for adding something to the graph.
		 */
		JMenu mnAdd = new JMenu("add");
		mnEdit.add(mnAdd);
		
		/**
		 * After pressing this menu item vertices can be added by clicking in the DrawPanel.
		 */
		JMenuItem mntmVertexdraw = new JMenuItem("vertex");
		mnAdd.add(mntmVertexdraw);
		mntmVertexdraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(7));
				startEdit();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * After pressing this menu item edges can be added by clicking on two vertices.
		 */
		JMenuItem mntmEdgedraw = new JMenuItem("edge (draw)");
		mnAdd.add(mntmEdgedraw);
		mntmEdgedraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(8));
				startEdit();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * After pressing this menu item an edge can be added by choosing vertex-names.
		 */
		JMenuItem mntmEdgespecify = new JMenuItem("edge (specify)");
		mnAdd.add(mntmEdgespecify);
		mntmEdgespecify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAddLineFrame();
				lblStatus.setText(status.getStatus(9));
				startEdit();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * This menu contains menu items for deleting something of the graph.
		 */
		JMenu mnDelete = new JMenu("delete");
		mnEdit.add(mnDelete);
		
		/**
		 * After pressing this menu item vertices can be deleted by clicking on a vertex.
		 */
		JMenuItem mntmDeleteVertex = new JMenuItem("delete vertex");
		mnDelete.add(mntmDeleteVertex);
		mntmDeleteVertex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(10));
				startEdit();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * After pressing this menu item edges can be deleted by clicking on two vertices.
		 */
		JMenuItem mntmDeleteEdge = new JMenuItem("delete edge");
		mnDelete.add(mntmDeleteEdge);
		mntmDeleteEdge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(11));
				startEdit();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * After pressing this menu item a window opens that asks if the graph should really be deleted..
		 */
		JMenuItem mntmDeleteGraph = new JMenuItem("delete graph");
		mnDelete.add(mntmDeleteGraph);
		mntmDeleteGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(0));
				createDeleteGraphFrame();
				startEdit();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * After pressing this menu item vertices can be moved by clicking on vertices and dragging.
		 */
		JMenuItem mntmMove = new JMenuItem("move");
		mnEdit.add(mntmMove);
		mntmMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(12));
				startEdit();
				btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * Pressing this menu item reverses the last operation.
		 */
		JMenuItem mntmUndo = new JMenuItem("undo");
		mntmUndo.setEnabled(false);
		mntmUndo.setIcon(new ImageIcon("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\src\\img\\Undo-icon.png"));
		mnEdit.add(mntmUndo);
		
		/**
		 * This menu contains menu items for dealing with files.
		 */
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		/**
		 * This menu contains menu items for opening files.
		 */
		JMenu mnOpen = new JMenu("open");
		mnFile.add(mnOpen);
		
		/**
		 * Pressing on this menu item opens a new text file for adding a graph as text file.
		 * (Not yet implemented.)
		 */
		JMenuItem mntmNew = new JMenuItem("new");
		mntmNew.setEnabled(false);
		mnOpen.add(mntmNew);
		
		/**
		 * Pressing this menu item opens a window for choosing a file that should be read.
		 */
		JMenuItem mntmFile = new JMenuItem("file");
		mnOpen.add(mntmFile);
		mntmFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(13));
				drawPanel.vertexColor = Color.BLUE;
				drawPanel.edgeColor = Color.BLACK;
				btnShowResult.setVisible(false);
		        btnVisualizeAlg.setVisible(false);
				/*
				 * produce open dialog
				 * read file
				 * produce and draw graph
				 */
				String directoryName = "C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles";
		        JFileChooser chooser = new JFileChooser(directoryName);
		        chooser.setDefaultLocale(Locale.ENGLISH); 
		        chooser.setLocale(Locale.ENGLISH);
		        chooser.updateUI();
		        if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		        {
		        	String fileName = chooser.getSelectedFile().getPath();
		           	try {
					graph = reader.read(fileName);
					typeOfGraph = graph.typeOfGraph();
					drawPanel.changeGraph(graph, typeOfGraph);
		           	} 
		           	catch (IOException e1) {
					lblStatus.setText(status.getStatus(16));
		           	}   
		        }
			}
		});
		
		/**
		 * Pressing on this menu item safes the current graph as text file.
		 * (Not yet implemented.)
		 */
		JMenuItem mntmSaveas = new JMenuItem("save (as)");
		mntmSaveas.setEnabled(false);
		mnFile.add(mntmSaveas);
		mntmSaveas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(13));
				/*
				 * produce save dialog
				 * write file
				 */
				String directoryName = "C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles";
		        JFileChooser chooser = new JFileChooser(directoryName);
		        if(chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
		        {
		        	String fileName = chooser.getSelectedFile().getName();
		        	
		        }
		        btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
			}
		});
		
		/**
		 * Pressing on this menu item opens the text file containing the current graph.
		 * (Not yet implemented.)
		 */
		JMenuItem mntmShow = new JMenuItem("show");
		mntmShow.setEnabled(false);
		mnFile.add(mntmShow);
		
		/**
		 * This menu contains menu items for showing or hiding edge weights.
		 * (Not yet implemented.)
		 */
		JMenu mnView = new JMenu("View");
		mnView.setEnabled(false);
		menuBar.add(mnView);
		
		/**
		 * Pressing on this menu item shows the edge weights.
		 * (Note yet implemented.)
		 */
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("show edge weights");
		mnView.add(mntmNewMenuItem_1);
		
		/**
		 * Pressing on this menu item hides the edge weights.
		 * (Note yet implemented.)
		 */
		JMenuItem mntmShowHideLabel = new JMenuItem("hide edge weights");
		mnView.add(mntmShowHideLabel);
		
		/**
		 * Pressing this menu shows a help page.
		 * (Not yet implemented.)
		 */
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setEnabled(false);
		menuBar.add(mnHelp);		
	}
	
	/**
	 * Creates a frame for adding an edge via specifying.
	 * It asks for startpoint and endpoint.
	 */
	public static void createAddLineFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setBounds(100, 150, 200, 75);
		
		JTextField txtC1 = new JTextField(1);
		JTextField txtC2 = new JTextField(1);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton btnAddEdge = new JButton("add");
		btnAddEdge.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!txtC1.getText().matches("\\d+") || !txtC2.getText().matches("\\d+")) {
					lblStatus.setText(status.getStatus(17));
					return;
				}
				lblStatus.setText(status.getStatus(9));
				int cid1 = Integer.parseInt(txtC1.getText());
				int cid2 = Integer.parseInt(txtC2.getText());
				try {
					drawPanel.addEdge(cid1, cid2);
				} catch (Exception e1) {
					lblStatus.setText(status.getStatus(17));
					return;
				}
				frame.dispose();
			}
		});
		
		frame.getContentPane().add(txtC1);
		frame.getContentPane().add(txtC2);
		frame.getContentPane().add(btnCancel);
		frame.getContentPane().add(btnAddEdge);
		frame.setVisible(true);
		frame.validate();
	}
	
	/**
	 * Creates a frame that appears after clicking on "delete graph" 
	 * and asks if the graph should really be deleted.
	 */
	public static void createDeleteGraphFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setBounds(390, 375, 220, 60);
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setUndecorated(true);
		
		JLabel lblText = new JLabel("Do you really want to delete the graph?");
		
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				graph = new UndirectedGraph();
				typeOfGraph = "u";
				drawPanel.changeGraph(graph, typeOfGraph);
				frame.dispose();
			}
		});
		
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		frame.getContentPane().add(lblText);
		frame.getContentPane().add(btnYes);
		frame.getContentPane().add(btnNo);
		frame.setVisible(true);
	}
	
	/**
	 * Creates a frame that appears after clicking on "Depth-First Search" 
	 * and asks for a start vertex.
	 * Runs DFS for that start vertex if it is contained in the graph.
	 */
	public static void createDfsStartpointFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setBounds(100, 150, 200, 100);
		
		JLabel lblText = new JLabel("Please enter a start vertex");
		JTextField txtC1 = new JTextField(1);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton btnApply = new JButton("apply");
		btnApply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!txtC1.getText().matches("\\d+")) {
					lblStatus.setText(status.getStatus(17));
					return;
				}
				lblStatus.setText(status.getStatus(2));
				Integer startpoint = Integer.parseInt(txtC1.getText());
				dfs = new DFS();
				try {
					operations = dfs.execute(graph, startpoint);
					resultGraph = dfs.getResult(graph, startpoint);
					lblStatus.setText(status.getStatus(2));
				} catch (Exception e1) {
					lblStatus.setText(status.getStatus(17));
					return;
				}
				frame.dispose();
			}
		});
		
		frame.getContentPane().add(lblText);
		frame.getContentPane().add(txtC1);
		frame.getContentPane().add(btnCancel);
		frame.getContentPane().add(btnApply);
		frame.setVisible(true);
		frame.validate();
	}
	
	/**
	 * Getter for the status number.
	 * 
	 * @return status number
	 */
	public static int getStatusNumber() {
		return status.getStatusNumber();
	}
	
	/**
	 * Sets the vertex- and edge-color to gray, repaints the graph
	 * and creates a new empty graph for the result.
	 */
	public void startAlgorithm() {
		graph = drawPanel.graph;
		drawPanel.vertexColor = Color.GRAY;
		drawPanel.edgeColor = Color.GRAY;
		drawPanel.repaint();
		if(graph.typeOfGraph().equals("undirected")) {
			resultGraph = new UndirectedGraph();
		}
		else {
			resultGraph = new DirectedGraph();
		}
	}
	
	/**
	 * Sets the vertex- and edge-color to the default colors.
	 */
	public void startEdit() {
		drawPanel.vertexColor = Color.BLUE;
		drawPanel.edgeColor = Color.BLACK;
	}
	
	/**
	 * Visualizes an algorithm.
	 */
	public void visualizeAlgorithm() {
		Graphics g = this.drawPanel.getGraphics();

		drawPanel.drawCompleteGraph(g, drawPanel.vertexColor, drawPanel.edgeColor);
		i = 0;
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(i<operations.size())) {
					timer.stop();
					return;
				}
				operation = operations.get(i);
				
				if(operation.getOperationType().equals("edge")) {
					edgeOperation = (EdgeOperation) operation;
					operationName = operation.getOperationName();
					switch(operationName) {
					case "consider":
						drawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.GREEN);
						break;
					case "choose":
						drawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.RED);
						break;
					case "not choose":
						drawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.BLACK);
						break;
					default:
						break;
					}
				}
				else {
					vertexOperation = (VertexOperation) operation;
					operationName = operation.getOperationName();
					switch(operationName) {
					case "consider":
						drawPanel.drawVertex(vertexOperation.getVertexName(), Color.GREEN);
						break;
					case "choose":
						drawPanel.drawVertex(vertexOperation.getVertexName(), Color.RED);
						break;
					case "not choose":
						drawPanel.drawVertex(vertexOperation.getVertexName(), Color.GRAY);
						break;
					default:
						break;
					}
				}
				i++;				
			}	
		});
			timer.start();	
	}
	
	/**
	 * Shows the result of an algorithm.
	 */
	public void showResult() {
		for(int i=0; i<operations.size(); i++) {
			operation = operations.get(i);
			if(operation.getOperationType().equals("edge")) {
				edgeOperation = (EdgeOperation) operation;
				if(operation.getOperationName().equals("choose")) {
					drawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.RED);
				}
			}
			else {
				vertexOperation = (VertexOperation) operation;
				if(operation.getOperationName().equals("choose")) {
					drawPanel.drawVertex(vertexOperation.getVertexName(), Color.RED);
				}
			}	
		}
	}
	
	/**
	 * Visualizes BUILD.
	 */
	public void visualizeAlgorithmBuild() {
		createHelpGraphFrame();
		Graphics g = this.drawPanel.getGraphics();
		Graphics auxiliaryG = this.auxiliaryDrawPanel.getGraphics();
		auxiliaryGraph = new UndirectedGraph();
		
		drawPanel.emptyDrawPanel(g);
		i = 0;
		frame.setEnabled(false);
		timer = new Timer(750, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(i<operations.size())) {
					timer.stop();
					return;
				}
				operation = operations.get(i);
				
				if(operation.getOperationType().equals("edge")) {
					edgeOperation = (EdgeOperation) operation;
					operationName = operation.getOperationName();
					switch(operationName) {
					case "consider":
						auxiliaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.GREEN);
						break;
					case "choose":
						auxiliaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.RED);
						break;
					case "not choose":
						auxiliaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.BLACK);
						break;
					case "build add":
						drawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.BLACK);
						break;
					case "aho add":
						auxiliaryDrawPanel.addEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName());
						auxiliaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.BLACK);
						break;
					default:
						break;
					}
				}
				else {
					vertexOperation = (VertexOperation) operation;
					operationName = operation.getOperationName();
					switch(operationName) {
					case "consider":
						auxiliaryDrawPanel.drawVertex(vertexOperation.getVertexName(), Color.GREEN);
						break;
					case "choose":
						auxiliaryDrawPanel.drawVertex(vertexOperation.getVertexName(), Color.RED);
						break;
					case "not choose":
						auxiliaryDrawPanel.drawVertex(vertexOperation.getVertexName(), Color.BLUE);
						break;
					case "build add":
						drawPanel.drawVertex(vertexOperation.getVertexName(), Color.BLUE);
						break;
					case "aho add":
						if(i!=0 && !operations.get(i-1).getOperationName().equals("aho add")) {
							auxiliaryGraph = new UndirectedGraph();
							auxiliaryDrawPanel.setGraph(auxiliaryGraph, "undirected");
						}					
						auxiliaryGraph.addVertex(vertexOperation.getVertexName());
						auxiliaryDrawPanel.setGraph(auxiliaryGraph, "undirected");
						if(i<operations.size()-1 && !(operations.get(i+1).getOperationName().equals("aho add") && operations.get(i+1).getOperationType().equals("vertex"))) {
							auxiliaryDrawPanel.drawCompleteGraph(auxiliaryG, auxiliaryDrawPanel.vertexColor, auxiliaryDrawPanel.edgeColor);
						}	
						break;
					default:
						break;
					}
				}
				i++;
				if(i==operations.size()) {
					frame.setEnabled(true);
					ahoframe.dispose();					
				}
			}
		});
		timer.start();		
	}
	
	/**
	 * Creates a frame that shows the Aho-graph for BUILD.
	 */
	private static JFrame ahoframe;
	public static void createHelpGraphFrame() {
		ahoframe = new JFrame("Aho-graph");
		ahoframe.getContentPane().setLayout(null);
		ahoframe.setBounds(910, 225, 400, 400);
		
		auxiliaryDrawPanel = new DrawPanel(390, 365);
		auxiliaryDrawPanel.setBorder(null);
		auxiliaryDrawPanel.setLocation(0, 0);
		auxiliaryDrawPanel.setSize(390, 365);
		auxiliaryDrawPanel.setLayout(drawPanel.getLayout());
		ahoframe.getContentPane().add(auxiliaryDrawPanel);
		ahoframe.setVisible(true);
	}
	
	/**
	 * Creates a frame that asks if the graph should really be deleted and the BUILD algorithm started.
	 */
	public  void createStartBuildFrame() {
		readerBuild = null;
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setBounds(390, 375, 220, 80);
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setUndecorated(true);
		
		JLabel lblText = new JLabel( "<html>Do you really want to delete the graph<p/>and start the BUILD algorithm?</html>" );
		
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Pair<UndirectedGraph, Integer> rootedTree;
				graph = new UndirectedGraph();
				typeOfGraph = "u";
				drawPanel.changeGraph(graph, typeOfGraph);
				frame.dispose();
				build = new Build();
				ArrayList<Integer> leaves = null;
				ArrayList<Pair<Pair<Integer, Integer>, Integer>> triples = null;
				String directoryName = "C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles";
		        JFileChooser chooser = new JFileChooser(directoryName);
		        JComponent.setDefaultLocale(Locale.ENGLISH); 
		        chooser.setLocale(Locale.ENGLISH);
		        chooser.updateUI();
		        if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		        {
		        	String fileName = chooser.getSelectedFile().getPath();
		           	try {
					ReaderBUILD.read(fileName);
					leaves = ReaderBUILD.getLeafset();
					triples = ReaderBUILD.getTripleset();
		           	} 
		           	catch (Exception e1) {
		           		lblStatus.setText(status.getStatus(16));
		           		return;
		           	}
		        }
		        rootedTree = build.build(triples, leaves, new Integer(-1));
		        resultGraph = rootedTree.getFirst();

		        operations = build.getChanges();
		        btnVisualizeAlg.setVisible(true);
				btnShowResult.setVisible(true);
		        if(resultGraph == null) {
		        	btnVisualizeAlg.setVisible(false);
					btnShowResult.setVisible(false);
		        	lblStatus.setText(status.getStatus(6) + " The given tripleset is inconsistent. " + ReaderBUILD.getLeafsetPrint() + ", " + ReaderBUILD.getTriplesetPrint());
		        	return;
		        }
				drawPanel.setGraph(resultGraph, "undirected");
				lblStatus.setText(status.getStatus(6) + " for " + ReaderBUILD.getLeafsetPrint() + ", " + ReaderBUILD.getTriplesetPrint());
			}
		});
		
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(lblText);
		frame.getContentPane().add(btnYes);
		frame.getContentPane().add(btnNo);
		frame.setVisible(true);
	}
}

