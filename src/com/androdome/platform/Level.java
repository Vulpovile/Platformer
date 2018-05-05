package com.androdome.platform;
import java.awt.Point;
import java.io.Serializable;

import com.androdome.platform.bricks.Brick;


public class Level implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Brick[][] bricks = new Brick[512][64];
	public Brick[][] bg1 = new Brick[512][64];
	public Brick[][] bg2 = new Brick[512][64];
	Point relativePoint = new Point(0,(-(64-16))*16);
}
