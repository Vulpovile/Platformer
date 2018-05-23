package com.androdome.platform;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public AboutWindow() {
		setModal(true);
		//setAlwaysOnTop(true);
		setResizable(false);
		setTitle("About");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 316, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Platform Engine version " + MainFrame.VERSION);
		lblNewLabel.setBounds(10, 11, 274, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDevelopedByJugomedia = new JLabel("Developed by Dynamica Interactive");
		lblDevelopedByJugomedia.setBounds(10, 28, 274, 14);
		contentPane.add(lblDevelopedByJugomedia);
		
		JLabel lblSpecialThanks = new JLabel("Credits:");
		lblSpecialThanks.setBounds(10, 53, 274, 14);
		contentPane.add(lblSpecialThanks);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(10, 78, 290, 159);
		contentPane.add(scrollPane);
		
		JTextArea txtrMusicalprogrammerSuggestions = new JTextArea();
		txtrMusicalprogrammerSuggestions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrMusicalprogrammerSuggestions.setEditable(false);
		txtrMusicalprogrammerSuggestions.setWrapStyleWord(true);
		txtrMusicalprogrammerSuggestions.setLineWrap(true);
		txtrMusicalprogrammerSuggestions.setText("MusicalProgrammer - Collision Logic Suggestion and Testing\r\nNukley - Logic Suggestion and Artwork\r\nArtsicle - Logic Suggestion\r\nAndreja6 - Programming and Art\r\n");
		scrollPane.setViewportView(txtrMusicalprogrammerSuggestions);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton.setBounds(230, 248, 70, 23);
		contentPane.add(btnNewButton);
	}
}
