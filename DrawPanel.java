import java.awt.BorderLayout;
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
	private UndirectedGraph graph;
	private ArrayList<Integer> vertices;
	private Integer vertex;
	private int x, y, x2, y2;
	private HashMap<Integer, Pair<Integer, Integer> > vertexCoordinates;
	private Pair<Integer, Integer> coordinates;
	private Pair<Integer, Integer> coordinates2;
	private Integer newVertex;
	private Integer startpoint;
	private Circle c;
	
	private int mouseX, mouseY;
	private JLabel lblMouseCoords;
	
	public DrawPanel() {
		graph = new UndirectedGraph();
		vertexCoordinates = new HashMap<Integer, Pair<Integer, Integer> >();
		coordinates = new Pair<Integer, Integer>(0, 0);
		coordinates2 = new Pair<Integer, Integer>(0, 0);
		startpoint = null;

		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if(GUI.getStatusNumber()==11) {
					vertex = findVertex(mouseX, mouseY);
					coordinates = new Pair<Integer, Integer>(mouseX, mouseY);
					vertexCoordinates.replace(vertex, coordinates);
				}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				lblMouseCoords.setText("coords: (" + mouseX + ", " + mouseY + ")");
				lblMouseCoords.repaint();
			}
			
		});		
		
		lblMouseCoords = new JLabel("TEST");
		this.add(lblMouseCoords, BorderLayout.NORTH);
		
		this.addMouseListener(new MouseAdapter() {
        //    private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
            	/*
            	 * add vertex
            	 */
            	if(GUI.getStatusNumber()==7) {
            		newVertex = graph.addVertex();
            		coordinates = new Pair<Integer, Integer>(mouseX, mouseY);
            		vertexCoordinates.put(newVertex, coordinates);
            	}
            	/*
            	 * add edge
            	 */
            	else if(GUI.getStatusNumber()==8){
            		vertex = findVertex(mouseX, mouseY);
            		if(startpoint == null) {
            			startpoint = vertex;
            		}
            		else {
            			addEdge(startpoint, vertex);
            			startpoint = null;
            		}
            	}
            	/*
            	 * delete vertex
            	 */
            	else if(GUI.getStatusNumber()==10) {
            		vertex = findVertex(mouseX, mouseY);
            		graph.deleteVertex(vertex);
            	}
            	repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
	}
	
	/*
	 * computes the distance of two points (with x and y coordinates)
	 */
	public double distance(int x1, int y1, int x2, int y2){
		double distance = Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2), 2));
		return distance;
	}
	
	/*
	 * finding a vertex at given coordinates
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
			if(distance < 10) {
				if(distance < shortestDistance) {
					v = vertex;
				}
			}
		}
//		if(v == null) {
//			throw new NullPointerException();
//		}
		return v;
	}
	
	public void addEdge(Integer cid1, Integer cid2) {
		graph.addEdge(cid1, cid2);
		repaint();
	}
	
	
	@Override
    protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		vertices = graph.getVertices();
		for(int i=0; i<vertices.size(); i++) {
			vertex = vertices.get(i);
			coordinates = vertexCoordinates.get(vertex);
			x = coordinates.getFirst();
			y = coordinates.getSecond();
			g.setColor(Color.BLACK);
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
			c.draw(g);
			
		}
		
	}
}
