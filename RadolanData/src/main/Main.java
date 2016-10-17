package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.text.html.ImageView;

import javafx.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import gui.Hauptfenster;
import gui.SplashScreen;

/**
 * Executable class for the visualization program.
 * 
 * @author Patrick Allan Blair
 * @author Andre Merdan
 *
 */
public class Main extends JFrame {

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// Open Splashscreen
		int threadNum = 2;

		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
		
		FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new Callable<Integer>(){
			
			public Integer call(){
				SplashScreen splashScreen = new SplashScreen();
				return null;
			}			
		});	
		taskList.add(futureTask1);
		executor.execute(futureTask1);
		
		FutureTask<Integer> futureTask2 = new FutureTask<Integer>(new Callable<Integer>(){
			
			public Integer call(){
				@SuppressWarnings("unused")
				Hauptfenster radolan = new Hauptfenster();
				return null;
				
			}			
		});	
		taskList.add(futureTask2);
		executor.execute(futureTask2);	
		
	}
}
