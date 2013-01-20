package lofi;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private BufferedImage image = null;
	private int width;
	private int height;
	private int frames;

	public SpriteSheet(String filename, int frames) {
		try {
			image = ImageIO.read(new File(filename));

			this.frames = frames;

			width = image.getWidth();
			height = image.getHeight();

			BufferedImage newImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int rgb = image.getRGB(x, y) & 0xFFFFFF;
					if (rgb == 0xFF0000 || rgb == 0x000000) {
						rgb |= 0xFF000000;
					} else {
						rgb = 0;
					}
					newImage.setRGB(x, y, rgb);
				}
			}
			image = newImage;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void drawFrame(Graphics2D g2, int frame, int x, int y) {
		int start = frame * width / frames;
		int fwidth = width / frames;
		g2.drawImage(image, x, y, x + fwidth, y + height, start, 0, start
				+ fwidth, height, null);
	}
}
