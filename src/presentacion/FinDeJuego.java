package presentacion;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.*;

public class FinDeJuego extends JPanel {
	private JButton play;
	private JButton stop;
	private JPanel opciones;
	protected JPanel datos;
	private JLabel puntaje;
	private JLabel mejor;
	protected FlappyGUI frame;
	private Image fondo;

	public FinDeJuego(FlappyGUI frame) {
		this.frame = frame;
		prepareElementos();
		prepareAcciones();
		this.setOpaque(false);
	}

	private void prepareAcciones() {
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jugar();

			}
		});
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parar();
			}
		});
	}

	protected void parar() {
		frame.irInicial();

	}

	protected void jugar() {
		frame.nuevoJuego(false);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(fondo, 0, 0, frame.getWidth(), frame.getHeight(), this);
		super.paint(g);
	}

	private void prepareElementos() {
		fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/findejuego.JPG"));
		setLayout(new BorderLayout());
		opciones = new JPanel();
		datos = new JPanel();
		puntaje = new JLabel("Puntaje jugador 1: " + frame.getPuntaje(0), JLabel.CENTER);
		mejor = new JLabel("Mejor puntaje: " + frame.getMejor(), JLabel.CENTER);
		puntaje.setFont(new Font("Comic Sans", Font.BOLD, 25));
		puntaje.setForeground(frame.getColor(0));
		mejor.setFont(new Font("Comic Sans", Font.BOLD, 25));
		opciones.setLayout(new BorderLayout());
		opciones.setBorder(new EmptyBorder(50, 120, 80, 120));
		play = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/play.png")).getImage()
				.getScaledInstance(120, -1, Image.SCALE_DEFAULT)));
		stop = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/stop.png")).getImage()
				.getScaledInstance(120, -1, Image.SCALE_DEFAULT)));
		play.setOpaque(false);
		stop.setOpaque(false);
		play.setContentAreaFilled(false);
		stop.setContentAreaFilled(false);
		play.setBorderPainted(false);
		stop.setBorderPainted(false);
		datos.setLayout(new GridLayout(3, 1, 5, 5));
		datos.setBorder(new EmptyBorder(15, 15, 15, 15));
		datos.add(puntaje);
		puntajeExtra();
		datos.add(mejor);
		datos.setOpaque(false);
		opciones.setOpaque(false);
		opciones.add(play, BorderLayout.WEST);
		opciones.add(stop, BorderLayout.EAST);
		opciones.add(datos, BorderLayout.CENTER);
		add(opciones, BorderLayout.SOUTH);

	}

	protected void puntajeExtra() {
		
	}

}
