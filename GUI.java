import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Color;



public class GUI {

	private JFrame frame;
	private static Status status;
	private static DrawPanel drawPanel;
	private Reader reader;
	private Graph graph;
	private String typeOfGraph;

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
		status = new Status();
		reader = new Reader();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("GraphGUI");
		//frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JCheckBox chckbxUndirected = new JCheckBox("undirected");
		chckbxUndirected.setSelected(true);
		chckbxUndirected.setBounds(700, 0, 95, 15);
		frame.getContentPane().add(chckbxUndirected);
		
		JCheckBox chckbxDirected = new JCheckBox("directed");
		chckbxDirected.setBounds(700, 15, 95, 15);
		frame.getContentPane().add(chckbxDirected);
		
		drawPanel = new DrawPanel();
		drawPanel.setBorder(null);
		drawPanel.setLocation(0,0);
		drawPanel.setSize(784,514);
		frame.getContentPane().add(drawPanel);
		
		JLabel lblStatus = new JLabel(status.getStatus(0));
		lblStatus.setBounds(0, 515, 784, 25);
		frame.getContentPane().add(lblStatus);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(135, 206, 250));
		frame.setJMenuBar(menuBar);
		
		JMenu mnAlgorithm = new JMenu("Algorithm");
		menuBar.add(mnAlgorithm);
		
		JMenuItem mntmBreadthFirstSearch = new JMenuItem("Breadth-First Search");
		mnAlgorithm.add(mntmBreadthFirstSearch);
		mntmBreadthFirstSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(1));
			}
		});
		
		JMenuItem mntmDepthfirstsearch = new JMenuItem("Depth-First Search");
		mnAlgorithm.add(mntmDepthfirstsearch);
		mntmDepthfirstsearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(2));
			}
		});
		
		JMenuItem mntmTopologicalSort = new JMenuItem("Topological Sort");
		mnAlgorithm.add(mntmTopologicalSort);
		mntmTopologicalSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(3));
			}
		});
		
		JMenuItem mntmMinimumSpanningTree = new JMenuItem("Minimum Spanning Tree");
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
			}
		});
		
		JMenuItem mntmBuild = new JMenuItem("BUILD");
		mnAlgorithm.add(mntmBuild);
		mntmBuild.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(6));
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
			}
		});
		
		JMenuItem mntmEdgedraw = new JMenuItem("edge (draw)");
		mnAdd.add(mntmEdgedraw);
		mntmEdgedraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(8));
			}
		});
		
		JMenuItem mntmEdgespecify = new JMenuItem("edge (specify)");
		mnAdd.add(mntmEdgespecify);
		mntmEdgespecify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAddLineFrame();
				lblStatus.setText(status.getStatus(9));
			}
		});
		
		JMenuItem mntmDelete = new JMenuItem("delete");
		mnEdit.add(mntmDelete);
		mntmDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(10));
			}
		});
		
		JMenuItem mntmMove = new JMenuItem("move");
		mnEdit.add(mntmMove);
		mntmMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(11));
			}
		});
		
		//JMenuItem mntmSetVertexProperties = new JMenuItem("set vertex properties");
		//mnEdit.add(mntmSetVertexProperties);
		
		JMenuItem mntmUndo = new JMenuItem("undo");
		mntmUndo.setIcon(new ImageIcon("C:\\Users\\Anica\\eclipse-workspace\\Graph_ST_AH\\img\\Undo-icon.png"));
		mnEdit.add(mntmUndo);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnOpen = new JMenu("open");
		mnFile.add(mnOpen);
		
		JMenuItem mntmNew = new JMenuItem("new");
		mnOpen.add(mntmNew);
		
		JMenuItem mntmFile = new JMenuItem("file");
		mnOpen.add(mntmFile);
		mntmFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblStatus.setText(status.getStatus(12));
				/*
				 * produce open dialog
				 * read file
				 * produce graph
				 */
				String directoryName = "C:\\Users\\Sonja\\eclipse-workspace\\SoftwarePraktikum\\src\\Textfiles";
		        JFileChooser chooser = new JFileChooser(directoryName);
		        if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		        {
		        	String fileName = chooser.getSelectedFile().getName();
		           	try {
					graph = reader.read(directoryName + "\\" + fileName);
					typeOfGraph = reader.typeOfGraph();
				} catch (IOException e1) {
					//System.out.println("Error concerning file:\\n");
					e1.printStackTrace();
				}   
		        }
		}
		});
		
		
		JMenuItem mntmSaveas = new JMenuItem("save (as)");
		mnFile.add(mntmSaveas);
		
		JMenuItem mntmShow = new JMenuItem("show");
		mnFile.add(mntmShow);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("show/ hide names");
		mnView.add(mntmNewMenuItem_1);
		
		JMenuItem mntmShowHideLabel = new JMenuItem("show/ hide label");
		mnView.add(mntmShowHideLabel);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}
	
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
	}
	
	public static int getStatusNumber() {
		return status.getStatusNumber();
	}
}

