package aplicacion;

public class Mimo extends Maquina {

	/**
	 * Crear una maquina mimo en una posicion dada, que imita el movimiento de su
	 * pareja.
	 * 
	 * @param xInicial
	 * @param yInicial
	 * @param app
	 */
	public Mimo(int xInicial, int yInicial, FlappyBird app) {
		super(xInicial, yInicial, app);
	}

	@Override
	boolean isSaltarMaquina() {
		return app.isSubiendo(0);
	}

}
