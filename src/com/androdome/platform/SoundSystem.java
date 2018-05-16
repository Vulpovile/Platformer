package com.androdome.platform;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

import ibxm.Channel;
import ibxm.IBXM;
import ibxm.Module;

public class SoundSystem {

	Module BGMModule;
	IBXM ibxm;
	private Thread playThread;
	boolean playing = false;
	MainFrame frame;
	public SoundSystem(MainFrame frame) {
		this.frame = frame;
	}
	
	public void loadModule(String name, byte[] data)
	{
		BGMModule = new Module(data);
		ibxm = new IBXM( BGMModule, 48000 );
		ibxm.setInterpolation(Channel.LINEAR);
	}
	
	private synchronized int getAudio( int[] mixBuf ) {
		int count = ibxm.getAudio( mixBuf );
		return count;
	}
	
	public void playModule()
	{
		playing = true;
		playThread = new Thread( new Runnable() {
			public void run() {
				int[] mixBuf = new int[ ibxm.getMixBufferLength() ];
				byte[] outBuf = new byte[ mixBuf.length * 4 ];
				AudioFormat audioFormat = null;
				SourceDataLine audioLine = null;
				try {
					audioFormat = new AudioFormat( 48000, 16, 2, true, true );
					audioLine = AudioSystem.getSourceDataLine( audioFormat );
					audioLine.open();
					audioLine.start();
					while( playing ) {
						int count = getAudio( mixBuf );
						int outIdx = 0;
						for( int mixIdx = 0, mixEnd = count * 2; mixIdx < mixEnd; mixIdx++ ) {
							int ampl = mixBuf[ mixIdx ];
							if( ampl > 32767 ) ampl = 32767;
							if( ampl < -32768 ) ampl = -32768;
							outBuf[ outIdx++ ] = ( byte ) ( ampl >> 8 );
							outBuf[ outIdx++ ] = ( byte ) ampl;
						}
						audioLine.write( outBuf, 0, outIdx );
					}
					audioLine.drain();
				} catch( Exception e ) {
					JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
				} finally {
					if( audioLine != null && audioLine.isOpen() ) audioLine.close();
				}	
			}
		} );
		playThread.start();
	}

}
