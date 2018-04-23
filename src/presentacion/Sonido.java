package presentacion;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class Sonido{
	
	public static final float VOLUMEN_NORMAL=(float) 0.0;
	public static final float VOLUMEN_MAXIMO=(float) 3.0;
	public static final float VOLUMEN_MINIMO=(float) -22.0;
	public static final float MUTE=(float) -80.0;
	public static float VOLUMEN = VOLUMEN_NORMAL;
	
	private Clip sonido;

	public Sonido(String ruta, float volumen) {
		sonido = cargarSonido(ruta);
		CambiarVolumen(volumen);
	}
	

	public Clip cargarSonido(final String ruta) {
		Clip clip = null;
		try {
			InputStream is = ClassLoader.class.getResourceAsStream(ruta);
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clip;
	}
	
	public void reproducir(long t) {
		sonido.stop();
		sonido.flush();
		sonido.setMicrosecondPosition(t);
		sonido.start();
	}

	public void repetir() {
		//sonido.stop();
		//sonido.flush();
		sonido.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void repetir(long t) {
		sonido.stop();
		sonido.flush();
		sonido.setMicrosecondPosition(t);
		sonido.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public long getLength() {
		return sonido.getMicrosecondLength();
	}
	
	public long getPosition(){
		return sonido.getMicrosecondPosition();
	}

	public void pausar() {
		sonido.stop();
	}
	
	public Clip CambiarVolumen(float volumen) {
		VOLUMEN=volumen;
		try {
			FloatControl gainControl = 
				    (FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volumen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sonido;
	}
	
	public boolean isRunning() {
		return sonido.isRunning();
	}
	
	
}