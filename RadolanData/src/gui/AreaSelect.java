package gui;

import gui.customPanels.MapDrawPanel;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.DataRead;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

/**
 * 
 * @author Patrick Allan Blair
 * @author Andre Merdan
 *
 */
public class AreaSelect extends JPanel{

	private static final long serialVersionUID = 3059395115902802865L;
	private JTextField area1;
	private JTextField area2;
	private JTextField area3;
	private JTextField area4;
	private static int[] area = {0, 0, 900, 900};
	private static MapDrawPanel drawPanel;
	private JLabel lblObererLinkerBreitengrad;
	private JLabel lblUntererRechterLngengrad;
	private JLabel lblUntererRechterBreitengrad;
	
	String[] bundesland = {"Baden-W�rttemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz" ,"Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Th�ringen"};

	public AreaSelect() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Bereich Ausw\u00E4hlen");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		drawPanel = new MapDrawPanel();
		drawPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_drawPanel = new GridBagConstraints();
		gbc_drawPanel.gridheight = 19;
		gbc_drawPanel.insets = new Insets(0, 0, 5, 5);
		gbc_drawPanel.fill = GridBagConstraints.BOTH;
		gbc_drawPanel.gridx = 1;
		gbc_drawPanel.gridy = 3;
		add(drawPanel, gbc_drawPanel);

		JLabel lblLngenBreitengrade = new JLabel("Oberer Linker L\u00E4ngengrad");
		GridBagConstraints gbc_lblLngenBreitengrade = new GridBagConstraints();
		gbc_lblLngenBreitengrade.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblLngenBreitengrade.insets = new Insets(0, 0, 5, 5);
		gbc_lblLngenBreitengrade.gridx = 2;
		gbc_lblLngenBreitengrade.gridy = 3;
		add(lblLngenBreitengrade, gbc_lblLngenBreitengrade);

		area1 = new JTextField();
		GridBagConstraints gbc_area1 = new GridBagConstraints();
		gbc_area1.gridwidth = 1;
		gbc_area1.insets = new Insets(0, 0, 5, 5);
		gbc_area1.fill = GridBagConstraints.HORIZONTAL;
		gbc_area1.gridx = 2;
		gbc_area1.gridy = 4;
		add(area1, gbc_area1);
		area1.setColumns(20);

		lblObererLinkerBreitengrad = new JLabel("Oberer Linker Breitengrad");
		GridBagConstraints gbc_lblObererLinkerBreitengrad = new GridBagConstraints();
		gbc_lblObererLinkerBreitengrad.anchor = GridBagConstraints.WEST;
		gbc_lblObererLinkerBreitengrad.insets = new Insets(0, 0, 5, 5);
		gbc_lblObererLinkerBreitengrad.gridx = 2;
		gbc_lblObererLinkerBreitengrad.gridy = 5;
		add(lblObererLinkerBreitengrad, gbc_lblObererLinkerBreitengrad);

		area2 = new JTextField();
		GridBagConstraints gbc_area2 = new GridBagConstraints();
		gbc_area2.gridwidth = 1;
		gbc_area2.insets = new Insets(0, 0, 5, 5);
		gbc_area2.fill = GridBagConstraints.HORIZONTAL;
		gbc_area2.gridx = 2;
		gbc_area2.gridy = 6;
		add(area2, gbc_area2);
		area2.setColumns(10);

		lblUntererRechterLngengrad = new JLabel("Unterer Rechter L\u00E4ngengrad");
		GridBagConstraints gbc_lblUntererRechterLngengrad = new GridBagConstraints();
		gbc_lblUntererRechterLngengrad.anchor = GridBagConstraints.WEST;
		gbc_lblUntererRechterLngengrad.insets = new Insets(0, 0, 5, 5);
		gbc_lblUntererRechterLngengrad.gridx = 2;
		gbc_lblUntererRechterLngengrad.gridy = 7;
		add(lblUntererRechterLngengrad, gbc_lblUntererRechterLngengrad);

		area3 = new JTextField();
		GridBagConstraints gbc_area3 = new GridBagConstraints();
		gbc_area3.gridwidth = 1;
		gbc_area3.insets = new Insets(0, 0, 5, 5);
		gbc_area3.fill = GridBagConstraints.HORIZONTAL;
		gbc_area3.gridx = 2;
		gbc_area3.gridy = 8;
		add(area3, gbc_area3);
		area3.setColumns(10);

