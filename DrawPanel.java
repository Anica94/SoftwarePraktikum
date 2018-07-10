import java.awt.BasicStroke;
import java.awt.BorderLayout;

/**
 * 
 * 
 * @author Anica
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	public Graph graph;
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
	private int width, height;
	
	private int mouseX, mouseY;
	private JLabel lblMouseCoords;
	
	public DrawPanel(int width, int height) {
		this.width = width;
		this.height = height;
		graph = new UndirectedGraph();
		vertexCoordinates = new HashMap<Integer, Pair<Integer, Integer> >();
		coordinates = new Pair<Integer, Integer>(0, 0);
		coordinates2 = new Pair<Integer, Integer>(0, 0);
		startpoint = -1;
		vertexColor = Color.BLUE;
		edgeColor = Color.BLACK;
	
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				/*
				 * move
				 */
				if(GUI.getStatusNumber()==12) {
					if(vertex != -1) {
						changeCoordinates(vertex, e.getX(), e.getY());
					}
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
		
		//lblMouseCoords = new JLabel("TEST");
		//this.add(lblMouseCoords, BorderLayout.NORTH);
		
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
					if(vertex != -1) {
						if(startpoint == -1) {
	            			startpoint = vertex;
	            		}
	            		else {
	            			if(startpoint != vertex) {
	            				addEdge(startpoint, vertex);
	            			startpoint = -1;
	            			}
	            		}
					}
            		break;
        		/*
        		 * delete vertex
        		 */
				case 10:
					vertex = findVertex(mouseX, mouseY);
					if(vertex != -1) {
						graph.deleteVertex(vertex);
					}
            		break;
        		/*
        		 * delete edge
        		 */
				case 11: 
					vertex = findVertex(mouseX, mouseY);
					if(vertex != -1) {
						if(startpoint == -1) {
	            			startpoint = vertex;
	            		}
	            		else {
	            			graph.deleteEdge(startpoint, vertex);
	            			startpoint = -1;
	            		}
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
		//double distance = Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2), 2));
		double distance = Math.hypot((x1-x2), (y1-y2));
		return distance;
	}
	
	/**
	 * Finds a vertex at given coordinates.
	 * 
	 * @param x1 x-coordinate 
	 * @param y2 y-coordinate 
	 */
	public Integer findVertex(int x1, int y1) {
		Integer v = -1;
		double distance;
		double shortestDistance = 100;
		vertices = graph.getVertices();
		for(int i=0; i<vertices.size(); i++) {
			vertex = vertices.get(i);
			coordinates = vertexCoordinates.get(vertex);
			x = coordinates.getFirst();
			y = coordinates.getSecond();
			distance = distance(x1, y1, x, y);
			if(distance < 20) {
				if(distance < shortestDistance) {
					v = vertex;
				}
			}
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
	 * Changes the actual graph (if a graph is read from a file or deleted).
	 * 
	 * @param graph the new graph.
	 * @param typeOfGraph the type of the new graph.
	 */
	public void setGraph(Graph graph, String typeOfGraph) {
		this.graph = graph;
		this.typeOfGraph = typeOfGraph;
		vertexCoordinates.clear();
		if(!graph.getVertices().isEmpty() && vertexCoordinates.isEmpty()) {
			createDefaultCoordinates();
			for (Pair<Integer, Integer> p : vertexCoordinates.values()) {
				assert(p != null);
			}
		}
	}
	
	/**
	 * Creates default coordinates for a graph which is read from a file.
	 */
	public void createDefaultCoordinates() {
		vertices = graph.getVertices();
		for(int i=0; i<vertices.size(); i++) {
			vertex = vertices.get(i);
			int x = (int)(Math.random() * ((width-20))+10);
			int y = (int)(Math.random() * ((height-20)+10));
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
	
	/**
	 * Draws the complete graph.
	 * 
	 * @param g graphical object
	 * @param vertexColor color in which the vertices should be drawn.
	 * @param edgeColor color in which the edges should be drawn.
	 */
	public void drawCompleteGraph(Graphics g, Color vertexColor, Color edgeColor) {
		super.paintComponent(g);
		Integer vertex;
		Pair<Integer, Integer> coordinates= new Pair<Integer, Integer>(null, null);
		vertices = graph.getVertices();
		for(int i=0; i<vertices.size(); i++) {
			vertex = vertices.get(i);
			assert(vertexCoordinates.containsKey(vertex));
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
	
	/**
	 * Draws the specified vertex in the specified color.
	 * 
	 * @param vertexName name of the vertex that should be drawn.
	 * @param color
	 */
	public void drawVertex(Integer vertexName, Color color) {
		Graphics g = this.getGraphics();	
		Pair<Integer, Integer> coordinates;
		coordinates = vertexCoordinates.get(vertexName);
		x = coordinates.getFirst();
		y = coordinates.getSecond();
		c = new Circle(vertexName, 10, x, y);
		c.draw(g, color);
	}
	
	/**
	 * Draws the specified edge in the specified color.
	 * 
	 * @param vertexNameStart start-vertex of the edge to be drawn.
	 * @param vertexNameEnd end-vertex of the edge to be drawn.
	 * @param color
	 */
	public void drawEdge(Integer vertexNameStart, Integer vertexNameEnd, Color color) {
		Graphics g = this.getGraphics();
		Pair<Integer, Integer> coordinates1;
		Pair<Integer, Integer> coordinates2;
		int x1, x2, y1, y2;
		coordinates1 = vertexCoordinates.get(vertexNameStart);
		x1 = coordinates1.getFirst();
		y1 = coordinates1.getSecond();
		coordinates2 = vertexCoordinates.get(vertexNameEnd);
		x2 = coordinates2.getFirst();
		y2 = coordinates2.getSecond();
		g.setColor(color);
		if(!color.equals(Color.BLACK)) {
			Graphics2D g2 = (Graphics2D) g;
		    g2.setStroke(new BasicStroke(2));
		    g2.drawLine(x1, y1, x2, y2);
		}
		else {
			g.drawLine(x1, y1, x2, y2);
		}
	}
		
	public void drawEdge(Integer vertexNameStart, Integer vertexNameEnd, Integer edgeWeight, Color color) {
		Graphics g = this.getGraphics();
		Pair<Integer, Integer> coordinates1;
		Pair<Integer, Integer> coordinates2;
		int x1, x2, y1, y2;
		coordinates1 = vertexCoordinates.get(vertexNameStart);
		x1 = coordinates1.getFirst();
		y1 = coordinates1.getSecond();
		coordinates2 = vertexCoordinates.get(vertexNameEnd);
		x2 = coordinates2.getFirst();
		y2 = coordinates2.getSecond();
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
		g.drawString(String.valueOf(edgeWeight), 1/2*(x1+x2), 1/2*(y1+y2));
	}
	
	/**
	 * Empties the drawpanel
	 * 
	 * @param g graphical object
	 */
	public void emptyDrawPanel(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
    public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		drawCompleteGraph(g, vertexColor, edgeColor);
	}
	
}
