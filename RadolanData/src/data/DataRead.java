package data;

import gui.Setup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.attribute.*;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Patrick Allan Blair
 *
 */
public class DataRead {

	private static int percentStep = 0;
	private static int increment = 0;
	private static boolean overwriteEntry;

	/**
	 * Begins the reading process for precipitation data.
	 * 
	 * @param path The filepath to be used.
	 * @param overwrite Whether existing data should be replaced.
	 */
	public static void read(String path, boolean overwrite){
		count(path);
		overwriteEntry = overwrite;
		iterateDirectory(path);
	}

	/**
	 * Deletes all images used for the visualization.
	 */
	public static void deletePictureMaterial(){
		Path p = Paths.get("webpage\\dataImages");
		FileVisitor<Path> visitor = new SimpleFileVisitor<Path>(){
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
				if(Files.probeContentType(file).equals("image/png")){
					System.out.println("Deleting " + file.toString());
					Files.delete(file);
				}
				return FileVisitResult.CONTINUE;
			}
		};
		try {
			Files.walkFileTree(p, visitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Counts the files that will be processed for input into the database.
	 * 
	 * @param path The filepath to be used.
	 */
	private static void count(String path){
		percentStep = 0;
		increment = 0;
		Path p = Paths.get(path);
		FileVisitor<Path> visitor = new SimpleFileVisitor<Path>(){
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
				percentStep++;
				return FileVisitResult.CONTINUE;
			}
		};
		try {
			Files.walkFileTree(p, visitor);
			percentStep = percentStep / 100;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Iterates through the given directory and subdirectories/files. Tries to read each file
	 * into the database.
	 * 
	 * @param path The filepath to be used.
	 */
	private static void iterateDirectory(String path){
		Path p = Paths.get(path);
		FileVisitor<Path> visitor = new SimpleFileVisitor<Path>(){
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
				if(increment + 1 > percentStep){
					Setup.addPercent();
					increment = 0;
				} else increment++;
				Setup.updateFileRead(file.toString());
				readBinaryFile(file);
				return FileVisitResult.CONTINUE;
			}
		};
		try {
			Files.walkFileTree(p, visitor);
			Setup.readCompletion();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static boolean[] bits(byte b1, byte b2) {
		return new boolean[]{
			(b1 &    1) != 0,
			(b1 &    2) != 0,
			(b1 &    4) != 0,
			(b1 &    8) != 0,
			(b1 & 0x10) != 0,
			(b1 & 0x20) != 0,
			(b1 & 0x40) != 0,
			(b1 & 0x80) != 0,
			(b2 &    1) != 0,
			(b2 &    2) != 0,
			(b2 &    4) != 0,
			(b2 &    8) != 0,
			(b2 & 0x10) != 0,
			(b2 & 0x20) != 0,
			(b2 & 0x40) != 0,
			(b2 & 0x80) != 0
		};
	}
	
	/**
	 * Reads the given file into the database as precipitation data if possible.
	 * Will not succeed if the filename does not equal the radolan standards, if
	 * no header ending can be found, or if less than 810,000 two byte pairs remain.
	 * 
	 * @param file The filepath to be used.
	 */
	private static void readBinaryFile(Path file){
		String filename = file.toFile().getName();
		
		if(!(filename.contains("rw") || filename.contains("ry"))) return;
		byte[] bytes;
		int size;
		try {
			bytes = Files.readAllBytes(file);
			size = bytes.length;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		int pos = 0;

		while(Byte.valueOf(bytes[pos]).intValue() != 3 && pos + 1 < size) pos++;
		if(size - pos <= 162000) return;
		
		String collection = "rw";
		if(filename.contains("ry")){
			collection = "ry";
		}
		int pointer = filename.indexOf("dwd");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(Integer.valueOf(filename.substring(pointer - 11, pointer - 9)) + 2000, Integer.valueOf(filename.substring(pointer - 9, pointer - 7)) - 1, 
				Integer.valueOf(filename.substring(pointer - 7, pointer - 5)), Integer.valueOf(filename.substring(pointer - 5, pointer - 3)), 
				Integer.valueOf(filename.substring(pointer - 3, pointer - 1)), 0);
		Date time = cal.getTime();

		if(!overwriteEntry && MongoAccess.checkExistingDataEntries(collection, time)) return;


		int[][] values = new int[900][900];
		int maxValue = 0;
		int value = 0;
		boolean[] bits = null;
		pos++;
		for(int x = 0; x < 900; x++){
			for(int y = 899; y >= 0; y--){
				bits = bits(bytes[pos], bytes[pos + 1]);
				
				if(bits.length != 16){
					pos = pos + 2;
					values[x][y] = 0;
					continue;
				}
				if(bits[13]){
					pos = pos + 2;
					values[x][y] = 0;
					continue;
				}
				
				value = 0;
				
				for (int i = 0; i < 12; i++){
					if(bits[i]){
						value += (1 << i);
					}
				}
				
				pos = pos + 2;
				
				if(maxValue < value) maxValue = value;
				values[x][y] = value;
			}
		}
		if(overwriteEntry) MongoAccess.deleteExistingDataEntries(collection, time, maxValue);
		String dataPath = collection + "\\" + cal.get(Calendar.YEAR) + "\\" + cal.get(Calendar.MONTH) + "\\Radolan-Data-" + time.toString().replace(":", "-") + "-" + collection + ".txt";
		
		writeValuesFile(dataPath, values);
		MongoAccess.addDataEntry(collection, time, maxValue);
	}

	/**
	 * Reads a specified value out of the precipitation data.
	 * 
	 * @param collection The collection (rw, ry) to be used.
	 * @param time The requested time.
	 * @param x The requested x-coordinate.
	 * @param y The requested y-coordinate.
	 * 
	 * @return The requested value.
	 */
	public static int getDistinctValue(String collection, Date time, int x, int y){
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		String dataPath = collection + "\\" + cal.get(Calendar.YEAR) + "\\" + cal.get(Calendar.MONTH) + "\\Radolan-Data-" + time.toString().replace(":", "-") + "-" + collection + ".txt";
		if(x < 0 || x >= 900 || y < 0 || y >= 900 || !checkValuesFile(dataPath)) return 0;
		return readValuesFile(dataPath)[x][y];
	}

	/**
	 * Creates a xml-file containing string values that can be read later. Used to save options.
	 * 
	 * @param entries The options that are to be saved into the created xml-file.
	 * @param filepath The path to be used.
	 */
	public static void writeXML(ArrayList<String> entries, String filepath){
		Document dom;
		Element e = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			dom = db.newDocument();

			Element root = dom.createElement("options");

			for(String s : entries){
				e = dom.createElement("entry");
				e.appendChild(dom.createTextNode(s));
				root.appendChild(e);
			}

			dom.appendChild(root);

			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

				if(!Files.exists(Paths.get(filepath))) {
					File f = new File(filepath);
					f.createNewFile();
				}
				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(filepath)));

			} catch (TransformerException te) {
				System.out.println(te.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		} catch (ParserConfigurationException pce) {
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
	}

	/**
	 * Reads previously created xml-files and attempts to extract saved options.
	 * @param filepath The path to be used.
	 * 
	 * @return A list of string values that were found in the xml-file.
	 */
	public static ArrayList<String> readXML(String filepath){
		ArrayList<String> list = new ArrayList<String>();

		if(!Files.exists(Paths.get(filepath))) {
			return list;
		}
		try {
			File xml = new File(filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("entry");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					System.out.println("Found Path : " + element.getTextContent());
					list.add(element.getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Creates a textfile containing the specified output.
	 * 
	 * @param buffer The buffer containing the text to be saved.
	 * @param path The path to be used.
	 */
	public static void writeTextOutputFile(StringBuffer buffer, String path){
		String dataPath = path + "\\Suchergebnisse_" + new Date().toString().replace(":", "_") + ".txt";
		File file = new File(dataPath);
		try {
			file.getParentFile().mkdirs();
			Files.write(file.toPath(), buffer.toString().getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Path: "+path);
		System.out.println("Datapath: "+dataPath);
	}

	/**
	 * Checks whether a given file containing precipitation data already exists.
	 * 
	 * @param fileName The filename to be used.
	 * @return True if the file exists.
	 */
	public static boolean checkValuesFile(String fileName){
		String filepath = Setup.getDatabasePath() + "\\RadolanTextFiles\\" + fileName;
		return Files.exists(Paths.get(filepath));
	}

	/**
	 * Writes precipitation data into a textfile.
	 * 
	 * @param fileName The filename to be used.
	 * @param values The values to be saved.
	 */
	public static void writeValuesFile(String fileName, int[][] values){
		File file = new File(Setup.getDatabasePath() + "\\RadolanTextFiles\\" + fileName);
		try {
			Files.deleteIfExists(file.toPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		for(int x = 0; x < 900; x++){
			for(int y = 0; y < 900; y++){
				buffer.append(values[x][y] + ";");
			}
		}

		try {
			file.getParentFile().mkdirs();
			Files.write(file.toPath(), buffer.toString().getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads precipitation data out of a textfile.
	 * @param fileName The filename to be used.
	 * @return The read precipitation data.
	 */
	public static int[][] readValuesFile(String fileName){
		int[][] values = new int[900][900];
		if(!checkValuesFile(fileName)) return null;
		File file = new File(Setup.getDatabasePath() + "\\RadolanTextFiles\\" + fileName);
		FileReader reader = null;
		try {
			StringBuffer content = new StringBuffer();
			reader = new FileReader(file);

			int i = 0;
			char curr = ' ';
			for(int x = 0; x < 900; x++){
				for(int y = 0; y < 900; y++){
					while(true){
						i = reader.read();
						if(i == -1){
							reader.close();
							return values;
						}
						curr = (char) i;
						if(curr != ';') content.append(curr);
						else break;
					}
					
					values[x][y] = Integer.valueOf(content.toString());
					content.setLength(0);
					
				}
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return values;
	}
}
