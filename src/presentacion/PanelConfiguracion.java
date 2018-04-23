package presentacion;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelConfiguracion extends JPanel {
	private JLabel jugador2;
	private JLabel jugador1;
	private FlappyGUI frame;
	private JComboBox comboJuagador1;
	private JComboBox comboJuagador2;
	private GridBagConstraints c;
	private JLabel color;
	private JSlider dificultad;
	private JSlider volumen;
	private JLabel dificul;
	private JLabel etiquetaVolumen;
	private JButton informacion;
	private JButton sonido;
	private JButton volver;
	private JPanel conf;
	private HashMap<String, Integer> valueColor;
	private Image fondo;

	public PanelConfiguracion(FlappyGUI frame) {
		this.frame = frame;
		prepareElementos();
		prepareAcciones();
		this.setOpaque(false);
	}

	private void prepareAcciones() {
		volver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.irInicial();

			}
		});

		sonido.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cambiarVolumen();
			}
		});

		volumen.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				frame.volumenMusica(volumen.getValue());
			}
		});

		informacion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.irInformacion();

			}
		});

	}

	private void cambiarVolumen() {
		if (Sonido.VOLUMEN == Sonido.MUTE) {
			volumen.setValue((int) Sonido.VOLUMEN_NORMAL);
			frame.volumenMusica(Sonido.VOLUMEN_NORMAL);
		} else {
			volumen.setValue((int) Sonido.MUTE);
			frame.volumenMusica(Sonido.MUTE);
		}
	}

	private int getVolumen() {
		int volumenActual = volumen.getValue();
		if (Sonido.VOLUMEN == Sonido.MUTE) {
			volumenActual = (int) Sonido.MUTE;
		}
		return volumenActual;
	}

	private void prepareElementos() {
		setLayout(new BorderLayout());
		c = new GridBagConstraints();
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.insets = new Insets(1, 10, 1, 10);
		c.fill = GridBagConstraints.BOTH;
		fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/configuracion.JPG"));
		conf = new JPanel(new GridBagLayout());
		conf.setOpaque(false);
		add(conf, BorderLayout.CENTER);
		prepareColor();
		prepareSliders();
		prepareBotones();
	}

	private void prepareColor() {
		jugador1 = new JLabel("Color jugador 1", JLabel.CENTER);
		jugador2 = new JLabel("Color jugador 2", JLabel.CENTER);
		color = new JLabel("Color", JLabel.CENTER);
		color.setFont(new Font("Cooper Black", Font.PLAIN, 25));
		contrains(0, 1, 1, 4);
		conf.add(color, c);
		jugador1.setFont(new Font("Cooper Black", Font.PLAIN, 20));
		jugador2.setFont(new Font("Cooper Black", Font.PLAIN, 20));
		comboJuagador1 = new JComboBox();
		setCombo(comboJuagador1);
		comboJuagador2 = new JComboBox();
		setCombo(comboJuagador2);
		comboJuagador2.setSelectedIndex(1);
		contrains(1, 1, 1, 2);
		conf.add(jugador1, c);
		contrains(1, 3, 1, 2);
		conf.add(comboJuagador1, c);
		contrains(2, 1, 1, 2);
		conf.add(jugador2, c);
		contrains(2, 3, 1, 2);
		conf.add(comboJuagador2, c);
		hashColor();

	}

	private void prepareSliders() {
		dificul = new JLabel("Dificultad", JLabel.CENTER);
		dificul.setFont(new Font("Cooper Black", Font.PLAIN, 25));
		contrains(3, 1, 1, 4);
		conf.add(dificul, c);
		dificultad = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		setSlider(dificultad);
		Hashtable labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("Aburrido"));
		labelTable.put(new Integer(25), new JLabel("Sencillo"));
		labelTable.put(new Integer(50), new JLabel("Normal"));
		labelTable.put(new Integer(75), new JLabel("Dificil"));
		labelTable.put(new Integer(100), new JLabel("Experto"));
		dificultad.setLabelTable(labelTable);
		contrains(4, 0, 2, 7);
		c.insets = new Insets(3, 10, 3, 10);
		conf.add(dificultad, c);
		etiquetaVolumen = new JLabel("Volumen", JLabel.CENTER);
		etiquetaVolumen.setFont(new Font("Cooper Black", Font.PLAIN, 25));
		contrains(6, 1, 1, 4);
		conf.add(etiquetaVolumen, c);
		volumen = new JSlider(JSlider.HORIZONTAL, (int) Sonido.VOLUMEN_MINIMO, (int) Sonido.VOLUMEN_MAXIMO,
				(int) Sonido.VOLUMEN_NORMAL);
		setSlider(volumen);
		Hashtable labelVolumen = new Hashtable();
		labelVolumen.put(new Integer(-22), new JLabel("Bajo"));
		labelVolumen.put(new Integer(-10), new JLabel("Medio"));
		labelVolumen.put(new Integer(3), new JLabel("Alto"));
		volumen.setLabelTable(labelVolumen);
		contrains(7, 0, 2, 7);
		c.insets = new Insets(3, 10, 3, 10);
		conf.add(volumen, c);
	}

	private void prepareBotones() {
		informacion = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/informacion.png"))
				.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
		sonido = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/sonido.png")).getImage()
				.getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
		volver = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("../img/volver.png")).getImage()
				.getScaledInstance(-1, 90, Image.SCALE_DEFAULT)));
		informacion.setOpaque(false);
		informacion.setContentAreaFilled(false);
		informacion.setBorderPainted(false);
		sonido.setOpaque(false);
		sonido.setContentAreaFilled(false);
		sonido.setBorderPainted(false);
		volver.setOpaque(false);
		volver.setContentAreaFilled(false);
		volver.setBorderPainted(false);
		contrains(9, 0, 1, 1);
		c.insets = new Insets(0, 0, 0, 0);
		conf.add(sonido, c);
		contrains(9, 6, 1, 1);
		conf.add(informacion, c);
		contrains(10, 0, 0, 7);
		volver.setAlignmentX(JButton.CENTER_ALIGNMENT);
		c.insets = new Insets(-50, 0, 10, 0);
		conf.add(volver, c);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(fondo, 0, 0, frame.getWidth(), frame.getHeight(), this);
		conf.setBorder(new EmptyBorder(frame.escalaHeight(200), frame.escalaWidth(130), frame.escalaHeight(30),
				frame.escalaWidth(120)));
		super.paint(g);
	}

	private void setSlider(JSlider slider) {
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("serif", Font.PLAIN, 15));
		slider.setOpaque(false);
	}

	private void setCombo(JComboBox combo) {
		combo.setFont(new Font("Cooper Black", Font.PLAIN, 20));
		combo.addItem("Verde");
		combo.addItem("Rojo");
		combo.addItem("Negro");
		combo.addItem("Azul");
		combo.setOpaque(false);
		combo.setRenderer(new RenderCombo());
	}

	private void hashColor() {
		valueColor = new HashMap<String, Integer>();
		valueColor.put("Verde", 0);
		valueColor.put("Rojo", 1);
		valueColor.put("Negro", 2);
		valueColor.put("Azul", 3);

	}

	public int[] getConfiguracion() {
		int[] configuracion = { valueColor.get(comboJuagador1.getSelectedItem()),
				valueColor.get(comboJuagador2.getSelectedItem()), Math.max(5, 100 - dificultad.getValue()),
				getVolumen() };
		return configuracion;
	}

	private void contrains(int i, int j, int height, int width) {
		c.gridx = j;
		c.gridy = i;
		c.gridwidth = width;
		c.gridheight = height;

	}
}