		lblUntererRechterBreitengrad = new JLabel("Unterer Rechter Breitengrad");
		GridBagConstraints gbc_lblUntererRechterBreitengrad = new GridBagConstraints();
		gbc_lblUntererRechterBreitengrad.anchor = GridBagConstraints.WEST;
		gbc_lblUntererRechterBreitengrad.insets = new Insets(0, 0, 5, 5);
		gbc_lblUntererRechterBreitengrad.gridx = 2;
		gbc_lblUntererRechterBreitengrad.gridy = 9;
		add(lblUntererRechterBreitengrad, gbc_lblUntererRechterBreitengrad);

		area4 = new JTextField();
		GridBagConstraints gbc_area4 = new GridBagConstraints();
		gbc_area4.gridwidth = 1;
		gbc_area4.insets = new Insets(0, 0, 5, 5);
		gbc_area4.fill = GridBagConstraints.HORIZONTAL;
		gbc_area4.gridx = 2;
		gbc_area4.gridy = 10;
		add(area4, gbc_area4);
		area4.setColumns(10);
		
		JList<String> bundesList = new JList<String>(bundesland);
		GridBagConstraints gbc_bundeslandList = new GridBagConstraints();
		gbc_bundeslandList.gridwidth = 3;
		gbc_bundeslandList.anchor = GridBagConstraints.WEST;
		gbc_bundeslandList.insets = new Insets(20, 0, 5, 5);
		gbc_bundeslandList.gridx = 2;
		gbc_bundeslandList.gridy = 12;
		bundesList.setSize(30, 1);
		
		JScrollPane scrollPane = new JScrollPane(bundesList);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Bundeslaender"));
		add(scrollPane, gbc_bundeslandList);
			
		bundesList.addListSelectionListener(new ListSelectionListener() {
			String[] bundesland = {"Baden-W�rttemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz" ,"Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Th�ringen"};

			@Override
			public void valueChanged(ListSelectionEvent e) {
				switch (bundesList.getSelectedValue()) {
				case "Baden-W�rttemberg":
					area[0] = 314;
					area[1] = 589;
					area[2] = 566;
					area[3] = 863;
					break;
				case "Bayern":
					area[0] = 442;
					area[1] = 496;
					area[2] = 830;
					area[3] = 895;
					break;
				case "Berlin":
					area[0] = 706;
					area[1] = 212;
					area[2] = 819;
					area[3] = 314;
					break;
				case "Brandenburg":
					area[0] = 618;
					area[1] = 145;
					area[2] = 873;
					area[3] = 405;
					break;
				case "Bremen":
					area[0] = 370;
					area[1] = 119;
					area[2] = 516;
					area[3] = 258;
					break;
				case "Hamburg":
					area[0] = 451;
					area[1] = 88;
					area[2] = 592;
					area[3] = 225;
					break;
				case "Hessen":
					area[0] = 346;
					area[1] = 366;
					area[2] = 548;
					area[3] = 644;
					break;
				case "Mecklenburg-Vorpommern":
					area[0] = 555;
					area[1] = 10;
					area[2] = 845;
					area[3] = 219; 
					break;
				case "Niedersachsen":
					area[0] = 284;
					area[1] = 110;
					area[2] = 639;
					area[3] = 422; 
					break;
				case "Nordrhein-Westfalen":
					area[0] = 216;
					area[1] = 268;
					area[2] = 496;
					area[3] = 524;
					break;
				case "Rheinland-Pfalz":
					area[0] = 223;
					area[1] = 446;
					area[2] = 425;
					area[3] = 693;
					break;
				case "Saarland":
					area[0] = 212;
					area[1] = 570;
					area[2] = 355;
					area[3] = 689;
					break;
				case "Sachsen":
					area[0] = 650;
					area[1] = 366;
					area[2] = 895;
					area[3] = 544;
					break;
				case "Sachsen-Anhalt":
					area[0] = 555;
					area[1] = 210;
					area[2] = 759;
					area[3] = 461;
					break;
				case "Schleswig-Holstein":
					area[0] = 392;
					area[1] = 0;
					area[2] = 618;
					area[3] = 180;
					break;
				case "Th�ringen":
					area[0] = 496;
					area[1] = 373;
					area[2] = 726;
					area[3] = 542;
					break;

				default:
					break;
				}
			}
		});
		
		JButton btnBereichLaden = new JButton();
		ImageIcon icon_btnBereichLaden = new ImageIcon("webpage\\img\\load.png");
		btnBereichLaden.setIcon(icon_btnBereichLaden);
		GridBagConstraints gbc_btnBereichLaden = new GridBagConstraints();
		gbc_btnBereichLaden.anchor = GridBagConstraints.WEST;
		gbc_btnBereichLaden.insets = new Insets(-4, 70, 0, 5);
		gbc_btnBereichLaden.gridx = 2;
		gbc_btnBereichLaden.gridy = 11;
		add(btnBereichLaden, gbc_btnBereichLaden);
		
