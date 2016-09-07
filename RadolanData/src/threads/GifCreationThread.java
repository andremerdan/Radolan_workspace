package threads;

import gui.GifCreator;
import java.util.ArrayList;
import java.util.Date;
import data.MongoAccess;
import data.VisPreparation;

/**
 * Used to create a animated visualization output of a given frame of time. This is saved as a GIF file.
 * Is run by feeding into the executor of the GUI main page.
 * 
 * @author Patrick Allan Blair
 *
 */
public class GifCreationThread implements Runnable {

	private String collection;
	private int minimum = 0;
	private ArrayList<Date> searchDates;
	private int[] searchArea;

	/**
	 * 
	 * @param collection The collection (rw, ry) that will by queried from the database for the operations of this runnable class.
	 * @param minimum The minimum precipitation value to used.
	 * @param searchDates The date range to be queried from the database.
	 * @param searchArea The area used for the query.
	 */
	public GifCreationThread(String collection, int minimum, ArrayList<Date> searchDates, int[] searchArea){
		this.collection = collection;
		this.minimum = minimum;
		this.searchDates = searchDates;
		this.searchArea = searchArea;
	}

	public void run() {
		ArrayList<Date> gifDateRange = new ArrayList<Date>();
		MongoAccess.searchBetween(collection, searchDates.get(0), searchDates.get(1), 0, searchArea, gifDateRange);

		String[] gifList = new String[gifDateRange.size()];
		int pos = 0;
		
		for(Date time: gifDateRange){
			if(!GifCreator.getActive()){
				GifCreator.setCurrentVisFile("n/a");
				return;
			}
			VisPreparation.createGIFImages(time, gifList, pos, searchArea, collection, minimum);
			pos++;
		}
		
		if(!GifCreator.getActive()){
			GifCreator.setCurrentVisFile("n/a");
			System.out.println("Fertig");
			return;
		}
		
		String path = GifCreator.getGifSavePath() + "\\" + searchDates.get(0).getTime() + "-" + searchDates.get(1).getTime()
				+ "-" + searchArea[0] + "-" + searchArea[1] + "-" + searchArea[2] + "-" + searchArea[3] + "-gif";
		VisPreparation.createGIF(path, gifList, searchArea);
		GifCreator.setCurrentVisFile("n/a");
		GifCreator.setActive(false);
		GifCreator.setAnimationStatus("Abgeschlossen");
	}

}
