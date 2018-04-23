package aplicacion;

public class Astuto extends Maquina {

	/**
	 * Crear una maquina astuta en una posicion dada, que su prioridad es evitar
	 * chocar con los tubos.
	 * 
	 * @param xInicial
	 * @param yInicial
	 * @param app
	 */
	public Astuto(int xInicial, int yInicial, FlappyBird app) {
		super(xInicial, yInicial, app);
	}

	@Override
	boolean isSaltarMaquina() {
		return this.getX() > app.lineaGuia();
	}

}
