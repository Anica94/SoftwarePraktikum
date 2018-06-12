import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	private Integer name;
	private int x, y, r;
	
	public Circle(Integer name, int r, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(x-r, y-r, r*2, r*2);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(name), x-3, y+3);
	}
}
