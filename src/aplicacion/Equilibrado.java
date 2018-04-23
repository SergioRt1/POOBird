package aplicacion;

public class Equilibrado extends Maquina {

	/**
	 * Crear una maquina equilibrada en una posicion dada, que busca mantener
	 * siempre su nivel de altitud.
	 * 
	 * @param xInicial
	 * @param yInicial
	 * @param app
	 */
	public Equilibrado(int xInicial, int yInicial, FlappyBird app) {
		super(xInicial, yInicial, app);
	}

	@Override
	boolean isSaltarMaquina() {
		return this.getX() > 450;
	}

}
