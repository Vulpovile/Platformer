package com.androdome.platform.bricks;
import java.awt.Polygon;
import java.io.Serializable;

import com.androdome.platform.player.Player;


public class Brick implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int type;
	public String img;
	public boolean collides = true;
	public Polygon collisionMap;
	public Brick()
	{
		type = 0;
		img = "brick.png";
	}
	public void animate()
	{
		
	}
	
	
	public Polygon getCollisionMap()
	{
		if(collisionMap == null)
		{
			collisionMap = new Polygon();
			collisionMap.addPoint(0, 0);
			collisionMap.addPoint(16, 0);
			collisionMap.addPoint(16, 16);
			collisionMap.addPoint(0, 16);
		}
		return collisionMap;
	}
	

}
