package com.androdome.platform;

import java.awt.Point;


public class GameTick extends Thread{
	MainFrame frame;
	Object NULL = null;
	static boolean running = true;
	public static boolean cycle = false;
	int animtick = 0;
	int dropTick = 0;
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
					//Will be used
					frame.player.onGround = false;
					double scalesize = frame.gamepanel.getHeight()/GamePanel.scalefactor;
					Point playerLocation = frame.gamepanel.getLevelRelativeLocation((int)((frame.player.location.x+frame.level.relativePoint.x+16)*scalesize), (int)((frame.player.location.y+frame.level.relativePoint.y+33)*scalesize));
					if(!frame.gamepanel.collides(playerLocation.x, playerLocation.y))
					{
						dropTick++;
						if(dropTick == 8)
						{
						frame.player.velocity.y++;
						dropTick = 0;
						}
					}
					else
					{
						frame.player.onGround = true;
						dropTick = 0;
					}
					if(frame.player.velocity.y != 0 || frame.player.velocity.x != 0)
					{
						Point newLocationY = frame.gamepanel.getLevelRelativeLocation((int)((frame.player.location.x+frame.level.relativePoint.x+16)*scalesize), (int)((frame.player.location.y+frame.player.velocity.y+frame.level.relativePoint.y+33)*scalesize));
						Point newLocationX = frame.gamepanel.getLevelRelativeLocation((int)((frame.player.location.x+frame.player.velocity.x+frame.level.relativePoint.x+16)*scalesize), (int)((frame.player.location.y+frame.level.relativePoint.y+33)*scalesize));
						if(frame.player.velocity.x < 0)
						{
							newLocationX = frame.gamepanel.getLevelRelativeLocation((int)((frame.player.location.x+frame.player.velocity.x+frame.level.relativePoint.x)*scalesize), (int)((frame.player.location.y+frame.level.relativePoint.y+33)*scalesize));
						}
						int top = Math.min(newLocationY.y, playerLocation.y);
						int bottom = Math.max(newLocationY.y, playerLocation.y);
						int left = Math.min(newLocationX.x, playerLocation.x);
						int right = Math.max(newLocationX.x, playerLocation.x);
						Point locy = null;
						Point locx = null;
						if(frame.player.velocity.y < 0)
						{
							locy = frame.gamepanel.hitY(newLocationY.x, top, bottom-1);
						}
						else
						{
							locy = frame.gamepanel.hitY(newLocationY.x, top, bottom);
						}
						if(frame.player.velocity.x != 0)
						{
								locx = frame.gamepanel.hitX(newLocationY.y-2, left, right);		
						}
							
						if(locy == null && locx == null)
						{
							frame.player.location.x+=frame.player.velocity.x;
							frame.player.location.y+=frame.player.velocity.y;
							
						}
						else if(locx == null)
						{
							frame.player.location.x+=frame.player.velocity.x;
							frame.player.velocity.y = 0;
							frame.player.location.y = (int)((locy.y*16))-32;
							frame.player.onGround = true;
							dropTick = 0;
						}
						else if(locy == null)
						{
							frame.player.location.y+=frame.player.velocity.y;
							if(frame.player.velocity.x > 0)
								frame.player.location.x = (int)((locx.x*16))-16;
							else
								frame.player.location.x = (int)((locx.x*16))+16;
							frame.player.velocity.x = 0;
						}
						else
						{
							if(frame.player.velocity.x > 0)
								frame.player.location.x = (int)((locx.x*16))-16;
							else
								frame.player.location.x = (int)((locx.x*16))+16;
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
					if(frame.player.right)
					{
						if(frame.player.velocity.x < 3)
							frame.player.velocity.x++;
					}
					else if(frame.player.left)
					{
						if(frame.player.velocity.x > -3)
							frame.player.velocity.x--;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
