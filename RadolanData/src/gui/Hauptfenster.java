package gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import connection.WebApi;
import data.MongoAccess;
import main.Main;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Patrick Allan Blair
 * @author Andre Merdan
 *
 */
public class Hauptfenster extends JFrame {

	private static final long serialVersionUID = -7503647960732616207L;
	private static JTabbedPane tabbedPane;
	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	private WebApi web;

	public Hauptfenster() {
		web = new WebApi();
		
		try {
			web.startServer();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int i=JOptionPane.showConfirmDialog(null, "Programm beenden?");
				if(i==0){
					web.stopServer();
					if(Setup.getShutdownMongo()) MongoAccess.stopMongoService(Setup.getMongoDPath());
					executor.shutdown();
					System.exit(0);
				}
			}
		});
		
		//Set Title
		this.setTitle("Radolan");
		
		//Set Menubar		
//		JMenuBar menuBar = new JMenuBar();
//		JMenu menu = new JMenu("Menü");
//		menu.setMnemonic(KeyEvent.VK_A);
//		menuBar.add(menu);
//		
//		JMenuItem newProject = new JMenuItem("Neues Projekt", KeyEvent.VK_B);`
//		menu.add(newProject);
//		
//		
//		
//		this.setJMenuBar(menuBar);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		Setup setup = new Setup();
		tabbedPane.addTab("Grundeinstellungen", null, setup, null);

		AreaSelect area = new AreaSelect();
		tabbedPane.addTab("Suchbereich Auswahl", null, area, null);

		VisCreator creator = new VisCreator();
		tabbedPane.addTab("Visualisierung", null, creator, null);
		
		SpiralCreator select = new SpiralCreator();
		tabbedPane.addTab("Kreisdarstellung", null, select, null);

		GifCreator gif = new GifCreator();
		tabbedPane.addTab("Animation Erstellen", null, gif, null);

		this.pack();
		//Center Frame
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Gives runnable to the executor, which will sequentially start them in a thread.
	 * 
	 * @param run The runnable to be given to the executor.
	 */
	public static void feedExecutor(Runnable run){
		executor.execute(run);
	}
}
