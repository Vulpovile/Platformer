package com.androdome.platform.bricks;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;


public class Brick implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int type;
	public String img;
	public boolean collides = true;
	public ArrayList<Point> collisionMap = null;
	public Brick()
	{
		type = 0;
		img = "brick.png";
	}
	public void animate()
	{
		
	}
	
	
	public ArrayList<Point> getCollisionMap()
	{
		if(collisionMap == null)
		{
			collisionMap = new ArrayList<Point>();
			collisionMap.add(new Point(0,0));
			collisionMap.add(new Point(17,0));
			collisionMap.add(new Point(17,17));
			collisionMap.add(new Point(0,17));
			
		}
		return collisionMap;
	}
	

}
