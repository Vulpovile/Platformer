package com.androdome.platform.bricks;

import java.awt.Point;
import java.util.ArrayList;

public class HillWallLeft extends Brick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HillWallLeft()
	{
		type = 5;
		img = "hillup1.png";
	}
	public ArrayList<Point> getCollisionMap()
	{
		return null;
	}
}
