package com.androdome.platform.bricks;

public class MysteryBox extends Brick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int index;
	int type;
	boolean desc = false;
	public MysteryBox()
	{
		this.type = 9;
		index = type;
		img = "mystery1.png";
		collides = false;
	}
	
	public void animate()
	{
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
