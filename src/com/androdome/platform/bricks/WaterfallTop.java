package com.androdome.platform.bricks;

import java.awt.Point;
import java.util.ArrayList;

public class WaterfallTop extends Brick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int index;
	public static boolean tick = true;
	public WaterfallTop()
	{
		type = 30;
		img = "waterfalltop.png";
	}
	
	public ArrayList<Point> getCollisionMap()
	{
		return null;
	}
}
