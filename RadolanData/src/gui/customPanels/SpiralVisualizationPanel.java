package gui.customPanels;

import gui.SpiralCreator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Used for the graphical component of the spiral visualization panel.
 * 
 * @author Patrick Allan Blair
 *
 */
public class SpiralVisualizationPanel extends JPanel{

	private static final long serialVersionUID = 1168726275881624465L;
	private int ringAmount = 1;
	private int startYear = 2000;
	private int shiftX = 0;
	private int shiftY = 0;

	private ArrayList<Integer> overflows = new ArrayList<Integer>();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		String[][] lookupTable = SpiralCreator.getLookupTable();

		if(lookupTable.length < ringAmount || lookupTable.length <= 0){
			g.dispose();
			return;
		}

		int x = 0;
		int y = 0;
		int size = 700 * ringAmount;
		int halfway = size / 2;

		for(int i = 0; i <= 180; i = i +30){
			x = (int) (Math.cos(Math.toRadians(i)) * size);
			y = (int) (Math.sin(Math.toRadians(i)) * size);
			g.drawLine(halfway + x + shiftX, halfway - y + shiftY, halfway - x + shiftX, halfway + y + shiftY);

		}

		int radiusIncrement = halfway / ringAmount;
		for(int i = 1; i <= ringAmount; i++){
			g.drawOval(halfway - radiusIncrement * i + shiftX, halfway - radiusIncrement * i + shiftY, 
					2 * radiusIncrement * i, 2 * radiusIncrement * i);
			g.drawString(String.valueOf(startYear + i - 1), halfway + 10 + shiftX, halfway + radiusIncrement * i - 70 + shiftY);
		}
		
		String monthName;
		for(int i = 0; i < 12; i++){
			monthName = "";
			switch (i) {
			case 0:
				monthName = "Jan";
				break;
			case 1:
				monthName = "Feb";
				break;
			case 2:
				monthName = "Mar";
				break;
			case 3:
				monthName = "Apr";
				break;
			case 4:
				monthName = "May";
				break;
			case 5:
				monthName = "Jun";
				break;
			case 6:
				monthName = "Jul";
				break;
			case 7:
				monthName = "Aug";
				break;
			case 8:
				monthName = "Sep";
				break;
			case 9:
				monthName = "Oct";
				break;
			case 10:
				monthName = "Nov";
				break;
			case 11:
				monthName = "Dec";
				break;
			default:
				break;
			}
			x = (int) (300 * Math.cos(Math.toRadians(i * 30)) - 50 * Math.cos(Math.toRadians(i * 30)) - 15);
			y = (int) (300 * Math.sin(Math.toRadians(i * 30)) - 50 * Math.sin(Math.toRadians(i * 30)) - 15);
			g.drawString(monthName, halfway + shiftX + x, halfway + shiftY + y);
		}

		String[] temp = null;
		for(int i = 0; i < ringAmount; i++){
			for(int h = 0; h < 12; h++){
				if(lookupTable[i][h] != null && lookupTable[i][h].contains(";")) temp = lookupTable[i][h].split(";");
				else continue;

				if(temp.length > 4){
					overflows.add(i);
					overflows.add(h);
					x = (int) (halfway + (i + 1) * radiusIncrement * Math.cos(Math.toRadians(h * 30)) - 16);
					y = (int) (halfway + (i + 1) * radiusIncrement * Math.sin(Math.toRadians(h * 30)) - 16);
					g.fillOval(x + shiftX, y + shiftY, 32, 32);
					g.drawString(String.valueOf(temp.length), x + shiftX, y + shiftY - 10);
				} else {
					x = (int) (halfway + (i + 1) * radiusIncrement * Math.cos(Math.toRadians(h * 30)) - 70);
					y = (int) (halfway + (i + 1) * radiusIncrement * Math.sin(Math.toRadians(h * 30)) - 70);
					int offsetX = 0;
					int offsetY = 0;
					BufferedImage img = null;
					for(String s : temp){
						try {
							img = ImageIO.read(new File("webpage\\dataImages\\" + s));
							g.drawImage(img, x + offsetX + shiftX, y + offsetY + shiftY, 65, 65, null);
							g.drawRect(x + offsetX + shiftX, y + offsetY + shiftY, 65, 65);
							offsetX = offsetX + 76;
							if(offsetX > 80){
								offsetX = 0;
								offsetY = offsetY + 76;
							}
						} catch (IOException e) {
						}
					}
				}
			}
		}

		g.dispose();
	}

	/**
	 * Updates the values used when drawing the spiral visualization.
	 * 
	 * @param ringAmount The amount of rings to be drawn. Corresponds to the year amount to be visualized.
	 * @param startYear The starting year for the visualization.
	 */
	public void updateValues(int ringAmount, int startYear){
		if(ringAmount > 0) this.ringAmount = ringAmount;
		if(startYear > 1990) this.startYear = startYear;
	}

	/**
	 * Shifts the visualization along the x- and y-axis.
	 * 
	 * @param x How much to shift along the x-axis.
	 * @param y How much to shift along the y-axis.
	 */
	public void shiftPosition(int x, int y){
		this.shiftX = this.shiftX + x;
		this.shiftY = this.shiftY + y;
	}

	/**
	 * Used to evaluate a position within the visualization. If the position contains a representation of multiple glyphs,
	 * the corresponding values are returned.
	 * 
	 * @param testX The x-coordinate.
	 * @param testY The y-coordinate.
	 * @return A list of values corresponding to the given area, if any.
	 */
	public String testCoordinates(int testX, int testY){
		int halfway = ringAmount * 350;
		int radiusIncrement = (halfway) / ringAmount;
		int x;
		int y;
		for(int i = 0; i < overflows.size(); i = i + 2){
			x = (int) (halfway + (overflows.get(i) + 1) * radiusIncrement * Math.cos(Math.toRadians(overflows.get(i + 1) * 30)));
			y = (int) (halfway + (overflows.get(i) + 1) * radiusIncrement * Math.sin(Math.toRadians(overflows.get(i + 1) * 30)));
			if(testX - shiftX  > x - 16 && testX - shiftX < x + 16){
				if(testY - shiftY > y - 16 && testY - shiftY < y + 16) return SpiralCreator.getLookupTable()[overflows.get(i)][overflows.get(i + 1)];
			}
		}
		return "";
	}
}
