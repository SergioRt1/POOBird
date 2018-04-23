package presentacion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class PanelInformacion extends JPanel {

	private FlappyGUI frame;
	private Image fondo;
	private JButton volver;
	private JButton previous;
	private JButton next;
	private CardLayout layout;
	private JPanel info;
	private JPanel botones;
	private JLabel objetivo;
	private JLabel elementos1;
	private JLabel elementos2;
	private JLabel comoJugar;
	private JLabel creditos;

	public PanelInformacion(FlappyGUI frame) {
		this.frame = frame;
		prepareElementos();
		prepareAcciones();
		this.setOpaque(false);
	}

	private void prepareElementos() {
		setLayout(new BorderLayout());
		fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/informacion.JPG"));
		prepareInformacion();
		prepareBotones();
	}

	public void prepareInformacion() {
		objetivo = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/Inf1.JPG")).getImage()
				.getScaledInstance(990, -1, Image.SCALE_DEFAULT)));
		elementos1 = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/Inf2.JPG")).getImage()
				.getScaledInstance(990, -1, Image.SCALE_DEFAULT)));
		elementos2 = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/Inf3.JPG")).getImage()
				.getScaledInstance(990, -1, Image.SCALE_DEFAULT)));
		comoJugar = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/Inf4.JPG")).getImage()
				.getScaledInstance(990, -1, Image.SCALE_DEFAULT)));
		creditos = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("../img/Inf5.JPG")).getImage()
				.getScaledInstance(990, -1, Image.SCALE_DEFAULT)));

		layout = new CardLayout();
		info = new JPanel(layout);
		info.setBorder(new EmptyBorder(115, 150, 10, 150));
		info.setOpaque(false);
		add(info);

		info.add(makeTextPanel(objetivo));
		info.add(makeTextPanel(elementos1));
		info.add(makeTextPanel(elementos2));
		info.add(makeTextPanel(comoJugar));
		info.add(makeTextPanel(creditos));
	}

	protected JComponent makeTextPanel(JLabel img) {
		JPanel panel = new JPanel(false);
		panel.setOpaque(false);
		panel.add(img);
		return panel;
	}
	
	private void prepareBotones() {
		botones = new JPanel(new GridLayout(1, 3, 30, 30));
		botones.setOpaque(false);
		previous = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/anterior.png")).getImage()
				.getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
		volver = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/volver.png")).getImage()
				.getScaledInstance(-1, 80, Image.SCALE_DEFAULT)));
		next = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/siguente.png")).getImage()
				.getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
		setButton(previous);
		setButton(volver);
		setButton(next);
		botones.add(previous);
		botones.add(volver);
		botones.add(next);
		add(botones, BorderLayout.SOUTH);

	}
	
	public void setButton(JButton b) {
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
	}

	private void prepareAcciones() {
		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.irConfiguracion();

			}
		});
		
		previous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.previous(info);
				
			}
		});
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.next(info);
				
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(fondo, 0, 0, frame.getWidth(), frame.getHeight(), this);
		super.paint(g);
	}
}
