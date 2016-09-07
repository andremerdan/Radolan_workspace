package threads;

import gui.Setup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import data.DataRead;
import data.UserCreatedEntry;

/**
 * Creates a summary of all given information and outputs this as a textfile.
 * Is run by feeding into the executor of the GUI main page.
 * 
 * @author Patrick Allan Blair
 *
 */
public class TextOutputCreationThread implements Runnable {
	private String collection;
	private int minimum;
	private ArrayList<Date> visDateRanges;
	private ArrayList<UserCreatedEntry> entryList;
	private int count;
	private String path;
	private ArrayList<Date> searchRange;
	private long trailing;
	private long leading;
	private int[] area;
	private int collectionDivision = 10;

	/**
	 * 
	 * @param collection The collection (rw, ry) that will by queried from the database for the operations of this runnable class.
	 * @param minimum The minimum precipitation value to used.
	 * @param visDateRanges The date ranges in which precipitation matching the used area and minimum value has been found.
	 * @param entryList A list of all user created entries matching the used query parameters.
	 * @param count The amount of values found within the database that exceed the minimum value used for the search.
	 * @param path The folder into which the resulting file will be saved.
	 * @param searchRange The date range to be queried from the database.
	 * @param trailing The additional time visualized before each data range of found data.
	 * @param leading The additional time visualized after each data range of found data.
	 * @param area The area used for the query.
	 */
	public TextOutputCreationThread(String collection, int minimum, ArrayList<Date> visDateRanges, ArrayList<UserCreatedEntry> entryList, 
			int count, String path, ArrayList<Date> searchRange, long trailing, long leading, int[] area){
		this.collection = collection;
		this.minimum = minimum;
		this.visDateRanges = visDateRanges;
		this.entryList = entryList;
		this.count = count;
		this.path = path;
		this.searchRange = searchRange;
		this.trailing = trailing;
		this.leading = leading;
		this.area = area;
		if(collection.equals("ry")) collectionDivision = 100;
	}

	public void run() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Suchergebnisse f�r ");
		buffer.append(new Date().toString());
		buffer.append("\n\n-----------------\n\n");

		buffer.append("Verwendeter Datensatz: ");
		if(collection.equals("rw")) buffer.append("St�ndliche Daten\n");
		else buffer.append("F�nf-Minuten Daten\n");

		buffer.append("Suche eingeschr�nkt auf Zeit: ");
		buffer.append(searchRange.get(0).toString());
		buffer.append(" - ");
		buffer.append(searchRange.get(1).toString());
		buffer.append("\n\tHinzugef�gte Zeit vor Funden:\t ");
		buffer.append(trailing - (trailing % 3600000) / 3600000);
		buffer.append(" Stunden, ");
		buffer.append((trailing % 3600000) / 30000);
		buffer.append(" Minuten\n\tHinzugef�gte Zeit nach Funden:\t ");
		buffer.append(leading - (leading % 3600000) / 3600000);
		buffer.append(" Stunden, ");
		buffer.append((leading % 3600000) / 30000);
		buffer.append(" Minuten\n\nSuche eingeschr�nkt auf Bereich: ");

		double upperX = Setup.getXPolar() + (double) area[0];
		double upperY = Setup.getYPolar() - (double) area[1];
		double lowerX = Setup.getXPolar() + (double) area[2];
		double lowerY = Setup.getYPolar() - (double) area[3];
		double r = 6370.040;
		double calc = 0;

		calc = Math.toDegrees(Math.atan((upperX * -1) / upperY)) + 10.0;
		buffer.append(Math.round(calc * 100000.0) / 100000.0);
		buffer.append("�E, ");

		calc = Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) - (Math.pow(upperX, 2) + Math.pow(upperY, 2));
		calc = calc / (Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) + (Math.pow(upperX, 2) + Math.pow(upperY, 2)));
		calc = Math.toDegrees(Math.asin(calc));
		buffer.append(Math.round(calc * 100000.0) / 100000.0);
		buffer.append("�N bis "); 

		calc = Math.toDegrees(Math.atan((lowerX * -1) / lowerY)) + 10.0;
		buffer.append(Math.round(calc * 100000.0) / 100000.0);
		buffer.append("�E, ");

		calc = Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) - (Math.pow(lowerX, 2) + Math.pow(lowerY, 2));
		calc = calc / (Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) + (Math.pow(lowerX, 2) + Math.pow(lowerY, 2)));
		calc = Math.toDegrees(Math.asin(calc));
		buffer.append(Math.round(calc * 100000.0) / 100000.0);

		buffer.append("�N\n\tIn polarstereographischen Koordinaten relativ zum Radolan-Raster: (");
		buffer.append(area[0]);
		buffer.append(", ");
		buffer.append(area[1]);
		buffer.append(") bis (");
		buffer.append(area[2]);
		buffer.append(", ");
		buffer.append(area[3]);
		buffer.append(")\n\tUrsprung vom Radolan-Raster in polarstereographischen Koordinaten bei ");
		buffer.append(Setup.getXPolar());
		buffer.append("�E, ");
		buffer.append(Setup.getYPolar());

		buffer.append("�N\n\n-----------------\n\nGesuchter Minimal-Wert in mm: ");
		buffer.append((double) minimum / 10.0);
		buffer.append("\n\tGefundene�berschreitungen: ");
		buffer.append(count);
		buffer.append("\n\tZeitbereiche mit �berschreitungen: ");
		buffer.append(visDateRanges.size() / 2);
		buffer.append("\n\tGefundene nutzererstellte Eintr�ge: ");
		buffer.append(entryList.size());

		buffer.append("\n\n-----------------\n\nGefundene Nutzereintr�ge\n");
		for(UserCreatedEntry e : entryList){
			buffer.append("\n\t");
			buffer.append(e.getDescription());
			buffer.append(": ");
			buffer.append(e.getStartTime().toString());
			buffer.append(" bis ");
			buffer.append(e.getEndTime().toString());
			buffer.append(", ");
			buffer.append(e.getMaxValue());
			buffer.append(" mm Maximalwert f�r Niederschlag, Windrichtung: ");
			buffer.append(e.getWindDirection());
			buffer.append("�");
		}

		long addition = 300000;
		if(collection.equals("rw")) addition = 3600000;

		buffer.append("\n\n-----------------\n\nGefundene Ereignisse\n");
		
		long current;
		long target;
		for(int i = 0; i < visDateRanges.size(); i = i + 2){
			current = visDateRanges.get(i).getTime();
			target = visDateRanges.get(i + 1).getTime();

			Date time;
			String dataPath;
			while(current <= target){
				time = new Date(current);
				Calendar cal = Calendar.getInstance();
				cal.setTime(time);
				dataPath = collection + "\\" + cal.get(Calendar.YEAR) + "\\" + cal.get(Calendar.MONTH) + "\\Radolan-Data-" + time.toString().replace(":", "-") + "-" + collection + ".txt";
				int[][] values = DataRead.readValuesFile(dataPath);
				current = current + addition;

				if(values.equals(null)) continue;
				buffer.append("\n\t�berschreitungen bei ");
				buffer.append(time.toString());
				for(int x = area[0]; x < area[2]; x++){
					for(int y = area[1]; y < area[3]; y++){
						if(values[x][y] >= minimum){
							buffer.append("\n\t\tStelle (");
							buffer.append(x);
							buffer.append(", ");
							buffer.append(y);
							buffer.append(") mit ");
							buffer.append(((double) values[x][y]) / collectionDivision);
							buffer.append(" mm");
						}
					}
				}
			}
		}

		DataRead.writeTextOutputFile(buffer, path);
	}
}