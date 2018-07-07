import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
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

/**
 * 
 * 
 * @author Anica, Sonja
 *
 */

public class GUI {

	private JFrame frame;
	private static Status status;
	private static DrawPanel drawPanel;
	private static DrawPanel auxilaryDrawPanel;
	private Reader reader;
	private static Graph graph;
	private static String typeOfGraph;
	private static Graph resultGraph;
	private ArrayList<Operation> operations;
	private Operation operation;
	private EdgeOperation edgeOperation;
	private VertexOperation vertexOperation;
	private MaximalMatching maximalMatching;
	private DFS dfs;
	private ConnectedComponents findConCom;
	private static Build build;
	private static ReaderBUILD readerBuild;

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
		frame = new JFrame("GraphGUI");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JCheckBox chckbxUndirected = new JCheckBox("undirected");
		chckbxUndirected.setSelected(true);
		chckbxUndirected.setBounds(592, 0, 95, 15);
		frame.getContentPane().add(chckbxUndirected);
			
		JCheckBox chckbxDirected = new JCheckBox("directed");
		chckbxDirected.setBounds(689, 0, 95, 15);
		chckbxDirected.setEnabled(false);
		frame.getContentPane().add(chckbxDirected);
		/*
		chckbxUndirected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chckbxUndirected.isSelected()) {
					typeOfGraph = "u";
					chckbxDirected.setSelected(false);
				}
			}
		});
		chckbxDirected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chckbxDirected.isSelected()) {
					typeOfGraph = "d";
					chckbxUndirected.setSelected(false);
				}
			}
		});
		*/
		drawPanel = new DrawPanel();
		drawPanel.setBorder(null);
		drawPanel.setLocation(0,23);
		drawPanel.setSize(784,491);
		frame.getContentPane().add(drawPanel);
		
		JLabel lblStatus = new JLabel(status.getStatus(0));
		lblStatus.setBounds(0, 515, 784, 25);
		frame.getContentPane().add(lblStatus);
		
