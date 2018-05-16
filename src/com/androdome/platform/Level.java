package com.androdome.platform;
import java.awt.Point;
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
}
