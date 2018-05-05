package com.androdome.platform;


public class GameTick extends Thread{
	MainFrame frame;
	Object NULL = null;
	static boolean running = true;
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
				frame.gamepanel.repaint();
				if(frame.running)
				{
					double scalesize = frame.gamepanel.getHeight()/256.000;
					if(!frame.gamepanel.collides(frame.player.location.x+5, frame.player.location.y-1))
					{
						frame.player.velocity.y += 1;
					}
					
					if(!frame.gamepanel.collides(frame.player.location.x+5, frame.player.location.y+frame.player.velocity.y-1))
					{
						frame.player.location.y += (int)(frame.player.velocity.y/10);
					}
					else
					{
						frame.player.location.y = frame.player.location.y + frame.player.location.y%16;
						frame.player.velocity.y = 0;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
