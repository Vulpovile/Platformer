package com.androdome.platform;

import java.awt.Point;


public class GameTick extends Thread{
	MainFrame frame;
	Object NULL = null;
	static boolean running = true;
	public static boolean cycle = false;
	int animtick = 0;
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
					double scalesize = frame.gamepanel.getHeight()/GamePanel.scalefactor;
					Point playerLocation = frame.gamepanel.getMouseClickLocation((int)((frame.player.location.x+frame.level.relativePoint.x+16)*scalesize), (int)((frame.player.location.y+frame.level.relativePoint.y+33)*scalesize));
					if(!frame.gamepanel.collides(playerLocation.x, playerLocation.y))
					{
						if(animtick % 8 == 0)
						frame.player.velocity.y++;
					}
					else
					{
						frame.player.onGround = true;
					}
					if(frame.player.velocity.y != 0)
					{
						Point newLocation = frame.gamepanel.getMouseClickLocation((int)((frame.player.location.x+frame.player.velocity.x+frame.level.relativePoint.x+16)*scalesize), (int)((frame.player.location.y+frame.player.velocity.y+frame.level.relativePoint.y+33)*scalesize));
						int top = Math.min(newLocation.y, playerLocation.y);
						int bottom = Math.max(newLocation.y, playerLocation.y);
						Point loc;
						if(frame.player.velocity.y < 0)
						{
							loc = frame.gamepanel.hitY(newLocation.x, top, bottom-1);
						}
						else
						{
							loc = frame.gamepanel.hitY(newLocation.x, top, bottom);
						}
						if(loc == null)
						{
							frame.player.location.x+=frame.player.velocity.x;
							frame.player.location.y+=frame.player.velocity.y;
							frame.player.onGround = false;
						}
						else
						{
							frame.player.velocity.y = 0;
							frame.player.location.y += (int)((loc.y/16));
							frame.player.onGround = true;
						}
					}
					else
					{
						frame.player.velocity.y = 0;
						frame.player.onGround = true;
					}
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
