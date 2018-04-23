package aplicacion;

import java.io.Serializable;

public class MonedaOro extends Moneda {

	private int valor;

	/**
	 * Crear una moneda de oro, que le permite al jugador ser inmune a un número
	 * determinado de tubos, dependiendo del valor de esta.
	 */
	public MonedaOro() {
		super();
		this.valor = (int) (Math.random() * 10) + 1;
		this.color = 2;
	}

	@Override
	void activar(Jugador jugador) {
		jugador.setEstado(new EstadoOro(jugador, valor));
	}

	@Override
	public Moneda nuevo() {
		return new MonedaOro();
	}

}
