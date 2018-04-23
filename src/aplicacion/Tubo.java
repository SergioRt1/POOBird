package aplicacion;

import java.io.Serializable;

public class Tubo extends Elemento {

	/**
	 * Crear un tubo verde o estandar.
	 */
	public Tubo() {
		super();
		this.altura = (int) (Math.random() * 300) + 225;
		this.espacio = 250;
		this.dano = 10;
		this.color = 4;
	}

	@Override
	Elemento nuevo() {
		return new Tubo();
	}

	@Override
	public int getTipo() {
		return Elemento.TIPO_TUBO;
	}

	@Override
	void colicion(Jugador jugador) {
		jugador.hacerDano(dano, getTipo());
		jugador.setIncremento(5);

	}

	/**
	 * Obtener los atributos del tubo.
	 * 
	 * @return ans, arreglo que contiene los atributos.
	 */
	public int[] getAtributos() {
		int[] ans = { altura, ancho, posicionY, espacio, dano };
		return ans;
	}

	/**
	 * Obtener el color del tubo.
	 * 
	 * @return color, numero que indica la fila en el sprite del tubo.
	 */
	public int getColor() {
		return color;
	}
}
