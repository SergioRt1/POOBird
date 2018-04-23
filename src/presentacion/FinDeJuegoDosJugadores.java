package presentacion;

import java.awt.Font;

import javax.swing.JLabel;

public class FinDeJuegoDosJugadores extends FinDeJuego {
	private JLabel puntaje2;

	public FinDeJuegoDosJugadores(FlappyGUI frame) {
		super(frame);
	}

	@Override
	protected void puntajeExtra() {
		puntaje2 = new JLabel("Puntaje jugador 2: " + frame.getPuntaje(1), JLabel.CENTER);
		puntaje2.setFont(new Font("Comic Sans", Font.BOLD, 25));
		puntaje2.setForeground(frame.getColor(1));
		datos.add(puntaje2);
	}
	
}
