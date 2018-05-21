package com.androdome.platform;

import java.awt.Point;


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
					/*Point playerLocation = frame.gamepanel.getLevelRelativeLocationNoScale((int)((frame.player.location.x+frame.level.relativePoint.x+15)), (int)((frame.player.location.y+frame.level.relativePoint.y+33)));
					if(!frame.gamepanel.collides(playerLocation.x, playerLocation.y) && !frame.gamepanel.collides(playerLocation.x-1, playerLocation.y))
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
						dropTick = 0;
					}
					if((frame.player.velocity.y != 0 || frame.player.velocity.x != 0))
					{
						Point newLocationY = frame.gamepanel.getLevelRelativeLocationNoScale((int)((frame.player.location.x+frame.level.relativePoint.x+15)), (int)((frame.player.location.y+frame.player.velocity.y+frame.level.relativePoint.y+33)));
						Point newLocationX = frame.gamepanel.getLevelRelativeLocationNoScale((int)((frame.player.location.x+frame.player.velocity.x+frame.level.relativePoint.x+16)), (int)((frame.player.location.y+frame.level.relativePoint.y+33)));
						if(frame.player.velocity.x < 0)
						{
							newLocationY = frame.gamepanel.getLevelRelativeLocationNoScale((int)((frame.player.location.x+frame.level.relativePoint.x+17)), (int)((frame.player.location.y+frame.player.velocity.y+frame.level.relativePoint.y+33)));
							newLocationX = frame.gamepanel.getLevelRelativeLocationNoScale((int)((frame.player.location.x+frame.player.velocity.x+frame.level.relativePoint.x-1)), (int)((frame.player.location.y+frame.level.relativePoint.y+33)));
						}
						int top = Math.min(newLocationY.y, playerLocation.y);
						int bottom = Math.max(newLocationY.y, playerLocation.y);
						int left = Math.min(newLocationX.x, playerLocation.x);
						int right = Math.max(newLocationX.x, playerLocation.x);
						Point locy = null;
						Point locy2 = null;
						Point locx = null;
						Point locx2 = null;
						Point locyheadhit = null;
						Point locyheadhit2 = null;
						
						if(frame.player.velocity.y < 0 && !frame.player.dead)
						{
							locy = frame.gamepanel.hitY(newLocationY.x, top, bottom-1);
							locy2 = frame.gamepanel.hitY(newLocationY.x-1, top, bottom-1);
							locyheadhit = frame.gamepanel.hitY(newLocationY.x, top-2, bottom-1);
							locyheadhit2 = frame.gamepanel.hitY(newLocationY.x-1, top-2, bottom-1);
						}
						else if(!frame.player.dead)
						{
							locy2 = frame.gamepanel.hitY(newLocationY.x-1, top, bottom);
							locy = frame.gamepanel.hitY(newLocationY.x, top, bottom);
						}
						
						if(locy == null && locy2 != null)
							locy = locy2;
						if(locyheadhit == null && locyheadhit2 != null)
							locyheadhit = locyheadhit2;
						
						if(frame.player.velocity.x != 0 && !frame.player.dead)
						{
							if(locy != null)
							{
								locx = frame.gamepanel.hitX(newLocationY.y-1, left, right);
								
							}
							else
								locx = frame.gamepanel.hitX(newLocationY.y, left, right);
							locx2 = frame.gamepanel.hitX(newLocationY.y-2, left, right);	
						}
						
						if(locx == null && locx2 != null)
						{
							locx = locx2;
						}
							
						if(locy == null && locx == null && locyheadhit == null)
						{
							frame.player.location.x+=frame.player.velocity.x;
							frame.player.location.y+=frame.player.velocity.y;
							
						}
						else if(locx == null)
						{
							frame.player.location.x+=frame.player.velocity.x;
							frame.player.velocity.y = 0;
							if(locyheadhit == null)
							frame.player.location.y = (int)((locy.y*16))-32;
							frame.player.onGround = true;
							dropTick = 0;
						}
						else if(locy == null && locyheadhit == null)
						{
							frame.player.location.y+=frame.player.velocity.y;
							if(frame.player.velocity.x > 0)
								frame.player.location.x = (int)((locx.x*16))-16;
							else
								frame.player.location.x = (int)((locx.x*16))+17;
							frame.player.velocity.x = 0;
						}
						else
						{
							if(frame.player.velocity.x > 0)
								frame.player.location.x = (int)((locx.x*16))-16;
							else
								frame.player.location.x = (int)((locx.x*16))+17;
							if(locyheadhit == null)
							frame.player.location.y = (int)((locy.y*16))-32;
							frame.player.velocity.x = 0;
							frame.player.velocity.y = 0;
							frame.player.onGround = true;
							dropTick = 0;
						}
					}
					else
					{
						frame.player.velocity.y = 0;
					}
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
					*/
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
					if(frame.gamepanel.hitDetect(frame.player.location.x+8, frame.player.location.y+1, frame.player.location.x+8, frame.player.location.y + 31) != null)
					{
						frame.player.onGround = true;
						frame.player.location.y-=frame.player.velocity.y;
						frame.player.velocity.y = 0;
					}
					if(frame.gamepanel.hitDetect(frame.player.location.x+1, frame.player.location.y+4, frame.player.location.x+15, frame.player.location.y+4) != null)
					{
						frame.player.location.x-=frame.player.velocity.x;
						frame.player.velocity.x = 0;
					}
					else if(frame.gamepanel.hitDetect(frame.player.location.x+1, frame.player.location.y+28, frame.player.location.x+15, frame.player.location.y+28) != null)
					{
						frame.player.location.x-=frame.player.velocity.x;
						frame.player.velocity.x = 0;
					}
					
					
					
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
