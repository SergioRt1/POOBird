package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PanelJuego extends JPanel {
	private Image fondo;
	private ImageIcon personajes;
	private ImageIcon tuboArriba;
	private ImageIcon tuboAbajo;
	private ImageIcon spriteDragon;
	private Image dibujo;
	private Graphics2D g2d;
	protected FlappyGUI frame;
	private javax.swing.Timer reloj;
	private JProgressBar barra;
	private JPanel panel;
	protected JPanel barras;
	protected JPanel potenciadores;
	protected JPanel puntajes;
	private JButton pausa;
	private String contador;
	protected Sonido salto;
	private Sonido golpe;
	private Sonido musicaFondo;
	private Sonido moneda;
	private Sonido dragon;
	private Sonido gameOver;
	private JLabel puntajeJugador1;
	private JLabel potenciadorJugador1;
	
	private boolean maquina;

	public PanelJuego(FlappyGUI frame, boolean maquina) {
		this.frame = frame;
		prepareElementos();
		prepareAcciones();
		setFocusable(true);
		setOpaque(false);
		salto = new Sonido("/sonidos/salto.wav", frame.getConfiguracion()[3]);
		golpe = new Sonido("/sonidos/golpe.wav", frame.getConfiguracion()[3]);
		musicaFondo = new Sonido("/sonidos/fondoJuego.wav", frame.getConfiguracion()[3]);
		moneda = new Sonido("/sonidos/coin.wav", frame.getConfiguracion()[3]);
		dragon = new Sonido("/sonidos/roar.wav", frame.getConfiguracion()[3]);
		gameOver = new Sonido("/sonidos/gameOver.wav", frame.getConfiguracion()[3]);
		this.maquina = maquina;

	}

	private void prepareAcciones() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_UP) {
					frame.moverArriba(0);
					salto.reproducir(0);
				}
				if (key == KeyEvent.VK_W) {
					accionesExtra();
				}
				if (key == KeyEvent.VK_ESCAPE) {
					pausa();
				}
			}

		});
		pausa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pausa();
			}
		});

	}

	private void pausa() {
		reloj.stop();
		musicaFondo.pausar();
		frame.irPausa();
	}

	private void prepareElementos() {
		contador = "";
		frame.setDificultad();
		setLayout(new BorderLayout());
		fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/Fondo.png"));
		personajes = new ImageIcon(getClass().getResource("../img/sprite.png"));
		tuboAbajo = new ImageIcon(getClass().getResource("../img/tubosAbajo.png"));
		tuboArriba = new ImageIcon(getClass().getResource("../img/tubosArriba.png"));
		spriteDragon = new ImageIcon(getClass().getResource("../img/spriteElementos.png"));
		prepareHUD();
		reloj = new javax.swing.Timer(18, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refresque();
			}
		});
	}

	private void prepareHUD() {
		barra = new JProgressBar(0, 100);
		barra.setStringPainted(true);
		barra.setOpaque(false);
		barra.setFont(new Font("Cooper Black", Font.PLAIN, 20));
		barra.setForeground(frame.getColor(0));
		panel = new JPanel();
		barras = new JPanel(new GridLayout(2, 1, 20, 20));
		puntajes = new JPanel(new GridLayout(1, 2, 20, 20));
		puntajes.setOpaque(false);
		potenciadores = new JPanel(new GridLayout(2, 1, 20, 20));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		add(panel, BorderLayout.NORTH);
		pausa = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/pause.png")).getImage()
				.getScaledInstance(70, -1, Image.SCALE_DEFAULT)));
		pausa.setOpaque(false);
		pausa.setContentAreaFilled(false);
		pausa.setBorderPainted(false);
		puntajeJugador1 = new JLabel("0");
		puntajeJugador1.setFont(new Font("Arial", Font.BOLD, 80));
		puntajeJugador1.setForeground(frame.getColor(0));
		barras.add(barra);
		barras.setOpaque(false);
		puntajes.add(puntajeJugador1);
		potenciadorJugador1 = new JLabel("",JLabel.CENTER);
		potenciadorJugador1.setFont(new Font("Arial", Font.BOLD, 50));
		potenciadores.add(potenciadorJugador1);
		potenciadores.setOpaque(false);
		extraHUD();
		panel.add(Box.createRigidArea(new Dimension(150, 0)));
		panel.add(barras);
		panel.add(Box.createHorizontalGlue());
		panel.add(potenciadores);
		panel.add(Box.createHorizontalGlue());
		panel.add(puntajes);
		panel.add(Box.createHorizontalGlue());
		panel.add(pausa);
		panel.setOpaque(false);
	}

	protected void refresque() {
		frame.appNext();
		if (maquina)
			maquina();
		refresqueExtra();
		puntajeJugador1.setText(Integer.toString(frame.getPuntaje(0)));
		barra.setValue(frame.getVidaJugador(0));
		barra.setString("Vida " + barra.getValue());
		potenciadorJugador1.setText(frame.getTextEstado(0));
		potenciadorJugador1.setForeground(valueColor(frame.getEstado(0)));
		repaint();
		for (int i = 0; i < frame.getSizeJugadores(); i++) {
			if (frame.isJugadorVivo(i)) {
				if (frame.getIncrementoJugador(i) == 5)
					golpe.reproducir(0);
				else if (frame.getIncrementoJugador(i) == 3)
					moneda.reproducir(0);
				else if (frame.getIncrementoJugador(i) == 4)
					dragon.reproducir(0);
			}
		}
		boolean finDeJuego = true;
		for (int i = 0; i < frame.getSizeJugadores(); i++) {
			finDeJuego = finDeJuego && !frame.isJugadorVivo(i);
			frame.setPuntaje(frame.getPuntaje(i));
		}
		if (finDeJuego) {
			finDeJuego();
		}

	}

	protected Color valueColor(String estado) {
		Color c;
		if(estado.equals("EstadoOro"))
			c = Color.yellow;
		else if(estado.equals("EstadoPlata"))
			c = Color.darkGray;
		else
			c = Color.black;
		return c;
	}

	protected void refresqueExtra() {

	}

	protected void extraHUD() {

	}

	protected void accionesExtra() {

	}

	@Override
	public void update(Graphics g) {
		paint(g);

	}

	@Override
	public void paint(Graphics g) {

		if (g2d == null || this.getHeight() != frame.getHeight() || this.getWidth() != frame.getWidth()) {
			dibujo = createImage(frame.getWidth(), frame.getHeight());
			g2d = (Graphics2D) dibujo.getGraphics();
		}
		int xVentana = this.getHeight();
		int yVentana = this.getWidth();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(fondo, 0, 0, yVentana, xVentana, this);
		for (int i = 0; i < frame.getSizeJugadores(); i++) {
			int mx = ((int) (frame.getIncrementoJugador(i)) % 6) * 269;
			int my = ((int) (frame.getIncrementoJugador(i) + frame.getColorJugador(i) * 6) / 6) * 209;
			int x = frame.escalaWidth(frame.getYJugador(i));
			int y = frame.escalaHeight(frame.getXJugador(i));
			g2d.drawImage(personajes.getImage(), x, y, x + frame.escalaWidth(100), y + frame.escalaHeight(78), mx, my,
					mx + 269, my + 209, this);
		}
		for (int i = 0; i < frame.getSizeObstaculos(); i++) {
			dibujarElemento(i, xVentana);
		}
		g.drawImage(dibujo, 0, 0, this);
		g.setColor(Color.white);
		g.setFont(new Font("Arial black", Font.PLAIN, 120));
		g.drawString(contador, yVentana / 2 - 60, xVentana / 2 - 60);
		super.paint(g);

	}

	private void dibujarElemento(int i, int xVentana) {
		int[] atributos = frame.getAtributosObstaculo(i);
		if (frame.tipoElemento(i) == 0) {
			int mx = frame.getColorElemento(i) * 133;
			int x = frame.escalaWidth(atributos[2]);
			int y = frame.escalaHeight(atributos[0] + atributos[3]);
			g2d.drawImage(tuboAbajo.getImage(), x, y, x + frame.escalaWidth(atributos[1]), y + xVentana, mx, 0,
					mx + 133, 1000, this);
			g2d.drawImage(tuboArriba.getImage(), x, frame.escalaHeight(atributos[0]) - xVentana,
					x + frame.escalaWidth(atributos[1]), frame.escalaHeight(atributos[0]), mx, 0, mx + 133, 1000, this);
		} else if (frame.isVisibleElemento(i)) {
			int color = frame.getColorElemento(i) < 2 ? 16 * frame.getColorElemento(i)
					: 8 * frame.getColorElemento(i) + 16;
			int mx = (int) ((frame.getIncrementoElemento(i) + color) % 8) * 289;
			int my = (int) ((frame.getIncrementoElemento(i) + color) / 8) * 250;
			int x = frame.escalaWidth(atributos[2]);
			int y = frame.escalaHeight(atributos[0]);
			g2d.drawImage(spriteDragon.getImage(), x, y, x + frame.escalaWidth(200), y + frame.escalaHeight(173), mx,
					my, mx + 289, my + 250, this);
		}
	}

	public void maquina() {
		if (frame.isSaltarMaquina()) {
			accionesExtra();
		}
	}

	public void iniciar() {
		requestFocus();
		paint(this.getGraphics());
		musicaFondo.repetir(3566000);
		for (int i = 3; 0 < i; i--) {
			contador = Integer.toString(i);
			paint(this.getGraphics());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		reloj.start();
		contador = "";

	}

	private void finDeJuego() {
		parar();
		gameOver.reproducir(0);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		frame.irFinDeJuego();
	}

	public void parar() {
		musicaFondo.pausar();
		reloj.stop();
	}

}
