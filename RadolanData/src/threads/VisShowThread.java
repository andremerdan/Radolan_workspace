package threads;

import gui.AreaSelect;
import gui.VisCreator;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import data.UserCreatedEntry;
import data.VisPreparation;

/**
 * Used to create all resources necessary for the visualization by the web application, 
 * i.e. image files for the precipitation data and the glyph representation of user created
 * entries.
 * Is run by feeding into the executor of the GUI main page.
 * 
 * @author Patrick Allan Blair
 *
 */
public class VisShowThread implements Runnable {

	private String collection;
	private int glyphType;
	private int minimum = 0;
	private ArrayList<Date> visDateRanges;
	private ArrayList<UserCreatedEntry> entryList;

	/**
	 * 
	 * @param collection The collection (rw, ry) that will by queried from the database for the operations of this runnable class.
	 * @param glyphType The type of glyph to be created for the visualization.
	 * @param minimum The minimum precipitation value to used.
	 * @param visDateRanges The date ranges in which precipitation matching the used area and minimum value has been found.
	 * @param entryList A list of all user created entries matching the used query parameters.
	 */
	public VisShowThread(String collection, int glyphType, int minimum, ArrayList<Date> visDateRanges, ArrayList<UserCreatedEntry> entryList){
		this.collection = collection;
		this.glyphType = glyphType;
		this.minimum = minimum;
		this.visDateRanges = visDateRanges;
		this.entryList = entryList;
	}

	public void run() {
		int[] searchArea = AreaSelect.getSelectedArea();
		long addition = 300000;
		if(collection.equals("rw")) addition = 3600000;

		String[] timeList = new String[visDateRanges.size() + 5];
		timeList[0] = collection;
		timeList[1] = String.valueOf(searchArea[0]);
		timeList[2] = String.valueOf(searchArea[1]);
		timeList[3] = String.valueOf(searchArea[2]);
		timeList[4] = String.valueOf(searchArea[3]);
		
		long current;
		long target;
		for(int i = 0; i < visDateRanges.size(); i = i + 2){
			current = visDateRanges.get(i).getTime();
			target = visDateRanges.get(i + 1).getTime();

			timeList[i + 5] = String.valueOf(current);
			timeList[i + 6] = String.valueOf(target);
			while(current <= target){
				if(!VisCreator.getActive()){
					VisCreator.setCurrentVisFile("n/a");
					return;
				}

				VisPreparation.createWebsiteImages(new Date(current), searchArea, collection, minimum);
				current = current + addition;
			}
		}

		VisCreator.setVisList(timeList);

		for(UserCreatedEntry e : entryList){
			current = e.getStartTime().getTime();
			target = e.getEndTime().getTime();

			while(current <= target){
				if(!VisCreator.getActive()){
					VisCreator.setCurrentVisFile("n/a");
					return;
				}
				System.out.println("Beginning image creation for visualisation");
				VisPreparation.createWebsiteImages(new Date(current), searchArea, collection, minimum);
				current = current + addition;
			}
			VisPreparation.createEntryPreviewGlyph(e, glyphType);
		}
		VisCreator.setCurrentVisFile("n/a");
		VisCreator.setActive(false);
		VisCreator.setAnimationStatus("Abgeschlossen");
		File htmlFile = new File("webpage\\mainpage.html");
		try {
			System.out.println("Starting Browser");
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
