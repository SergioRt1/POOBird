package aplicacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.text.AbstractDocument.ElementEdit;

public class FlappyBird implements Serializable {

	private LinkedList<Elemento> elementos;
	private ArrayList<Jugador> jugadores;
	private static final int gravedad = 2;
	private int veces;
	private int valor;
	private float velocidadJuego;
	private int dificultad;
	private Elemento primerElemento;
	private Elemento segundoElemento;
	public static final int ANCHO = 1778;
	public static final int ALTO = 1000;
	private ArrayList<Elemento> posiblesObstaculos;
	private ArrayList<Elemento> posiblesElementos;
	private ArrayList<Elemento> obstaculosAtras;
	private Elemento[] ultimosElementos;
	private boolean dragonRojo;
	private int numeroDeObstaculos;
	private String perfilMaquina;
	private boolean isMaquina;

	/**
	 * Constructor de la clase FlappyBird.
	 * 
	 * @param elementos,
	 *            Lista con valores de verdad de los elementos a poner en el juego.
	 * @param perfil,
	 *            Nombre del perfil que se usara en la maquina.
	 */
	public FlappyBird(boolean[] elementos, String perfil) {
		this();
		jugadores.add(new Jugador(50, 50));
		isMaquina = false;
		perfilMaquina = perfil;
		if (elementos[0]) {
			jugadores.add(new Jugador(120, 50));
		} else if (elementos[1]) {
			switch (perfilMaquina) {
			case "Timido":
				jugadores.add(new Timido(120, 50, this));
				break;
			case "Equilibrado":
				jugadores.add(new Equilibrado(120, 50, this));
				break;
			case "Astuto":
				jugadores.add(new Astuto(120, 50, this));
				break;
			case "Mimo":
				jugadores.add(new Mimo(120, 50, this));
				break;
			}
			isMaquina = true;
		}
		if (elementos[11]) {
			for (int i = 0; i < elementos.length; i++) {
				elementos[i] = Math.random() < 0.5;
			}
		}
		if (elementos[10])
			posiblesObstaculos.add(new DragonVerde());
		if (elementos[9])
			dragonRojo = true;
		if (elementos[7])
			posiblesElementos.add(new MonedaPlata());
		if (elementos[6])
			posiblesElementos.add(new MonedaOro());
		if (elementos[8])
			posiblesElementos.add(new MonedaAlgodon());
		if (elementos[5])
			posiblesObstaculos.add(new TuboMagenta());
		if (elementos[4])
			posiblesObstaculos.add(new TuboAzul());
		if (elementos[3])
			posiblesObstaculos.add(new TuboRojo());
		if (elementos[2])
			posiblesObstaculos.add(new Tubo());

	}

	/**
	 * Constructor de la clase FlappyBird.
	 * 
	 * @param elementos,
	 *            Lista con valores de verdad de los elementos a poner en el juego.
	 */
	public FlappyBird(boolean[] elementos) {
		this(elementos, "Ninguno");
	}

	public FlappyBird() {
		jugadores = new ArrayList<Jugador>();
		elementos = new LinkedList<Elemento>();
		posiblesElementos = new ArrayList<Elemento>();
		posiblesObstaculos = new ArrayList<Elemento>();
		obstaculosAtras = new ArrayList<Elemento>();
		ultimosElementos = new Elemento[2];
		veces = 0;
		numeroDeObstaculos = 0;
		valor = 70;
		velocidadJuego = 8;
		dificultad = 50;
		dragonRojo = false;
	}

	/**
	 * Hace subir al jugador.
	 * 
	 * @param i,
	 *            indice del jugador, de la lista jugadores, que se esta subiendo.
	 */
	public void subir(int i) {
		jugadores.get(i).subir(-25);
	}

	/**
	 * Indica si el jugador dado esta subiendo.
	 * 
	 * @param i,
	 *            indice del jugador.
	 * @return boolen, true en caso de que el jugador i-esimo este subiendo, false
	 *         en cualquier otro caso.
	 */
	public boolean isSubiendo(int i) {
		return -25 < jugadores.get(i).getXMaximo() && jugadores.get(i).getXMaximo() < -22;
	}

