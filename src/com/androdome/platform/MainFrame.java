package com.androdome.platform;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Polygon;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.JSplitPane;

import com.androdome.platform.bricks.*;
import com.androdome.platform.player.Player;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;
import java.awt.Image;

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
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;

import javax.swing.JToolBar;
import javax.swing.JCheckBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class MainFrame extends JFrame implements ListSelectionListener, ActionListener, WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageIcon[] icons = new ImageIcon[256];
	File f = new File("lv.tmp");
	private JPanel contentPane;
	public Level level = new Level();
	GameTick tick = new GameTick(this);
	GamePanel gamepanel = new GamePanel(this);
	Brick selectedPart = null;
	ArrayList<Brick> listit = new ArrayList<Brick>();
	ArrayList<String> listnames = new ArrayList<String>();
	JList itemList = new JList();
	JMenuItem mntmSave = new JMenuItem("Save...");
	JMenuItem mntmLoad = new JMenuItem("Load...");
	JMenuItem mntmResourceManager = new JMenuItem("Resource Manager...");
	JMenuItem mntmSetBackground = new JMenuItem("Set Background...");
	JMenuItem mntmSetTextureSet = new JMenuItem("Set Textureset...");
	JMenuItem mntmNew = new JMenuItem("New");
	JMenuItem mntmGenGrass = new JMenuItem("Generate Grass Floor");
	JMenuItem mntmGenCrack = new JMenuItem("Generate Cracked Floor");
	JMenuItem mntmGenNull = new JMenuItem("Generate Null Floor");
	Player player = new Player(this);
	public boolean running = false;
	public Font font;
	JTextField textField;
	JPanel controlPanel = new JPanel();
	SoundSystem sound = new SoundSystem(this);
	Polygon collisionMapTest = new Polygon();
	

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
		
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new GZIPInputStream(getClass().getResourceAsStream("/images/fnt")));
		} catch (FontFormatException e1) {
			font = new Font("Consolas", Font.BOLD, 18);
			e1.printStackTrace();
		} catch (IOException e1) {
			font = new Font("Consolas", Font.BOLD, 18);
			e1.printStackTrace();
		}
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
		listit.add(new GrassBottom());
		listnames.add("Grass Bottom");
		listit.add(new GrassTurnLeft());
		listnames.add("Grass Edge Left");
		listit.add(new GrassLeft());
		listnames.add("Grass Left");
		listit.add(new GrassTurnLeftBottom());
		listnames.add("Grass Edge Left Bottom");
		listit.add(new GrassTurnRight());
		listnames.add("Grass Edge Right");
		listit.add(new GrassRight());
		listnames.add("Grass Right");
		listit.add(new GrassTurnRightBottom());
		listnames.add("Grass Edge Right Bottom");
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
		
		
		mntmSetBackground.addActionListener(this);
		mnManage.add(mntmSetBackground);
		mnManage.add(mntmGenGrass);
		mnManage.add(mntmGenCrack);
		mnManage.add(mntmGenNull);
		mnManage.add(mntmSetTextureSet);
		mntmSetTextureSet.addActionListener(this);
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
		
		
		controlPanel.setPreferredSize(new Dimension(-1, 180));
		panel.add(controlPanel, BorderLayout.SOUTH);
		controlPanel.setLayout(null);
		
		JButton btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.y+=16;
			}
		});
		btnUp.setBounds(52, 11, 89, 23);
		controlPanel.add(btnUp);
		
		JButton btnNewButton = new JButton("Left");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.x+=16;
			}
		});
		btnNewButton.setBounds(10, 45, 68, 23);
		controlPanel.add(btnNewButton);
		
		JButton btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.x-=16;
			}
		});
		btnRight.setBounds(120, 45, 68, 23);
		controlPanel.add(btnRight);
		
		JButton btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level.relativePoint.y-=16;
			}
		});
		btnDown.setBounds(52, 79, 89, 23);
		controlPanel.add(btnDown);
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GamePanel.scalefactor -= 16;
			}
		});
		button.setBounds(52, 113, 89, 23);
		controlPanel.add(button);
		
		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GamePanel.scalefactor += 16;
			}
		});
		button_1.setBounds(52, 147, 89, 23);
		controlPanel.add(button_1);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(1);
		splitPane_1.setDividerLocation(220);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.add(splitPane_1, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane_1.setLeftComponent(scrollPane);
		scrollPane.setPreferredSize(new Dimension(-1, 150));
		itemList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		itemList.setCellRenderer(new DefaultListCellRenderer() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        int scalesize = 32;
		        Brick brick = listit.get(index);
		        if(brick != null)
				{
					try {
						int idx = level.tileTitle.indexOf(brick.img);
						if(idx > -1)
							icons[index] = new ImageIcon(level.tileData.get(idx).getImage().getScaledInstance(scalesize, scalesize, Image.SCALE_FAST));
						else
							icons[index] = new ImageIcon(ImageIO.read(getClass().getResource("/images/" + brick.img)).getScaledInstance(scalesize, scalesize, Image.SCALE_FAST));
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(404);
					}
				}
		        else
		        {
		        	icons[index] = new ImageIcon(new BufferedImage(scalesize,scalesize,BufferedImage.TYPE_INT_ARGB));
		        }
				if(icons[index] != null)
					label.setIcon(icons[index]);
		        return label;
		    }
		});
		itemList.setListData(listnames.toArray());
		itemList.setSelectedIndex(0);
		scrollPane.setViewportView(itemList);
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setRightComponent(panel_1);
		panel_1.setPreferredSize(new Dimension(-1, 50));
		panel_1.setLayout(new GridLayout(5, 1, 0, 0));
		
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
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(null);
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel_1.add(panel_4);
		
		JLabel lblName = new JLabel("Name");
		panel_4.add(lblName);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				level.zone = textField.getText();
			}
		});
		panel_4.add(textField);
		textField.setColumns(14);
		textField.setText(level.zone);
		itemList.addListSelectionListener(this);
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
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		final JCheckBox chckbxShowControlPanel = new JCheckBox("Show control panel");
		chckbxShowControlPanel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				controlPanel.setVisible(chckbxShowControlPanel.isSelected());
			}
		});
		chckbxShowControlPanel.setSelected(true);
		toolBar.add(chckbxShowControlPanel);
		
		toolBar.addSeparator();
		
		final JButton btnGo = new JButton("Go");
		toolBar.add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getID() != ActionEvent.KEY_EVENT_MASK)
				{
					
					if(running)
					{
						sound.stopModule();
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
								GameTick.drawIntroScreen = false;
								gamepanel.drawingIntro = false;
								gamepanel.loc = 0;
								GameTick.deadCount = 0;
							} catch (FileNotFoundException e) {
								JOptionPane.showMessageDialog(MainFrame.this, "Reset file not found", "Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(MainFrame.this, "Reset file corrupted", "Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						else
						{
							JOptionPane.showMessageDialog(MainFrame.this, "Reset file not found", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						try {
							if(level.moddata != null)
							{
								sound.loadModule(level.modname, level.moddata);
								sound.playModule();
							}
							gamepanel.gameOverOverlay = 1F;
							GameTick.deadCount = 250;
							GameTick.drawIntroScreen = true;
							level.generateCollisionMap();
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
		tick.start();
	}

	
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
	
	
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == mntmSave)
		{
			save();
		}
		else if(arg0.getSource() == this.mntmSetBackground)
		{
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileFilter() {

				   public String getDescription() {
				       return "Image file";
				   }

				   public boolean accept(File f) {
				       if (f.isDirectory()) {
				           return true;
				       } else {
				           String filename = f.getName().toLowerCase();
				           return filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".gif");
				       }
				   }
				});
			if(jfc.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
			{
				
				File testFile = jfc.getSelectedFile();
				try {
					level.bg = new ImageIcon(testFile.toURL());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
					if(level.fg == null)
						level.fg = new Brick[level.bricks.length][level.bricks[0].length];
					if(level.zone == null)
						level.zone = "Unknown Fields";
					if(level.playerStart == null)
					{
						level.playerStart = new Point(16, (level.bricks[0].length - 8)*16);
						for(int x = 0; x < level.bricks.length; x++)
						{
							for(int y = 0; y < level.bricks[x].length; y++)
							{
								if(!(level.bricks[x][y] instanceof HillWallLeft) && !(level.bricks[x][y] instanceof HillWallMid) && !(level.bricks[x][y] instanceof HillWallRight) && level.bricks[x][y] != null)
								{
									level.bricks[x][y].collides = true;
								}
							}
						}
					}
					if(level.tileTitle == null || level.tileData == null)
					{
						level.tileData = new ArrayList<ImageIcon>();
						level.tileTitle = new ArrayList<String>();
					}
					this.textField.setText(level.zone);
					gamepanel.blocks = new Image[256];
					icons = new ImageIcon[256];
					itemList.repaint();
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
				gamepanel.blocks = new Image[256];
				icons = new ImageIcon[256];
				itemList.repaint();
				prepStartup();
				this.textField.setText(level.zone);
			}
			else if(outcome == JOptionPane.OK_OPTION)
			{
				if(save())
				{
					level = new Level();
					gamepanel.blocks = new Image[256];
					icons = new ImageIcon[256];
					itemList.repaint();
					prepStartup();
					this.textField.setText(level.zone);
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
		else if(arg0.getSource() == mntmSetTextureSet)
		{
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileFilter() {

				   public String getDescription() {
				       return "Zip files (*.zip)";
				   }

				   public boolean accept(File f) {
				       if (f.isDirectory()) {
				           return true;
				       } else {
				           String filename = f.getName().toLowerCase();
				           return filename.endsWith(".zip");
				       }
				   }
				});
			if(jfc.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
			{
				try {
					level.tileData.clear();
					level.tileTitle.clear();
					ZipFile testFile = new ZipFile(jfc.getSelectedFile());
					Enumeration<? extends ZipEntry> entries = testFile.entries();

					while(entries.hasMoreElements()){
					    ZipEntry entry = entries.nextElement();
					    if(!entry.isDirectory())
					    {
						    try{
						    	if(entry.getName().endsWith(".png"))
						    	{
						    	level.tileData.add(new ImageIcon(ImageIO.read(testFile.getInputStream(entry))));
						    	level.tileTitle.add(entry.getName());
						    	}						    	
						    }
						    catch(Exception ex){}
					    }
					}
					testFile.close();
					gamepanel.blocks = new Image[256];
					icons = new ImageIcon[256];
					itemList.repaint();
				} catch (ZipException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
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

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		if(f.exists())
			f.delete();		
	}

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

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private class MyDispatcher implements KeyEventDispatcher {
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
				player.velocity.y = -6;
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
