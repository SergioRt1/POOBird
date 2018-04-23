package aplicacion;

import java.io.Serializable;

public class TuboMagenta extends Tubo {
	
	/**
	 * Crear un tubo magenta, que se mueve de abajo a arriba junto con su abertura.
	 */
	public TuboMagenta() {
		super();
		this.color = 0;
		this.altura = (int) (Math.random() * 120) + 400;
	}

	@Override
	public void mover(int distancia) {
		if (altura > 100)
			altura -= 2;
		posicionY -= distancia;

	}

	@Override
	Elemento nuevo() {
		return new TuboMagenta();
	}
}
