import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import jaco.mp3.player.MP3Player;

public class Panel extends JPanel implements Common
{
	private static final long serialVersionUID = 1L;
	private BufferedImage myImage;
    private Graphics myBuffer;
    JFrame frame=new JFrame();
      
    
    
    private boolean Up = false;
    private boolean Down = false;
    private boolean Right = false;
    private boolean Left = false;
    private boolean Enter = false;
    
    boolean  superMan=false; //無敵模式
    boolean collBoolean=false;
    int scope=0;	//分數  
    int ladderWait=0;
  
    
    float alpha=0.2f;//透明度
    float alphaOffset=1;
    
    
    Thread bgmThread=new Thread(new BGMThread());
    Timer inGameTimer;
    Timer collideTimer;
    Timer starTimer;
    Timer EndTimer;
    
    
    Man player;
    List<Ladder> ladders=new ArrayList<Ladder>();
    
    //------------------------------------------------------------
	public Panel()
	{
		
		new JFrame("floor");
		frame.setSize(BG_WIDTH+10, BG_HEIGHT+10);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
        
        addKeyListener(new Key());
        setFocusable(true);
        
	   	myImage =  new BufferedImage(BG_WIDTH, BG_HEIGHT, BufferedImage.TYPE_INT_RGB);
      	myBuffer = myImage.getGraphics();
        	
      	
      	player=new Man(275,50);
      	
      	collideTimer=new Timer(1,new coll());
      	inGameTimer = new Timer(25,new InGame());
      	starTimer=new Timer(25, new Start());
      	EndTimer=new Timer(25, new End());
      	
      	starTimer.start();
      	
	}
	//------------------------------------------------------------
    public void paintComponent(Graphics g)
    {
 	   g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }
    //---------------------------------------------------------------
    public void drawSquare(Graphics g,int x1,int y1,int width,int height)
    {
    	Graphics2D g2 =(Graphics2D) g;
    	g2.setStroke(new BasicStroke(5.0f));
    	g2.setColor(Color.RED.darker());
    	g2.drawRect(x1, y1, width, height);
    }
    //---------------------------------------------------------------
    class Start implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
       {
        	myBuffer.drawImage(STARTBG.getImage(), 0, 0, BG_WIDTH, BG_HEIGHT, null);//畫背景 
        	      	
        	myBuffer.setFont(new Font("Monospaced", Font.BOLD, 60));
        	
        	myBuffer.setColor(new Color(1,1,1,alpha));
        	
        	alpha = alpha+0.05f*alphaOffset;
        	
        	
        	
        	if(alpha>=1)
        		alphaOffset=-1;
        	else if(alpha<=0.2)
        		alphaOffset=1;
        	
        	
        	
        	myBuffer.drawString("Press ENTER to start", 30 , 600);
        	
        	if(Enter)			//進入遊戲
        	{
        		Enter=false;
        		starTimer.stop();    	
        		inGameTimer.start();
              	collideTimer.start();
              	player=new Man(275,50);
              	ladders.clear();
        	}
        	
        	repaint();
        	
       }

    }
    //----------------------------------------------------------------
    class InGame implements ActionListener
    {
            public void actionPerformed(ActionEvent e)
            {
            	  myBuffer.drawImage(BG.getImage(), 0, 0, BG_WIDTH, BG_HEIGHT, null);//畫背景 
	
            	  scope++;
            	  
	          	  if(Left)
	          		  player.moveX(-1);
	        	  if(Right)
	          		  player.moveX(1);
	          	  if(collBoolean)
	          		  player.moveY();
	        	  
	          	  if(player.y<=0||player.y>=BG_HEIGHT)
	          	  {
	          		  inGameTimer.stop();
	          		  collideTimer.stop();
	          		  EndTimer.start();
	          		  scope/=40;
	          	  }
	          	  
	          	  if(player.x<=0)
	          		  player.x=0;
	          	  if(player.x+50>=600)
	          		  player.x=550;
	          	  
	        	  
	          	  if(ladderWait%20==0)
	          	  {
	          		ladders.add(new Ladder((int) ((Math.random())*420), BG_HEIGHT));
	          	  }
	          	  ladderWait++;

	          	  
	          	  for(int i=0;i<ladders.size();i++)
	          	  {
	          		  ladders.get(i).draw(myBuffer);
	          		  ladders.get(i).move();
	          	  }
	          	  
	          	  
	          	  
	          	 player.draw(myBuffer);
	          	 repaint();
           }
            
    }
    //----------------------------------------------------------------
    class coll implements ActionListener
    {
            public void actionPerformed(ActionEvent e)
            {
	          	  for(int i=0;i<ladders.size();i++)
	          	  {
	          		 if(!Collision.collide(player, ladders.get(i)))
	          		 {
	          			collBoolean=true;
	          		 } 	
	          		 else 
	          		 {
						player.y=ladders.get(i).y-Man.HEIGHT;
					 }
	          	  }
            	repaint();
            }
    }
    //---------------------------------------------------------------
    class End implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
       {
        	myBuffer.drawImage(ENDBG.getImage(), 0, 0, BG_WIDTH, BG_HEIGHT, null);//畫背景 
        	      	
        	myBuffer.setFont(new Font("Monospaced", Font.BOLD, 60));
        	
        	myBuffer.setColor(Color.BLACK);

        	
        	
        	myBuffer.drawString("YOU DIE", 150 , 100);
        	myBuffer.drawString("YOU SCOPE: " + scope, 80 , 320);
        	myBuffer.drawString("Press ENTER", 120 , 600);
        	
        	
        	if(Enter)			//進入遊戲
        	{
        		Enter = false;
        		EndTimer.stop();  	
        		starTimer.start();
        	}
        	
        	repaint();
        	
       }

    }
    //------------------------------------------------------------按鍵觸發
	class Key extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode()==KeyEvent.VK_UP)
				Up=true;
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				Down=true;
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
				Left=true;
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				Right=true;	
			if(e.getKeyCode()==KeyEvent.VK_ENTER)
				Enter=true;	
		}
		
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode()==KeyEvent.VK_UP)
				Up=false;
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				Down=false;
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
				Left=false;
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				Right=false;
			if(e.getKeyCode()==KeyEvent.VK_ENTER)
				Enter=false;	
		}	
		
		public void keyTyped(KeyEvent e)
		{
			if(e.getKeyChar()=='s'||e.getKeyChar()=='S')
			{
				if(superMan)
				{	
					superMan=false;
				}	
				else
				{
					superMan=true;
				}
					
			}
		}
	}
	//-------------------------------------------------------------------
	
  
}
