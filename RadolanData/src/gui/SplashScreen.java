package gui;


import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashScreen extends JFrame{	
	
	public SplashScreen(){
		ImageIcon image = new ImageIcon("webpage\\img\\welcome.png");
		this.getContentPane().add(new JLabel(image), SwingConstants.CENTER);
		
		this.setPreferredSize(new Dimension(900, 560));
		this.setSize(900, 560);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		

		try {
		    Thread.sleep(10000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
	
		this.setVisible(false);
		this.dispose();
	}

}



