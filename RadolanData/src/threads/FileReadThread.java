package threads;

import gui.Setup;
import data.DataRead;

/**
 * Reads through a file or folder (and subfolders), and processes all found precipitation data into the database.
 * Is run by feeding into the executor of the GUI main page.
 * 
 * @author Patrick Allan Blair
 *
 */
public class FileReadThread implements Runnable{

	String path;
	boolean overwrite;

	/**
	 * 
	 * @param path The path of the file of folder to be read.
	 * @param overwrite Overwrites existing entries of the database if true.
	 */
	public FileReadThread(String path, boolean overwrite){
		this.path = path;
		this.overwrite = overwrite;
	}

	public void run() {
		Setup.switchReadingState();
		DataRead.read(path, overwrite);
		Setup.switchReadingState();
	}
}
