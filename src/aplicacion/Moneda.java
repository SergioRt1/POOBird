package aplicacion;

import java.io.Serializable;

public abstract class Moneda extends Elemento {

	private boolean activo;

	/**
	 * Crear una moneda activa y con altura predeterminada.
	 */
	public Moneda() {
		super();
		this.activo = true;
		this.altura = FlappyBird.ALTO - 350;
	}

	@Override
	void colicion(Jugador jugador) {
		activar(jugador);
		jugador.setIncremento(3);
		this.activo = false;
	}

	@Override
	public boolean isActivo() {
		return activo;
	}

	@Override
	public String toString() {
		return super.toString() + " " + activo;
	}

	@Override
	public int getTipo() {
		return Elemento.TIPO_MONEDA;
	}

	/**
	 * Cambiar el valor del atributo activo.
	 * 
	 * @param activo.
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Activar u otorgar los beneficios de la moneda en el jugador.
	 * 
	 * @param personaje.
	 */
	abstract void activar(Jugador personaje);

	/**
	 * Crear una nueva moneda.
	 * 
	 * @return Moneda.
	 */
	abstract Moneda nuevo();

}
