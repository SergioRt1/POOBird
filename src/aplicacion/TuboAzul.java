package aplicacion;

import java.io.Serializable;

public class TuboAzul extends Tubo {
	
	/**
	 * Crear un tubo azul, que se mueve de arriba a abajo junto con su abertura.
	 */
	public TuboAzul() {
		super();
		this.color = 1;
		this.altura = (int) (Math.random() * 120) + 80;
	}

	@Override
	public void mover(int distancia) {
		if (altura < FlappyBird.ALTO - espacio - 100)
			altura += 2;
		posicionY -= distancia;

	}

	@Override
	Elemento nuevo() {
		return new TuboAzul();
	}
}
