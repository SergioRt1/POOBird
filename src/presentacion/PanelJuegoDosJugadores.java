package presentacion;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class PanelJuegoDosJugadores extends PanelJuego {
	private JLabel puntajeJugador2;
	private JProgressBar barra2;
	private JLabel potenciadorJugador2;
	public PanelJuegoDosJugadores(FlappyGUI frame, boolean maquina) {
		super(frame, maquina);
		
	}
	@Override
	protected void extraHUD() {
		barra2 = new JProgressBar(0, 100);
		barra2.setStringPainted(true);
		barra2.setOpaque(false);
		barra2.setFont(new Font("Cooper Black", Font.PLAIN, 20));
		barra2.setForeground(frame.getColor(1));
		puntajeJugador2 = new JLabel("0");
		puntajeJugador2.setFont(new Font("Arial", Font.BOLD, 80));
		puntajeJugador2.setForeground(frame.getColor(1));
		barras.add(barra2);
		puntajes.add(puntajeJugador2);
		potenciadorJugador2 = new JLabel("",JLabel.CENTER);
		potenciadorJugador2.setFont(new Font("Arial", Font.BOLD, 50));
		potenciadores.add(potenciadorJugador2);
	}
	
	@Override
	protected void refresqueExtra() {
		puntajeJugador2.setText(Integer.toString(frame.getPuntaje(1)));
		barra2.setValue(frame.getVidaJugador(1));
		barra2.setString("Vida " + barra2.getValue());
		potenciadorJugador2.setText(frame.getTextEstado(1));
		potenciadorJugador2.setForeground(valueColor(frame.getEstado(1)));
	}
	
	@Override
	protected void accionesExtra() {
		frame.moverArriba(1);
		salto.reproducir(0);
	}
}
