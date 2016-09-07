package gui;


import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashScreen{	
	
	
	// Generated Splashscreen
	public static Integer SplashScreenThread() {


		ImageIcon image = new ImageIcon("webpage\\img\\welcome.png");
		JWindow window = new JWindow();
		window.getContentPane().add(new JLabel(image), SwingConstants.CENTER);
		
		window.setBounds(500, 150, 500, 500);
		window.setVisible(true);
		

		try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		window.setVisible(false);
		window.dispose();
		
		return null;
	}

}



