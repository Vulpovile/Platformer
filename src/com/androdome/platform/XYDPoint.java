package com.androdome.platform;

import java.awt.Point;

public class XYDPoint extends java.awt.Point {
	public static final long serialVersionUID = 1L;
	public int d;
	public XYDPoint(int x, int y, int d)
	{
		super(x, y);
		this.d = d;
	}
	public XYDPoint(Point p, int d)
	{
		super(p);
		this.d = d;
	}
	public XYDPoint(XYDPoint p)
	{
		this.x = p.x;
		this.y = p.y;
		this.d = p.d;
	}
	
	public boolean isMinimum(XYDPoint[] pArray)
	{
		for(int i = 0; i < pArray.length; i++)
		{
			if(pArray[i] != null && Math.abs(pArray[i].d) < Math.abs(this.d))
			{
				return false;
			}
		}
		return true;
	}
	
	public static XYDPoint shortest(XYDPoint point1, XYDPoint point2)
	{
		if(Math.min(point1.d, point2.d) == point1.d)
			return point1;
		else return point2;
	}
	public static XYDPoint longest(XYDPoint point1, XYDPoint point2)
	{
		if(Math.max(point1.d, point2.d) == point1.d)
			return point1;
		else return point2;
	}
}
