package com.androdome.platform.bricks;
import java.io.Serializable;


public class Brick implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int type;
	public String img;
	public boolean collides = true;
	public Brick()
	{
		type = 0;
		img = "brick.png";
	}
	public void animate()
	{
		
	}
	

}
