package com.androdome.platform;


public class GameTick extends Thread{
	MainFrame frame;
	Object NULL = null;
	static boolean running = true;
	public static boolean cycle = false;
	int animtick = 0;
	int dropTick = 0;
	public static int deadCount = 0;
	public static boolean drawIntroScreen = false;
	public GameTick(MainFrame frame)
	{
		this.frame = frame;
		
	}
	public void run()
	{
		while(running)
		{
			
			try {
				Thread.sleep(16);
				if(drawIntroScreen)
				{
					drawIntroScreen();
					drawIntroScreen = false;
				}
				animtick++;
				if(animtick >= 8)
				{
					cycle = !cycle;
					frame.gamepanel.animate();
					animtick = 0;
				}
				frame.gamepanel.repaint();
				if(frame.running)
				{
					if(frame.player.dead)
					{
						deadCount++;
					}
					frame.player.onGround = false;
					if(frame.player.right && !frame.player.dead)
					{
						if(frame.player.velocity.x < 3)
							frame.player.velocity.x++;
					}
					else if(frame.player.left && !frame.player.dead)
					{
						if(frame.player.velocity.x > -3)
							frame.player.velocity.x--;
					}
					if(frame.player.location.y > frame.level.bricks[0].length*16 && !frame.player.dead)
					{
						frame.player.dead = true;
						frame.player.velocity.x = 0;
						frame.player.velocity.y = -10;
					}
					
					
					
					dropTick++;
					if(dropTick == 4 && (frame.player.jump || frame.player.dead))
					{
					frame.player.velocity.y++;
					dropTick = 0;
					}
					else if(!(frame.player.jump || frame.player.dead))
					{
						frame.player.velocity.y++;
						dropTick = 2;
					}
					
					frame.player.location.x+=frame.player.velocity.x;
					frame.player.location.y+=frame.player.velocity.y;
					if(!frame.player.dead)
					{
						//Collision check cycle
						if(frame.gamepanel.hitRect(frame.player.location.x+2, frame.player.location.y+1, 12 , 31))
						{
							frame.player.onGround = true;
							frame.player.location.y-=frame.player.velocity.y;
							if(frame.gamepanel.hitRect(frame.player.location.x+2, frame.player.location.y+1, 12 , 31))
							{
								frame.player.location.y--;
							}
							frame.player.velocity.y = 0;
						}
						if(frame.gamepanel.hitRect(frame.player.location.x+1, frame.player.location.y+2, 14, 28))
						{
							frame.player.location.x-=frame.player.velocity.x;
							frame.player.velocity.x = 0;
						}
					}
					
					
					//Friction
					if(frame.player.onGround && frame.player.velocity.x != 0 && !(frame.player.left || frame.player.right))
					{
						if(frame.player.velocity.x > 0)
						{
							frame.player.velocity.x--;
						}
						else
						{
							frame.player.velocity.x++;
						}
					}
					
					double scalesize = frame.gamepanel.getHeight()/GamePanel.scalefactor;
					if((frame.player.location.x+frame.level.relativePoint.x)*scalesize > frame.gamepanel.getWidth()/1.5)
					{
						frame.level.relativePoint.x-=3;
					}
					if((frame.player.location.x+frame.level.relativePoint.x)*scalesize <  frame.gamepanel.getWidth()*0.25 && frame.level.relativePoint.x+3 <= 0)
					{
						frame.level.relativePoint.x+=3;
					}
					
					/*
					if(frame.gamepanel.hitDetect(frame.player.location.x, frame.player.location.y+31, frame.player.location.x, frame.player.location.y + 34) == null && frame.gamepanel.hitDetect(frame.player.location.x+16, frame.player.location.y+31, frame.player.location.x+16, frame.player.location.y + 34) == null)
					{	
						dropTick++;
						if(dropTick == 4 && (frame.player.jump || frame.player.dead))
						{
						frame.player.velocity.y++;
						dropTick = 0;
						}
						else if(!(frame.player.jump || frame.player.dead))
						{
							frame.player.velocity.y++;
							dropTick = 2;
						}
					}
					else
					{
						frame.player.onGround = true;
					}
					Point newLoc = new Point(frame.player.location.x + frame.player.velocity.x, frame.player.location.y + frame.player.velocity.y);
					XYDPoint point1 = frame.gamepanel.hitDetect(frame.player.location.x+1, frame.player.location.y, newLoc.x+1, newLoc.y);
					XYDPoint point2 = frame.gamepanel.hitDetect(frame.player.location.x+15, frame.player.location.y, newLoc.x+15, newLoc.y);
					XYDPoint point3 = frame.gamepanel.hitDetect(frame.player.location.x+15, frame.player.location.y+31, newLoc.x+15, newLoc.y+31);
					XYDPoint point4 = frame.gamepanel.hitDetect(frame.player.location.x+1, frame.player.location.y+31, newLoc.x+1, newLoc.y+31);
					if(point1 != null || point2 != null || point3 != null || point4 != null)
					{
						XYDPoint[] pArray = new XYDPoint[]{point1, point2, point3, point4};
						if(point1 != null && point1.isMinimum(pArray))
						{
							frame.player.location = new Point(point1.x - 1, point1.y);
						}
						else if(point2 != null && point2.isMinimum(pArray))
						{
							frame.player.location = new Point(point2.x-15, point2.y);
						}
						else if(point3 != null && point3.isMinimum(pArray))
						{
							frame.player.location = new Point(point3.x-15, point3.y-31);
						}
						else if(point4 != null && point4.isMinimum(pArray))
						{
							frame.player.location = new Point(point4.x-1, point4.y-31);
						}
						frame.player.velocity.y = 0;
						frame.player.velocity.x = 0;
						frame.player.onGround = true;
					}
					else
					{
						frame.player.location.y += frame.player.velocity.y;
						frame.player.location.x += frame.player.velocity.x;
						if(frame.player.onGround)
						{
							if(frame.player.velocity.x > 0)
							{
								frame.player.velocity.x--;
							}
							else if(frame.player.velocity.x < 0)
							{
								frame.player.velocity.x++;
							}
						}
					}
					*/
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void drawIntroScreen() {
		try {
			int i = 0;
			frame.gamepanel.drawingIntro = true;
			while(i < 100)
			{
				i+=2;
				frame.gamepanel.loc = i;
				frame.gamepanel.repaint();
				Thread.sleep(10L);
				
			}
			frame.gamepanel.gameOverOverlay = 0;
			deadCount = 0;
			frame.player.dead = false;
			frame.player.location = frame.level.playerStart;
			frame.player.velocity.x = 0;
			frame.player.velocity.y = 0;
			Thread.sleep(2000);
			while(i < 200)
			{
				i+=2;
				frame.gamepanel.loc = i;
				frame.gamepanel.repaint();
				Thread.sleep(10L);
				
			}
			Thread.sleep(20);
			frame.gamepanel.drawingIntro = false;
			frame.gamepanel.loc = 0;
			frame.gamepanel.repaint();
			
		} catch (InterruptedException e) {
			frame.gamepanel.loc = 0;
			frame.gamepanel.drawingIntro = false;
			e.printStackTrace();
		}
		
	}

}
