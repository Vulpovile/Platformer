package com.androdome.platform.bricks;

import java.awt.Point;
import java.util.ArrayList;

public class HillWallRight extends Brick {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HillWallRight()
	{
		type = 7;
		img = "hillup3.png";
	}
	public ArrayList<Point> getCollisionMap()
	{
		return null;
	}
}
