package com.androdome.platform;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.JSplitPane;

import com.androdome.platform.bricks.*;
import com.androdome.platform.player.Player;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;

import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ListSelectionModel;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;


public class MainFrame extends JFrame implements ListSelectionListener, ActionListener, WindowListener{

	/**
	 * 
	 */
	File f = new File("lv.tmp");
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public Level level = new Level();
	GameTick tick = new GameTick(this);
	GamePanel gamepanel = new GamePanel(this);
	Brick selectedPart = null;
	ArrayList<Brick> listit = new ArrayList<Brick>();
	ArrayList<String> listnames = new ArrayList<String>();
	JList<Object> itemList = new JList<Object>();
	JMenuItem mntmSave = new JMenuItem("Save...");
	JMenuItem mntmLoad = new JMenuItem("Load...");
	JMenuItem mntmResourceManager = new JMenuItem("Resource Manager...");
	JMenuItem mntmNew = new JMenuItem("New");
	JMenuItem mntmGenGrass = new JMenuItem("Generate Grass Floor");
	JMenuItem mntmGenCrack = new JMenuItem("Generate Cracked Floor");
	JMenuItem mntmGenNull = new JMenuItem("Generate Null Floor");
	SoundSystem sound = new SoundSystem();
	Player player = new Player(this);
	public boolean running = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
		ButtonGroup group = new ButtonGroup();
		listit.add(null);
		listnames.add("Null");
		listit.add(new Brick());
		listnames.add("Brick");
		listit.add(new Tile());
		listnames.add("Tile");
		listit.add(new Bricks());
		listnames.add("Bricks");
		listit.add(new MysteryBox());
		listnames.add("Mystery Box");
		listit.add(new Glass());
		listnames.add("Glass");
		listit.add(new GrassTop());
		listnames.add("Grass Top");
		listit.add(new GrassMid());
		listnames.add("Grass Middle");
		listit.add(new GrassTurnLeft());
		listnames.add("Grass Edge Left");
		listit.add(new GrassTurnRight());
		listnames.add("Grass Edge Right");
		listit.add(new GrassRight());
		listnames.add("Grass Right");
		listit.add(new GrassLeft());
		listnames.add("Grass Left");
		listit.add(new GrassTurnLeftBottom());
		listnames.add("Grass Edge Left Bottom");
		listit.add(new GrassTurnRightBottom());
		listnames.add("Grass Edge Right Bottom");
		listit.add(new GrassBottom());
		listnames.add("Grass Bottom");
		listit.add(new GrassCornerTopLeft());
		listnames.add("Grass Corner Top Left");
		listit.add(new GrassCornerBottomLeft());
		listnames.add("Grass Corner Bottom Left");
		listit.add(new GrassCornerBottomRight());
		listnames.add("Grass Corner Bottom Right");
		listit.add(new GrassCornerTopRight());
		listnames.add("Grass Corner Top Right");
		listit.add(new HillCornerLeft());
		listnames.add("Hill Corner Left");
		listit.add(new HillMiddle());
		listnames.add("Hill Middle");
		listit.add(new HillCornerRight());
		listnames.add("Hill Corner Right");
		listit.add(new HillWallLeft());
		listnames.add("Hill Wall Left");
		listit.add(new HillWallMid());
		listnames.add("Hill Wall Middle");
		listit.add(new HillWallRight());
		listnames.add("Hill Wall Right");
		setTitle("Platformer");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		
		mntmSave.addActionListener(this);
		

		mntmNew.addActionListener(this);
		
		
		
