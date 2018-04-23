package aplicacion;

import java.io.Serializable;

public class EstadoAbrir extends EstadoApertura {

	private TuboEstados tuboEspecial;

	/**
	 * Estado abrir tubo.
	 * 
	 * @param tuboEspecial
	 */
	public EstadoAbrir(TuboEstados tuboEspecial) {
		this.tuboEspecial = tuboEspecial;
	}

	@Override
	void mover(int distancia) {
		tuboEspecial.abrir(distancia);

	}

}
