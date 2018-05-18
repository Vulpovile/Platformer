package com.androdome.platform;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.androdome.platform.bricks.Brick;


public class Level implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Brick[][] bricks = new Brick[2048][64];
	public Brick[][] bg1 = new Brick[2048][64];
	public Brick[][] bg2 = new Brick[2048][64];
	public Brick[][] fg = new Brick[2048][64];
	//public ArrayList<String> clipTitle = new ArrayList<String>();
	//public ArrayList<byte[]> clipData = new ArrayList<byte[]>();
	//public byte[] selectedClip = null;
	
	public String modname = "";
	public byte[] moddata = null;
	public Point playerStart = new Point(16,800);
	public String zone = "Unknown Fields";
	public ImageIcon bg;
	public int act = 1;
	//boolean locked = false;
	Point relativePoint = new Point(0,(-(64-16))*16);
	public ArrayList<String> tileTitle = new ArrayList<String>();
	public ArrayList<ImageIcon> tileData = new ArrayList<ImageIcon>();
	public int collisionMapHashCode = -1;
	ArrayList<Shape> collisionMap = new ArrayList<Shape>();
	public void generateCollisionMap() {
		if(bricks.hashCode() != collisionMapHashCode)
		{
			collisionMap.clear();
			ArrayList<Area> collisionShape = new ArrayList<Area>();
			for(int x = 0; x < bricks.length; x++)
			{
				for(int y = 0; y < bricks[x].length; y++)
				{
					if(bricks[x][y] != null)
					{
						Polygon poly = new Polygon();
						ArrayList<Point> points = bricks[x][y].getCollisionMap();
						for(int z = 0; z < points.size(); z++)
						{
							poly.addPoint(points.get(z).x + x*16, points.get(z).y + y*16);
						}
						Area blockCol = new Area(poly);
						boolean go = true;
						for(int i = 0; i < collisionShape.size(); i++)
						{
							
							Area area = new Area(collisionShape.get(i));
							area.intersect(blockCol);
							if(!area.isEmpty())
							{
								collisionShape.get(i).add(blockCol);
								go = false;
								break;
							}
						}
						if(go)
						{
							collisionShape.add(new Area(blockCol));
						}
					}
				}
			}
			for(int i = 0; i < collisionShape.size(); i++)
			{
				collisionMap.add(AffineTransform.getTranslateInstance(0,0).createTransformedShape(collisionShape.get(i)));
			}
		}
	}
}
