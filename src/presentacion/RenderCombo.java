package presentacion;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class RenderCombo extends JLabel implements ListCellRenderer{
	private HashMap<Object, ImageIcon> colores;
	private ImageIcon aves;
	
	public RenderCombo() {
		aves = new ImageIcon(getClass().getResource("../img/sprite.png"));
		colores = new HashMap<Object, ImageIcon>();
		BufferedImage img1 = new BufferedImage(90, 70, BufferedImage.TRANSLUCENT);
		BufferedImage img2 = new BufferedImage(90, 70, BufferedImage.TRANSLUCENT);
		BufferedImage img3 = new BufferedImage(90, 70, BufferedImage.TRANSLUCENT);
		BufferedImage img4 = new BufferedImage(90, 70, BufferedImage.TRANSLUCENT);
		img1.getGraphics().drawImage(aves.getImage(), 0, 0, (100), (78), 0, 0, 269, 209, this);
		img2.getGraphics().drawImage(aves.getImage(), 0, 0, (100), (78), 0, 209, 269, 209*2, this);
		img3.getGraphics().drawImage(aves.getImage(), 0, 0, (100), (78), 0, 209*2, 269, 209*3, this);
		img4.getGraphics().drawImage(aves.getImage(), 0, 0, (100), (78), 0, 209*3, 269, 209*4, this);
		colores.put("Verde",new ImageIcon(img1));
		colores.put("Rojo",new ImageIcon(img2));
		colores.put("Negro",new ImageIcon(img3));
		colores.put("Azul",new ImageIcon(img4));
		setOpaque(false);
		
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setIcon(colores.get(value));
		setText((String) value);
		
		return this;
	}
}
