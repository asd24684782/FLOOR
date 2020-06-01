import java.awt.Color;
import java.awt.Graphics;

public class Ladder 
{
	public static int WIDTH=150;
	public static int HEIGHT=20;
	
	
	public int x;
	public int y;
	public static int dy=8;
	
	
	public Ladder()
	{
		x=0;
		y=0;
	}
	
	public Ladder(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public void move()
	{
		y-=dy;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
		g.fillRect(x, y, WIDTH, HEIGHT);	
	}
}