	/**
	 * Avanza una unidad de tiempo en la aplicacion.
	 * 
	 * @throws FlappyException
	 */
	public void next() throws FlappyException {
		if (posiblesObstaculos.isEmpty())
			throw new FlappyException(FlappyException.OBTACULOS_VACIO);
		for (Jugador j : jugadores) {
			j.setXMaximo(j.getXMaximo() + gravedad);
			j.cambiarPosicionX(j.getXMaximo());
		}
		veces %= 2147483647;
		if (valor > 64 * 8 / velocidadJuego && !posiblesObstaculos.isEmpty()) {
			valor = 0;
			agregarElemento(posiblesObstaculos);
			numeroDeObstaculos++;
		}

		if (numeroDeObstaculos % 30 == 0 && valor > 64 * 4 / velocidadJuego && !posiblesElementos.isEmpty()) {
			agregarElemento(posiblesElementos);
			numeroDeObstaculos++;
		} else if (dragonRojo && numeroDeObstaculos == 90) {
			DragonRojo d = new DragonRojo();
			d.setApp(this);
			obstaculosAtras.clear();
			obstaculosAtras.add(d);
			elementos.addFirst(d);
			numeroDeObstaculos = 0;
		}
		if ((veces % (int) (Math.tan(toRadians(dificultad)) * 30)) == 0) {
			velocidadJuego += 0.1;
		}
		veces++;
		valor++;
		moverElementos();
		incrementar();
		if (primerElemento == null) {
			buscarPrimeros();
		}
		primeros();
		colicion(primerElemento);
		for (Elemento e : obstaculosAtras) {
			colicion(e);
		}
	}

	/**
	 * Convertir a radianes.
	 * 
	 * @param dificultad,
	 *            valor a convertir.
	 * @return double, valor en radianes.
	 */
	private double toRadians(int dificultad) {
		return Math.PI * dificultad / 200;
	}

	/**
	 * Aumenta el atributo incremento de los elementos y jugadores para avanzar en
	 * las columnas de los sprites.
	 */
	private void incrementar() {
		for (Elemento e : elementos) {
			e.incrementar();
		}
		for (Jugador j : jugadores) {
			j.incrementar();
		}
	}

	/**
	 * Mueve todos los elementos.
	 */
	private void moverElementos() {
		for (int i = 0; i < elementos.size(); i++) {
			elementos.get(i).mover((int) velocidadJuego);
		}
	}

	/**
	 * Agrega un nuevo elemento de forma aleatoria, y en caso de que un elemento de
	 * la lista este por fuera del tablero se remueve.
	 * 
	 * @param a,
	 *            lista de elementos actuales.
	 */
	public void agregarElemento(ArrayList<Elemento> a) {
		elementos.add(elementoAleatorio(a));
		while (elementos.getFirst().getPosicionY() < -200
				|| elementos.getFirst().getPosicionY() > FlappyBird.ANCHO + 1) {
			elementos.removeFirst();
		}

	}

	/**
	 * Da un elemento de una lista de forma aleatoria.
	 * 
	 * @param a,
	 *            lista de elementos.
	 * @return Elemento.
	 */
	private Elemento elementoAleatorio(ArrayList<Elemento> a) {
		int i = (int) (Math.random() * (a.size() * a.size() - 1));
		return a.get((int) Math.sqrt(i)).nuevo();
	}

