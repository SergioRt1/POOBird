package aplicacion;

import java.io.Serializable;

public abstract class EstadoApertura implements Serializable {

	/**
	 * Mover un tubo segun su estado de apertura.
	 * 
	 * @param distancia,
	 *            cantidad que se movera verticalmente.
	 */
	abstract void mover(int distancia);
}
