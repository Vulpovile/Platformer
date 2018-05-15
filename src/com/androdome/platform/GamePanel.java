package com.androdome.platform;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.androdome.platform.bricks.Brick;


public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image[] blocks = new Image[256];
	MainFrame frame;
	public static double scalefactor = 256.000;
	public static boolean runGameTick = false;
	public float gameOverOverlay = 0;
	public int loc;
	public boolean drawingIntro = false;
	public GamePanel(MainFrame frame) {
		this.frame = frame;
		frame.prepStartup();
		
	}
	
	Point getLevelRelativeLocation(int x, int y)
	{
		
		double scalesize = this.getHeight()/scalefactor;
		int newx = (int)Math.floor(((x/scalesize) - frame.level.relativePoint.x)/16.000);
		int newy = (int)Math.floor(((y/scalesize) - frame.level.relativePoint.y)/16.000);
		return new Point(newx,newy);
	}
	
	public Point hitY(int x, int ystart, int yend)
	{
		try
		{
			if(x >= frame.level.bricks.length)
			{
				return null;
			}
			for(int y = ystart; y <= yend; y++)
			{
				if(y >= frame.level.bricks[x].length)
				{
					return null;
					
				}
				if(frame.level.bricks[x][y] != null && frame.level.bricks[x][y].collides)
				{
					if(!frame.level.bricks[x][y].obtainable)
						return new Point(x, y);
					else
					{
						frame.level.bricks[x][y].obtain(frame.player);
						frame.level.bricks[x][y] = null;
					}
				}
			}
		}
		catch(Exception ex){}
		return null;
		
	}
	
	public boolean collides(int x, int y)
	{
		try{
			if(frame.level.bricks[x][y] != null && frame.level.bricks[x][y].collides)
			{
				return true;
			}
			else return false;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	public void paintComponent(Graphics g)
	{
		g.clearRect(0, 0, frame.getWidth(), this.getHeight());
		g.setColor(this.getBackground());
		g.fillRect(0, 0, frame.getWidth(), this.getHeight());
		double scalesize = this.getHeight()/scalefactor;
		if(frame.level.bg != null)
		{
			Image bg = frame.level.bg.getImage();//.getScaledInstance(-1, this.getHeight(), Image.SCALE_FAST);
			int width = bg.getWidth(this) * (getHeight()/bg.getHeight(this));
			for(int i = frame.level.relativePoint.x/2; i < getWidth(); i += width)
			g.drawImage(bg, i, 0, width, getHeight(), this);
		}
		for(int x = Math.max(0,(getLevelRelativeLocation(0,0).x)); x < Math.min(frame.level.bricks.length,(getLevelRelativeLocation(getWidth(),0).x)+1); x++)
		{
			for(int y = Math.max(0,(getLevelRelativeLocation(0,0).y)); y < Math.min(frame.level.bricks[x].length,(getLevelRelativeLocation(0,getHeight()).y)); y++)
			{
				if(frame.level.bg1[x][y] != null)
				{
					Brick brick = frame.level.bg1[x][y];
					if(blocks[brick.type] == null)
					{
						try {
							int index = frame.level.tileTitle.indexOf(brick.img);
							if(index > -1)
								blocks[brick.type] = frame.level.tileData.get(index).getImage();
							else
								blocks[brick.type] = ImageIO.read(getClass().getResource("/images/" + brick.img));
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(404);
						}
					}
					if(blocks[brick.type] != null)
						g.drawImage(blocks[brick.type], (int)((x*16 + frame.level.relativePoint.x) * scalesize),(int)((y*16 + frame.level.relativePoint.y) * scalesize),(int)Math.ceil(16 * scalesize),(int)Math.ceil(16 * scalesize), null);
				}
				if(frame.level.bg2[x][y] != null)
				{
					Brick brick = frame.level.bg2[x][y];
					if(blocks[brick.type] == null)
					{
						try {
							int index = frame.level.tileTitle.indexOf(brick.img);
							if(index > -1)
								blocks[brick.type] = frame.level.tileData.get(index).getImage();
							else
								blocks[brick.type] = ImageIO.read(getClass().getResource("/images/" + brick.img));
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(404);
						}
					}
					if(blocks[brick.type] != null)
						g.drawImage(blocks[brick.type], (int)((x*16 + frame.level.relativePoint.x) * scalesize),(int)((y*16 + frame.level.relativePoint.y) * scalesize),(int)Math.ceil(16 * scalesize),(int)Math.ceil(16 * scalesize), null);
				}
				if(frame.level.bricks[x][y] != null)
				{
					Brick brick = frame.level.bricks[x][y];
					if(blocks[brick.type] == null)
					{
						try {
							int index = frame.level.tileTitle.indexOf(brick.img);
							if(index > -1)
								blocks[brick.type] = frame.level.tileData.get(index).getImage();
							else
								blocks[brick.type] = ImageIO.read(getClass().getResource("/images/" + brick.img));
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(404);
						}
					}
					if(blocks[brick.type] != null)
						g.drawImage(blocks[brick.type], (int)((x*16 + frame.level.relativePoint.x) * scalesize),(int)((y*16 + frame.level.relativePoint.y) * scalesize),(int)Math.ceil(16 * scalesize),(int)Math.ceil(16 * scalesize), null);
					//g.fillRect((int)((x*16 + frame.level.relativePoint.x) * scalesize),(int)((y*16 + frame.level.relativePoint.y) * scalesize),(int)Math.ceil(16 * scalesize),(int)Math.ceil(16 * scalesize));
				}
				drawPlayer(scalesize, g);
				if(frame.level.fg[x][y] != null)
				{
					Brick brick = frame.level.fg[x][y];
					if(blocks[brick.type] == null)
					{
						try {
							int index = frame.level.tileTitle.indexOf(brick.img);
							if(index > -1)
								blocks[brick.type] = frame.level.tileData.get(index).getImage();
							else
								blocks[brick.type] = ImageIO.read(getClass().getResource("/images/" + brick.img));
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(404);
						}
					}
					if(blocks[brick.type] != null)
						g.drawImage(blocks[brick.type], (int)((x*16 + frame.level.relativePoint.x) * scalesize),(int)((y*16 + frame.level.relativePoint.y) * scalesize),(int)Math.ceil(16 * scalesize),(int)Math.ceil(16 * scalesize), null);
				}
			}
		}	
		if(GameTick.deadCount > 120)
		{
			if(GameTick.deadCount % 2 == 0)
			{
				if(gameOverOverlay < 1.0F)
					gameOverOverlay += 0.05F;
				
			}
			g.setColor(new Color(0,0,0,gameOverOverlay));
			g.fillRect(0, 0, getWidth(), getHeight());
			if(GameTick.deadCount == 180)
			{
				
				try {
					
					ObjectInputStream oos = new ObjectInputStream(new GZIPInputStream(new FileInputStream(frame.f)));
					frame.level = (Level) oos.readObject();
					oos.close();
					GameTick.drawIntroScreen = true;
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(this, "Reset file not found", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "Reset file not found", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(this, "Reset file corrupted", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
			//	drawTitleCard(true, g);
			}
		}
		if(this.drawingIntro)
		{
			g.setColor(Color.BLUE);
			g.fillRect(0, 0 - (this.getHeight() - loc*this.getHeight()/100), this.getWidth(), this.getHeight());
			g.setColor(Color.YELLOW);
			g.fillRect(this.getWidth() - loc*this.getWidth()/100, (int) (this.getHeight()/1.25), this.getWidth(), this.getHeight());
			g.setColor(Color.RED);
			g.fillRect(0, this.getHeight() - loc*this.getHeight()/100, this.getWidth()/4, this.getHeight());
			g.setFont(frame.font.deriveFont((float) (16*scalesize)));
			g.setColor(Color.BLACK);
			g.drawString(frame.level.zone, (int) (loc*this.getWidth()/100 - (g.getFontMetrics().stringWidth(frame.level.zone)+(50 - scalesize*2))), (int) (52 * scalesize));
			g.drawString("zone", (int) (loc*this.getWidth()/100 - (g.getFontMetrics().stringWidth("zone")+(50 - scalesize*2))), (int) (82 * scalesize));
			g.setColor(Color.WHITE);
			g.drawString(frame.level.zone, loc*this.getWidth()/100 - (g.getFontMetrics().stringWidth(frame.level.zone)+50), (int) (50 * scalesize));
			g.drawString("zone", loc*this.getWidth()/100 - (g.getFontMetrics().stringWidth("zone")+50), (int) (80 * scalesize));
			
		}
		
	}
	
	private void drawPlayer(double scalesize, Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect((int)((frame.player.location.x+frame.level.relativePoint.x)*scalesize), (int)((frame.player.location.y+frame.level.relativePoint.y)*scalesize), (int)(16*scalesize), (int)(32*scalesize));
	}

	public void animate() {
		for(int x = Math.max(0,(getLevelRelativeLocation(0,0).x)); x < Math.min(frame.level.bricks.length,(getLevelRelativeLocation(getWidth(),0).x)+1); x++)
		{
			for(int y = Math.max(0,(getLevelRelativeLocation(0,0).y)); y < Math.min(frame.level.bricks[x].length,(getLevelRelativeLocation(0,getHeight()).y)); y++)
			{
				if(frame.level.bg1[x][y] != null)
				{
					frame.level.bg1[x][y].animate();
				}
				if(frame.level.bg2[x][y] != null)
				{
					frame.level.bg2[x][y].animate();
				}				
				if(frame.level.bricks[x][y] != null)
				{
					frame.level.bricks[x][y].animate();
				}
				if(frame.level.fg[x][y] != null)
				{
					frame.level.fg[x][y].animate();
				}
			}
		}
		
	}

	public Point hitX(int y, int left, int right) {
		try
		{
			if(y >= frame.level.bricks.length)
			{
				return null;
			}
			for(int x = left; x <= right; x++)
			{
				if(x >= frame.level.bricks.length)
				{
					return null;
					
				}
				if(frame.level.bricks[x][y] != null && frame.level.bricks[x][y].collides)
				{
					if(!frame.level.bricks[x][y].obtainable)
						return new Point(x, y);
					else
					{
						frame.level.bricks[x][y].obtain(frame.player);
						frame.level.bricks[x][y] = null;
					}
				}
			}
		}
		catch(Exception ex){}
		return null;
	}
	
}