		mnFile.add(mntmNew);
		
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(150, 1));
		mnFile.add(separator);
		mnFile.add(mntmSave);
		
		mntmLoad.addActionListener(this);
		mnFile.add(mntmLoad);
		
		JMenu mnManage = new JMenu("Manage");
		menuBar.add(mnManage);
		
		
		
		mnManage.add(mntmResourceManager);
		mnManage.add(mntmGenGrass);
		mnManage.add(mntmGenCrack);
		mnManage.add(mntmGenNull);
		mntmGenGrass.addActionListener(this);
		mntmGenCrack.addActionListener(this);
		mntmGenNull.addActionListener(this);
		mntmResourceManager.addActionListener(this);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(getWidth()-220);
		splitPane.setResizeWeight(1);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(-1, 150));
		panel.add(scrollPane, BorderLayout.NORTH);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		itemList.setListData(listnames.toArray());
		itemList.setSelectedIndex(0);
		scrollPane.setViewportView(itemList);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_2.add(panel_1, BorderLayout.NORTH);
		panel_1.setPreferredSize(new Dimension(-1, 60));
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		final JRadioButton rdbtnMainGame = new JRadioButton("Main game");
		group.add(rdbtnMainGame);
		rdbtnMainGame.setSelected(true);
		panel_1.add(rdbtnMainGame);
		
		final JRadioButton rdbtnBg = new JRadioButton("BG1");
		group.add(rdbtnBg);
		panel_1.add(rdbtnBg);
		
		final JRadioButton rdbtnBg_1 = new JRadioButton("BG2");
		group.add(rdbtnBg_1);
		panel_1.add(rdbtnBg_1);
		
		final JRadioButton rdbtnFg = new JRadioButton("FG");
		group.add(rdbtnFg);
		panel_1.add(rdbtnFg);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(-1, 220));
		panel.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(null);
		
		JButton btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.y+=16;
			}
		});
		btnUp.setBounds(52, 11, 89, 23);
		panel_3.add(btnUp);
		
		JButton btnNewButton = new JButton("Left");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.x+=16;
			}
		});
		btnNewButton.setBounds(10, 45, 68, 23);
		panel_3.add(btnNewButton);
		
		JButton btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.x-=16;
			}
		});
		btnRight.setBounds(121, 45, 68, 23);
		panel_3.add(btnRight);
		
		JButton btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.y-=16;
			}
		});
		btnDown.setBounds(52, 79, 89, 23);
		panel_3.add(btnDown);
		
		final JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getID() != ActionEvent.KEY_EVENT_MASK)
				{
					
					if(running)
					{
						running = false;
						btnGo.setText("Go");
						if(f.exists())
						{
							
							try {
								ObjectInputStream oos = new ObjectInputStream(new GZIPInputStream(new FileInputStream(f)));
								level = (Level) oos.readObject();
								oos.close();
								f.delete();
								player.location = level.playerStart;
								player.dead = false;
								gamepanel.gameOverOverlay = 0;
								GameTick.deadCount = 0;
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					else
					{
						try {
							ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(f)));
							oos.writeObject(level);
							oos.close();
							f.deleteOnExit();
							running = true;
							btnGo.setText("Stop");
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(MainFrame.this, "A file IO error was encountered when trying to process your request.\r\nPlease make sure the excecutable location is writable\r\nYour state was not saved and game was not started", "Error", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(MainFrame.this, "A file IO error was encountered when trying to process your request.\r\nPlease make sure the excecutable location is writable\r\nYour state was not saved and game was not started", "Error", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
						
					}
				}
			}
		});
		btnGo.setBounds(52, 113, 89, 23);
		panel_3.add(btnGo);
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GamePanel.scalefactor -= 16;
			}
		});
		button.setBounds(52, 147, 89, 23);
		panel_3.add(button);
		
		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GamePanel.scalefactor += 16;
			}
		});
		button_1.setBounds(52, 181, 89, 23);
		panel_3.add(button_1);
		gamepanel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				int notches = arg0.getWheelRotation();
			    if (notches < 0) {
			    	if(arg0.isShiftDown())
			    	{
			    		level.relativePoint.x-=16;
			    	}
			    	else
			    		level.relativePoint.y+=16;
			    }	
			    else
			    {
			    	if(arg0.isShiftDown())
			    	{
			    		level.relativePoint.x+=16;
			    	}
			    	else
			    		level.relativePoint.y-=16;
			    }
			}
		});
		gamepanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Point pnt = gamepanel.getLevelRelativeLocation(arg0.getX(), arg0.getY());
				if(pnt.x >= 0 && pnt.y >= 0 && pnt.x < level.bricks.length && pnt.y < level.bricks[0].length)
				{
					if(SwingUtilities.isLeftMouseButton(arg0))
					{
						if(rdbtnMainGame.isSelected())
							level.bricks[pnt.x][pnt.y] = selectedPart;
						else if(rdbtnBg.isSelected())
							level.bg1[pnt.x][pnt.y] = selectedPart;
						else if(rdbtnBg_1.isSelected())
							level.bg2[pnt.x][pnt.y] = selectedPart;
						else
							level.fg[pnt.x][pnt.y] = selectedPart;
							
					}
					else if(SwingUtilities.isRightMouseButton(arg0))
					{
							level.bricks[pnt.x][pnt.y] = null;
							level.bg1[pnt.x][pnt.y] = null;
							level.bg2[pnt.x][pnt.y] = null;
							level.fg[pnt.x][pnt.y] = null;
					}
				}
			}
		});
		gamepanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Point pnt = gamepanel.getLevelRelativeLocation(arg0.getX(), arg0.getY());
				if(pnt.x >= 0 && pnt.y >= 0 && pnt.x < level.bricks.length && pnt.y < level.bricks[0].length)
				{
					if(SwingUtilities.isLeftMouseButton(arg0))
					{
						if(rdbtnMainGame.isSelected())
							level.bricks[pnt.x][pnt.y] = selectedPart;
						else if(rdbtnBg.isSelected())
							level.bg1[pnt.x][pnt.y] = selectedPart;
						else if(rdbtnBg_1.isSelected())
							level.bg2[pnt.x][pnt.y] = selectedPart;
						else
							level.fg[pnt.x][pnt.y] = selectedPart;
							
					}
					else if(SwingUtilities.isRightMouseButton(arg0))
					{
							level.bricks[pnt.x][pnt.y] = null;
							level.bg1[pnt.x][pnt.y] = null;
							level.bg2[pnt.x][pnt.y] = null;
							level.fg[pnt.x][pnt.y] = null;
					}
				}
				
			}
		});
		gamepanel.setBackground(new Color(102, 204, 255));
		
		
		splitPane.setLeftComponent(gamepanel);
		itemList.addListSelectionListener(this);
		tick.start();
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		selectedPart = listit.get(itemList.getSelectedIndex());
	}

	
	public boolean save()
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileFilter() {

			   public String getDescription() {
			       return "Platformer Saves (*.plf)";
			   }

			   public boolean accept(File f) {
			       if (f.isDirectory()) {
			           return true;
			       } else {
			           String filename = f.getName().toLowerCase();
			           return filename.endsWith(".plf");
			       }
			   }
			});
		if(jfc.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
		{
			String fileString = jfc.getSelectedFile().getAbsolutePath();
			if(!fileString.endsWith(".plf"))
			{
				fileString = fileString + ".plf";
			}
			
			File testFile = new File(fileString);
			
			try {
				ObjectOutputStream stream = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(testFile)));
				stream.writeObject(level);
				stream.close();
				return true;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "A file not found error was encountered when trying to process your request.\r\nPlease make sure you have a valid file name\r\nYour file was not saved", "Save Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "A I/O exception was encountered when trying to process your request.\r\nPlease make sure you have a valid file name\r\nYour file was not saved", "Save Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == mntmSave)
		{
			save();
		}
		else if(arg0.getSource() == mntmLoad)
		{
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileFilter() {

				   public String getDescription() {
				       return "Platformer Saves (*.plf)";
				   }

				   public boolean accept(File f) {
				       if (f.isDirectory()) {
				           return true;
				       } else {
				           String filename = f.getName().toLowerCase();
				           return filename.endsWith(".plf");
				       }
				   }
				});
			if(jfc.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
			{
				
				File testFile = jfc.getSelectedFile();
				try {
					ObjectInputStream stream = new ObjectInputStream(new GZIPInputStream(new FileInputStream(testFile)));
					level = (Level) stream.readObject();
					stream.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(arg0.getSource() == mntmNew)
		{
			int outcome = JOptionPane.showConfirmDialog(this, "Do you want to save your changes before making a new level?", "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION);
			if(outcome == JOptionPane.NO_OPTION)
			{
				level = new Level();
				prepStartup();
			}
			else if(outcome == JOptionPane.OK_OPTION)
			{
				if(save())
				{
					level = new Level();
					prepStartup();
				}
			}
		}
		else if(arg0.getSource() == mntmResourceManager)
		{
			new ResManager(this).setVisible(true);
		}
		else if(arg0.getSource() == mntmGenGrass)
		{
			prepStartup();
		}
		else if(arg0.getSource() == mntmGenCrack)
		{
			genFloor(new Brick());
		}
		else if(arg0.getSource() == mntmGenNull)
		{
			genFloor(null);
		}
	}

	public void prepStartup() {
		for(int x = 0; x < level.bricks.length; x++)
		{
			for(int y = 0; y < level.bricks[x].length; y++)
			{
				if(y == level.bricks[x].length - 2)
					level.bricks[x][y] = new GrassTop();
				else if(y == level.bricks[x].length - 1)
					level.bricks[x][y] = new GrassMid();
			}
		}
	}
	
	public void genFloor(Brick br) {
		for(int x = 0; x < level.bricks.length; x++)
		{
			for(int y = 0; y < level.bricks[x].length; y++)
			{
				if(y >= level.bricks[x].length - 2)
					level.bricks[x][y] = br;
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		if(f.exists())
			f.delete();		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int outcome = JOptionPane.showConfirmDialog(this, "Do you want to save your changes before closing?", "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION);
		if(outcome == JOptionPane.NO_OPTION)
		{
			this.dispose();
			GameTick.running = false;
			this.running = false;
			if(f.exists())
				f.delete();
			System.exit(0);
		}
		else if(outcome == JOptionPane.OK_OPTION)
		{
			if(save())
			{
				this.dispose();
				GameTick.running = false;
				this.running = false;
				if(f.exists())
					f.delete();
				System.exit(0);
			}
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
            	keyPressed(e);
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                keyUp(e);
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
                //System.out.println("3test3");
            }
            return false;
        }
    }
	
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_V)
		{
			if(player.onGround && player.velocity.y == 0)
			{
				player.jump = true;
				player.velocity.y = -5;
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_A)
		{
			player.left = true;
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_D)
		{
			player.right = true;
		}
	}
	public void keyUp(KeyEvent arg0)
	{
		if(arg0.getKeyCode() == KeyEvent.VK_A)
		{
			player.left = false;
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_D)
		{
			player.right = false;
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_V)
		{
			player.jump = false;
		}
	}
}
