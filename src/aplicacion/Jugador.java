package aplicacion;

import java.io.Serializable;

public class Jugador implements Serializable {
	private int posicionX;
	private int posicionY;
	private int xMaximo;
	private int vida;
	private int puntaje;
	private int color;
	private float incremento;
	public static final int ANCHO = 50;
	public static final int ALTO = 30;
	private EstadoJugador estado;

	/**
	 * Describe un jugador en el mundo del juego.
	 * 
	 * @param xInicial
	 *            posicion inicial en x del jugador.
	 * @param yInicial
	 *            posicion inicial en y del jugador.
	 */
	public Jugador(int xInicial, int yInicial) {
		this.posicionX = xInicial;
		this.posicionY = yInicial;
		this.vida = 100;
		this.puntaje = 0;
		this.incremento = 0;
		this.estado = new EstadoJugador(this);
	}

	/**
	 * Mueve al jugador una distancia en X.
	 * 
	 * @param distancia
	 *            distancia que se va a mover.
	 */
	public void cambiarPosicionX(int distancia) {
		posicionX = Math.max(0, posicionX + distancia);
		if (posicionX > FlappyBird.ALTO) {
			posicionX = FlappyBird.ALTO;
			hacerDano(1, Elemento.TIPO_PISO);
		}
	}

	/**
	 * Aumenta el atributo incremento del jugador en 0.1 si es mayor a 3 lo reinicia
	 * a 0, esto para el manejo de los sprites.
	 */
	public void incrementar() {
		if ((incremento += 0.1) > 3) {
			incremento = 0;
		}
	}

	/**
	 * Efectua un daño al jugador.
	 * 
	 * @param dano
	 *            Valor que se le va ha hacer daño al jugador.
	 * @param tipo
	 *            Elemento que le hizo daño al jugador.
	 */
	public void hacerDano(int dano, int tipo) {
		estado.hacerDano(dano, tipo);
	}

	/**
	 * Aumenta en uno el puntaje actual del jugador.
	 */
	public void addPuntaje() {
		puntaje++;

	}

	/**
	 * Hace subir al personaje segun su estado.
	 * 
	 * @param i
	 *            Valor que va a subir el jugador (negativo).
	 */
	public void subir(int i) {
		estado.subir(i);

	}

	/**
	 * Indica si el jugador se encuentra vivo.
	 * 
	 * @return true si esta vivo, de lo contrario false.
	 */
	public boolean isVivo() {
		return vida > 0 || posicionX != FlappyBird.ALTO;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + posicionX + " " + posicionY + " " + xMaximo + " " + vida + " "
				+ puntaje + " " + color + " " + incremento + " " + estado.toString();
	}

	/**
	 * Obtener el valor del atributo incremento, para el manejo de los sprites.
	 * 
	 * @return incremento.
	 */
	public float getIncremento() {
		return this.incremento;

	}

	/**
	 * Obtener el puntaje actual del jugador.
	 * 
	 * @return puntaje.
	 */
	public int getPuntaje() {
		return puntaje;
	}

	/**
	 * Obtener el color del jugador.
	 * 
	 * @return int, valor de la fila en los sprites que indica el color del jugador.
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * Obtener la posicion en Y del jugador.
	 * 
	 * @return posicionY.
	 */
	public int getY() {
		return posicionY;
	}

	/**
	 * Obtener la posicion en X del jugador.
	 * 
	 * @return posicionX.
	 */
	public int getX() {
		return posicionX;
	}

	/**
	 * Obtener el vertice de la parabola.
	 * 
	 * @return xMaximo, valor de la cima de la parabola de la caida.
	 */
	public int getXMaximo() {
		return xMaximo;
	}

	/**
	 * Obtener la vida del jugador.
	 * 
	 * @return vida.
	 */
	public int getVida() {
		return vida;
	}

	/**
	 * Obtener el estado actual del jugador.
	 * 
	 * @return estado.
	 */
	public EstadoJugador getEstado() {
		return estado;
	}

	/**
	 * Dar un nuevo valor a la vida del jugador.
	 * 
	 * @param vida.
	 */
	public void setVida(int vida) {
		this.vida = vida;

	}

	/**
	 * Dar una nueva posicion en X al jugador.
	 * 
	 * @param x.
	 */
	public void setPosicionX(int x) {
		this.posicionX = x;
	}

	/**
	 * Dar una nueva posicion en Y al jugador.
	 * 
	 * @param y.
	 */
	public void setPosicionY(int y) {
		this.posicionY = y;
	}

	/**
	 * Dar un nuevo color al jugador.
	 * 
	 * @param color,
	 *            valor de la fila en el sprite para el nuevo color del jugador.
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Dar un nuevo estado al jugador.
	 * 
	 * @param estadoJugador.
	 */
	public void setEstado(EstadoJugador estadoJugador) {
		this.estado = estadoJugador;
	}

	/**
	 * Dar un nuevo valor al vertice de la parabola.
	 * 
	 * @param x
	 *            nuevo valor de la cima de la parabola de la caida.
	 */
	public void setXMaximo(int x) {
		this.xMaximo = x;
	}

	/**
	 * Dar un nuevo valor al atributo incremento, para el manejo de los sprites.
	 * 
	 * @param incremento.
	 */
	public void setIncremento(int incremento) {
		this.incremento = incremento;
	}

	/**
	 * Dar un nuevo puntaje al jugador.
	 * 
	 * @param puntaje.
	 */
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
}
