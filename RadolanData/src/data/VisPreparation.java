package data;

import gui.GifCreator;
import gui.VisCreator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.*;
import javax.imageio.metadata.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 * 
 * @author Patrick Allan Blair
 *
 */
public class VisPreparation {

	private static String PATH = "webpage\\dataImages";

	/**
	 * Pre-processes the given entry for the chosen glyph type and calls corresponding methods for the actual creation. 
	 * 
	 * @param entry The entry for which to create the glyph.
	 * @param glyphType Which glyph type will be created.
	 */
	public static void createEntryPreviewGlyph(UserCreatedEntry entry, int glyphType){
		double length = entry.getEndTime().getTime() - entry.getStartTime().getTime();
		length = 1 + (length / 1125000);
		if(length > 32) length = 32;
		double extent = entry.getMaxValue() / 78;
		if(extent > 32) extent = 32;

		switch (glyphType) {
		case 0:
			createDiamondGlyph((int) length, (int) extent, entry.getGlyphName());
			break;
		case 1:
			createArrowGlyph((int) length / 2, (int) extent / 2, entry.getWindDirection(), entry.getGlyphName());
			break;
		case 2:
			int minutes = 0;
			minutes = (int) ((entry.getEndTime().getTime() - entry.getStartTime().getTime()) / 60000);
			createCrossGlyph(entry.getWindDirection(), entry.getMaxValue(), minutes, entry.getGlyphName());
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param direction The wind direction for the depicted entry.
	 * @param maxVal The maximum precipitation to be depicted.
	 * @param minutes The timeframe of the depicted data.
	 * @param path Under which name the glyph will be saved.
	 */
	private static void createCrossGlyph(int direction, int maxVal, int minutes, String path){
		BufferedImage bi = new BufferedImage(65, 65, BufferedImage.TYPE_INT_ARGB);

		System.out.println("Beginning image draw");

		Graphics g = bi.getGraphics();
		g.setColor(new Color(255, 255, 255, 255));
		g.fillRect(0, 0, 64, 64);

		g.setColor(Color.BLACK);
		Font f = new Font("Arial", Font.BOLD, 10);
		g.setFont(f);
		g.drawLine(0, 32, 64, 32);
		g.drawLine(32, 0, 32, 32);

		g.drawString("Wind:", 4, 12);
		g.drawString(direction + "°", 4, 24);
		g.drawString("Max:", 36, 12);
		g.drawString(maxVal + "mm", 36, 24);
		g.drawString("Minuten:", 4, 44);
		g.drawString(minutes + " min", 4, 56);

		g.dispose();
		try {
			ImageIO.write(bi, "PNG", new File("webpage\\dataImages\\" + path));
			System.out.println("Image created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param length The timeframe of the depicted data.
	 * @param extent The maximum precipitation to be depicted.
	 * @param direction The wind direction for the depicted entry.
	 * @param path Under which name the glyph will be saved.
	 */
	private static void createArrowGlyph(int length, int extent, int direction, String path){
		BufferedImage bi = new BufferedImage(65, 65, BufferedImage.TYPE_INT_ARGB);

		System.out.println("Beginning image draw");

		Graphics g = bi.getGraphics();
		g.setColor(new Color(255, 255, 255, 255));
		g.fillRect(0, 0, 64, 64);

		double degreeX = Math.cos(Math.toRadians(direction));
		double degreeY = Math.sin(Math.toRadians(direction));

		int[] x = new int[7];

		x[0] = (int) (32 + (length + 16) * degreeX);
		x[1] = (int) (32 - (extent + 16) * degreeY);
		x[2] = (int) (32 - ((extent + 16) / 2) * degreeY);
		x[3] = (int) (32 - ((extent + 16) / 2) * degreeY - (length + 16) * degreeX);
		x[4] = (int) (32 + ((extent + 16) / 2) * degreeY - (length + 16) * degreeX);
		x[5] = (int) (32 + ((extent + 16) / 2) * degreeY);
		x[6] = (int) (32 + (extent + 16) * degreeY);

		int[] y = new int[7];

		y[0] = (int) (32 - (length + 16) * degreeY);
		y[1] = (int) (32 - (extent + 16) * degreeX);
		y[2] = (int) (32 - ((extent + 16) / 2) * degreeX);
		y[3] = (int) (32 - ((extent + 16) / 2) * degreeX + (length + 16) * degreeY);
		y[4] = (int) (32 + ((extent + 16) / 2) * degreeX + (length + 16) * degreeY);
		y[5] = (int) (32 + ((extent + 16) / 2) * degreeX);
		y[6] = (int) (32 + (extent + 16) * degreeX);

		g.setColor(Color.BLACK);
		g.drawPolygon(x, y, x.length);
		g.dispose();
		try {
			ImageIO.write(bi, "PNG", new File("webpage\\dataImages\\" + path));
			System.out.println("Image created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param length The timeframe of the depicted data.
	 * @param extent The maximum precipitation to be depicted.
	 * @param path Under which name the glyph will be saved.
	 */
	private static void createDiamondGlyph(int length, int extent, String path){
		BufferedImage bi = new BufferedImage(65, 65, BufferedImage.TYPE_INT_ARGB);

		System.out.println("Beginning image draw");

		Graphics g = bi.getGraphics();
		g.setColor(new Color(255, 255, 255, 255));
		g.fillRect(0, 0, 64, 64);

		int[] x = new int[4];

		x[0] = 32 - length;
		x[1] = 32;
		x[2] = 32 + length;
		x[3] = 32;

		int[] y = new int[4];

		y[0] = 32;
		y[1] = (int) (32 - extent);
		y[2] = 32;
		y[3] = (int) (32 + extent);

		g.setColor(Color.BLACK);
		g.drawPolygon(x, y, x.length);
		g.dispose();
		try {
			ImageIO.write(bi, "PNG", new File("webpage\\dataImages\\" + path));
			System.out.println("Image created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param path Where to save the created image file.
	 * @param values The precipitation values to be used.
	 * @param area The area that the image will be limited to.
	 * @param minValue The minimum value that will be drawn into the image.
	 */
	private static void createPNG(String path, int[][] values, int[] area, int minValue){
		int w = area[2] - area[0];
		int h = area[3] - area[1];
		if(w <= 0 || h <= 0) return;
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		System.out.println("Beginning image draw");

		Graphics g = bi.getGraphics();
		g.setColor(new Color(Color.TRANSLUCENT));
		g.drawRect(0, 0, w, h);
		g.setColor(Color.LIGHT_GRAY);

		for(int x = area[0]; x < area[2]; x++){
			for(int y = area[1]; y < area[3]; y++){
				if(values[x][y] >= minValue) g.fillRect(900 - y - area[1] - 1, 900 - x - area[0] - 1, 3, 3);
			}
		}
		int value = 0;
		for(int x = area[0]; x < area[2]; x++){
			for(int y = area[1]; y < area[3]; y++){
				if(values[x][y] >= minValue){
					value = (int) ((100 * (double) values[x][y]) / 4096);
					if(value > 0 && value <= 4096){
						g.setColor(new Color(100 - value, 100 - value, 200 - value));
						g.fillRect(900 - y - area[1], 900 - x - area[0], 1, 1);
					}
				}
			}
		}
		g.dispose();

		try {
			ImageIO.write(bi, "PNG", new File(path));
			System.out.println("Image created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param time The time within the precipitation data that will be visualized.
	 * @param list A list of image files to which the here created file will be added.
	 * @param pos The position of this file within the list.
	 * @param area The area that the image will be limited to.
	 * @param collection The collection (rw, ry) that is used.
	 * @param minValue The minimum value that will be drawn into the image.
	 */
	public static void createGIFImages(Date time, String[] list, int pos, int[] area, String collection, int minValue){
		System.out.println("Creating image");
		String filepath = null;
		String filename = time.getTime()  + ".png";
		filepath = PATH + "\\" + filename;
		System.out.println("Testing path " + filepath);
		list[pos] = filepath;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		String dataPath = collection + "\\" + cal.get(Calendar.YEAR) + "\\" + cal.get(Calendar.MONTH) + "\\Radolan-Data-" + time.toString().replace(":", "-") + "-" + collection + ".txt";
		int[][] values = DataRead.readValuesFile(dataPath);
		if(values == null) return;
		GifCreator.setCurrentVisFile(filename);
		createPNG(filepath, values, area, minValue);
	}

	/**
	 * 
	 * @param path Where to save the created gif file.
	 * @param list A list of images used for the gif.
	 * @param area The area that the gif will be limited to.
	 */
	public static void createGIF(String path, String[] list, int[] area){
		int b = area[2] - area[0];
		int h = area[3] - area[1];

		BufferedImage originalMap = null;
		BufferedImage mapLayer = null;
		BufferedImage image = null;
		BufferedImage combined = new BufferedImage(b, h, BufferedImage.TYPE_INT_ARGB);
		try {
			originalMap = ImageIO.read(new File("webpage\\img\\RadolanBearbeitet.png"));

			mapLayer = new BufferedImage(900, 900, originalMap.getType());  
			Graphics2D g = mapLayer.createGraphics();  
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
			g.drawImage(originalMap, 0, 0, 900, 900, 0, 0, originalMap.getWidth(), originalMap.getHeight(), null);  
			g.dispose();  

			System.out.println("Subarea: " + area[0] + " " + area[1] + " to " + area[2] + " " + area[3]);
			if(area[0] != area[2] && area[1] != area[3]) mapLayer = mapLayer.getSubimage(area[0], area[1], area[2], area[3]);
			image = ImageIO.read(new File(list[0]));

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   

		ImageOutputStream output = null;
		try {
			output = new FileImageOutputStream(new File(path));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    

		Graphics g = combined.getGraphics();
		g.drawImage(mapLayer, 0, 0, null);
		g.drawImage(image, 0, 0, null);
		ImageWriter writer = null;

		Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
		if(!iter.hasNext()) {
			try {
				throw new IIOException("No GIF Image Writers Exist");
			} catch (IIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			writer = iter.next();
		}

		writer.setOutput(output);
		ImageWriteParam imageWriteParam = writer.getDefaultWriteParam();
		IIOMetadata imageMetaData = writer.getDefaultImageMetadata(ImageTypeSpecifier.createFromBufferedImageType(combined.getType()), imageWriteParam);

		String metaFormatName = imageMetaData.getNativeMetadataFormatName();

		IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);

		IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");

		graphicsControlExtensionNode.setAttribute("disposalMethod", "restoreToBackgroundColor");
		graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(1500));
		graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

		IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
		commentsNode.setAttribute("CommentExtension", "Created by MAH");

		IIOMetadataNode appEntensionsNode = getNode(root, "ApplicationExtensions");

		IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

		child.setAttribute("applicationID", "NETSCAPE");
		child.setAttribute("authenticationCode", "2.0");

		int loop = 1;

		child.setUserObject(new byte[]{ 0x1, (byte) (loop & 0xFF), (byte) ((loop >> 8) & 0xFF)});
		appEntensionsNode.appendChild(child);

		try {
			imageMetaData.setFromTree(metaFormatName, root);
		} catch (IIOInvalidTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// write out the first image to our sequence...
		try {
			writer.prepareWriteSequence(imageMetaData);
			writer.writeToSequence(new IIOImage(combined, null, imageMetaData), imageWriteParam);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		for(int i = 1; i < list.length; i++) {      
			if(!GifCreator.getActive()){
				break;
			}
			try {
				image = ImageIO.read(new File(list[i]));

				g.clearRect(0, 0, b, h);
				g.drawImage(mapLayer, 0, 0, null);
				g.drawImage(image, 0, 0, null);

				writer.writeToSequence(new IIOImage(combined, null, imageMetaData), imageWriteParam);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}    
		try {
			writer.endWriteSequence();
			output.close(); 
			g.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0) {
				return((IIOMetadataNode) rootNode.item(i));
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return(node);
	}

	/**
	 * 
	 * @param time The time within the precipitation data that will be visualized.
	 * @param area The area that the image will be limited to.
	 * @param collection The collection (rw, ry) that is used.
	 * @param minValue The minimum value that will be drawn into the images.
	 */
	public static void createWebsiteImages(Date time, int[] area, String collection, int minValue){
		System.out.println("Creating image");
		String filepath = null;
		String filename = time.getTime() + ".png";
		filepath = PATH + "\\" + filename;
		System.out.println("Testing path " + filepath);

		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		String dataPath = collection + "\\" + cal.get(Calendar.YEAR) + "\\" + cal.get(Calendar.MONTH) + "\\Radolan-Data-" + time.toString().replace(":", "-") + "-" + collection + ".txt";
		int[][] values = DataRead.readValuesFile(dataPath);
		if(values == null) return;
		VisCreator.setCurrentVisFile(filename);
		createPNG(filepath, values, area, minValue);
	}
}
