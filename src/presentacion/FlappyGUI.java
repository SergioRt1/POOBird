package presentacion;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import aplicacion.FlappyBird;
import aplicacion.FlappyException;

public class FlappyGUI extends JFrame {

	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private FlappyBird app;
	public float incremento;
	private CardLayout layout;
	private JPanel principal;
	private PanelJuego panelJuego;
	private FinDeJuego finDeJuego;
	private int mejorPuntaje;
	private PanelOpciones panelOpciones;
	private PanelConfiguracion panelConfiguracion;
	protected HashMap<Integer, Color> valueColor;
	private JFileChooser chooser;
	private Sonido musicaFondo;
	private boolean yaSalvado;

	private FlappyGUI() {
		try {
			prepareElementos();
			prepareAcciones();
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ha surgido un error inesperado.", "Error",
					JOptionPane.WARNING_MESSAGE);
			app.log(e);
		}
	}

	private void prepareElementos() {
		setTitle("Flappybird");
		layout = new CardLayout();
		setMinimumSize(new Dimension(1280, 720));
		this.getContentPane().setLayout(new CardLayout());
		principal = new JPanel(layout);
		yaSalvado = false;
		add(principal);
		setSize(screenSize.width / 2 + 60, screenSize.height / 2 + 60);
		setLocationRelativeTo(null);
		app = new FlappyBird(new boolean[12]);
		chooser = new JFileChooser();
		principal.add(new PanelInicial(this), "Inicial");
		principal.add(new PanelPausa(this), "Pausa");
		panelConfiguracion = new PanelConfiguracion(this);
		principal.add(panelConfiguracion, "Configuracion");
		panelOpciones = new PanelOpciones(this);
		principal.add(panelOpciones, "Opciones");
		principal.add(new PanelInformacion(this), "Informacion");
		musicaFondo = new Sonido("/sonidos/fondoInicial.wav", Sonido.VOLUMEN_NORMAL);
		hashColor();
		irInicial();
		reproducirMusica();
	}

	public void setLayout(LayoutManager manager) {
		if (isRootPaneCheckingEnabled()) {
			getContentPane().setLayout(manager);
		} else {
			super.setLayout(manager);
		}
	}

	public int escalaWidth(int w) {
		return (int) w * getWidth() / 1778;
	}

	public int escalaHeight(int h) {
		return (int) h * getHeight() / 1000;
	}

	/**
	 * Define los oyentes respectivos del GUI
	 */
	private void prepareAcciones() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				salga();
			}
		});
	}

	protected void moverArriba(int i) {
		app.subir(i);

	}

	public void nuevoJuego(boolean isAbrir) {
		try {
			if (!isAbrir) {
				app = new FlappyBird(panelOpciones.getOpciones(), panelOpciones.getPerfil());
				app.getJugadores().get(0).setColor(panelConfiguracion.getConfiguracion()[0]);
			}

			if (app.getJugadores().size() == 2) {
				if (!isAbrir)
					app.getJugadores().get(1).setColor(panelConfiguracion.getConfiguracion()[1]);
				panelJuego = new PanelJuegoDosJugadores(this, app.isMaquina());
			} else
				panelJuego = new PanelJuego(this, app.isMaquina());
			principal.add(panelJuego, "Juego");
			irJuego();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ha surgido un error inesperado.", "Error",
					JOptionPane.WARNING_MESSAGE);
			app.log(e);
		}
	}

	protected void irFinDeJuego() {
		if (panelOpciones.getOpciones()[0])
			finDeJuego = new FinDeJuegoDosJugadores(this);
		else
			finDeJuego = new FinDeJuego(this);
		principal.add(finDeJuego, "Fin");
		irPanel("Fin");
	}

	public void irJuego() {
		try {
			pausarMusica();
			irPanel("Juego");
			panelJuego.iniciar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ha surgido un error inesperado.", "Error",
					JOptionPane.WARNING_MESSAGE);
			app.log(e);
		}
	}

	public void irInicial() {
		try {
			irPanel("Inicial");
			musicaFondo.repetir();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ha surgido un error inesperado.", "Error",
					JOptionPane.WARNING_MESSAGE);
			app.log(e);
		}
	}

	public void irPausa() {
		irPanel("Pausa");
	}

	public void irConfiguracion() {
		irPanel("Configuracion");
	}

	public void irOpciones() {
		irPanel("Opciones");
	}

	public void irInformacion() {
		irPanel("Informacion");
	}

	private void irPanel(String name) {
		layout.show(principal, name);
	}

	/**
	 * Realiza la operacion de cerrar la ventana de la aplicacion.
	 */

	public void salga() {
		if (JOptionPane.showConfirmDialog(null, "¿Esta seguro de salir de FlappyBird?", "Salir",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			setVisible(false);
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		FlappyGUI gui = new FlappyGUI();
		gui.setVisible(true);
	}

	public int getMejor() {
		return mejorPuntaje;
	}

	public int getYJugador(int i) {
		return app.getJugadores().get(i).getY();
	}

	public int getXJugador(int i) {
		return app.getJugadores().get(i).getX();
	}

	public int getSizeObstaculos() {
		return app.getElementos().size();
	}

	public int[] getAtributosObstaculo(int i) {
		return app.getElementos().get(i).getAtributos();
	}

	public int getVidaJugador(int i) {
		return app.getJugadores().get(i).getVida();
	}

	public void appNext() {
		try {
			app.next();
		} catch (FlappyException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Datos invalidos", JOptionPane.WARNING_MESSAGE);
			panelJuego.parar();
			app = new FlappyBird(new boolean[12]);
			irOpciones();
		}

	}

	public int getSizeJugadores() {
		return app.getJugadores().size();
	}

	public int[] getConfiguracion() {
		return panelConfiguracion.getConfiguracion();

	}

	public int getPuntaje(int i) {
		return app.getJugadores().get(i).getPuntaje();
	}

	public float getIncrementoJugador(int i) {
		return app.getJugadores().get(i).getIncremento();
	}

	public float getIncrementoElemento(int i) {
		return app.getElementos().get(i).getIncremento();
	}

	public int getColorElemento(int i) {
		return app.getElementos().get(i).getColor();
	}

	public Color getColor(int i) {
		return valueColor.get(app.getJugadores().get(i).getColor());
	}

	public int getColorJugador(int i) {
		return app.getJugadores().get(i).getColor();
	}

	public void setPuntaje(int puntaje) {
		mejorPuntaje = Math.max(mejorPuntaje, puntaje);
	}

	public boolean isJugadorVivo(int i) {
		return app.getJugadores().get(i).isVivo();
	}

	public void setDificultad() {
		app.setDificultad(panelConfiguracion.getConfiguracion()[2]);

	}

	public void importar() {
		int res = chooser.showOpenDialog(this);
		if (res == JFileChooser.APPROVE_OPTION) {
			try {
				app = FlappyBird.importar(chooser.getSelectedFile());
				nuevoJuego(true);
			} catch (FlappyException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\nImportar  archivo: " + chooser.getSelectedFile().getName());
			}
		}

	}

	public void abrir() {
		int res = chooser.showOpenDialog(this);
		if (res == JFileChooser.APPROVE_OPTION) {
			try {
				app = FlappyBird.abrir(chooser.getSelectedFile());
				nuevoJuego(true);
			} catch (FlappyException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\nSalvar  archivo: " + chooser.getSelectedFile().getName());
			}
		}

	}

	public void exportar() {
		int res = chooser.showSaveDialog(this);
		if (res == JFileChooser.APPROVE_OPTION) {
			try {
				app.exportar(chooser.getSelectedFile());
			} catch (FlappyException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\nExportar  archivo: " + chooser.getSelectedFile().getName());
			}
		}

	}

	public void salvar() {
		if (yaSalvado) {
			try {
				app.salvarComo(chooser.getSelectedFile());
			} catch (FlappyException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\nSalvar  archivo: " + chooser.getSelectedFile().getName());
			}
		} else {
			salvarComo();
		}
	}

	private void salvarComo() {
		int res = chooser.showSaveDialog(this);
		if (res == JFileChooser.APPROVE_OPTION) {
			try {
				app.salvarComo(chooser.getSelectedFile());
				yaSalvado = true;
			} catch (FlappyException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\nSalvar  archivo: " + chooser.getSelectedFile().getName());
			}
		}

	}

	private void hashColor() {
		valueColor = new HashMap<Integer, Color>();
		valueColor.put(0, Color.green);
		valueColor.put(1, Color.red);
		valueColor.put(2, Color.darkGray);
		valueColor.put(3, Color.cyan);

	}

	public int tipoElemento(int i) {
		return app.getElementos().get(i).getTipo();
	}

	public void reproducirMusica() {
		musicaFondo.repetir();
	}

	public void pausarMusica() {
		musicaFondo.pausar();
	}

	public void volumenMusica(float d) {
		musicaFondo.CambiarVolumen(d);
	}

	public boolean isVisibleElemento(int i) {
		return app.getElementos().get(i).isActivo();
	}

	public int getLineaGuia() {
		return app.lineaGuia();
	}

	public boolean isSaltarMaquina() {
		return app.isSaltarMaquina();
	}

	public String getTextEstado(int i) {
		return app.getJugadores().get(i).getEstado().getEstado();
	}

	public String getEstado(int i) {
		return app.getJugadores().get(i).getEstado().getClass().getSimpleName();
	}

}