	/**
	 * Verificar si hay colicion entre un jugador y un elemento.
	 */
	public void colicion(Elemento o) {
		int dy = Jugador.ANCHO;
		int dx = Jugador.ALTO;
		for (int i = 0; i < jugadores.size(); i++) {
			int yElemento = o.getPosicionY();
			int aElemento = o.getAncho() + yElemento;
			int hElemento = o.getAltura();
			int sElemento = o.getEspacio() + hElemento;
			int xJugador = jugadores.get(i).getX() + 18;
			int yJugador = jugadores.get(i).getY() + 10;
			if (yElemento <= yJugador + dy && yJugador + dy <= aElemento
					&& (!(o.getTipo() == Elemento.TIPO_TUBO) ^ (sElemento <= xJugador || xJugador <= hElemento))) {
				if (ultimosElementos[i] != o) {
					o.colicion(jugadores.get(i));
					ultimosElementos[i] = o;
				}
			} else if (yElemento <= yJugador + dy && yJugador + dy <= aElemento && (!(o.getTipo() == Elemento.TIPO_TUBO)
					^ (sElemento <= xJugador + dx || xJugador + dx <= hElemento))) {
				if (ultimosElementos[i] != o) {
					o.colicion(jugadores.get(i));
					ultimosElementos[i] = o;
				}
			} else if (yElemento <= yJugador && yJugador <= aElemento
					&& (!(o.getTipo() == Elemento.TIPO_TUBO) ^ (sElemento <= xJugador || xJugador <= hElemento))) {
				if (ultimosElementos[i] != o) {
					o.colicion(jugadores.get(i));
					ultimosElementos[i] = o;
				}
			} else if (yElemento <= yJugador && yJugador <= aElemento && (!(o.getTipo() == Elemento.TIPO_TUBO)
					^ (sElemento <= xJugador + dx || xJugador + dx <= hElemento))) {
				if (ultimosElementos[i] != o) {
					o.colicion(jugadores.get(i));
					ultimosElementos[i] = o;
				}
			}
		}
	}

	/**
	 * Busca los primeros elementos, valida si ya paso el primer elemento y
	 * actualiza el puntaje del jugador.
	 */
	private void primeros() {
		if (jugadores.get(0).getY() - (primerElemento.getPosicionY() + primerElemento.getAncho()) < velocidadJuego / 2
				&& jugadores.get(0).getY()
						- (primerElemento.getPosicionY() + primerElemento.getAncho()) >= -velocidadJuego / 2) {
			buscarPrimeros();
			for (int i = 0; i < jugadores.size() && primerElemento.getTipo() == Elemento.TIPO_TUBO; i++)
				if (jugadores.get(i).isVivo())
					jugadores.get(i).addPuntaje();
		}
	}

	/**
	 * Busca los dos primeros elementos proximos al jugador.
	 */
	public void buscarPrimeros() {
		int i = 0;
		while (i < elementos.size() && (jugadores.get(0).getY()
				- (elementos.get(i).getPosicionY() + elementos.get(i).getAncho()) >= -velocidadJuego / 2
				|| !elementos.get(i).isActivo() || elementos.get(i).isAtras())) {
			i++;
		}
		primerElemento = elementos.get(i);
		segundoElemento = elementos.get(Math.min(i + 1, elementos.size() - 1));
	}

	/**
	 * Crea una linea a la altura del proximo elemento.
	 *
	 * @return int, valor en X que le indica a la maquina donde debe saltar.
	 */
	public int lineaGuia() {
		int x = 0;
		int dx = 55;
		int salto = 130;
		if (!elementos.isEmpty()) {
			Jugador jugador = jugadores.get(1);
			if ((primerElemento.getTipo() == Elemento.TIPO_DRAGON)) {

				int abajo = primerElemento.getAltura() + primerElemento.getEspacio() + Jugador.ALTO + salto + dx;
				int arriba = primerElemento.getAltura() - Jugador.ALTO - dx;
				if ((segundoElemento.getTipo() == Elemento.TIPO_DRAGON)
						^ (primerElemento.altura + primerElemento.getEspacio() / 2 < segundoElemento.getAltura()
								+ segundoElemento.getEspacio() / 2)) {
					if (primerElemento.getTipo() == Elemento.TIPO_DRAGON && jugador.getX() < primerElemento.getAltura()
							&& primerElemento.getPosicionY() - dx < jugador.getY() + Jugador.ANCHO
							&& jugador.getY() < primerElemento.getPosicionY() + dx + primerElemento.getAncho())
						x = arriba;
					else
						x = abajo;
				} else {
					if (primerElemento.getTipo() == Elemento.TIPO_DRAGON
							&& jugador.getX() > primerElemento.getAltura() + primerElemento.getEspacio()
							&& primerElemento.getPosicionY() - dx < jugador.getY() + Jugador.ANCHO
							&& jugador.getY() < primerElemento.getPosicionY() + dx + primerElemento.getAncho())
						x = abajo;
					else
						x = arriba;
				}

			} else
				x = primerElemento.altura + primerElemento.getEspacio() - Jugador.ALTO - dx;
			if (segundoElemento.getTipo() == Elemento.TIPO_DRAGON
					&& segundoElemento.getPosicionY() - jugador.getY() + Jugador.ANCHO < 180) {
				int difx = segundoElemento.getAltura() + segundoElemento.getEspacio() / 2
						- (jugador.getX() + Jugador.ALTO / 2);
				if (-150 < difx && difx < -95)
					x = FlappyBird.ALTO;
				if (-95 < difx && difx < 150)
					x = 0;
			}
			if (primerElemento.getTipo() == Elemento.TIPO_DRAGON
					&& primerElemento.getPosicionY() - jugador.getY() + Jugador.ANCHO < 180) {
				int difx = primerElemento.getAltura() + primerElemento.getEspacio() / 2
						- (jugador.getX() + Jugador.ALTO / 2);
				if (-150 < difx && difx < -95)
					x = FlappyBird.ALTO;
				if (-95 < difx && difx < 150)
					x = 0;
			}
		}
		return x;
	}

