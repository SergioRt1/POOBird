package aplicacion;

import java.io.Serializable;

public abstract class TuboEstados extends Tubo {

	protected int intervalo;
	protected EstadoApertura estado;

	/**
	 * Crear un tubo que le permite tener un estado de abrir y cerrar.
	 */
	public TuboEstados() {
		super();
	}

	@Override
	public void mover(int distancia) {
		posicionY -= distancia;
		estado.mover(distancia / 5);
		if (isAbrir()) {
			estado = new EstadoAbrir(this);
		} else if (isCerrar()) {
			estado = new EstadoCerrar(this);
		}

	}

	/**
	 * Indica si el tubo debe empezar a cerrarse.
	 * 
	 * @return boolean, true si el espacio entre los tubos ha superado su limite o
	 *         ha pasado el intervalo, en otro caso false.
	 */
	abstract boolean isCerrar();

	/**
	 * Indica si el tubo debe empezar a abrirse.
	 * 
	 * @return boolean, true si el espacio entre tubos es menor a cero o estos ya se
	 *         han cerrado, en otro caso false.
	 */
	abstract boolean isAbrir();

	/**
	 * Aumentar el espacio entre los dos tubos para que estos se abran.
	 * 
	 * @param distancia
	 */
	abstract void abrir(int distancia);

	/**
	 * Disminuir el espacio entre los dos tubos para que estos se cierren.
	 * 
	 * @param distancia
	 */
	abstract void cerrar(int distancia);
}
