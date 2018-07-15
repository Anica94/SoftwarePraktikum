import java.awt.Color;
import java.awt.Graphics;

/**
 * This is a class that creates and draws circles (for the vertices of a graph).
 * A circle contains a name, an x- and y-coordinate and a radius.
 * 
 * @author Anica
 */

public class Circle {
	/**
	 * Containers for the name, x- and y-coordinate and radius of the circle.
	 */
	private Integer name;
	private int x, y, r;
	
	/**
	 * Constructor of the class which creates a new circle with name <tt>name<tt>, radius <tt>r<tt>,
	 * x-coordinate <tt>x<tt> and y-coordinate <tt>y<tt>.
	 * @param name
	 * @param r
	 * @param x
	 * @param y
	 */
	public Circle(Integer name, int r, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	/**
	 * Draws a circle in the specified color.
	 * @param g graphical object.
	 * @param c color in which the circle should be drawn.
	 */
	public void draw(Graphics g, Color c) {
		g.setColor(c);
		g.fillOval(x-r, y-r, r*2, r*2);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(name), x-3, y+3);
	}
}
