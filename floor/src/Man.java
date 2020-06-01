import java.awt.Graphics;

public class Man implements Common
{
	public static int WIDTH=50;
	public static int HEIGHT=50;
	public int x;
	public int y;
	public int dy=15;
	public int dx=10;
	
	
	public Man()
	{
		x=0;
		y=0;
		dx=15;
		dy=10;
	}
	
	public Man(int x,int y)
	{
		this.x=x;
		this.y=y;
		this.dx=dx;
		this.dy=dy;
	}
	
	public void moveX(int dir)
	{
		x+=dx*dir;
	}
	
	public void moveY()
	{
		y+=dy;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(man.getImage(), x, y,WIDTH,HEIGHT,null);
	}

}
