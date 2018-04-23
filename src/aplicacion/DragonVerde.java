package aplicacion;

public class DragonVerde extends Elemento {
	/**
	 * Crear dragon verde, que va en direccion contraria de los pajaros.
	 */
	public DragonVerde() {
		super();
		this.dano = 100;
		this.color = 1;
	}

	@Override
	public Elemento nuevo() {
		return new DragonVerde();
	}

	@Override
	void colicion(Jugador jugador) {
		jugador.hacerDano(dano, getTipo());
		jugador.setIncremento(4);
	}

	@Override
	public int getTipo() {
		return Elemento.TIPO_DRAGON;
	}

}
