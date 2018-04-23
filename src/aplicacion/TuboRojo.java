package aplicacion;

import java.io.Serializable;

public class TuboRojo extends TuboEstados {
	
	/**
	 * Crear un tubo rojo, que se cierra y abre despues de determinado tiempo y su
	 * apertura siempre es en el mismo punto.
	 */
	public TuboRojo() {
		super();
		this.espacio = 400;
		intervalo = espacio;
		this.color = 2;
		estado = new EstadoCerrar(this);
		int cambio = (int) (Math.random() * intervalo / 2) + 1;
		cerrar(cambio);
	}

	@Override
	public void abrir(int distancia) {
		espacio += 2 * distancia;
		altura -= distancia;

	}

	@Override
	public void cerrar(int distancia) {
		espacio -= 2 * distancia;
		altura += distancia;
	}

	@Override
	public boolean isCerrar() {
		return espacio > intervalo;
	}

	@Override
	public boolean isAbrir() {
		return espacio < 0;
	}

	@Override
	Elemento nuevo() {
		return new TuboRojo();
	}
}
