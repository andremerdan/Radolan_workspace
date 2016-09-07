package threads;

import gui.VisCreator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import data.UserCreatedEntry;
import data.MongoAccess;

/**
 * Searches through the database for precipitation data matching the given parameters and creates
 * a listing of found user created entries and data ranges in which matching precipitation data was found.
 * Is run by feeding into the executor of the GUI main page.
 * 
 * @author Patrick Allan Blair
 *
 */
public class DatabaseVisSearchThread implements Runnable{

	private String collection = "";
	private int minimum = 0;
	private ArrayList<Date> searchDates;
	private long minus;
	private long plus;
	private int[] searchArea;

	/**
	 * 
	 * @param collection The collection (rw, ry) that will by queried from the database for the operations of this runnable class.
	 * @param minimum The minimum precipitation value to used.
	 * @param searchDates The date range to be queried from the database.
	 * @param minus The additional time visualized before each data range of found data.
	 * @param plus The additional time visualized after each data range of found data.
	 * @param searchArea The area used for the query.
	 */
	public DatabaseVisSearchThread(String collection, int minimum, ArrayList<Date> searchDates, long minus, long plus, int[] searchArea){
		this.collection = collection;
		this.minimum = minimum;
		this.searchDates = searchDates;
		this.minus = minus;
		this.plus = plus;
		this.searchArea = searchArea;
	}

	public void run() {
		boolean hourly = collection.equals("rw");
		ArrayList<UserCreatedEntry> entryList = MongoAccess.queryUserCreatedEntries(searchDates.get(0), searchDates.get(1), searchArea);
		VisCreator.setFoundList(entryList);

		ArrayList<Date> foundDates = new ArrayList<Date>();
		long amount = 0;

		if(VisCreator.setToIgnore()) searchDates = MongoAccess.queryNegativeEntries(searchDates.get(0), searchDates.get(1), searchArea);
		// Counting the number of founding Events
		for(int i = 0; i < searchDates.size(); i = i + 2){		
			amount = amount + MongoAccess.searchBetween(collection, searchDates.get(i), searchDates.get(i + 1), minimum, searchArea, foundDates);
		}
		VisCreator.setFoundEntryNumbers(amount);

		// 3600000 milliseconds = 1 hour
		// 300000 milliseconds = 5 minutes

		ArrayList<Date> visDateRanges = new ArrayList<Date>();
		Collections.sort(foundDates);
		boolean add = true;
		Date test1;
		Date test2;
		for(Date d: foundDates){
			add = true;
			for(int i = 0; i < visDateRanges.size() - 1; i = i + 2){
				test1 = visDateRanges.get(i);
				test2 = visDateRanges.get(i + 1);
				if(hourly){
					if(d.after(test1) && d.before(test2)) add = false;
					else if(d.after(test1) && test2.getTime() + 3600000 >= d.getTime()){
						visDateRanges.set(i + 1, d);
						add = false;
					}
					else if(d.before(test2) && test1.getTime() - 3600000 <= d.getTime()){
						visDateRanges.set(i, d);
						add = false;
					}
				} else {
					if(d.after(test1) && d.before(test2)) add = false;
					else if(d.after(test1) && test2.getTime() + 300000 >= d.getTime()){
						visDateRanges.set(i + 1, d);
						add = false;
					}
					else if(d.before(test2) && test1.getTime() - 300000 <= d.getTime()){
						visDateRanges.set(i, d);
						add = false;
					}
				}
			}
			if(add){
				visDateRanges.add(d);
				visDateRanges.add(d);
			}
		}
		long start;
		long stop;
		for(int i = 0; i < visDateRanges.size(); i = i + 2){
			start = visDateRanges.get(i).getTime() - minus;
			visDateRanges.set(i, new Date(start));
			stop = visDateRanges.get(i + 1).getTime() + plus;
			visDateRanges.set(i + 1, new Date(stop));
		}
		
		long start1;
		long stop1;
		long start2;
		long stop2;
		for(int i = 0; i < visDateRanges.size() - 3; i = i + 2){
			start1 = visDateRanges.get(i).getTime();
			stop1 = visDateRanges.get(i + 1).getTime();
			start2 = visDateRanges.get(i + 2).getTime();
			stop2 = visDateRanges.get(i + 3).getTime();
			if(start2 >= start1 && start2 <= stop1){
				if(stop2 > stop1){
					visDateRanges.set(i + 1, new Date(stop2));
				}
				visDateRanges.remove(i + 2);
				visDateRanges.remove(i + 2);
				i = i - 2;
			}
		}

		VisCreator.setDateRanges(visDateRanges);
	}

}
