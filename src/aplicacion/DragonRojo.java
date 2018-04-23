package aplicacion;

import java.io.Serializable;

public class DragonRojo extends DragonVerde {

	private FlappyBird app;
	private int veces;

	/**
	 * Crear un dragon rojo, que va la direccion de los pajaros, escupen bolas de
	 * fuego que avanzan a velocidades aleatorias.
	 */
	public DragonRojo() {
		super();
		this.color = 0;
		this.posicionY = -200;
		this.veces = 20;
	}

	@Override
	public void mover(int distancia) {
		posicionY += distancia;
		this.altura = (int) (Math.sin(Math.toRadians(posicionY / 10)) * 800);
		if (veces == 50 && 0 < this.getAltura() && this.getAltura() < FlappyBird.ALTO - 200) {
			BolaFuego e = new BolaFuego();
			e.setAltura(this.getAltura());
			e.setPosicionY(this.getPosicionY() + 100);
			int i = 0;
			while (i < app.getElementos().size() && (app.getElementos().get(i).getPosicionY() < e.getPosicionY()
					|| app.getElementos().get(i) == this)) {
				i++;
			}
			app.adicioneObstaculoAtras(e);
			app.getElementos().add(i, e);
			app.buscarPrimeros();
			veces = 0;
		}
		veces++;
	}

	@Override
	public Elemento nuevo() {
		return new DragonRojo();
	}

	@Override
	public boolean isAtras() {
		return true;
	}

	/**
	 * Dar la aplicacion al dragon.
	 * 
	 * @param app.
	 */
	public void setApp(FlappyBird app) {
		this.app = app;
	}

}
