import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	private Graph graph;
	private ArrayList<Circle> circles;
	private ArrayList<Line> lines;
	private final int WIDTH = 500;
	private final int HEIGHT = 300;
	
	private int mouseX, mouseY;
	private JLabel lblMouseCoords;
	
	public DrawPanel() {
		graph = new UndirectedGraph();
		circles = new ArrayList<Circle>();
		lines = new ArrayList<Line>();
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2, false));
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
            	circles.add(new Circle(10, mouseX, mouseY));
            	repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
	}
	
	public void addLine(int cid1, int cid2) {
		if (cid1 < 0 || cid2 < 0 || cid1 >= circles.size() || cid2 >= circles.size()) {
			throw new IllegalArgumentException("Ids must be valid");
		}
		lines.add(new Line(circles.get(cid1), circles.get(cid2)));
		repaint();
	}
	
	
	@Override
    protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		for (Line l: lines) {
			l.draw(g);
		}
		
		int i = 0;
		for (Circle c : circles) {
			c.draw(g);
			g.setColor(Color.WHITE);
			g.drawString(String.valueOf(i), c.getX() - 3, c.getY() + 3);
			i += 1;
		}
	}
}
