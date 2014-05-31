package openlync.utilidades;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class AudioPlayer implements Runnable {

	private String audio;
	private Player player;

	private void execute() {
		try {
			File file = new File(audio);
			FileInputStream in = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(in);
			try {
				player = new Player(bis);
				player.play();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return player;
	}
	
	public void setAudioFile(String audio) {
		this.audio = audio;
	}

	@Override
	public void run() {
		execute();
	}

}