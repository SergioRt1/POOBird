package aplicacion;

import java.io.Serializable;
import java.security.GeneralSecurityException;

public abstract class Elemento implements Serializable {

	protected int altura;
	protected int posicionY;
	protected int dano;
	protected int ancho;
	protected int espacio;
	protected int color;
	protected float incremento;
	public static final int TIPO_TUBO = 0;
	public static final int TIPO_DRAGON = 1;
	public static final int TIPO_MONEDA = 2;
	public static final int TIPO_PISO = 3;

	/**
	 * Crear elemento con sus atributos predeterminados.
	 */
	public Elemento() {
		this.altura = (int) (Math.random() * 400) + 200;
		this.posicionY = FlappyBird.ANCHO;
		this.ancho = 150;
		this.espacio = 190;
		this.incremento = 0;
	}

	/**
	 * Mover el elemento una distancia dada sobre el eje Y.
	 * 
	 * @param distancia
	 */
	public void mover(int distancia) {
		posicionY -= distancia;
	}

	/**
	 * Aumenta el valor en el atributo incremento para recorrer los sprites.
	 */
	public void incrementar() {
		if ((this.incremento += 0.1) > 4) {
			this.incremento = 0;
		}
	}

	/**
	 * Saber si un elemento esta activo.
	 * 
	 * @return bool, en caso de estar activo true, en otro caso false.
	 */
	public boolean isActivo() {
		return true;
	}

	/**
	 * Indica si el elemento debe salir de izquierda a derecha en el tablero.
	 * 
	 * @return
	 */
	public boolean isAtras() {
		return false;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + altura + " " + posicionY + " " + dano + " " + ancho + " "
				+ espacio + " " + color + " " + incremento;
	}

	/**
	 * Obtener incremento.
	 * 
	 * @return incremento.
	 */
	public float getIncremento() {
		return this.incremento;
	}

	/**
	 * Obtener la posicion en X del elemento.
	 * 
	 * @return altura.
	 */
	public int getAltura() {
		return altura;
	}

	/**
	 * Obtener el ancho del elemento.
	 * 
	 * @return ancho.
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Obtener la posicion en Y del elemento.
	 * 
	 * @return posicionY.
	 */
	public int getPosicionY() {
		return posicionY;
	}

	/**
	 * Obtener el dano que hace el elemento.
	 * 
	 * @return dano.
	 */
	public int getDano() {
		return dano;
	}

	/**
	 * Obtener la altura del elemento.
	 * 
	 * @return espacio.
	 */
	public int getEspacio() {
		return espacio;
	}

	/**
	 * Obtener los atributos del elemento.
	 * 
	 * @return ans, arreglo que contiene los atributos.
	 */
	public int[] getAtributos() {
		int[] ans = { altura, ancho, posicionY, espacio };
		return ans;
	}

	/**
	 * Obtener el color del elemento.
	 * 
	 * @return int, numero que indica la fila en el sprite que se debe usar para dar
	 *         color al elemento.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Dar un valor al atributo incremento, para seleccionar una imagen en el
	 * sprite.
	 * 
	 * @param incremento.
	 */
	public void setIncremento(int incremento) {
		this.incremento = incremento;
	}

	/**
	 * Dar un nuevo valor a la altura del elemento.
	 * 
	 * @param altura,
	 *            nueva altura que tendra el elemento.
	 */
	public void setAltura(int altura) {
		this.altura = altura;
	}

	/**
	 * Dar una nueva posicion en Y al elemento.
	 * 
	 * @param posicionY.
	 */
	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}

	/**
	 * Dar una valor al atributo dano.
	 * 
	 * @param dano,
	 *            nuevo dano que hara el elemento.
	 */
	public void setDano(int dano) {
		this.dano = dano;
	}

	/**
	 * Dar un nuevo ancho al elemento.
	 * 
	 * @param ancho.
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * Dar una nueva altura al elemento.
	 * 
	 * @param espacio.
	 */
	public void setEspacio(int espacio) {
		this.espacio = espacio;
	}

	/**
	 * Dar un nuevo color al elemento.
	 * 
	 * @param color,
	 *            numero de la fila en el sprite que indicara el nuevo color.
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Conocer el tipo de elemento.
	 * 
	 * @return int, numero que indica el tipo de elemento que se tiene.
	 */
	public abstract int getTipo();

	/**
	 * Crear un nuevo elemento.
	 * 
	 * @return Elemento.
	 */
	abstract Elemento nuevo();

	/**
	 * Comportamiento que tiene cada elemento al colicionar con un jugador.
	 * 
	 * @param jugador,
	 *            jugador con el que colisiona el jugador.
	 */
	abstract void colicion(Jugador jugador);

}
