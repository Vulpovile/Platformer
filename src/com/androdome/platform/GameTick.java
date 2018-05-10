package com.androdome.platform;

import com.androdome.platform.bricks.MysteryBox;

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
					@SuppressWarnings("unused")
					double scalesize = frame.gamepanel.getHeight()/GamePanel.scalefactor;
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
