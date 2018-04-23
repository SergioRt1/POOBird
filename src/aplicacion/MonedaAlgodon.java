package aplicacion;

import java.io.Serializable;

public class MonedaAlgodon extends Moneda {

	/**
	 * Crear una moneda de algodon, que le permite al jugador recuperar el 50% de la
	 * fortaleza perdida.
	 */
	public MonedaAlgodon() {
		super();
		this.color = 3;
	}

	@Override
	void activar(Jugador jugador) {
		jugador.setEstado(new EstadoJugador(jugador));
		jugador.hacerDano(-50, Elemento.TIPO_MONEDA);

	}

	@Override
	public Moneda nuevo() {
		return new MonedaAlgodon();
	}

}
