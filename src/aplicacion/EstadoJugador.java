package aplicacion;

import java.io.Serializable;

public class EstadoJugador implements Serializable {

	protected Jugador jugador;
	protected String estado;

	/**
	 * Otorga un estado natural al jugador.
	 * 
	 * @param jugador
	 */
	public EstadoJugador(Jugador jugador) {
		this.jugador = jugador;
		this.estado = "";
	}

	/**
	 * Hace subir al jugador.
	 * 
	 * @param i
	 *            cambia el vertice de la parabola.
	 */
	public void subir(int i) {
		jugador.setXMaximo(i);

	}

	/**
	 * Hace dano al jugador y en caso de que su vida sea menor a uno, cambia su
	 * estado a muerto.
	 * 
	 * @param dano
	 *            valor del dano.
	 * @param tipo
	 *            que obstaculo hace el dano.
	 */
	public void hacerDano(int dano, int tipo) {
		jugador.setVida(Math.min(jugador.getVida() - dano, 100));
		if (jugador.getVida() < 1)
			jugador.setEstado(new EstadoMuerto(jugador));

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Obtener estado.
	 * 
	 * @return estado.
	 */
	public String getEstado() {
		return this.estado;
	}
}
