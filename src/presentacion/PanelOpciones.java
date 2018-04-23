package presentacion;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class PanelOpciones extends JPanel {

	private FlappyGUI frame;
	private JPanel opciones;
	private Image fondo;
	private GridBagConstraints c;
	private String[] listaPerfiles = { "Timido", "Equilibrado", "Astuto", "Mimo" };
	private JLabel jugadores;
	private JLabel objetos;
	private JLabel iconUnJugador;
	private JLabel iconDosJugadores;
	private JLabel iconMaquina;
	private JLabel iconTubo;
	private JLabel iconMoneda;
	private JLabel iconDragon;
	private JLabel iconAleatorio;
	private JRadioButton unJugador;
	private JRadioButton dosJugadores;
	private JRadioButton maquina;
	private ButtonGroup grupoJugadores;
	private JComboBox perfiles;
	private JCheckBox tVerdes;
	private JCheckBox tRojos;
	private JCheckBox tAzules;
	private JCheckBox tMagenta;
	private JCheckBox oro;
	private JCheckBox plata;
	private JCheckBox algodon;
	private JCheckBox dVerde;
	private JCheckBox dRojo;
	private JCheckBox aleatorio;
	private JButton play;
	private JButton volver;
	private JButton importar;
	private JButton abrir;

	public PanelOpciones(FlappyGUI frame) {
		this.frame = frame;
		prepareElementos();
		prepareAcciones();
		this.setOpaque(false);
	}

	private void prepareElementos() {
		setLayout(new BorderLayout());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(1, 2, 1, 2);
		c.fill = GridBagConstraints.HORIZONTAL;
		fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/opciones.JPG"));
		opciones = new JPanel(new GridBagLayout());
		opciones.setOpaque(false);
		add(opciones, BorderLayout.CENTER);
		prepareJugadores();
		prepareObstaculos();
		prepareBotones();
	}

	private void prepareJugadores() {
		jugadores = new JLabel("Jugadores", JLabel.CENTER);
		jugadores.setFont(new Font("Cooper Black", Font.PLAIN, 35));
		contrains(0, 0, 1, 8);
		opciones.add(jugadores, c);
		c.weighty = 0.001;
		perfiles = new JComboBox(listaPerfiles);
		perfiles.setSelectedIndex(0);
		perfiles.setOpaque(false);
		perfiles.setFont(new Font("Arial", Font.BOLD, 18));
		contrains(1, 5, 1, 2);
		opciones.add(perfiles, c);
		unJugador = new JRadioButton("Un jugador", true);
		iconUnJugador = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/birdBlack.png"))
				.getImage().getScaledInstance(-1, 80, Image.SCALE_DEFAULT)));
		contrains(2, 1, 1, 2);
		opciones.add(iconUnJugador, c);
		dosJugadores = new JRadioButton("Dos jugadores");
		iconDosJugadores = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/dosJugadores.png"))
				.getImage().getScaledInstance(-1, 90, Image.SCALE_DEFAULT)));
		contrains(2, 3, 1, 2);
		opciones.add(iconDosJugadores, c);
		maquina = new JRadioButton("Jugador vs Maquina");
		iconMaquina = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/maquina.png")).getImage()
				.getScaledInstance(-1, 80, Image.SCALE_DEFAULT)));
		contrains(2, 5, 1, 2);
		opciones.add(iconMaquina, c);
		setRadio(unJugador);
		setRadio(dosJugadores);
		setRadio(maquina);
		grupoJugadores = new ButtonGroup();
		grupoJugadores.add(unJugador);
		grupoJugadores.add(dosJugadores);
		grupoJugadores.add(maquina);
		contrains(3, 1, 1, 2);
		opciones.add(unJugador, c);
		contrains(3, 3, 1, 2);
		opciones.add(dosJugadores, c);
		contrains(3, 5, 1, 2);
		opciones.add(maquina, c);
	}

	private void prepareObstaculos() {
		c.weighty = 1;
		objetos = new JLabel("Elementos", JLabel.CENTER);
		objetos.setFont(new Font("Cooper Black", Font.PLAIN, 35));
		contrains(4, 0, 1, 8);
		opciones.add(objetos, c);
		c.weighty = 0.001;
		iconTubo = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/tube.png")).getImage()
				.getScaledInstance(100, -1, Image.SCALE_DEFAULT)));
		contrains(5, 0, 2, 2);
		opciones.add(iconTubo, c);
		tVerdes = new JCheckBox("Verde", true);
		setCkeckBox(tVerdes);
		contrains(5, 2, 1, 1);
		opciones.add(tVerdes, c);
		tRojos = new JCheckBox("Rojo");
		setCkeckBox(tRojos);
		contrains(6, 2, 1, 1);
		opciones.add(tRojos, c);
		tMagenta = new JCheckBox("Magenta");
		setCkeckBox(tMagenta);
		contrains(5, 3, 1, 1);
		opciones.add(tMagenta, c);
		tAzules = new JCheckBox("Azul");
		setCkeckBox(tAzules);
		contrains(6, 3, 1, 1);
		opciones.add(tAzules, c);
		iconMoneda = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/coin.png")).getImage()
				.getScaledInstance(100, -1, Image.SCALE_DEFAULT)));
		contrains(5, 4, 2, 2);
		opciones.add(iconMoneda, c);
		oro = new JCheckBox("Oro");
		setCkeckBox(oro);
		contrains(5, 6, 1, 1);
		opciones.add(oro, c);
		plata = new JCheckBox("Plata");
		setCkeckBox(plata);
		contrains(6, 6, 1, 1);
		opciones.add(plata, c);
		algodon = new JCheckBox("Algodon");
		setCkeckBox(algodon);
		contrains(5, 7, 1, 1);
		opciones.add(algodon, c);

		iconDragon = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/dragon.png")).getImage()
				.getScaledInstance(100, -1, Image.SCALE_DEFAULT)));
		contrains(7, 0, 2, 2);
		opciones.add(iconDragon, c);
		dRojo = new JCheckBox("Rojo");
		setCkeckBox(dRojo);
		contrains(7, 2, 1, 2);
		opciones.add(dRojo, c);
		dVerde = new JCheckBox("Verde");
		setCkeckBox(dVerde);
		contrains(8, 2, 1, 2);
		opciones.add(dVerde, c);
		iconAleatorio = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/aleatorio.png"))
				.getImage().getScaledInstance(100, -1, Image.SCALE_DEFAULT)));
		contrains(7, 4, 2, 2);
		opciones.add(iconAleatorio, c);
		aleatorio = new JCheckBox("Aleatorio");
		setCkeckBox(aleatorio);
		contrains(7, 6, 2, 2);
		opciones.add(aleatorio, c);

	}

	private void prepareBotones() {
		play = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/play.png")).getImage()
				.getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
		setBoton(play);
		volver = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/volverDos.png")).getImage()
				.getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
		setBoton(volver);
		importar = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/importar.png")).getImage()
				.getScaledInstance(90, -1, Image.SCALE_DEFAULT)));

		setBoton(importar);
		abrir = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/abrir.png")).getImage()
				.getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
		setBoton(abrir);
		contrains(9, 0, 2, 2);
		opciones.add(volver, c);
		contrains(9, 2, 2, 2);
		opciones.add(abrir, c);
		contrains(9, 4, 2, 2);
		opciones.add(importar, c);
		contrains(9, 6, 2, 2);
		opciones.add(play, c);

	}

	public boolean[] getOpciones() {
		boolean[] opciones = new boolean[12];
		opciones[0] = dosJugadores.isSelected();
		opciones[1] = maquina.isSelected();
		opciones[2] = tVerdes.isSelected();
		opciones[3] = tRojos.isSelected();
		opciones[4] = tAzules.isSelected();
		opciones[5] = tMagenta.isSelected();
		opciones[6] = oro.isSelected();
		opciones[7] = plata.isSelected();
		opciones[8] = algodon.isSelected();
		opciones[9] = dRojo.isSelected();
		opciones[10] = dVerde.isSelected();
		opciones[11] = aleatorio.isSelected();
		return opciones;
	}

	public String getPerfil() {
		return (String) perfiles.getSelectedItem();
	}

	private void setBoton(JButton b) {
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
	}

	private void setCkeckBox(JCheckBox c) {
		c.setOpaque(false);
		c.setHorizontalAlignment(JCheckBox.CENTER);
		c.setFont(new Font("Arial", Font.BOLD, 18));
	}

	private void setRadio(JRadioButton r) {
		r.setOpaque(false);
		r.setVerticalTextPosition(JRadioButton.BOTTOM);
		r.setHorizontalAlignment(JRadioButton.CENTER);
		r.setHorizontalTextPosition(JRadioButton.CENTER);
		r.setFont(new Font("Arial", Font.BOLD, 18));
	}

	private void prepareAcciones() {
		accionesBotones();

	}

	private void accionesBotones() {
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.nuevoJuego(false);

			}
		});
		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.irInicial();

			}
		});
		importar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.importar();

			}
		});
		abrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.abrir();

			}
		});

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(fondo, 0, 0, frame.getWidth(), frame.getHeight(), this);
		opciones.setBorder(new EmptyBorder(frame.escalaHeight(50), frame.escalaWidth(130), frame.escalaHeight(40),
				frame.escalaWidth(120)));
		super.paint(g);
	}

	private void contrains(int i, int j, int height, int width) {
		c.gridx = j;
		c.gridy = i;
		c.gridwidth = width;
		c.gridheight = height;

	}
}