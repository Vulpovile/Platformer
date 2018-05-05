package com.androdome.platform;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ResManager extends JFrame implements WindowListener{

	private JPanel contentPane;
	
	MainFrame frame;
	JList list = new JList();
	private JTextField textField;
	AudioDataStream audioStream;
	boolean playing = false;
	
	public ResManager(MainFrame frame) {
		setResizable(false);
		setTitle("Resource Manager");
		this.frame = frame;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setBounds(100, 100, 450, 204);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(109, 11, 325, 123);
		contentPane.add(scrollPane);
		
		
		list.setListData(frame.level.clipTitle.toArray());
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Add Music");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new FileFilter() {

					   public String getDescription() {
					       return "Uncompressed Files (*.WAV, *.AU)";
					   }

					   public boolean accept(File f) {
					       if (f.isDirectory()) {
					           return true;
					       } else {
					           String filename = f.getName().toLowerCase();
					           return filename.endsWith(".wav") || filename.endsWith(".au");
					       }
					   }
					});
				if(jfc.showOpenDialog(ResManager.this) == JFileChooser.APPROVE_OPTION)
				{
					
					File testFile = jfc.getSelectedFile();
					
					try {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						BufferedInputStream in = new BufferedInputStream(new FileInputStream(testFile));
						int read;
						byte[] buff = new byte[1024];
						while ((read = in.read(buff)) > 0)
						{
						    out.write(buff, 0, read);
						}
						out.flush();
						byte[] audioBytes = out.toByteArray();
						in.close();out.close();
						ResManager.this.frame.level.clipTitle.add(testFile.getName());
						ResManager.this.frame.level.clipData.add(audioBytes);
						ResManager.this.list.setListData(ResManager.this.frame.level.clipTitle.toArray());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					 
		            
				}
			}
		});
		btnNewButton.setBounds(10, 9, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnDeleteSelected = new JButton("Delete");
		btnDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() > -1)
				{
					ResManager.this.frame.level.clipData.remove(list.getSelectedIndex());
					ResManager.this.frame.level.clipTitle.remove(list.getSelectedIndex());
					if(ResManager.this.frame.sound.soundName == (String)list.getSelectedValue())
					{
						stop();
						ResManager.this.frame.sound.clearBGM();
						textField.setText(ResManager.this.frame.sound.soundName);
					}
					ResManager.this.list.setListData(ResManager.this.frame.level.clipTitle.toArray());
				}
			}
		});
		btnDeleteSelected.setBounds(10, 43, 89, 23);
		contentPane.add(btnDeleteSelected);
		
		JButton btnSetAsBgm = new JButton("Set as BGM");
		btnSetAsBgm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() > -1)
				{
					ResManager.this.frame.sound.setBGM(ResManager.this.frame.level.clipData.get(list.getSelectedIndex()), (String)list.getSelectedValue());
					textField.setText(ResManager.this.frame.sound.soundName);
				}
			}
		});
		btnSetAsBgm.setBounds(10, 77, 89, 23);
		contentPane.add(btnSetAsBgm);
		
		JButton btnPlaystop = new JButton("Play/Stop");
		btnPlaystop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() > -1 && !playing)
				{
					play(ResManager.this.frame.level.clipData.get(list.getSelectedIndex()));
					playing = true;
				}
				else if(playing)
				{
					stop();
					playing = false;
				}
			}
		});
		btnPlaystop.setBounds(10, 111, 89, 23);
		contentPane.add(btnPlaystop);
		
		JLabel lblCurrentBgm = new JLabel("Current BGM:");
		lblCurrentBgm.setBounds(10, 148, 89, 14);
		contentPane.add(lblCurrentBgm);
		
		textField = new JTextField();
		textField.setBounds(109, 145, 325, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(ResManager.this.frame.sound.soundName);
	}
	protected void stop() {
		if(audioStream != null)
		{
			AudioPlayer.player.stop(audioStream);
		}
		audioStream = null;	
	}
	public void play(byte[] clickSamples)
	{
		AudioData audiodata = new AudioData(clickSamples);
		// Create an AudioDataStream to play back
		if(audioStream != null)
		{
			AudioPlayer.player.stop(audioStream);
		}
		audioStream = new AudioDataStream(audiodata);
		// Play the sound
		AudioPlayer.player.start(audioStream);
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		stop();
		dispose();
		
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
}
