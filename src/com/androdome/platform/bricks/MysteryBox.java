package com.androdome.platform.bricks;

import com.androdome.platform.GameTick;

public class MysteryBox extends Brick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int index;
	boolean desc = false;
	public static boolean tick = true;
	public MysteryBox()
	{
		type = 9;
		index = type;
		img = "mystery1.png";
		obtainable = true;
	}
	
	public void animate()
	{
		if(tick != GameTick.cycle)
		{
			tick = GameTick.cycle;
			if(!desc)
				type++;
			else
				type--;
			if(type > 11)
			{
				this.type = 10;
				desc = true;
			}
			if(type < 9)
			{
				type = 10;
				desc = false;
			}
			else if(type == 10)
			{
				img = "mystery2.png";
			}
			else if(type == 11)
			{
				img = "mystery3.png";
			}
		}
			
	}
}