		JButton btnVisualizeAlg = new JButton("visualize Alg");
		btnVisualizeAlg.setBounds(0, 0, 104, 23);
		frame.getContentPane().add(btnVisualizeAlg);
		btnVisualizeAlg.setVisible(false);
		btnVisualizeAlg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//drawPanel.repaint();
				visualizeAlgorithm();
			}
		});
		
		JButton btnShowResult = new JButton("show result");
		btnShowResult.setBounds(105, 0, 104, 23);
		frame.getContentPane().add(btnShowResult);
		btnShowResult.setVisible(false);
		btnShowResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showResult();
			}
		});
	
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(135, 206, 250));
		frame.setJMenuBar(menuBar);
		
		JMenu mnAlgorithm = new JMenu("Algorithm");
		menuBar.add(mnAlgorithm);
		
		JMenuItem mntmBreadthFirstSearch = new JMenuItem("Breadth-First Search");
		mntmBreadthFirstSearch.setEnabled(false);
		mnAlgorithm.add(mntmBreadthFirstSearch);
		mntmBreadthFirstSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(1));
			}
		});
		
		JMenuItem mntmDepthfirstsearch = new JMenuItem("Depth-First Search");
		//mntmDepthfirstsearch.setEnabled(false);
		mnAlgorithm.add(mntmDepthfirstsearch);
		mntmDepthfirstsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(2));
				startAlgorithm();	
				dfs = new DFS();
				operations = dfs.execute(graph);
				resultGraph = dfs.getResult(graph);
				btnShowResult.setVisible(true);
				btnVisualizeAlg.setVisible(true);
			}
		});
		
		JMenuItem mntmFindConnectedComponents = new JMenuItem("Find connected components");
		//mntmFindConnectedComponents.setEnabled(false);
		mnAlgorithm.add(mntmFindConnectedComponents);
		mntmFindConnectedComponents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(2));
				startAlgorithm();	
				findConCom = new ConnectedComponents();
				operations = findConCom.execute(graph);
				resultGraph = findConCom.getResult(graph);
				btnShowResult.setVisible(true);
				btnVisualizeAlg.setVisible(true);
			}
		});
		
		JMenuItem mntmTopologicalSort = new JMenuItem("Topological Sort");
		mntmTopologicalSort.setEnabled(false);
		mnAlgorithm.add(mntmTopologicalSort);
		mntmTopologicalSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(3));
			}
		});
		
		JMenuItem mntmMinimumSpanningTree = new JMenuItem("Minimum Spanning Tree");
		mntmMinimumSpanningTree.setEnabled(false);
		mnAlgorithm.add(mntmMinimumSpanningTree);
		mntmMinimumSpanningTree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(4));
			}
		});
		
		JMenuItem mntmMaximalMatching = new JMenuItem("Maximal Matching");
		mnAlgorithm.add(mntmMaximalMatching);
		mntmMaximalMatching.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lblStatus.setText(status.getStatus(5));
				startAlgorithm();	
				maximalMatching = new MaximalMatching();
				operations = maximalMatching.execute(graph);
				resultGraph = maximalMatching.getResult(graph);
				btnShowResult.setVisible(true);
				btnVisualizeAlg.setVisible(true);
			}
		});
		
		JMenuItem mntmBuild = new JMenuItem("BUILD");
		mnAlgorithm.add(mntmBuild);
		mntmBuild.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(6));
				createStartBuildFrame();
				System.out.println("wieder zurück");
				//operations = build.getChanges();
				//btnShowResult.setVisible(true);
				//btnVisualizeAlg.setVisible(true);
			}
		});
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnAdd = new JMenu("add");
		mnEdit.add(mnAdd);
		
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
		
		JMenu mnDelete = new JMenu("delete");
		mnEdit.add(mnDelete);
		
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
		
		//JMenuItem mntmSetVertexProperties = new JMenuItem("set vertex properties");
		//mnEdit.add(mntmSetVertexProperties);
		
		JMenuItem mntmUndo = new JMenuItem("undo");
		mntmUndo.setEnabled(false);
		mntmUndo.setIcon(new ImageIcon("C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles\\Undo-icon.png"));
		mnEdit.add(mntmUndo);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnOpen = new JMenu("open");
		mnFile.add(mnOpen);
		
		JMenuItem mntmNew = new JMenuItem("new");
		mntmNew.setEnabled(false);
		mnOpen.add(mntmNew);
		
		JMenuItem mntmFile = new JMenuItem("file");
		mnOpen.add(mntmFile);
		mntmFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(13));
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
		        	String fileName = chooser.getSelectedFile().getName();
		           	try {
					graph = reader.read(directoryName + "\\" + fileName);
					typeOfGraph = reader.typeOfGraph();
					drawPanel.changeGraph(graph, typeOfGraph);
		           	} 
		           	catch (IOException e1) {
					//System.out.println("Error concerning file:\\n");
					e1.printStackTrace();
		           	}   
		        }
		        btnShowResult.setVisible(false);
				btnVisualizeAlg.setVisible(false);
		}
		});
		
		
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
		
		JMenuItem mntmShow = new JMenuItem("show");
		mntmShow.setEnabled(false);
		mnFile.add(mntmShow);
		
		JMenu mnView = new JMenu("View");
		mnView.setEnabled(false);
		menuBar.add(mnView);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("show edge weights");
		mnView.add(mntmNewMenuItem_1);
		
		JMenuItem mntmShowHideLabel = new JMenuItem("hide edge weights");
		mnView.add(mntmShowHideLabel);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
	}
	
	/**
	 * Creates a frame for adding an edge via specifying.
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
				int cid1 = Integer.parseInt(txtC1.getText());
				int cid2 = Integer.parseInt(txtC2.getText());
				drawPanel.addEdge(cid1, cid2);
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
	 * Creates a frame that appears after clicking on the "delete graph" 
	 * and asks if the graph should really be deleted.
	 */
	public static void createDeleteGraphFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setBounds(390, 375, 220, 60);
		frame.getContentPane().setBackground(new Color(0, 204, 204));
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
	 * Getter for the statusnumber.
	 * 
	 * @return statusnumber
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
		//System.out.println("in visualize");
		//System.out.println("number of operations = "+operations.size());
		Graphics g = this.drawPanel.getGraphics();

		drawPanel.drawCompleteGraph(g, drawPanel.vertexColor, drawPanel.edgeColor);
		String operationName;
		
		for(int i=0; i<operations.size(); i++) {
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
					drawPanel.drawVertex(vertexOperation.getVertexName(), Color.BLACK);
					break;
				default:
					break;
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}		
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
		System.out.println("in visualize");
		System.out.println("number of operations = "+operations.size());
		Graphics g = this.drawPanel.getGraphics();
		Graphics auxilaryG = this.auxilaryDrawPanel.getGraphics();
		UndirectedGraph auxilaryGraph = new UndirectedGraph();
		//auxilaryDrawPanel.changeGraph(resultGraph, "undirected");
		//auxilaryDrawPanel.drawCompleteGraph(auxilaryG, auxilaryDrawPanel.vertexColor, auxilaryDrawPanel.edgeColor);

		//drawPanel.drawCompleteGraph(g, drawPanel.vertexColor, drawPanel.edgeColor);
		String operationName;
		
		for(int i=0; i<operations.size(); i++) {
			operation = operations.get(i);
			
			if(operation.getOperationType().equals("edge")) {
				edgeOperation = (EdgeOperation) operation;
				operationName = operation.getOperationName();
				switch(operationName) {
				case "consider":
					auxilaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.GREEN);
					break;
				case "choose":
					auxilaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.RED);
					break;
				case "not choose":
					auxilaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.BLACK);
					break;
				case "build add":
					drawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.BLACK);
					break;
				case "aho add":
					auxilaryDrawPanel.addEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName());
					auxilaryDrawPanel.drawEdge(edgeOperation.getStartVertexName(), edgeOperation.getEndVertexName(), Color.BLACK);
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
					auxilaryDrawPanel.drawVertex(vertexOperation.getVertexName(), Color.GREEN);
					break;
				case "choose":
					auxilaryDrawPanel.drawVertex(vertexOperation.getVertexName(), Color.RED);
					break;
				case "not choose":
					auxilaryDrawPanel.drawVertex(vertexOperation.getVertexName(), Color.BLACK);
					break;
				case "build add":
					drawPanel.drawVertex(vertexOperation.getVertexName(), Color.BLACK);
					break;
				case "aho add":
					auxilaryGraph.addVertex(vertexOperation.getVertexName());
					auxilaryDrawPanel.changeGraph(auxilaryGraph, "undirected");
					auxilaryDrawPanel.drawCompleteGraph(auxilaryG, auxilaryDrawPanel.vertexColor, auxilaryDrawPanel.edgeColor);
					auxilaryDrawPanel.drawVertex(vertexOperation.getVertexName(), Color.BLACK);
					break;
				default:
					break;
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}		
	}
	
	/**
	 * Creates a frame that shows the helpgraph for BUILD
	 */
	public static void createHelpGraphFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setBounds(910, 225, 400, 400);
		//frame.getContentPane().setBackground(new Color(0, 204, 204));
		//frame.setUndecorated(true);
		
		auxilaryDrawPanel = new DrawPanel();
		auxilaryDrawPanel.setBorder(null);
		auxilaryDrawPanel.setLocation(910,225);
		auxilaryDrawPanel.setSize(400,400);
		frame.getContentPane().add(auxilaryDrawPanel);
		frame.setVisible(true);
		// Test, ob auxilaryDrawPanel überhaupt malt.
		// funktioniert aber nicht :(
		/*Graphics auxilaryG = auxilaryDrawPanel.getGraphics();
		UndirectedGraph auxilaryGraph = new UndirectedGraph();
		auxilaryGraph.addVertex(1);
		auxilaryGraph.addVertex(2);
		auxilaryGraph.addEdge(1, 2);
		
		auxilaryDrawPanel.changeGraph(auxilaryGraph, "undirected");
		System.out.println("number of vert = " +auxilaryDrawPanel.vertices.size());
		auxilaryDrawPanel.drawCompleteGraph(auxilaryG, auxilaryDrawPanel.vertexColor, auxilaryDrawPanel.edgeColor);
		*/
	}
	
	/**
	 * Creates a frame that asks if the graph should really be deleted and the BUILD algorithm started.
	 */
	public  void createStartBuildFrame() {
		readerBuild = null;
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setBounds(390, 375, 220, 80);
		frame.getContentPane().setBackground(new Color(0, 204, 204));
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
		        	String fileName = chooser.getSelectedFile().getName();
		           	try {
					ReaderBUILD.read(directoryName + "\\" + fileName);
					//leaves = readerBuild.getLeafset();
					//triples = readerBuild.getTripleset();
		           	} 
		           	catch (IOException e1) {
					//System.out.println("Error concerning file:\\n");
					e1.printStackTrace();
		           	}   
		           	leaves = ReaderBUILD.getLeafset();
					triples = ReaderBUILD.getTripleset();
		        }
		        System.out.println("num of leaves= " + leaves.size());
		        System.out.println("num of triples= " + triples.size());
		        createHelpGraphFrame();
		        rootedTree = build.build(triples, leaves, new Integer(-1));
		        resultGraph = rootedTree.getFirst();

		        operations = build.getChanges();
		        System.out.println("fertig mit build");
				drawPanel.changeGraph(resultGraph, "undirected");
		        visualizeAlgorithmBuild();
		        /*
		        ArrayList<Integer> v = graph.getVertices();
		        System.out.println("number of vertices of result: "+ v.size());
		        drawPanel.changeGraph(graph, typeOfGraph);
		        Graphics g = drawPanel.getGraphics();
		        drawPanel.drawCompleteGraph(g, Color.CYAN, Color.BLACK);
		        System.out.println("I'm here");
		        System.out.println("root= "+ rootedTree.getSecond());
		        */
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

