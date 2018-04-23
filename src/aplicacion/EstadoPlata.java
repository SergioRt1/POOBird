package aplicacion;

import java.io.Serializable;

public class EstadoPlata extends EstadoJugador {
	/**
	 * Otorga un estado de plata al jugador, que lo hace inmune (solo una vez) a los
	 * dragones y a las bolas de fuego que estos lanzan.
	 * 
	 * @param jugador
	 */
	public EstadoPlata(Jugador jugador) {
		super(jugador);
		this.estado = "1";
	}

	@Override
	public void hacerDano(int dano, int tipo) {
		if (tipo == Elemento.TIPO_DRAGON)
			jugador.setEstado(new EstadoJugador(jugador));
		else
			super.hacerDano(dano, tipo);
	}

}
