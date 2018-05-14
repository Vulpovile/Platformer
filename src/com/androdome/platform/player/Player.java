package com.androdome.platform.player;

import java.awt.Point;

import com.androdome.platform.MainFrame;

public class Player {
public Point location;
public Point velocity = new Point(0,0);
MainFrame frame;
public boolean onGround = false;
public boolean left = false;
public boolean right = false;
public boolean jump = false;
public boolean dead = false;
	public Player(MainFrame frame)
	{
		this.frame = frame;
		location = frame.level.playerStart;
	}
}
