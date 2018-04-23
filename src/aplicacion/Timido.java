package aplicacion;

public class Timido extends Maquina {

	/**
	 * Crear una maquina timida en una posicion dada, que busca terminar el juego lo
	 * antes posible estrellandose con los tubos.
	 * 
	 * @param xInicial
	 * @param yInicial
	 * @param app
	 */
	public Timido(int xInicial, int yInicial, FlappyBird app) {
		super(xInicial, yInicial, app);
	}

	@Override
	boolean isSaltarMaquina() {
		return this.getX() > 850;
	}

}