		btnBereichLaden.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> list = DataRead.readXML("options\\SavedAreaText.xml");
				if(list.size() == 4){
					area1.setText(list.get(0));
					area2.setText(list.get(1));
					area3.setText(list.get(2));
					area4.setText(list.get(3));
				}
			}
		});
		
		JButton btnBereichSpeichern = new JButton();
		ImageIcon icon_btnBereichSpeichern = new ImageIcon("webpage\\img\\save.png");
		btnBereichSpeichern.setIcon(icon_btnBereichSpeichern);
		GridBagConstraints gbc_btnBereichSpeichern = new GridBagConstraints();
		gbc_btnBereichSpeichern.anchor = GridBagConstraints.WEST;
		gbc_btnBereichSpeichern.insets = new Insets(-5, 140, 0, 5);
		gbc_btnBereichSpeichern.gridx = 2;
		gbc_btnBereichSpeichern.gridy = 11;
		add(btnBereichSpeichern, gbc_btnBereichSpeichern);
		
		btnBereichSpeichern.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(area1.getText());
				list.add(area2.getText());
				list.add(area3.getText());
				list.add(area4.getText());
				DataRead.writeXML(list, "options\\SavedAreaText.xml");
			}
		});
		
		final JToggleButton tglbtnBereichEinzeichnen = new JToggleButton();
		ImageIcon icon_tglbtnBereichEinzeichnen = new ImageIcon("webpage\\img\\pointer.png");
		tglbtnBereichEinzeichnen.setIcon(icon_tglbtnBereichEinzeichnen);
		GridBagConstraints gbc_tglbtnBereichEinzeichnen = new GridBagConstraints();
		gbc_tglbtnBereichEinzeichnen.anchor = GridBagConstraints.WEST;
		gbc_tglbtnBereichEinzeichnen.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnBereichEinzeichnen.gridx = 2;
		gbc_tglbtnBereichEinzeichnen.gridy = 11;
		add(tglbtnBereichEinzeichnen, gbc_tglbtnBereichEinzeichnen);

		areaToText();

		drawPanel.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent arg0) {
				if(tglbtnBereichEinzeichnen.isSelected()){
					tglbtnBereichEinzeichnen.setSelected(false);
				}
			}

			public void mousePressed(MouseEvent arg0) {
				if(tglbtnBereichEinzeichnen.isSelected()){
					int smaller = drawPanel.getWidth();
					if(smaller > drawPanel.getHeight()) smaller = drawPanel.getHeight();
					double mod = (double) 900 / (double) smaller;
					area[0] = (int) (arg0.getX() * mod);
					area[1] = (int) (arg0.getY() * mod);
					area[2] = area[0];
					area[3] = area[1];

					if(area[0] < 0) area[0] = 0;
					if(area[1] < 0) area[1] = 0;
					if(area[2] > 900) area[2] = 900;
					if(area[3] > 900) area[3] = 900;

					areaToText();
					drawPanel.updateArea(area);
					drawPanel.repaint();
				}
			}

			public void mouseExited(MouseEvent arg0) {

			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mouseClicked(MouseEvent arg0) {

			}
		});

		drawPanel.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent arg0) {

			}

			public void mouseDragged(MouseEvent arg0) {
				if(tglbtnBereichEinzeichnen.isSelected()){
					int smaller = drawPanel.getWidth();
					if(smaller > drawPanel.getHeight()) smaller = drawPanel.getHeight();
					double mod = (double) 900 / (double) smaller;
					area[2] = (int) (arg0.getX() * mod);
					area[3] = (int) (arg0.getY() * mod);

					if(area[0] > area[2]){
						int cache = area[0];
						area[0] = area[2];
						area[2] = cache;
					}

					if(area[1] > area[3]){
						int cache = area[1];
						area[1] = area[3];
						area[3] = cache;
					}
					if(area[0] < 0) area[0] = 0;
					if(area[1] < 0) area[1] = 0;
					if(area[2] > 900) area[2] = 900;
					if(area[3] > 900) area[3] = 900;

					areaToText();
					drawPanel.updateArea(area);
					drawPanel.repaint();
				}
			}
		});

		KeyListener listener = new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {

				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					textToArea();
				}
			}

			public void keyTyped(KeyEvent arg0) {
			}

		};

		area1.addKeyListener(listener);
		area2.addKeyListener(listener);
		area3.addKeyListener(listener);
		area4.addKeyListener(listener);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("webpage\\img\\RadolanBearbeitet.png"));
			drawPanel.updateImage(img);
		} catch (IOException e) {
		}
	}

	private void areaToText(){
		double upperX = Setup.getXPolar() + (double) area[0];
		double upperY = Setup.getYPolar() - (double) area[1];

		double lowerX = Setup.getXPolar() + (double) area[2];
		double lowerY = Setup.getYPolar() - (double) area[3];

		double r = 6370.040;

		double calc = 0;

		calc = Math.toDegrees(Math.atan((upperX * -1) / upperY)) + 10.0;

		area1.setText(String.valueOf(Math.round(calc * 100000.0) / 100000.0));

		calc = Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) - (Math.pow(upperX, 2) + Math.pow(upperY, 2));
		calc = calc / (Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) + (Math.pow(upperX, 2) + Math.pow(upperY, 2)));
		calc = Math.toDegrees(Math.asin(calc));
		area2.setText(String.valueOf(Math.round(calc * 100000.0) / 100000.0));

		calc = Math.toDegrees(Math.atan((lowerX * -1) / lowerY)) + 10.0;

		area3.setText(String.valueOf(Math.round(calc * 100000.0) / 100000.0));

		calc = Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) - (Math.pow(lowerX, 2) + Math.pow(lowerY, 2));
		calc = calc / (Math.pow(r, 2) * Math.pow((1 + Math.sin(Math.toRadians(60.0))), 2) + (Math.pow(lowerX, 2) + Math.pow(lowerY, 2)));
		calc = Math.toDegrees(Math.asin(calc));

		area4.setText(String.valueOf(Math.round(calc * 100000.0) / 100000.0));
	}

	private void textToArea(){

		double longitude1 = 0;
		double latitude1 = 0;
		double longitude2 = 0;
		double latitude2 = 0;
		try {
			longitude1 = Double.valueOf(area1.getText());
			latitude1 = Double.valueOf(area2.getText());
			longitude2 = Double.valueOf(area3.getText());
			latitude2 = Double.valueOf(area4.getText());
		} catch (NumberFormatException  e) {
			System.out.println("Konvertierung auf Integer fehlgeschlagen");
		}

		double temp = 0;
		if(longitude1 > longitude2){
			temp = longitude2;
			longitude2 = longitude1;
			longitude1 = temp;
			area1.setText(String.valueOf(longitude1));
			area3.setText(String.valueOf(longitude2));
		}
		if(latitude1 < latitude2){
			temp = latitude2;
			latitude2 = latitude1;
			latitude1 = temp;
			area2.setText(String.valueOf(latitude1));
			area4.setText(String.valueOf(latitude2));
		}

		double r = 6370.040;
		double m = (1 + Math.sin(Math.toRadians(60.0))) / (1 + Math.sin(Math.toRadians(latitude1)));

		double upperX = Setup.getXPolar() * -1;
		double upperY = Setup.getYPolar() * -1;

		upperX = upperX + (r * m * Math.cos(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(longitude1 - 10)));
		upperY = upperY + (-1 * r * m * Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(longitude1 - 10)));
		upperY = upperY * -1;

		m = (1 + Math.sin(Math.toRadians(60.0))) / (1 + Math.sin(Math.toRadians(latitude2)));

		double lowerX = Setup.getXPolar() * -1;
		double lowerY = Setup.getYPolar() * -1;

		lowerX = lowerX + (r * m * Math.cos(Math.toRadians(latitude2)) * Math.sin(Math.toRadians(longitude2 - 10)));
		lowerY = lowerY + (-1 * r * m * Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(longitude2 - 10)));
		lowerY = lowerY * -1;

		if(upperX >= 0 && upperX <= 900) area[0] = (int) upperX;
		if(upperY >= 0 && upperY <= 900) area[1] = (int) upperY;
		if(lowerX >= 0 && lowerX <= 900) area[2] = (int) lowerX;
		if(lowerY >= 0 && lowerY <= 900) area[3] = (int) lowerY;
		if(area[0] > area[2]){
			int cache = area[0];
			area[0] = area[2];
			area[2] = cache;
		}

		if(area[1] > area[3]){
			int cache = area[1];
			area[1] = area[3];
			area[3] = cache;
		}
		if(area[0] < 0) area[0] = 0;
		if(area[1] < 0) area[1] = 0;
		if(area[2] > 900) area[2] = 900;
		if(area[3] > 900) area[3] = 900;
		drawPanel.updateArea(area);
		drawPanel.repaint();
	}

	public static int[] getSelectedArea(){
		if(area[0] < 0) area[0] = 0;
		if(area[1] < 0) area[1] = 0;
		if(area[2] > 900) area[2] = 900;
		if(area[3] > 900) area[3] = 900;
		return area;
	}
}