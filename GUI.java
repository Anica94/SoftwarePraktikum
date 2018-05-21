import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Color;



public class GUI {

	private JFrame frame;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("GraphGUI");
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(0, 515, 784, 25);
		frame.getContentPane().add(lblStatus);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(135, 206, 250));
		frame.setJMenuBar(menuBar);
		
		JMenu mnAlgorithm = new JMenu("Algorithm");
		menuBar.add(mnAlgorithm);
		
		JMenuItem mntmBreadthFirstSearch = new JMenuItem("Breadth-First Search");
		mnAlgorithm.add(mntmBreadthFirstSearch);
		
		JMenuItem mntmDepthfirstsearch = new JMenuItem("Depth-First Search");
		mnAlgorithm.add(mntmDepthfirstsearch);
		
		JMenuItem mntmTopologicalSort = new JMenuItem("Topological Sort");
		mnAlgorithm.add(mntmTopologicalSort);
		
		JMenuItem mntmMaximumSpanningTree = new JMenuItem("Minimum Spanning Tree");
		mnAlgorithm.add(mntmMaximumSpanningTree);
		
		JMenuItem mntmMaximalMatching = new JMenuItem("Maximal Matching");
		mnAlgorithm.add(mntmMaximalMatching);
		
		JMenuItem mntmBuild = new JMenuItem("BUILD");
		mnAlgorithm.add(mntmBuild);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnAdd = new JMenu("add");
		mnEdit.add(mnAdd);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("vertex (draw)");
		mnAdd.add(mntmNewMenuItem);
		
		JMenuItem mntmVertexspecify = new JMenuItem("vertex (specify)");
		mnAdd.add(mntmVertexspecify);
		
		JMenuItem mntmEdgedraw = new JMenuItem("edge (draw)");
		mnAdd.add(mntmEdgedraw);
		
		JMenuItem mntmEdgespecify = new JMenuItem("edge (specify)");
		mnAdd.add(mntmEdgespecify);
		
		JMenuItem mntmDelete = new JMenuItem("delete");
		mnEdit.add(mntmDelete);
		
		JMenuItem mntmMove = new JMenuItem("move");
		mnEdit.add(mntmMove);
		
		JMenuItem mntmSetVertexProperties = new JMenuItem("set vertex properties");
		mnEdit.add(mntmSetVertexProperties);
		
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
}

