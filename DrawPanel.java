import java.awt.BorderLayout;

/**
 * 
 * 
 * @author Anica
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.Iterator;

public class DrawPanel extends JPanel {
	private Graph graph;
	private String typeOfGraph;
	private ArrayList<Integer> vertices;
	private Integer vertex;
	private int x, y, x2, y2;
	private HashMap<Integer, Pair<Integer, Integer> > vertexCoordinates;
	private Pair<Integer, Integer> coordinates;
	private Pair<Integer, Integer> coordinates2;
	private Integer newVertex;
	private Integer startpoint;
	private Circle c;
	private int status;
	public Color vertexColor;
	public Color edgeColor;
	
	private int mouseX, mouseY;
	private JLabel lblMouseCoords;
	
	public DrawPanel() {
		//this.graph = graph;
		//this.typeOfGraph = typeOfGraph;
		graph = new UndirectedGraph();
		vertexCoordinates = new HashMap<Integer, Pair<Integer, Integer> >();
		coordinates = new Pair<Integer, Integer>(0, 0);
		coordinates2 = new Pair<Integer, Integer>(0, 0);
		startpoint = null;
		vertexColor = Color.BLUE;
		edgeColor = Color.BLACK;
/*
		System.out.println("number of vertices " + graph.getVertices().size());
		if(!graph.getVertices().isEmpty() && vertexCoordinates.isEmpty()) {
			createDefaultCoordinates();
		}*/
		//repaint();
		
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				/*
				 * move
				 */
				if(GUI.getStatusNumber()==12) {
					//vertex = findVertex(x, y);
					changeCoordinates(vertex, e.getX(), e.getY());
					//coordinates = new Pair<Integer, Integer>(mouseX, mouseY);
					//vertexCoordinates.replace(vertex, coordinates);
				}
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				//lblMouseCoords.setText("coords: (" + mouseX + ", " + mouseY + ")");
				//lblMouseCoords.repaint();
			}
			
		});		
		
		lblMouseCoords = new JLabel("TEST");
		this.add(lblMouseCoords, BorderLayout.NORTH);
		
		this.addMouseListener(new MouseAdapter() {
        //    private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
            	status = GUI.getStatusNumber();
            	switch (status) {
            	/*
            	 * add vertex
            	 */
				case 7:
					//System.out.println("case addVertex");
					newVertex = graph.addVertex();
					//System.out.println("newVertex = "+ newVertex);
					//System.out.println("number of vertices " + graph.getVertices().size());
            		coordinates = new Pair<Integer, Integer>(mouseX, mouseY);
            		vertexCoordinates.put(newVertex, coordinates);
            		//System.out.println("vertexCoordniates.size() = " + vertexCoordinates.size());
					break;
				/*
            	 * add edge by drawing
            	 */
				case 8:
					vertex = findVertex(mouseX, mouseY);
            		if(startpoint == null) {
            			startpoint = vertex;
            		}
            		else {
            			addEdge(startpoint, vertex);
            			startpoint = null;
            		}
            		break;
        		/*
        		 * delete vertex
        		 */
				case 10:
					vertex = findVertex(mouseX, mouseY);
            		graph.deleteVertex(vertex);
            		break;
        		/*
        		 * delete edge
        		 */
				case 11: 
					vertex = findVertex(mouseX, mouseY);
            		if(startpoint == null) {
            			startpoint = vertex;
            		}
            		else {
            			graph.deleteEdge(startpoint, vertex);
            			startpoint = null;
            		}
            		break;
        		/*
        		 * move
        		 */
				case 12:
					x = mouseX;
            		y = mouseY;
            		vertex = findVertex(x, y);
            		break;
				default:
					break;
				}            	
            	repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
	}
	
	/**
	 * Computes the distance of two points (with x and y coordinates).
	 * 
	 * @param x1 x-coordinate of the first point.
	 * @param y1 y-coordinate of the first point.
	 * @param x2 x-coordinate of the second point.
	 * @param y2 y-coordinate of the second point.
	 */
	public double distance(int x1, int y1, int x2, int y2){
		double distance = Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2), 2));
		return distance;
	}
	
	/**
	 * Finds a vertex at given coordinates.
	 * 
	 * @param x1 x-coordinate 
	 * @param y2 y-coordinate 
	 */
	public Integer findVertex(int x1, int y1) {
		Integer v = null;
		double distance;
		double shortestDistance = 100;
		vertices = graph.getVertices();
		for(int i=0; i<vertices.size(); i++) {
			vertex = vertices.get(i);
			coordinates = vertexCoordinates.get(vertex);
			x = coordinates.getFirst();
			y = coordinates.getSecond();
			distance = distance(x1, y1, x, y);
			if(distance < 15) {
				if(distance < shortestDistance) {
					v = vertex;
				}
			}
		}
		if(v == null) {
			throw new NullPointerException();
		}
		return v;
	}
	
	/**
	 * Adds an edge to the graph.
	 * 
	 * @param vertexNameStart name of the startpoint of the edge to be added.
	 * @param vertexNameEnd name of the endpoint of the edge to be added.
	 */
	public void addEdge(Integer vertexNameStart, Integer vertexNameEnd) {
		graph.addEdge(vertexNameStart, vertexNameEnd);
		repaint();
	}

	/**
	 * Changes the actual graph (if a graph is read from a file or deleted).
	 * 
	 * @param graph the new graph.
	 * @param typeOfGraph the type of the new graph.
	 */
	public void changeGraph(Graph graph, String typeOfGraph) {
		this.graph = graph;
		this.typeOfGraph = typeOfGraph;
		//System.out.println("vertexCoordniates.size() = " + vertexCoordinates.size());
		vertexCoordinates.clear();
		//System.out.println("number of vertices " + this.graph.getVertices().size());
		//System.out.println("vertexCoordniates.size() = " + vertexCoordinates.size());
		if(!graph.getVertices().isEmpty() && vertexCoordinates.isEmpty()) {
			createDefaultCoordinates();
		}
		//System.out.println("vertexCoordniates.size() = " + vertexCoordinates.size());
		repaint();
	}
	
	/**
	 * Creates default coordinates for a graph which is read from a file.
	 */
	public void createDefaultCoordinates() {
		vertices = graph.getVertices();
		for(int i=0; i<vertices.size(); i++) {
			vertex = vertices.get(i);
			int x = (int)(Math.random() * ((784) + 1));
			int y = (int)(Math.random() * ((514) + 1));
			coordinates = new Pair<Integer, Integer>(x, y);
			vertexCoordinates.put(vertex, coordinates);
		}
	}
	
	/**
	 * Changes the coordinates of a given vertex.
	 * 
	 * @param vertex name of the vertex of which the coordinates should be changed.
	 * @param x new x-coordinate of the vertex.
	 * @param y new y-coordinate of the vertex.
	 */
	public void changeCoordinates(Integer vertex, int x, int y) {
		coordinates = new Pair<Integer, Integer>(x, y); 
		vertexCoordinates.replace(vertex, coordinates);
	}
