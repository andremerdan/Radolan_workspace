package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jdesktop.swingx.search.TableSearchable;

import connection.WebApi;
import data.MongoAccess;
import main.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Hauptfenster extends JFrame implements ActionListener{

	private static final long serialVersionUID = -7503647960732616207L;
	public static JTabbedPane tabbedPane;
	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	private WebApi web;

	public static JPanel buttonPanel;
	public static JButton moveLeft;
	public static JButton moveRight;
	public static Hauptfenster frame;
	
//	private Setup setup;

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
		


		initComponents();
		this.setPreferredSize(new Dimension(900, 560));
		this.pack();
		//Center Frame
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
		
		frame = this;
	}
	
	/**
	 * Initialise the containers, layout and components.
	 */
	private void initComponents() {
		tabbedPane = new JTabbedPane();
		buttonPanel = new JPanel();
		moveLeft = new JButton("Zurück");
		moveRight = new JButton("Weiter");		
		
		// add actionListeners to the buttons // 
		moveLeft.addActionListener(this);
		moveRight.addActionListener(this);		
		
		buttonPanel.setLayout( new GridLayout(1,2));
		buttonPanel.add(moveLeft);
		buttonPanel.add(moveRight);
		
		
		addTestPanes();	
		
		// add both containers to the JFrame //
		add(tabbedPane, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);		
		
//		if(setup.verbinden.isSelected()){	
//			moveRight.setBackground(Color.green);
//		}
	}
	
	/**
	 * Method just adds some tabs to the panel with
	 * automatically generated names and labels.
	 */
	private void addTestPanes() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER); 	

		Setup setup = new Setup();
		tabbedPane.addTab("1. Grundeinstellungen", null, setup, null);

		AreaSelect area = new AreaSelect();
		tabbedPane.addTab("2. Suchbereich Auswahl", null, area, null);

		VisCreator creator = new VisCreator();
		tabbedPane.addTab("3. Visualisierung", null, creator, null);
		
		SpiralCreator select = new SpiralCreator();
		tabbedPane.addTab("Kreisdarstellung", null, select, null);

		GifCreator gif = new GifCreator();
		tabbedPane.addTab("Animation Erstellen", null, gif, null);
		
	}

	/**
	 * Implements the desired actions to perform on an event,
	 * in this case. Change the selected tab on a
	 * JTabbedPane.
	 */
	public void actionPerformed(ActionEvent evt) {		
		// check there is more than zero tabs
		if (tabbedPane.getTabCount() == 0) {
			System.err.println("No Tabs In Pane");
			return;
		}
		if (evt.getSource() == moveLeft) {
			if(tabbedPane.getSelectedIndex() == 0)
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			else 
				tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex()-1);			
		}
		if (evt.getSource() == moveRight) {			
			if(tabbedPane.getSelectedIndex() == tabbedPane.getTabCount()-1)
				tabbedPane.setSelectedIndex(0);
			else 
				tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex()+1);	
		}
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
