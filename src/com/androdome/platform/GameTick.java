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
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
