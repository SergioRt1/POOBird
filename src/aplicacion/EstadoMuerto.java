package aplicacion;

import java.io.Serializable;

public class EstadoMuerto extends EstadoJugador {
	
	/**
	 * Otorga un estado de muerte al jugador.
	 * 
	 * @param jugador
	 */
	public EstadoMuerto(Jugador jugador) {
		super(jugador);
	}

	@Override
	public void subir(int i) {
	}

	@Override
	public void hacerDano(int dano, int tipo) {
	}

}
