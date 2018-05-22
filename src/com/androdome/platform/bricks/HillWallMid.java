package com.androdome.platform.bricks;

import java.awt.Point;
import java.util.ArrayList;

public class HillWallMid extends Brick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HillWallMid()
	{
		type = 6;
		img = "hillup2.png";
	}
	
	public ArrayList<Point> getCollisionMap()
	{
		return null;
	}
}
