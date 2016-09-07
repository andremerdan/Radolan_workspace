package threads;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import data.UserCreatedEntry;
import data.MongoAccess;
import data.VisPreparation;
import gui.SpiralCreator;
import gui.customPanels.SpiralVisualizationPanel;

/**
 * Queries the database for user created entries matching the given parameters and creates the glyph images for each entry.
 * Then updates the given panel for the spiral visualization with this new data.
 * Is run by feeding into the executor of the GUI main page.
 * 
 * @author Patrick Allan Blair
 *
 */
public class DatabaseSpiralVisThread implements Runnable{

	private SpiralVisualizationPanel drawReference;
	private ArrayList<Date> searchDates;
	private int glyphType;
	private int[] searchArea;

	/**
	 * 
	 * @param drawReference The panel in which the information that will be found is actually drawn.
	 * @param searchDates The date range to be queried from the database.
	 * @param searchArea The area used for the query.
	 * @param glyphType The type of glyph to be created for the visualization.
	 */
	public DatabaseSpiralVisThread(SpiralVisualizationPanel drawReference, ArrayList<Date> searchDates, int[] searchArea, int glyphType){
		this.drawReference = drawReference;
		this.searchDates = searchDates;
		this.searchArea = searchArea;
		this.glyphType = glyphType;
	}

	public void run(){
		ArrayList<UserCreatedEntry> spiralEntries = MongoAccess.queryUserCreatedEntries(searchDates.get(0), searchDates.get(1), searchArea);
		if(spiralEntries.size() == 0) return;

		int smallestYear = 0;
		int largestYear = 0;
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat df2 = new SimpleDateFormat("MM");
		
		int entryYear;
		for(UserCreatedEntry e : spiralEntries){
			entryYear = Integer.valueOf(df1.format(e.getStartTime()));
			if(smallestYear == 0 || entryYear < smallestYear) smallestYear = entryYear;
			if(largestYear < entryYear) largestYear = entryYear;

			VisPreparation.createEntryPreviewGlyph(e, glyphType);
		}
		int amount = largestYear - smallestYear + 1;

		String[][] lookupTable = new String[amount][12];
		int year;
		int month;
		for(UserCreatedEntry e : spiralEntries){
			year = Integer.valueOf(df1.format(e.getStartTime())) - smallestYear;
			month = Integer.valueOf(df2.format(e.getStartTime())) - 1;
			if(year >= amount || month >= 12) continue;
			if(lookupTable[year][month] == null) lookupTable[year][month] = "";
			lookupTable[year][month] = lookupTable[year][month] + e.getGlyphName() + ";";
		}

		SpiralCreator.setLookupTable(lookupTable);

		drawReference.updateValues(amount, smallestYear);

		int size = 240 * amount;
		drawReference.setSize(size, size);
		drawReference.repaint();
	}

}