	/**
	 * Abrir un archivo con extension .dat para recuperar un juego.
	 * 
	 * @param selectedFile
	 * @return FlappyBird, juego guardado.
	 * @throws FlappyException
	 */
	public static FlappyBird abrir(File selectedFile) throws FlappyException {
		try {
			String path;
			if (selectedFile.getPath().contains(".")) {
				if (!Pattern.compile(".*.dat$").matcher(selectedFile.getPath()).matches())
					throw new FlappyException(FlappyException.EXTENSION_INVALIDA);
				path = selectedFile.getPath();
			} else {
				path = selectedFile.getPath() + ".dat";
			}
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			FlappyBird f = (FlappyBird) in.readObject();
			in.close();
			return f;
		} catch (FileNotFoundException e) {
			throw new FlappyException(FlappyException.ARCHIVO_NO_ENCONTRADO);
		} catch (IOException e) {
			throw new FlappyException(FlappyException.ERROR_ABRIR_ARCHIVO);
		} catch (ClassNotFoundException e) {
			throw new FlappyException(FlappyException.CLASE_INVALIDA);
		}
	}

	/**
	 * Importar un archivo .txt para recuperar un juego.
	 * 
	 * @param selectedFile
	 * @return FlappyBird, juego importado.
	 * @throws FlappyException
	 */
	public static FlappyBird importar(File selectedFile) throws FlappyException {
		FlappyBird f = new FlappyBird();
		ArrayList<String> errores = new ArrayList<String>();
		String path;
		if (selectedFile.getPath().contains(".")) {
			if (!Pattern.compile(".*.txt$").matcher(selectedFile.getPath()).matches())
				throw new FlappyException(FlappyException.EXTENSION_INVALIDA);
			path = selectedFile.getPath();
		} else {
			path = selectedFile.getPath() + ".txt";
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String line;
			int nLinea = 0;
			String[] linea;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				nLinea++;
				linea = line.split(" ");
				if (!line.equals("Jugadores"))
					errores.add(String.format(FlappyException.LINEA_INVALIDA, nLinea, linea.length, linea[0]));
				try {
					while ((line = in.readLine()) != null && !line.equals("Elementos")) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						if ((linea.length == 9 || (linea.length == 10 && linea[8].equals("EstadoOro")))
								&& (linea[0].equals("Jugador") || linea[0].equals("Astuto")
										|| linea[0].equals("Equilibrado") || linea[0].equals("Timido")
										|| linea[0].equals("Mimo")))
							f.adicioneJugador(importarJugador(linea, f));
						else if (linea[0].equals("") && linea.length == 1)
							errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));
						else
							errores.add(String.format(FlappyException.LINEA_INVALIDA, nLinea, linea.length, linea[0]));
					}
					nLinea++;
					if (line == null)
						errores.add(String.format(FlappyException.ERROR_FATAL, nLinea));
					while ((line = in.readLine()) != null && !line.equals("Obstaculos posibles")) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						if (linea.length == 8 || (linea.length == 9 && (linea[0].equals("MonedaOro")
								|| linea[0].equals("MonedaPlata") || linea[0].equals("MonedaAlgodon"))))
							f.adicioneElemento(importarElemento(linea, f));
						else if (linea[0].equals("") && linea.length == 1)
							errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));
						else
							errores.add(String.format(FlappyException.LINEA_INVALIDA, nLinea, linea.length, linea[0]));
					}
					nLinea++;
					if (line == null)
						errores.add(String.format(FlappyException.ERROR_FATAL, nLinea));
					while ((line = in.readLine()) != null && !line.equals("Obstaculos elementos")) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						if (linea.length == 8 || (linea.length == 9 && (linea[0].equals("MonedaOro")
								|| linea[0].equals("MonedaPlata") || linea[0].equals("MonedaAlgodon"))))
							f.adicioneObstaculoPosible(importarElemento(linea, f));
						else if (linea[0].equals("") && linea.length == 1)
							errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));
						else
							errores.add(String.format(FlappyException.LINEA_INVALIDA, nLinea, linea.length, linea[0]));
					}
					nLinea++;
					if (line == null)
						errores.add(String.format(FlappyException.ERROR_FATAL, nLinea));
					while ((line = in.readLine()) != null && !line.equals("Datos")) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						if (linea.length == 8 || (linea.length == 9 && (linea[0].equals("MonedaOro")
								|| linea[0].equals("MonedaPlata") || linea[0].equals("MonedaAlgodon"))))
							f.adicioneElementosPosible(importarElemento(linea, f));
						else if (linea[0].equals("") && linea.length == 1)
							errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));
						else
							errores.add(String.format(FlappyException.LINEA_INVALIDA, nLinea, linea.length, linea[0]));
					}
					nLinea++;
					if (line == null)
						errores.add(String.format(FlappyException.ERROR_FATAL, nLinea));
					if ((line = in.readLine()) != null) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						f.setVelocidad(Float.parseFloat(linea[1]));
						if (!linea[0].equals("Velocidad"))
							errores.add(String.format(FlappyException.FORMATO_INVALIDO, nLinea));
					} else
						errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));
					if ((line = in.readLine()) != null) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						f.setMaquina(Boolean.parseBoolean(linea[1]));
						if (!linea[0].equals("Maquina"))
							errores.add(String.format(FlappyException.FORMATO_INVALIDO, nLinea));
					} else
						errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));
					if ((line = in.readLine()) != null) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						f.setDificultad(Integer.parseInt(linea[1]));
						if (!linea[0].equals("Dificultad"))
							errores.add(String.format(FlappyException.FORMATO_INVALIDO, nLinea));
					} else
						errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));
					if ((line = in.readLine()) != null) {
						line = line.trim();
						nLinea++;
						linea = line.split(" ");
						f.setDragonRojo(Boolean.parseBoolean(linea[1]));
						if (!linea[0].equals("DragonRojo"))
							errores.add(String.format(FlappyException.FORMATO_INVALIDO, nLinea));
					} else
						errores.add(String.format(FlappyException.LINEA_EN_BLANCO, nLinea));

				} catch (InstantiationException e) {
					e.printStackTrace();
					errores.add(FlappyException.ERROR_IMPORTAR);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					errores.add(FlappyException.ERROR_IMPORTAR);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					errores.add(String.format(FlappyException.ARGUMENTO_INVALIDO, nLinea));
				} catch (InvocationTargetException e) {
					errores.add(FlappyException.ERROR_IMPORTAR);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					errores.add(FlappyException.ERROR_IMPORTAR);
				} catch (SecurityException e) {
					e.printStackTrace();
					errores.add(FlappyException.ERROR_IMPORTAR);
				} catch (ClassNotFoundException e) {
					errores.add(String.format(FlappyException.CLASE_INVALIDA, nLinea, linea[0]));
				} catch (IndexOutOfBoundsException e) {
					errores.add(String.format(FlappyException.FORMATO_INVALIDO, nLinea));
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			errores.add(FlappyException.ARCHIVO_NO_ENCONTRADO);
			e.printStackTrace();
		} catch (IOException e) {
			errores.add(FlappyException.ERROR_IMPORTAR);
			e.printStackTrace();
		}
		if (!errores.isEmpty()) {
			try {
				String subPath = path.substring(0, path.length() - 4);
				PrintWriter out = new PrintWriter(new FileWriter(subPath + "_FlappyErr.txt"));
				for (String i : errores) {
					out.println(i);
				}
				out.close();
			} catch (IOException e) {
				throw new FlappyException(FlappyException.ERROR_IMPORTAR);
			}
			throw new FlappyException(FlappyException.ERROR_IMPORTAR);
		} else {
			return f;
		}
	}

	/**
	 * Crear un nuevo elemento dada una cadena, que viene de leer una linea de texto
	 * de un archivo.
	 * 
	 * @param linea,
	 *            cadena que contiene la informacion para crear un nuevo elemento.
	 * @param f
	 * @return elemento
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	private static Elemento importarElemento(String[] linea, FlappyBird f)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Elemento e = (Elemento) Class.forName("aplicacion." + linea[0]).getConstructor().newInstance();
		e.setAltura(Integer.parseInt(linea[1]));
		e.setPosicionY(Integer.parseInt(linea[2]));
		e.setDano(Integer.parseInt(linea[3]));
		e.setAncho(Integer.parseInt(linea[4]));
		e.setEspacio(Integer.parseInt(linea[5]));
		e.setColor(Integer.parseInt(linea[6]));
		e.setIncremento((int) Float.parseFloat(linea[7]));
		if (linea[0].equals("DragonRojo"))
			((DragonRojo) e).setApp(f);
		if (linea.length == 9)
			((Moneda) e).setActivo(Boolean.parseBoolean(linea[8]));
		return e;
	}

	/**
	 * Crear un nuevo jugador dada una cadena, que viene de leer una linea de texto
	 * de un archivo.
	 * 
	 * @param linea,
	 *            cadena que contiene la informacion para crear un nuevo jugador.
	 * @param f
	 * @return Jugador
	 * @throws NumberFormatException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	private static Jugador importarJugador(String[] linea, FlappyBird f)
			throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Jugador j;
		if (linea[0].equals("Jugador")) {
			j = (Jugador) Class.forName("aplicacion." + linea[0]).getConstructor(int.class, int.class)
					.newInstance(Integer.parseUnsignedInt(linea[1]), Integer.parseUnsignedInt(linea[2]));
		} else {
			j = (Jugador) Class.forName("aplicacion." + linea[0]).getConstructor(int.class, int.class, FlappyBird.class)
					.newInstance(Integer.parseUnsignedInt(linea[1]), Integer.parseUnsignedInt(linea[2]), f);
		}
		j.setXMaximo(Integer.parseInt(linea[3]));
		j.setVida(Integer.parseInt(linea[4]));
		j.setPuntaje(Integer.parseInt(linea[5]));
		j.setColor(Integer.parseInt(linea[6]));
		j.setIncremento((int) Float.parseFloat(linea[7]));
		j.setEstado(
				(EstadoJugador) Class.forName("aplicacion." + linea[8]).getConstructor(Jugador.class).newInstance(j));
		if (linea[8].equals("EstadoOro")) {
			((EstadoOro) j.getEstado()).setValor(Integer.parseInt(linea[9]));
		}
		return j;

	}

	/**
	 * Exportar un juego a un archivo .txt
	 * 
	 * @param selectedFile
	 * @throws FlappyException
	 */
	public void exportar(File selectedFile) throws FlappyException {
		try {
			String path;
			if (selectedFile.getPath().contains(".")) {
				if (!Pattern.compile(".*.txt$").matcher(selectedFile.getPath()).matches())
					throw new FlappyException(FlappyException.EXTENSION_INVALIDA);
				path = selectedFile.getPath();
			} else {
				path = selectedFile.getPath() + ".txt";
			}
			PrintWriter out = new PrintWriter(path);
			out.println("Jugadores");
			for (Jugador j : jugadores) {
				out.println(j.toString());
			}
			out.println("Elementos");
			for (Elemento e : elementos) {
				out.println(e.toString());
			}
			out.println("Obstaculos posibles");
			for (Elemento e : posiblesObstaculos) {
				out.println(e.toString());
			}
			out.println("Obstaculos elementos");
			for (Elemento e : posiblesElementos) {
				out.println(e.toString());
			}
			out.println("Datos");
			out.println("Velocidad " + velocidadJuego);
			out.println("Maquina " + isMaquina);
			out.println("Dificultad " + dificultad);
			out.println("DragonRojo " + dragonRojo);
			out.close();

		} catch (FileNotFoundException e) {
			throw new FlappyException(FlappyException.ARCHIVO_NO_ENCONTRADO);
		}

	}

	/**
	 * Salvar un juego en un archivo .dat
	 * 
	 * @param file
	 * @throws FlappyException
	 */
	public void salvarComo(File file) throws FlappyException {
		try {
			String path;
			if (file.getPath().contains(".")) {
				if (!Pattern.compile(".*.dat$").matcher(file.getPath()).matches())
					throw new FlappyException(FlappyException.EXTENSION_INVALIDA);
				path = file.getPath();
			} else {
				path = file.getPath() + ".dat";
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			throw new FlappyException(FlappyException.ERROR_GUARDAR_ARCHIVO);
		}

	}

	/**
	 * Agregar un jugador a la lista de jugadores.
	 * 
	 * @param jugador
	 */
	private void adicioneJugador(Jugador jugador) {
		jugadores.add(jugador);
	}

	/**
	 * Agregar un elemento a la lista de elementos.
	 * 
	 * @param elemento
	 */
	public void adicioneElemento(Elemento elemento) {
		elementos.add(elemento);
	}

	/**
	 * Agregar posibles obstaculos a la lista correspondiente.
	 * 
	 * @param elemento
	 */
	private void adicioneObstaculoPosible(Elemento elemento) {
		posiblesObstaculos.add(elemento);
	}

	/**
	 * Agregar elmentos a la lista de elementos que se encuentran atras del jugador.
	 * 
	 * @param elemento
	 */
	public void adicioneObstaculoAtras(Elemento elemento) {
		obstaculosAtras.add(elemento);
	}

	/**
	 * Agregar posibles elementos a la lista correspondiente.
	 * 
	 * @param elemento
	 */
	private void adicioneElementosPosible(Elemento elemento) {
		posiblesElementos.add(elemento);
	}

	/**
	 * Indica si hay una maquina jugando actualmente.
	 * 
	 * @return boolean, true si hay una maquina, false en otro caso.
	 */
	public boolean isMaquina() {
		return this.isMaquina;
	}

	/**
	 * Indica si la maquina debe saltar o no.
	 * 
	 * @return boolean, true en caso de que la maquina tenga que saltar, false en
	 *         otro caso.
	 */
	public boolean isSaltarMaquina() {
		return ((Maquina) this.jugadores.get(1)).isSaltarMaquina();
	}

	/**
	 * Crear archivo con los errores capturados.
	 * 
	 * @param e
	 */
	public void log(Exception e) {
		Registro.registre(e);
	}

	/**
	 * Obtener la lista de jugadores.
	 * 
	 * @return jugadores.
	 */
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	/**
	 * Obtener la lista de elementos.
	 * 
	 * @return elementos.
	 */
	public LinkedList<Elemento> getElementos() {
		return elementos;
	}

	/**
	 * Dar una nueva velocidad con la que se mueven los elementos.
	 * 
	 * @param velocidad
	 * 
	 */
	private void setVelocidad(float velocidad) {
		this.velocidadJuego = velocidad;

	}

	/**
	 * Indicar si hay una maquina en el juego o no.
	 * 
	 * @param isMaquina,
	 *            true si hay una maquina, false en otro caso.
	 */
	private void setMaquina(boolean isMaquina) {
		this.isMaquina = isMaquina;

	}

	/**
	 * Dar una nueva dificultad que indicara la frecuencia con que aumenta la
	 * velocidad.
	 * 
	 * @param value
	 */
	public void setDificultad(int value) {
		this.dificultad = value;
	}

	/**
	 * Indicar si hay un dragon rojo en el juego o no.
	 * 
	 * @param dragonRojo,
	 *            true si hay un dragon rojo, false en otro caso.
	 */
	private void setDragonRojo(boolean dragonRojo) {
		this.dragonRojo = dragonRojo;
	}
}
