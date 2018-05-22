package com.androdome.platform.bricks;

import java.awt.Point;
import java.util.ArrayList;

import com.androdome.platform.GameTick;

public class Waterfall extends Brick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static int index;
	public static boolean tick = true;
	public Waterfall()
	{
		type = 26;
		img = "waterfall1.png";
	}
	
	public void animate()
	{
		if(tick != GameTick.cycle)
		{
			tick = GameTick.cycle;
			type++;
			if(type >= 30)
				type = 26;
			img = "waterfall" + (type-25) + ".png"; 
		}	
	}
	
	public ArrayList<Point> getCollisionMap()
	{
		return null;
	}
}
