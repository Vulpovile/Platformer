package com.androdome.platform;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;

import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;

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


public class MainFrame extends JFrame implements ListSelectionListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Level level = new Level();
	GameTick tick = new GameTick(this);
	GamePanel gamepanel = new GamePanel(this);
	Brick selectedPart = null;
	ArrayList<Brick> listit = new ArrayList<Brick>();
	JList itemList = new JList();
	JMenuItem mntmSave = new JMenuItem("Save");
	JMenuItem mntmLoad = new JMenuItem("Load");
	JMenuItem mntmNew = new JMenuItem("New");
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
		ButtonGroup group = new ButtonGroup();
		listit.add(null);
		listit.add(new Brick());
		listit.add(new Tile());
		listit.add(new Bricks());
		listit.add(new HillCornerLeft());
		listit.add(new HillMiddle());
		listit.add(new HillCornerRight());
		listit.add(new HillWallLeft());
		listit.add(new HillWallMid());
		listit.add(new HillWallRight());
		setTitle("Platformer");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 561);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		
		mntmSave.addActionListener(this);
		

		mntmNew.addActionListener(this);
		
		mnFile.add(mntmNew);
		mnFile.add(mntmSave);
		
		mntmLoad.addActionListener(this);
		mnFile.add(mntmLoad);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(400);
		splitPane.setResizeWeight(1);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(-1, 150));
		panel.add(scrollPane, BorderLayout.NORTH);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		itemList.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"Null", "Cracked Block", "Tile", "Bricks", "Hill Left", "Hill Middle", "Hill Right", "Hill Wall Left", "Hill Wall Middle", "Hill Wall Right"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
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
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(-1, 200));
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
				if(running)
				{
					running = false;
					btnGo.setText("Go");
				}
				else
				{
					running = true;
					btnGo.setText("Stop");
				}
			}
		});
		btnGo.setBounds(52, 113, 89, 23);
		panel_3.add(btnGo);
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
				Point pnt = gamepanel.getMouseClickLocation(arg0.getX(), arg0.getY());
				if(pnt.x >= 0 && pnt.y >= 0 && pnt.x < level.bricks.length && pnt.y < level.bricks[0].length)
				{
					if(SwingUtilities.isLeftMouseButton(arg0))
					{
						if(rdbtnMainGame.isSelected())
							level.bricks[pnt.x][pnt.y] = selectedPart;
						else if(rdbtnBg.isSelected())
							level.bg1[pnt.x][pnt.y] = selectedPart;
						else
							level.bg2[pnt.x][pnt.y] = selectedPart;
					}
					else if(SwingUtilities.isRightMouseButton(arg0))
					{
							level.bricks[pnt.x][pnt.y] = null;
							level.bg1[pnt.x][pnt.y] = null;
							level.bg2[pnt.x][pnt.y] = null;
					}
				}
			}
		});
		gamepanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Point pnt = gamepanel.getMouseClickLocation(arg0.getX(), arg0.getY());
				if(SwingUtilities.isLeftMouseButton(arg0))
				{
					if(rdbtnMainGame.isSelected())
						level.bricks[pnt.x][pnt.y] = selectedPart;
					else if(rdbtnBg.isSelected())
						level.bg1[pnt.x][pnt.y] = selectedPart;
					else
						level.bg2[pnt.x][pnt.y] = selectedPart;
				}
				else if(SwingUtilities.isRightMouseButton(arg0))
				{
						level.bricks[pnt.x][pnt.y] = null;
						level.bg1[pnt.x][pnt.y] = null;
						level.bg2[pnt.x][pnt.y] = null;
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == mntmSave)
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
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			if(JOptionPane.showConfirmDialog(this, "Are you sure you want to make a new level?\r\nYou will loose all unsaved changes!") == JOptionPane.OK_OPTION)
			{
				level = new Level();
				prepStartup();
			}
		}
	}

	public void prepStartup() {
		for(int x = 0; x < level.bricks.length; x++)
		{
			for(int y = 0; y < level.bricks[x].length; y++)
			{
				if(y >= 30)
					level.bricks[x][y] = new Brick();
			}
		}
	}
}