/*	
	private void drawPoint(Integer vertexName, int x, int y) {
        // Neues Grafik-Objekt aus dem altem Erzeugen und darauf dann die
        // Grafik-Funktionen nutzen.
		
		int r = 10;
        Graphics g = this.getGraphics();
        //g.fillOval(x, y, 3, 3);
        g.setColor(Color.BLUE);
		g.fillOval(x-r, y-r, r*2, r*2);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(vertexName), x-3, y+3);
		repaint();
    }
*/	
	
	public void drawCompleteGraph(Graphics g, Color vertexColor, Color edgeColor) {
		Integer vertex;
		Pair<Integer, Integer> coordinates;
		vertices = graph.getVertices();
		//System.out.println("number of vertices = " + vertices.size());
		for(int i=0; i<vertices.size(); i++) {
			vertex = vertices.get(i);
			coordinates = vertexCoordinates.get(vertex);
			x = coordinates.getFirst();
			y = coordinates.getSecond();
			g.setColor(edgeColor);
			TreeMap<Integer, Integer> startAdjacent =graph.getStartpoints().get(vertex);
			Set<Integer> set = startAdjacent.keySet();
			java.util.Iterator<Integer> itr = set.iterator();
		    while (itr.hasNext()){
		    	coordinates2 = vertexCoordinates.get(itr.next());
		    	x2 = coordinates2.getFirst();
		    	y2 = coordinates2.getSecond();
		    	g.drawLine(x, y, x2, y2);
		    }
			c = new Circle(vertex, 10, x, y);
			c.draw(g, vertexColor);
		}
	}
	
	@Override
    protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		drawCompleteGraph(g, vertexColor, edgeColor);
	}
	
}
