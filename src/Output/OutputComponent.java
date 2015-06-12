package Output;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class OutputComponent extends JComponent{

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

		//the ClassLoader for the images
		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		//InputStream object gets the image
		InputStream input = cl.getResourceAsStream("Bear.JPG");
		Image bear = null;
		try {
			bear = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image image = bear.getScaledInstance(400, 365, 0);
		g2.drawImage(image, 0, 0, 400, 365, null);

	}

}
