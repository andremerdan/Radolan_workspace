package gui;

import gui.customPanels.SpiralVisualizationPanel;
import gui.customPanels.SpiralNotificationPanel;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSpinner;

import org.jdesktop.swingx.JXDatePicker;

import threads.DatabaseSpiralVisThread;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * 
 * @author Patrick Allan Blair
 *
 */
public class SpiralCreator extends JPanel{

	private static final long serialVersionUID = 1046658425006633788L;

	private JLabel lblStartDatum;
	private JLabel lblDatum;
	private JLabel lblUhrzeit;
	private static JXDatePicker firstDatePick;
	private JSpinner firstTimePick;
	private static JXDatePicker secondDatePick;
	private JSpinner secondTimePick;
	private JLabel lblBis;

	private JButton btnGewhlteZeitAuf;
	private JScrollPane scrollPane;
	private SpiralVisualizationPanel drawPanel;

	private static String[][] lookupTable = new String[0][0];
	private int panelMoveX = 0;
	private int panelMoveY = 0;
	@SuppressWarnings("rawtypes")
	private JComboBox visualizationSelect;
	private JLabel lblVisualisierungsart;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SpiralCreator() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2015, 0, 1, 0, 0, 0);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		lblStartDatum = new JLabel("Visualisierung von Nutzereintr\u00E4gen");
		lblStartDatum.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblStartDatum = new GridBagConstraints();
		gbc_lblStartDatum.gridwidth = 10;
		gbc_lblStartDatum.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDatum.gridx = 1;
		gbc_lblStartDatum.gridy = 1;
		add(lblStartDatum, gbc_lblStartDatum);

		label_2 = new JLabel("Zeitbereich f\u00FCr Suche");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.gridwidth = 5;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 3;
		add(label_2, gbc_label_2);

		lblDatum = new JLabel("Datum");
		GridBagConstraints gbc_lblDatum = new GridBagConstraints();
		gbc_lblDatum.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatum.gridx = 1;
		gbc_lblDatum.gridy = 4;
		add(lblDatum, gbc_lblDatum);

		lblUhrzeit = new JLabel("Uhrzeit");
		GridBagConstraints gbc_lblUhrzeit = new GridBagConstraints();
		gbc_lblUhrzeit.insets = new Insets(0, 0, 5, 5);
		gbc_lblUhrzeit.gridx = 2;
		gbc_lblUhrzeit.gridy = 4;
		add(lblUhrzeit, gbc_lblUhrzeit);

		label = new JLabel("Datum");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 4;
		add(label, gbc_label);

		label_1 = new JLabel("Uhrzeit");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 5;
		gbc_label_1.gridy = 4;
		add(label_1, gbc_label_1);

		lblVisualisierungsart = new JLabel("Visualisierungsart:");
		GridBagConstraints gbc_lblVisualisierungsart = new GridBagConstraints();
		gbc_lblVisualisierungsart.anchor = GridBagConstraints.EAST;
		gbc_lblVisualisierungsart.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisualisierungsart.gridx = 9;
		gbc_lblVisualisierungsart.gridy = 4;
		add(lblVisualisierungsart, gbc_lblVisualisierungsart);

		visualizationSelect = new JComboBox();
		visualizationSelect.setModel(new DefaultComboBoxModel(new String[] {"Diamant", "Pfeil", "Textkreuz"}));
		GridBagConstraints gbc_visualizationSelect = new GridBagConstraints();
		gbc_visualizationSelect.anchor = GridBagConstraints.WEST;
		gbc_visualizationSelect.insets = new Insets(0, 0, 5, 5);
		gbc_visualizationSelect.gridx = 10;
		gbc_visualizationSelect.gridy = 4;
		add(visualizationSelect, gbc_visualizationSelect);

		firstDatePick = new JXDatePicker(cal.getTime());
		GridBagConstraints gbc_firstDatePick = new GridBagConstraints();
		gbc_firstDatePick.insets = new Insets(0, 0, 5, 5);
		gbc_firstDatePick.gridx = 1;
		gbc_firstDatePick.gridy = 5;
		add(firstDatePick, gbc_firstDatePick);

		SpinnerModel model1 = new SpinnerDateModel();
		firstTimePick = new JSpinner(model1);
		JComponent editor1 = new JSpinner.DateEditor(firstTimePick, "HH:mm");
		firstTimePick.setEditor(editor1);
		firstTimePick.setValue(cal.getTime());

		GridBagConstraints gbc_firstTimePick = new GridBagConstraints();
		gbc_firstTimePick.insets = new Insets(0, 0, 5, 5);
		gbc_firstTimePick.gridx = 2;
		gbc_firstTimePick.gridy = 5;
		add(firstTimePick, gbc_firstTimePick);

		lblBis = new JLabel("bis");
		GridBagConstraints gbc_lblBis = new GridBagConstraints();
		gbc_lblBis.insets = new Insets(0, 0, 5, 5);
		gbc_lblBis.gridx = 3;
		gbc_lblBis.gridy = 5;
		add(lblBis, gbc_lblBis);

		SpinnerModel model2 = new SpinnerDateModel();

		secondDatePick = new JXDatePicker(cal.getTime());
		GridBagConstraints gbc_secondDatePick = new GridBagConstraints();
		gbc_secondDatePick.insets = new Insets(0, 0, 5, 5);
		gbc_secondDatePick.gridx = 4;
		gbc_secondDatePick.gridy = 5;
		add(secondDatePick, gbc_secondDatePick);
		secondTimePick = new JSpinner(model2);
		JComponent editor2 = new JSpinner.DateEditor(secondTimePick, "HH:mm");
		secondTimePick.setEditor(editor2);
		secondTimePick.setValue(cal.getTime());

		GridBagConstraints gbc_secondTimePick = new GridBagConstraints();
		gbc_secondTimePick.insets = new Insets(0, 0, 5, 5);
		gbc_secondTimePick.gridx = 5;
		gbc_secondTimePick.gridy = 5;
		add(secondTimePick, gbc_secondTimePick);


		btnGewhlteZeitAuf = new JButton("Gew\u00E4hlte Zeit auf Eintr\u00E4ge \u00FCberpr\u00FCfen");
		GridBagConstraints gbc_btnGewhlteZeitAuf = new GridBagConstraints();
		gbc_btnGewhlteZeitAuf.anchor = GridBagConstraints.EAST;
		gbc_btnGewhlteZeitAuf.gridwidth = 4;
		gbc_btnGewhlteZeitAuf.insets = new Insets(0, 0, 5, 5);
		gbc_btnGewhlteZeitAuf.gridx = 7;
		gbc_btnGewhlteZeitAuf.gridy = 5;
		add(btnGewhlteZeitAuf, gbc_btnGewhlteZeitAuf);

		btnGewhlteZeitAuf.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Hauptfenster.feedExecutor(new DatabaseSpiralVisThread(drawPanel, getDates(), AreaSelect.getSelectedArea(), visualizationSelect.getSelectedIndex()));
			}
		});

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 10;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 7;
		add(scrollPane, gbc_scrollPane);

		drawPanel = new SpiralVisualizationPanel();
		drawPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		drawPanel.setSize(800, 800);
		scrollPane.setViewportView(drawPanel);

		drawPanel.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent arg0) {

			}

			public void mousePressed(MouseEvent arg0) {
				panelMoveX = arg0.getX();
				panelMoveY = arg0.getY();
			}

			public void mouseExited(MouseEvent arg0) {

			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Checking");
				int x = arg0.getX();
				int y = arg0.getY();
				String images = drawPanel.testCoordinates(x, y);
				String[] temp = null;
				if(images.contains(";")) temp = images.split(";");
				if(temp != null){
					System.out.println("Creating");
					JFrame jf = new JFrame();
					JPanel mainpanel = new JPanel(new FlowLayout());

					for(String s : temp){
						mainpanel.add(new SpiralNotificationPanel(s));
					}
					mainpanel.repaint();
					jf.getContentPane().add(mainpanel);
					jf.setLocation(x, y);
					jf.pack();
					jf.setVisible(true);
				}
			}
		});

		drawPanel.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent arg0) {

			}

			public void mouseDragged(MouseEvent arg0) {
				drawPanel.shiftPosition(arg0.getX() - panelMoveX, arg0.getY() - panelMoveY);
				drawPanel.repaint();
				panelMoveX = arg0.getX();
				panelMoveY = arg0.getY();
			}
		});
	}
	
	// Changing the Date - ActionListener from verbinden
	public static void changeDate(){
		firstDatePick.setDate(Setup.begin);
		secondDatePick.setDate(Setup.end);
	}

	/**
	 * Returns the selected starting and ending dates for this tab.
	 * @return A list with the two dates.
	 */
	private ArrayList<Date> getDates(){
		ArrayList<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);

		Date selected = firstDatePick.getDate();
		int year = 0;
		year = Integer.valueOf(new SimpleDateFormat("yyyy").format(selected));

		int month = 0;
		month = Integer.valueOf(new SimpleDateFormat("MM").format(selected)) - 1;

		int day = 0;
		day = Integer.valueOf(new SimpleDateFormat("dd").format(selected));

		selected = ((Date) firstTimePick.getValue());
		int hour = 0;
		hour = Integer.valueOf(new SimpleDateFormat("HH").format(selected));

		int minute = 0;
		minute = Integer.valueOf(new SimpleDateFormat("mm").format(selected));
		minute = minute - minute % 5;
		cal.set(year, month, day, hour, minute, 0);
		Date startDate = cal.getTime();

		cal.setTimeInMillis(0);
		selected = secondDatePick.getDate();
		year = 0;
		year = Integer.valueOf(new SimpleDateFormat("yyyy").format(selected));

		month = 0;
		month = Integer.valueOf(new SimpleDateFormat("MM").format(selected)) - 1;

		day = 0;
		day = Integer.valueOf(new SimpleDateFormat("dd").format(selected));

		selected = ((Date) secondTimePick.getValue());
		hour = 0;
		hour = Integer.valueOf(new SimpleDateFormat("HH").format(selected));

		minute = 0;
		minute = Integer.valueOf(new SimpleDateFormat("mm").format(selected));
		minute = minute - minute % 5;
		cal.set(year, month, day, hour, minute, 0);

		Date stopDate = cal.getTime();

		list.add(startDate);
		list.add(stopDate);
		return list;
	}

	public static String[][] getLookupTable(){
		return lookupTable;
	}

	public static void setLookupTable(String[][] table){
		lookupTable = table;
	}
}
