
public class Collision 
{
	 private static double nearestX;	// used to approximate what point of the bumper  
     private static double nearestY;  // a ball collided with
     
	public static boolean collide(Man m,Ladder l)
	{
		findImpactPoint(m,l);
		
		if(distance(nearestX, nearestY, m.x+Man.WIDTH/2,m.y+Man.HEIGHT/2)<=25&&nearestY>m.y)
		{
			return true;
		}
		else
			return false;
	}
	
	private static void findImpactPoint(Man m,Ladder l)
    {
        nearestX = l.x;
        nearestY = l.y;
        
        for (int x =  l.x; x <=  l.x + Ladder.WIDTH; x++) 
        {
           updateIfCloser(x, l.y, m);
           updateIfCloser(x, l.y + Ladder.HEIGHT, m);
        }
        for (int y = l.y; y <= l.y + Ladder.HEIGHT; y++)  
        {
           updateIfCloser(l.x, y, m);
           updateIfCloser(l.x + + Ladder.WIDTH, y, m);
        }
     }
        

      private static void updateIfCloser(int x, int y,Man m)
     {
        if(distance(x, y,m.x+Man.WIDTH/2,m.y+Man.HEIGHT/2) < distance(nearestX, nearestY,m.x+Man.WIDTH/2,m.y+Man.HEIGHT/2))
        {
           nearestX = x;
           nearestY = y;
        }
     }
         

      private static double distance(double x1, double y1, double x2, double y2)
     {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
     }	
}
