package aplicacion;

import java.io.Serializable;

public class MonedaPlata extends Moneda {

	/**
	 * Crear una moneda de plata, que le permite al usuario ser inmune a los
	 * mini-dragones (solo se puede usar una vez).
	 */
	public MonedaPlata() {
		super();
		this.color = 4;
	}

	@Override
	void activar(Jugador jugador) {
		jugador.setEstado(new EstadoPlata(jugador));

	}

	@Override
	public Moneda nuevo() {
		return new MonedaPlata();
	}

}
