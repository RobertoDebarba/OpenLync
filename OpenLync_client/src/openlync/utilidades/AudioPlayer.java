package openlync.utilidades;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javazoom.jl.player.Player;

public class AudioPlayer implements Runnable {

	private URL audio;
	private Player player;

	private void execute() {
		try {
			AudioClip ac = Applet.newAudioClip(audio);
			ac.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return player;
	}
	
	public void setAudioFile(URL audio) {
		this.audio = audio;

	}

	@Override
	public void run() {
		execute();
	}

}