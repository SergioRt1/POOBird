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
import javax.swing.SwingConstants;
import javax.swing.border.*;


public class PanelInicial extends JPanel{
	private JButton play;
	private JButton configuracion;
	private JPanel opciones;
	private JButton salir;
	private FlappyGUI frame;
	private Image fondo;

	public PanelInicial(FlappyGUI frame) {
		super();
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
		configuracion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				configuracion();
			}
		});
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.salga();
				
			}
		});
	}
	
	protected void configuracion() {
		frame.irConfiguracion();
		
	}


	protected void jugar() {
		frame.irOpciones();
		
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(fondo, 0, 0, frame.getWidth(), frame.getHeight(), this);
		super.paint(g);
	}

	private void prepareElementos() {
		fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/inicial.JPG"));
		setLayout(new BorderLayout());
		opciones = new JPanel();
		opciones.setLayout(new BorderLayout());
		opciones.setBorder(new EmptyBorder(50, 120, 80, 120));
		play = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/play.png")).getImage().getScaledInstance(120, -1, Image.SCALE_FAST)));
		configuracion = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/config.png")).getImage().getScaledInstance(120, -1, Image.SCALE_FAST)));
		salir = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/salir.png")).getImage().getScaledInstance(140, -1, Image.SCALE_FAST)));
		salir.setVerticalAlignment(SwingConstants.BOTTOM);
		play.setOpaque(false);
		configuracion.setOpaque(false);
		salir.setOpaque(false);
		play.setContentAreaFilled(false);
		configuracion.setContentAreaFilled(false);
		salir.setContentAreaFilled(false);
		play.setBorderPainted(false);
		configuracion.setBorderPainted(false);
		salir.setBorderPainted(false);
		opciones.setOpaque(false);
		opciones.add(play,BorderLayout.WEST);
		opciones.add(configuracion,BorderLayout.EAST);
		opciones.add(salir,BorderLayout.CENTER);
		add(opciones,BorderLayout.SOUTH);
		
	}

}
