package com.androdome.platform;


public class GameTick extends Thread{
	MainFrame frame;
	Object NULL = null;
	static boolean running = true;
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
				if(animtick >= 15)
				{
					frame.gamepanel.animate();
					animtick = 0;
				}
				frame.gamepanel.repaint();
				if(frame.running)
				{
					double scalesize = frame.gamepanel.getHeight()/GamePanel.scalefactor;
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
