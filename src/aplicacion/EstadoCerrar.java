package aplicacion;

import java.io.Serializable;

public class EstadoCerrar extends EstadoApertura {

	private TuboEstados tuboEspecial;

	/**
	 * Estado cerrar tubo.
	 * 
	 * @param tuboEspecial
	 */
	public EstadoCerrar(TuboEstados tuboEspecial) {
		this.tuboEspecial = tuboEspecial;
	}

	@Override
	void mover(int distancia) {
		tuboEspecial.cerrar(distancia);

	}

}
