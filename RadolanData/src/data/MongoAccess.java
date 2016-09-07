package data;

import gui.Setup;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

import connection.GsonHelper;

/**
 * This class contains all operations relating to the database.
 * 
 * @author Patrick Allan Blair
 *
 */
public class MongoAccess {

	private static Mongo mongo;
	private static DB db;

	/**
	 * Creates (optionally) and connects to the database.
	 * 
	 * @param mongod The path to the mongod.exe file
	 * @param database The path to the used database.
	 * @param startMongo Whether this should start the database or just connect to an already existing one.
	 * 
	 * @return True if a connection was established.
	 */
	public static boolean startMongo(String mongod, String database, boolean startMongo){
		if(!startMongo) return createMongoAccess();
		if(startMongoService(mongod, database)){
			return createMongoAccess();
		}
		else return false;
	}

	/**
	 * Starts the mongod.exe file.
	 * 
	 * @param mongod The path to the used database.
	 * @param database The path to the used database.
	 * 
	 * @return True if successful.
	 */
	private static boolean startMongoService(String mongod, String database){
		Process process;
		try {
			process = Runtime.getRuntime().exec(mongod + "\\mongod.exe --dbpath " + database);
			while(process.getInputStream().available() <= 0);
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Connects to the database.
	 * 
	 * @return True if successful.
	 */
	private static boolean createMongoAccess(){
		try {
			if(mongo == null) mongo  = new Mongo();
		} catch (UnknownHostException e) {
			System.out.println("Datenbank konnte nicht gefunden werden.");
			e.printStackTrace();
			return false;
		}

		db = mongo.getDB("RadolanData");
		if(db == null) return false;
		File file = new File(Setup.getDatabasePath() + "\\RadolanTextFiles");
		file.mkdirs();
		
		return true;
	}

	/**
	 * Stops the database.
	 * 
	 * @param mongod The path to the used database.
	 */
	public static void stopMongoService(String mongod){
		if(mongo == null) return;
		DB admin = mongo.getDB("admin");
		try{
			admin.command(new BasicDBObject("shutdown", 1));
		} catch (Exception e){
		} finally{
			mongo.close();
			db = null;
			mongo = null;
		}
	}

	/**
	 * Searches through the database for dates matching the given paramaters.
	 * 
	 * @param collection The collection(rw, ry) to be used.
	 * @param fromDate The starting date for the search.
	 * @param toDate The ending date for the search.
	 * @param minValue The minimum precipitation value to be searched for.
	 * @param area The area in which the search is to be conducted.
	 * @param dates A list that is to be filled with dates matching the given search parameters.
	 * @return The number of found dates matching the parameters.
	 */
	public static int searchBetween(String collection, Date fromDate, Date toDate, int minValue, int[] area, ArrayList<Date> dates){
		if(mongo == null || area[2] > 900 || area[0] < 0 || area[1] < 0 || area[3] > 900) return 0;

		long beginning = fromDate.getTime();
		long target = toDate.getTime();
		DBObject query = QueryBuilder.start("t").greaterThanEquals(beginning).and("t").lessThanEquals(target).and("v").greaterThanEquals(minValue).get();

		int subID = minValue - (minValue % 100);
		int count = 0;

		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int startYear = cal.get(Calendar.YEAR);
		cal.setTime(toDate);
		int stopYear = cal.get(Calendar.YEAR);

		for(int i = startYear; i <= stopYear; i++){
			String subcollection = collection + "_" + i + "_" + subID;
			DBCollection coll = db.getCollection(subcollection);

			if(coll.count(query) > 0){
				DBCursor cursor = coll.find(query);
				while(cursor.hasNext()){
					Date entry = new Date((Long) cursor.next().get("t"));

					cal.setTime(entry);
					String dataPath = collection + "\\" + cal.get(Calendar.YEAR) + "\\" + cal.get(Calendar.MONTH) + "\\Radolan-Data-" + entry.toString().replace(":", "-") + "-" + collection + ".txt";
					int[][] values = DataRead.readValuesFile(dataPath);
					if(values == null) continue;
					int addition = 0;
					for(int x = area[0]; x < area[2]; x++){
						for(int y = area[1]; y < area[3]; y++){
							if(values[x][y] >= minValue) addition++;
						}
					}
					if(addition > 0){
						dates.add(entry);
						count = count + addition;
					}
				}
				cursor.close();
			}
		}
		return count;
	}

	/**
	 * Adds a user created entry to the database.
	 * @param entry The entry to be added-
	 */
	public static void addUserCreatedEntry(UserCreatedEntry entry){
		if(mongo == null) return;
		DBCollection coll = db.getCollection("found");
		DBObject dbObject = (DBObject) JSON.parse(GsonHelper.serialize(entry));
		coll.insert(dbObject);
	}

	/**
	 * Searches through user created entries for those matching the given parameters.
	 * 
	 * @param start The starting date for the search.
	 * @param end The ending date for the search.
	 * @param area The area for the search.
	 * @return A list of user entries matching the given parameters.
	 */
	public static ArrayList<UserCreatedEntry> queryUserCreatedEntries(Date start, Date end, int[] area){
		if(mongo == null){
			return null;
		}
		DBCollection coll = db.getCollection("found");

		DBObject query = QueryBuilder.start("t1").greaterThanEquals(start.getTime()).and("t2").lessThanEquals(end.getTime()).get();
		DBCursor cursor = coll.find(query);

		ArrayList<UserCreatedEntry> foundEntries = new ArrayList<UserCreatedEntry>();
		while(cursor.hasNext()){
			UserCreatedEntry testing = (UserCreatedEntry) GsonHelper.deserialize(cursor.next().toString(), UserCreatedEntry.class);
			int[] c = testing.getCenter();
			if(c.length == 0) foundEntries.add((UserCreatedEntry) GsonHelper.deserialize(cursor.curr().toString(), UserCreatedEntry.class));
			for(int i = 0; i < c.length; i = i + 2){
				if(c[i] >= area[0] && c[i] <= area[2] && c[i+1] >= area[1] && c[i+1] <= area[3]){
					foundEntries.add((UserCreatedEntry) GsonHelper.deserialize(cursor.curr().toString(), UserCreatedEntry.class));
					break;
				}
			}
		}
		cursor.close();
		return foundEntries;
	}

	/**
	 * Deletes a specified user created entry.
	 * 
	 * @param g The unique glyph-file name of the entry.
	 */
	public static void deleteUserCreatedEntry(String g){
		if(mongo == null) return;
		DBCollection coll = db.getCollection("found");
		DBObject query = QueryBuilder.start("g").is(g).get();

		DBCursor cursor = coll.find(query);
		while(cursor.hasNext()){
			DBObject found = cursor.next();
			coll.remove(found);
		}
		cursor.close();
	}

	/**
	 * Searches the database for the specified entry.
	 * 
	 * @param g The unique glyph-file name of the entry.
	 * @return The user entry, if found.
	 */
	public static UserCreatedEntry querySpecificUserCreatedEntry(String g){
		if(mongo == null) return null;
		DBCollection coll = db.getCollection("found");
		DBObject query = QueryBuilder.start("g").is(g).get();

		DBCursor cursor = coll.find(query);
		if(cursor.hasNext()){
			UserCreatedEntry entry = (UserCreatedEntry) GsonHelper.deserialize(cursor.next().toString(), UserCreatedEntry.class);
			cursor.close();
			return entry;
		} else return null;
	}

	/**
	 * Overwrites the specified user created entry.
	 * @param oldEntry The unique glyph-file name of the old entry.
	 * @param newEntry The new entry.
	 */
	public static void overwriteUserCreatedEntry(String oldEntry, UserCreatedEntry newEntry){
		if(mongo == null) return;
		DBCollection coll = db.getCollection("found");

		DBObject query = new BasicDBObject();
		query.put("g", oldEntry);

		DBCursor cursor = coll.find(query);
		while(cursor.hasNext()){
			DBObject found = cursor.next();
			coll.remove(found);
		}
		cursor.close();
		coll.insert((DBObject) JSON.parse(GsonHelper.serialize(newEntry)));
	}

	/**
	 * 
	 * @param entry
	 */
	public static void addNegativeEntry(NegativeEntry entry){
		if(mongo == null) return;
		DBCollection coll = db.getCollection("negatives");

		DBObject query = QueryBuilder.start("t1").lessThanEquals(entry.getStartTime()).and("t2").greaterThanEquals(entry.getStartTime()).
				or(QueryBuilder.start("t1").lessThanEquals(entry.getEndTime()).and("t2").greaterThanEquals(entry.getEndTime()).get()).get();
		DBCursor cursor = coll.find(query);

		while(cursor.hasNext()){
			DBObject cache = cursor.next();
			NegativeEntry testing = (NegativeEntry) GsonHelper.deserialize(cache.toString(), NegativeEntry.class);
			int[] tArea = testing.getArea();
			int[] eArea = entry.getArea();
			if(!(eArea[0] > tArea[2]) && !(eArea[1] > tArea[3]) && !(eArea[2] < tArea[0]) && !(eArea[3] < tArea[1])){
				if(entry.getStartTime().equals(testing.getStartTime()) && entry.getEndTime().equals(testing.getEndTime())){
					if(eArea[0] == tArea[0] && eArea[2] == tArea[2]){
						if(eArea[1] > tArea[1]) entry.setY1(tArea[1]);
						if(eArea[3] < tArea[3]) entry.setY2(tArea[3]);
						coll.find(cache).remove();
					} else if(eArea[0] <= tArea[0] && eArea[2] >= tArea[2] && eArea[1] <= tArea[1] && eArea[3] >= tArea[3]){
						coll.find(cache).remove();
					} else if (eArea[1] == tArea[1] && eArea[3] == tArea[3]){
						if(eArea[0] > tArea[0]) entry.setX1(tArea[0]);
						if(eArea[2] < tArea[2]) entry.setX2(tArea[2]);
						coll.find(cache).remove();
					} else if(eArea[0] >= tArea[0] && eArea[2] <= tArea[2] && eArea[1] >= tArea[1] && eArea[3] <= tArea[3]){
						entry = testing;
						coll.find(cache).remove();
					}
				} else if (eArea[0] == tArea[0] && eArea[1] == tArea[1] && eArea[2] == tArea[2] && eArea[3] == tArea[3]){
					if(entry.getStartTime().after(testing.getStartTime())) entry.setStartTime(testing.getStartTime());
					if(entry.getEndTime().before(testing.getEndTime())) entry.setEndTime(testing.getEndTime());
					coll.find(cache).remove();
				}
			}
		}

		DBObject dbObject = (DBObject) JSON.parse(GsonHelper.serialize(entry));
		coll.insert(dbObject);
		cursor.close();
	}

	/**
	 * 
	 */
	public static void deleteNegativeEntry(NegativeEntry entry){
		if(mongo == null) return;
		DBCollection coll = db.getCollection("negatives");

		DBObject query = QueryBuilder.start("t1").lessThanEquals(entry.getStartTime()).and("t2").greaterThanEquals(entry.getStartTime()).
				or(QueryBuilder.start("t1").lessThanEquals(entry.getEndTime()).and("t2").greaterThanEquals(entry.getEndTime()).get()).get();
		DBCursor cursor = coll.find(query);

		while(cursor.hasNext()){
			DBObject cache = cursor.next();
			NegativeEntry testing = (NegativeEntry) GsonHelper.deserialize(cache.toString(), NegativeEntry.class);
			int[] tArea = testing.getArea();
			int[] eArea = entry.getArea();
			if(!(eArea[0] > tArea[2]) && !(eArea[1] > tArea[3]) && !(eArea[2] < tArea[0]) && !(eArea[3] < tArea[1])){
				if((entry.getStartTime().after(testing.getStartTime()) && entry.getStartTime().before(testing.getEndTime()))
						|| (entry.getEndTime().after(testing.getStartTime()) && entry.getEndTime().before(testing.getEndTime()))
						|| (entry.getStartTime().before(testing.getStartTime()) && entry.getEndTime().after(testing.getEndTime()))) 
					coll.find(cache).remove();
			}
		}
		cursor.close();
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @param area
	 * @return
	 */
	public static ArrayList<Date> queryNegativeEntries(Date start, Date end, int[] area){
		if(mongo == null) return null;
		DBCollection coll = db.getCollection("negatives");
		DBObject query = QueryBuilder.start("t1").lessThanEquals(start.getTime()).and("t2").greaterThanEquals(end.getTime()).get();
		DBCursor cursor = coll.find(query);

		ArrayList<Date> found = new ArrayList<Date>();
		found.add(start);
		found.add(end);
		while(cursor.hasNext()){
			NegativeEntry testing = (NegativeEntry) GsonHelper.deserialize(cursor.toString(), NegativeEntry.class);
			int[] tArea = testing.getArea();
			if(area[0] >= tArea[0] && area[1] >= tArea[1] && area[2] <= tArea[2] && area[3] <= tArea[3]){
				for(int i = 0; i < found.size(); i = i + 2){
					if(found.get(i).getTime() < testing.getStartTime().getTime() && found.get(i + 1).getTime() > testing.getStartTime().getTime()){
						if(found.get(i + 1).getTime() <= testing.getEndTime().getTime()) found.get(i + 1).setTime(testing.getStartTime().getTime());
						else {
							found.add(testing.getEndTime());
							found.add(found.get(i + 1));
							found.get(i + 1).setTime(testing.getStartTime().getTime());
							i = i + 2;
						}
					} else if(found.get(i).getTime() >= testing.getStartTime().getTime() && found.get(i).getTime() < testing.getEndTime().getTime()){
						if(found.get(i + 1).getTime() > testing.getEndTime().getTime()) found.get(i).setTime(testing.getEndTime().getTime());
						else {
							found.remove(i);
							found.remove(i);
							i = i - 2;
						}
					}
				}
			}
		}
		cursor.close();
		return found;
	}
	
	/**
	 * Checks whether a entry already exists within the database.
	 * 
	 * @param collection The collection (rw, ry) the entry would be found under.
	 * @param time The time of the entry.
	 * @return True, if entry is found.
	 */
	public static boolean checkExistingDataEntries(String collection, Date time){
		if(mongo == null) return false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);

		String subcollection = collection + "_" + cal.get(Calendar.YEAR) + "_0";
		DBObject query = QueryBuilder.start("t").is(time.getTime()).get();
		DBCollection coll = db.getCollection(subcollection);
		if(coll.count(query) >= 1){
			return true;
		} 
		return false;
	}
	
	/**
	 * Deletes an entry from the database, including all redundant entries dependant upon
	 * the maximum precipitation value.
	 * 
	 * @param collection The collection (rw, ry) the entry would be found under.
	 * @param time The time of the entry.
	 * @param maxValue The maximum value of precipitation data corresponding to this entry.
	 */
	public static void deleteExistingDataEntries(String collection, Date time, int maxValue){
		if(mongo == null) return;
		if(!checkExistingDataEntries(collection, time)) return;

		DBObject query = QueryBuilder.start("t").is(time.getTime()).get();
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);

		for(int i = 0; i <= maxValue - (maxValue % 100); i = i + 100){
			String subcollection = collection + "_" + cal.get(Calendar.YEAR) + "_" + i;
			DBCollection coll = db.getCollection(subcollection);

			DBCursor cursor = coll.find(query);
			while(cursor.hasNext()){
				DBObject found = cursor.next();
				coll.remove(found);
			}
			cursor.close();
		}
	}
	
	/**
	 * Adds the given values as an entry into the database.
	 * 
	 * @param collection The collection (rw, ry) the entry will be found under.
	 * @param time The time of the entry.
	 * @param maxValue The maximum value of precipitation data corresponding to this entry.
	 */
	public static void addDataEntry(String collection, Date time, int maxValue){
		if(mongo == null) return;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);

		BasicDBObject dbobject = new BasicDBObject();
		dbobject.put("t", time.getTime());
		dbobject.put("v", maxValue);
		for(int i = 0; i <= maxValue - (maxValue % 100); i = i + 100){
			String subcollection = collection + "_" + cal.get(Calendar.YEAR) + "_" + i;
			DBCollection coll = db.getCollection(subcollection);
			coll.insert(dbobject);
		}
	}
	
	/**
	 * Drops a collection from the database.
	 * 
	 * @param collection The collection to be dropped.
	 */
	public static void dropCollection(String collection){
		if(mongo == null) return;
		db.getCollection(collection).drop();
	}
}
