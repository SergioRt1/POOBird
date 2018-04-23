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


public class PanelPausa extends JPanel{
	private JButton play;
	private JButton stop;
	private JButton save;
	private JButton export;
	private JPanel opciones;
	private FlappyGUI frame;
	private Image fondo;
	

	public PanelPausa(FlappyGUI frame) {
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
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.salvar();
				
			}
		});
		export.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.exportar();
				
			}
		});
	}
	
	protected void parar() {
		frame.irInicial();
		
	}

	protected void jugar() {
		frame.irJuego();
		
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(fondo, 0, 0, frame.getWidth(), frame.getHeight(), this);
		super.paint(g);
	}

	private void prepareElementos() {
		fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/pausa.JPG"));
		setLayout(new BorderLayout());
		opciones = new JPanel();
		opciones.setLayout(new GridLayout(1, 4, 30, 30));
		opciones.setBorder(new EmptyBorder(50, 120, 80, 120));
		play = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/play.png")).getImage().getScaledInstance(120, -1, Image.SCALE_DEFAULT)));
		stop = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/stop.png")).getImage().getScaledInstance(120, -1, Image.SCALE_DEFAULT)));
		save = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/guardar.png")).getImage().getScaledInstance(120, -1, Image.SCALE_DEFAULT)));
		export = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/exportar.png")).getImage().getScaledInstance(120, -1, Image.SCALE_DEFAULT)));
		setButton(play);
		setButton(stop);
		setButton(save);
		setButton(export);
		opciones.setOpaque(false);
		opciones.add(play);
		opciones.add(save);
		opciones.add(export);
		opciones.add(stop);
		add(opciones,BorderLayout.SOUTH);
		
	}
	
	public void setButton(JButton b) {
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
	}

}
