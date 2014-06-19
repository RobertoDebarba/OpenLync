package openlync.utilidades;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class AudioPlayer implements Runnable {

	private AudioClip ac;

	@SuppressWarnings("static-access")
	private void execute() {
		try {
			ac.play();
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		execute();
	}
	
	public AudioPlayer(URL audioFile) {
		this.ac = Applet.newAudioClip(audioFile);
	}

}