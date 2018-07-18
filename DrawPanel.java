package AlgraviS;
import java.awt.BasicStroke;
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
import javax.swing.JPanel;

/**
 * This is a drawpanel. It is used for drawing, editing and displaying a graph.
 * 
 * @author Anica
 */

public class DrawPanel extends JPanel {
	/**
	 * Containers for the width and height of the drawpanel.
	 */
	private int width, height;
	
	/**
	 * Containers for the graph.
	 */
	public Graph graph;
	private String typeOfGraph;
	private ArrayList<Integer> vertices;
	private Integer vertex;
	
	/**
	 * Containers for the vertex coordinates.
	 */
	private HashMap<Integer, Pair<Integer, Integer> > vertexCoordinates;
	private Pair<Integer, Integer> coordinates;
	private Pair<Integer, Integer> coordinates2;
	private int x, y, x2, y2;
	/**
	 * Containers for the mouse coordinates
	 */
	private int mouseX, mouseY;
	
	private Integer newVertex;
	private Integer startpoint;
	private Circle c;
	private int status;
	public Color vertexColor;
	public Color edgeColor;
	
	/**
	 * Constructor of the class. It creates a drawpanel with width <tt>width</tt> and height <tt>height</tt>.
	 * @param width width of the created drawpanel.
	 * @param height height of the created drawpanel.
	 */
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

			/*
			 * The mouse coordinates are always saved whenever the mouse is moved.
			 */
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
		});		
		
		this.addMouseListener(new MouseAdapter() {

			/**
			 * Defines what happens when the mouse is pressed in the drawpanel at a certain status.
			 */
            @Override
            public void mousePressed(MouseEvent e) {
            	status = GUI.getStatusNumber();
            	switch (status) {
            	/**
            	 * While the status is "add vertex", pressing the mouse adds a vertex to the graph 
            	 * with current mouse coordinates and draws it.
            	 */
				case 7:
					newVertex = graph.addVertex();
            		coordinates = new Pair<Integer, Integer>(mouseX, mouseY);
            		vertexCoordinates.put(newVertex, coordinates);
					break;
				/**
            	 * While the status is "add edge" , pressing the mouse on two vertices (one after the other)
            	 * adds an edge between these vertices to the graph and draws it.
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
        		/**
        		 * While the status is "delete vertex", pressing the mouse deletes the vertex
        		 * at this coordinates (and incident edges) from the graph.
        		 */
				case 10:
					vertex = findVertex(mouseX, mouseY);
					if(vertex != -1) {
						graph.deleteVertex(vertex);
					}
            		break;
        		/**
        		 * While the status is "delete edge" , pressing the mouse on two vertices (one after the other)
            	 * deletes the edge between these vertices from the graph.
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
        		/**
        		 * While the status is "move", pressing the mouse on a vertex safes the vertex at this 
        		 * coordinates and moves it while dragging the mouse.
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
        });
	}
	
	/**
	 * Computes the distance of two points (with x and y coordinates).
	 * 
	 * @param x1 x-coordinate of the first point.
	 * @param y1 y-coordinate of the first point.
	 * @param x2 x-coordinate of the second point.
	 * @param y2 y-coordinate of the second point.
	 * 
	 * @return distance of the vertices with specified coordinates.
	 */
	private double distance(int x1, int y1, int x2, int y2){
		double distance = Math.hypot((x1-x2), (y1-y2));
		return distance;
	}
	
	/**
	 * Finds a vertex near given coordinates.
	 * 
	 * @param x1 x-coordinate 
	 * @param y1 y-coordinate 
	 * 
	 * @return name of the vertex at the specified coordinates if one exists; otherwise -1.
	 */
	private Integer findVertex(int x1, int y1) {
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
	 * Changes the actual graph (if a graph is read from a file or deleted) and repaints it.
	 * 
	 * @param graph the new graph.
	 * @param typeOfGraph the type of the new graph.
	 */
	public void changeGraph(Graph graph, String typeOfGraph) {
		this.graph = graph;
		this.typeOfGraph = typeOfGraph;
		vertexCoordinates.clear();
		if(!graph.getVertices().isEmpty() && vertexCoordinates.isEmpty()) {
			createDefaultCoordinates();
		}
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
	 * Creates default coordinates for a graph which is not drawn.
	 */
	private void createDefaultCoordinates() {
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
	private void changeCoordinates(Integer vertex, int x, int y) {
		coordinates = new Pair<Integer, Integer>(x, y); 
		vertexCoordinates.replace(vertex, coordinates);
	}	
	
	/**
	 * Draws the complete graph with given vertex- and edge colors.
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
	 * @param color color in which the vertex should be drawn.
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
	 * @param color color in which the line should be drawn.
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
		
	/**
	 * Draws the specified edge with edge weight in the specified color.
	 * (Not yet used.)
	 * 
	 * @param vertexNameStart start-vertex of the edge to be drawn.
	 * @param vertexNameEnd end-vertex of the edge to be drawn.
	 * @param edgeWeight weight of the edge to be drawn.
	 * @param color color in which the line should be drawn.
	 */
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
	 * Clears the drawpanel.
	 * 
	 * @param g graphical object
	 */
	public void emptyDrawPanel(Graphics g) {
		super.paintComponent(g);
	}
	
	/**
	 * Paintcomponent of the drawpanel.
	 * Clears the drawpanel and draws the current graph.
	 */
	@Override
    protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		drawCompleteGraph(g, vertexColor, edgeColor);
	}
	
}
