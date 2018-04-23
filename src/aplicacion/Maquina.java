package aplicacion;

public abstract class Maquina extends Jugador {

	protected FlappyBird app;

	/**
	 * Crear una nueva maquina con una posicion dada.
	 * 
	 * @param xInicial
	 * @param yInicial
	 * @param app
	 */
	public Maquina(int xInicial, int yInicial, FlappyBird app) {
		super(xInicial, yInicial);
		this.app = app;
	}

	/**
	 * Indica si la maquina debe saltar.
	 * 
	 * @return boolean
	 */
	abstract boolean isSaltarMaquina();

}
