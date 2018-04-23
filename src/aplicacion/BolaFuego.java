package aplicacion;

public class BolaFuego extends DragonRojo {
	
	/**
	 * Crear una bola de fuego.
	 */
	public BolaFuego() {
		super();
		this.color = 5;
	}

	@Override
	public Elemento nuevo() {
		return new BolaFuego();
	}

	@Override
	public void mover(int distancia) {
		posicionY -= distancia;
	}

	@Override
	public boolean isAtras() {
		return false;
	}

}
