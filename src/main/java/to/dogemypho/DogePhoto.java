package to.dogemypho;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.imageio.ImageIO;


public class DogePhoto {
	ArrayList<DogeText> existingText;
	private Random randomGenerator;
	
	public static final String EMPTY_TEXT_LIST = "List of text to add must be 1 or more elements in length";
	public static final String FONT_POINT_SIZE_SHOULD_BE_GREATER_THAN_0 = "Font Point Size should be greater than 0";
	public static final String FILENAME_CANNOT_BE_EMPTY = "Filename cannot be empty";


	private final static Logger LOGGER = Logger.getLogger(DogePhoto.class.getName()); 
	
	public String dogeMyPhoto(String fileName, int fontPointSize, List<String> dogeText) throws Exception {
		LOGGER.info("Begin dogeMyPhoto function");
		String dogedFile="";
		/*
		 * Check passed in parameters
		 */
		if (fileName == null || fileName.isEmpty()) {
			throw new Exception(FILENAME_CANNOT_BE_EMPTY);
		}
		if (fontPointSize < 1) {
			throw new Exception(FONT_POINT_SIZE_SHOULD_BE_GREATER_THAN_0);
		}
		if (dogeText == null || dogeText.isEmpty()) {
			throw new Exception(EMPTY_TEXT_LIST);
		}
		/*
		 * Load image
		 */
		BufferedImage image = loadImage(fileName);
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		List<DogeText> processedDogeText = new ArrayList<DogeText>();
		for (Iterator<String> iterUserText = dogeText.iterator(); iterUserText.hasNext();) {
			String newText = (String) iterUserText.next();
			int[] textMetrics = getTextMetrics(image, newText, fontPointSize);
			boolean collision = false;
			do {
				collision = false;
				DogeText newDogeText = createNewText(newText, imageWidth, imageHeight, textMetrics[0], textMetrics[1]);
				for (Iterator iterProcessedText = processedDogeText.iterator(); iterProcessedText.hasNext();) {
					DogeText oldDogeText = (DogeText) iterProcessedText.next();
					if (checkForCollision(oldDogeText, newDogeText)) {
						collision = true;
						break;
					}
				}
				if (!collision) {
					processedDogeText.add(newDogeText);
					addTextToImage(image, newDogeText, fontPointSize);
				}
			} while (collision);
		}
		saveImage(image);
		LOGGER.info("dogeMyPhoto function end");
		return dogedFile;
	}

	public BufferedImage loadImage(String fileName) throws IOException {
		LOGGER.info("Loading Image: " + fileName);
		BufferedImage img = ImageIO.read(new File(fileName));
		return img;
	}

	public void saveImage(BufferedImage bfi) throws IOException {
		File file = new File("newimage.jpg");
		ImageIO.write(bfi, "jpg", file);
	}
	
	public DogeText createNewText(String text, int imageWidth, int imageHeight, int textWidth, int textHeight) {
		LOGGER.info("Create new text object");
	    int x = getRandomGenerator().nextInt(imageWidth - textWidth);
	    int y = getRandomGenerator().nextInt(imageHeight - textHeight);
	    return new DogeText(text, x, y, textWidth, textHeight);
	}

	public void addTextToImage(BufferedImage bfi, DogeText text, int fontPointSize) {
		LOGGER.info("Add text to image");
		Graphics g = bfi.getGraphics();
	    Font font = getFont(fontPointSize);
		g.setFont(font);
		g.setColor(getRandomColour());
	    g.drawString(text.getText(), text.getLeft(), text.getTop());
	    g.dispose();
	    g = null;
	}
	
	public Font getFont(int fontPointSize) {
		return new Font("Helvetica", Font.PLAIN, fontPointSize);
	}

	public Color getRandomColour() {
		float r = getRandomGenerator().nextFloat();
		float g = getRandomGenerator().nextFloat();
		float b = getRandomGenerator().nextFloat();

		return new Color(r, g, b);
	}

	public int[] getTextMetrics(BufferedImage bfi, String text, int fontPointSize) {
		int[] result = new int[2];

		Graphics g = bfi.getGraphics();
	    Font font = getFont(fontPointSize);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		
		result[0] = fm.stringWidth(text);
		result[1] = fm.getHeight();
		LOGGER.info("String: " + text + " Width: " + result[0] + " Height: " + result[1]);
		
		return result;
	}
	
	public boolean checkForCollision(DogeText a, DogeText b) {		
		if (( a.getLeft() <= b.getLeft() && b.getLeft() <= a.getRight()
		|| a.getLeft() <= b.getRight() && b.getRight() <= a.getRight())
		&& (a.getTop() <= b.getTop() && b.getTop() <= a.getBottom()
		|| a.getTop() <= b.getBottom() && b.getBottom() <= a.getBottom())) {
			LOGGER.info("Collision detected");
			return true;
		}
		return false;
	}

	public Random getRandomGenerator() {
		return randomGenerator;
	}

	public void setRandomGenerator(Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

}
