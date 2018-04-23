package aplicacion;

import java.io.Serializable;

public class EstadoOro extends EstadoJugador {

	private int numeroInmune;

	/**
	 * Otorga un estado de oro al jugador, que lo hace inmune a una cantidad de
	 * tubos determinados.
	 * 
	 * @param jugador
	 * @param valor
	 */
	public EstadoOro(Jugador jugador, int valor) {
		super(jugador);
		this.numeroInmune = valor;
		this.estado = String.valueOf(numeroInmune);
	}

	/**
	 * Otorga un estado de oro al jugador, que lo hace inmune a una cantidad de
	 * tubos determinados.
	 * 
	 * @param jugador
	 */
	public EstadoOro(Jugador jugador) {
		this(jugador, 5);
	}

	@Override
	public void hacerDano(int dano, int tipo) {
		if (tipo == Elemento.TIPO_TUBO) {
			if (numeroInmune <= 1)
				jugador.setEstado(new EstadoJugador(jugador));
			numeroInmune--;
			this.estado = String.valueOf(numeroInmune);
		} else
			super.hacerDano(dano, tipo);
	}

	@Override
	public String toString() {
		return super.toString() + " " + numeroInmune;
	}

	/**
	 * Dar un valor de inmunidad.
	 * 
	 * @param valor
	 */
	public void setValor(int valor) {
		this.numeroInmune = valor;
	}
}
