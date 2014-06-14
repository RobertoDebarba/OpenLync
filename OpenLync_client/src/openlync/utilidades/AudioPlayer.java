package openlync.utilidades;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class AudioPlayer implements Runnable {

	private URL audioFile;

	private void execute() {
		try {
			AudioClip ac = Applet.newAudioClip(audioFile);
			ac.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setAudioFile(URL audio) {
		this.audioFile = audio;

	}

	@Override
	public void run() {
		execute();
	}

}