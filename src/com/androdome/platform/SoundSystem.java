package com.androdome.platform;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class SoundSystem {
private AudioDataStream bgm;
public String soundName = "";
	public void setBGM(byte[] sound, String name)
	{
		soundName = name;
		AudioData audiodata = new AudioData(sound);
		if(bgm != null)
		{
			AudioPlayer.player.stop(bgm);
		}
		bgm = new AudioDataStream(audiodata);
	}
	public void clearBGM()
	{
		soundName = "";
		if(bgm != null)
		{
			AudioPlayer.player.stop(bgm);
		}
		bgm = null;
	}
	public void playBGM()
	{
		if(bgm != null)
		{
			AudioPlayer.player.stop(bgm);
			bgm.reset();
			AudioPlayer.player.start(bgm);
		}
	}
	public void stopBGM()
	{
		if(bgm != null)
		{
			bgm.reset();
			AudioPlayer.player.start(bgm);
		}
	}
}
