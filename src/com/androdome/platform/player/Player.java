package com.androdome.platform.player;

import java.awt.Point;

import com.androdome.platform.MainFrame;

public class Player {
public Point location = new Point(16,400);
public Point velocity = new Point(0,0);
MainFrame frame;
	public Player(MainFrame frame)
	{
		this.frame = frame;
	}
}
