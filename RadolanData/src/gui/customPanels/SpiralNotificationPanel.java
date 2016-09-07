package gui.customPanels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The panel used to show glyphs if there were too many for the main spiral visualization panel. When
 * the user is shown these glyphs, multiple SpiralNotificationPanels will be opened, one for each glyph.
 * 
 * @author Patrick Allan Blair
 *
 */
public class SpiralNotificationPanel extends JPanel{

	private static final long serialVersionUID = 8817377860000096735L;

	private BufferedImage img = null;

	/**
	 * Sets the glyph image for this panel.
	 * 
	 * @param path The path to the glyph image.
	 */
	public SpiralNotificationPanel(String path){
		this.setPreferredSize(new Dimension(100, 100));
		try {
			img = ImageIO.read(new File("webpage\\dataImages\\" + path));
			System.out.println("Loaded glyph: " + path);
		} catch (IOException e) {
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(img != null){
			g.drawImage(img, 0, 0, 65, 65, null);
			g.drawRect(0, 0, 65, 65);
		}
		g.dispose();
	}
}
