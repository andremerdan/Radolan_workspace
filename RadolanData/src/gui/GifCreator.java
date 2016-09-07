package gui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import threads.GifCreationThread;
import data.DataRead;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import org.jdesktop.swingx.JXDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * 
 * @author Patrick Allan Blair
 *
 */
public class GifCreator extends JPanel{

	private static final long serialVersionUID = 504925333548385060L;
	private JLabel lblNiederschlagsminimum;
	private static JTextField savePath;
	private JButton createGIF;
	private JButton btnShowFolder;
	private Hauptfenster parent;
	private static boolean active = false;

	private JButton btnOrdner;
	private JLabel lblNiederschlagsminimumInMl;
	private JTextField minimumSet;
	private JLabel label_3;
	private JLabel lblAnimationErstellen;
	@SuppressWarnings("rawtypes")
	private JComboBox datasetSelectAnimation;
	private JButton btnVisualisierungsbilderLschen;
	private JLabel lblBisherigeBilderFr;
	private JLabel lblAktuellErstellteDatei;
	private static JLabel lblCurrentVisFile;
	private JButton btnAbbrechen;
	private static JLabel lblAnimationStatus;
	private static JLabel lblVisCreationStatus;
	private JLabel lblZeitbereichFrAnimation;
	private JLabel label_1;
	private static JXDatePicker firstDatePick;
	private JLabel label_2;
	private static JSpinner firstTimePick;
	private JLabel label_4;
	private static JXDatePicker secondDatePick;
	private static JSpinner secondTimePick;
	private JLabel label_5;
	private JLabel label_6;
	
	JFileChooser chooser;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GifCreator(){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2015, 0, 1, 0, 0, 0);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		String[] auswahl = {"rw", "ry"};
		@SuppressWarnings("unused")
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(auswahl);

		lblAnimationErstellen = new JLabel("Animation");
		lblAnimationErstellen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAnimationErstellen = new GridBagConstraints();
		gbc_lblAnimationErstellen.gridwidth = 9;
		gbc_lblAnimationErstellen.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnimationErstellen.gridx = 1;
		gbc_lblAnimationErstellen.gridy = 1;
		add(lblAnimationErstellen, gbc_lblAnimationErstellen);

		lblZeitbereichFrAnimation = new JLabel("Zeitbereich f\u00FCr Animation");
		lblZeitbereichFrAnimation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblZeitbereichFrAnimation = new GridBagConstraints();
		gbc_lblZeitbereichFrAnimation.gridwidth = 5;
		gbc_lblZeitbereichFrAnimation.insets = new Insets(0, 0, 5, 5);
		gbc_lblZeitbereichFrAnimation.gridx = 1;
		gbc_lblZeitbereichFrAnimation.gridy = 3;
		add(lblZeitbereichFrAnimation, gbc_lblZeitbereichFrAnimation);

		label_1 = new JLabel("Datum");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		add(label_1, gbc_label_1);

		label_2 = new JLabel("Uhrzeit");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 4;
		add(label_2, gbc_label_2);

		label_5 = new JLabel("Datum");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 4;
		gbc_label_5.gridy = 4;
		add(label_5, gbc_label_5);

		label_6 = new JLabel("Uhrzeit");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 5;
		gbc_label_6.gridy = 4;
		add(label_6, gbc_label_6);

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

		label_4 = new JLabel("bis");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 3;
		gbc_label_4.gridy = 5;
		add(label_4, gbc_label_4);

		secondDatePick = new JXDatePicker(cal.getTime());
		GridBagConstraints gbc_secondDatePick = new GridBagConstraints();
		gbc_secondDatePick.insets = new Insets(0, 0, 5, 5);
		gbc_secondDatePick.gridx = 4;
		gbc_secondDatePick.gridy = 5;
		add(secondDatePick, gbc_secondDatePick);

		SpinnerModel model2 = new SpinnerDateModel();
		secondTimePick = new JSpinner(model2);
		JComponent editor2 = new JSpinner.DateEditor(secondTimePick, "HH:mm");
		secondTimePick.setEditor(editor2);
		secondTimePick.setValue(cal.getTime());

		GridBagConstraints gbc_secondTimePick = new GridBagConstraints();
		gbc_secondTimePick.insets = new Insets(0, 0, 5, 5);
		gbc_secondTimePick.gridx = 5;
		gbc_secondTimePick.gridy = 5;
		add(secondTimePick, gbc_secondTimePick);

		label_3 = new JLabel("Verwendeter Datensatz:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.gridwidth = 2;
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 7;
		add(label_3, gbc_label_3);

		datasetSelectAnimation = new JComboBox(new DefaultComboBoxModel(new String[] {"rw", "ry"}));
		GridBagConstraints gbc_datasetSelectAnimation = new GridBagConstraints();
		gbc_datasetSelectAnimation.anchor = GridBagConstraints.WEST;
		gbc_datasetSelectAnimation.insets = new Insets(0, 0, 5, 5);
		gbc_datasetSelectAnimation.gridx = 3;
		gbc_datasetSelectAnimation.gridy = 7;
		add(datasetSelectAnimation, gbc_datasetSelectAnimation);

		lblNiederschlagsminimumInMl = new JLabel("Niederschlagsminimum in mm:");
		GridBagConstraints gbc_lblNiederschlagsminimumInMl = new GridBagConstraints();
		gbc_lblNiederschlagsminimumInMl.gridwidth = 2;
		gbc_lblNiederschlagsminimumInMl.anchor = GridBagConstraints.EAST;
		gbc_lblNiederschlagsminimumInMl.insets = new Insets(0, 0, 5, 5);
		gbc_lblNiederschlagsminimumInMl.gridx = 1;
		gbc_lblNiederschlagsminimumInMl.gridy = 8;
		add(lblNiederschlagsminimumInMl, gbc_lblNiederschlagsminimumInMl);

		minimumSet = new JTextField();
		minimumSet.setColumns(10);
		GridBagConstraints gbc_minimumSet = new GridBagConstraints();
		gbc_minimumSet.gridwidth = 4;
		gbc_minimumSet.insets = new Insets(0, 0, 5, 5);
		gbc_minimumSet.fill = GridBagConstraints.HORIZONTAL;
		gbc_minimumSet.gridx = 3;
		gbc_minimumSet.gridy = 8;
		add(minimumSet, gbc_minimumSet);

		lblNiederschlagsminimum = new JLabel("Speicherort:");
		GridBagConstraints gbc_lblNiederschlagsminimum = new GridBagConstraints();
		gbc_lblNiederschlagsminimum.gridwidth = 2;
		gbc_lblNiederschlagsminimum.anchor = GridBagConstraints.EAST;
		gbc_lblNiederschlagsminimum.insets = new Insets(0, 0, 5, 5);
		gbc_lblNiederschlagsminimum.gridx = 1;
		gbc_lblNiederschlagsminimum.gridy = 9;
		add(lblNiederschlagsminimum, gbc_lblNiederschlagsminimum);

		savePath = new JTextField();
		GridBagConstraints gbc_savePath = new GridBagConstraints();
		gbc_savePath.gridwidth = 4;
		gbc_savePath.insets = new Insets(0, 0, 5, 5);
		gbc_savePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_savePath.gridx = 3;
		gbc_savePath.gridy = 9;
		add(savePath, gbc_savePath);
		savePath.setColumns(10);

		btnOrdner = new JButton("Ordner...");
		GridBagConstraints gbc_btnOrdner = new GridBagConstraints();
		gbc_btnOrdner.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOrdner.insets = new Insets(0, 0, 5, 5);
		gbc_btnOrdner.gridx = 7;
		gbc_btnOrdner.gridy = 9;
		add(btnOrdner, gbc_btnOrdner);

		
		btnOrdner.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(parent);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					savePath.setText(chooser.getSelectedFile().getPath());
				}
			}
		});

		createGIF = new JButton("Erstellen");
		GridBagConstraints gbc_createGIF = new GridBagConstraints();
		gbc_createGIF.fill = GridBagConstraints.HORIZONTAL;
		gbc_createGIF.insets = new Insets(0, 0, 5, 5);
		gbc_createGIF.gridx = 7;
		gbc_createGIF.gridy = 10;
		add(createGIF, gbc_createGIF);
		
		//Open Folder
		btnShowFolder = new JButton("Ordner öffnen");
		GridBagConstraints gbc_btnShowFolder = new GridBagConstraints();
		gbc_btnShowFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShowFolder.insets = new Insets(0, 0, 5, 5);
		gbc_btnShowFolder.gridx = 6;
		gbc_btnShowFolder.gridy = 10;
		add(btnShowFolder, gbc_btnShowFolder); 
		
		btnShowFolder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String folderPath = savePath.getText();
				try {
					Desktop.getDesktop().open(new File(folderPath));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});

		lblAktuellErstellteDatei = new JLabel("Aktuell erstellte Datei:");
		GridBagConstraints gbc_lblAktuellErstellteDatei = new GridBagConstraints();
		gbc_lblAktuellErstellteDatei.gridwidth = 2;
		gbc_lblAktuellErstellteDatei.anchor = GridBagConstraints.EAST;
		gbc_lblAktuellErstellteDatei.insets = new Insets(0, 0, 5, 5);
		gbc_lblAktuellErstellteDatei.gridx = 1;
		gbc_lblAktuellErstellteDatei.gridy = 12;
		add(lblAktuellErstellteDatei, gbc_lblAktuellErstellteDatei);

		lblCurrentVisFile = new JLabel("n/a");
		GridBagConstraints gbc_lblCurrentVisFile = new GridBagConstraints();
		gbc_lblCurrentVisFile.gridwidth = 4;
		gbc_lblCurrentVisFile.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentVisFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentVisFile.gridx = 3;
		gbc_lblCurrentVisFile.gridy = 12;
		add(lblCurrentVisFile, gbc_lblCurrentVisFile);

		lblAnimationStatus = new JLabel("Visualisierungsstatus:");
		GridBagConstraints gbc_lblAnimationStatus = new GridBagConstraints();
		gbc_lblAnimationStatus.gridwidth = 2;
		gbc_lblAnimationStatus.anchor = GridBagConstraints.EAST;
		gbc_lblAnimationStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnimationStatus.gridx = 1;
		gbc_lblAnimationStatus.gridy = 13;
		add(lblAnimationStatus, gbc_lblAnimationStatus);

		lblVisCreationStatus = new JLabel("n/a");
		GridBagConstraints gbc_lblVisCreationStatus = new GridBagConstraints();
		gbc_lblVisCreationStatus.gridwidth = 4;
		gbc_lblVisCreationStatus.anchor = GridBagConstraints.WEST;
		gbc_lblVisCreationStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisCreationStatus.gridx = 3;
		gbc_lblVisCreationStatus.gridy = 13;
		add(lblVisCreationStatus, gbc_lblVisCreationStatus);

		btnAbbrechen = new JButton("Abbrechen");
		GridBagConstraints gbc_btnAbbrechen = new GridBagConstraints();
		gbc_btnAbbrechen.insets = new Insets(0, 0, 5, 5);
		gbc_btnAbbrechen.gridx = 7;
		gbc_btnAbbrechen.gridy = 13;
		add(btnAbbrechen, gbc_btnAbbrechen);

		lblBisherigeBilderFr = new JLabel("Bisherige Bilder f\u00FCr Visualisierung L\u00F6schen:");
		GridBagConstraints gbc_lblBisherigeBilderFr = new GridBagConstraints();
		gbc_lblBisherigeBilderFr.gridwidth = 4;
		gbc_lblBisherigeBilderFr.anchor = GridBagConstraints.EAST;
		gbc_lblBisherigeBilderFr.insets = new Insets(0, 0, 0, 5);
		gbc_lblBisherigeBilderFr.gridx = 3;
		gbc_lblBisherigeBilderFr.gridy = 14;
		add(lblBisherigeBilderFr, gbc_lblBisherigeBilderFr);

		btnVisualisierungsbilderLschen = new JButton("L\u00F6schen");
		GridBagConstraints gbc_btnVisualisierungsbilderLschen = new GridBagConstraints();
		gbc_btnVisualisierungsbilderLschen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVisualisierungsbilderLschen.insets = new Insets(0, 0, 0, 5);
		gbc_btnVisualisierungsbilderLschen.gridx = 7;
		gbc_btnVisualisierungsbilderLschen.gridy = 14;
		add(btnVisualisierungsbilderLschen, gbc_btnVisualisierungsbilderLschen);

		btnVisualisierungsbilderLschen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				DataRead.deletePictureMaterial();
			}
		});

		btnAbbrechen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(active) lblVisCreationStatus.setText("Abgebrochen");
				active = false;
			}
		});

		createGIF.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int minimum = 4000;
				String collection = datasetSelectAnimation.getSelectedItem().toString();
				try {
					if(collection.equals("rw")) minimum = (int) (Double.valueOf(minimumSet.getText()) * 10);
					else minimum = (int) (Double.valueOf(minimumSet.getText()) * 100);
				} catch (NumberFormatException  e) {
					System.out.println("Konvertierung auf Integer fehlgeschlagen");
				}
				active = true;
				Icon loading = new ImageIcon("webpage\\img\\loading.gif");
				
				lblVisCreationStatus.setIcon(loading);
				lblVisCreationStatus.setText("Erstellung beginnt");
				Hauptfenster.feedExecutor(new GifCreationThread(collection, minimum, getDates(), AreaSelect.getSelectedArea()));
			}
		});
	}
	


	public static String getGifSavePath(){
		return savePath.getText();
	}

	public static void setCurrentVisFile(String file){
		lblCurrentVisFile.setText(file);
	}

	public static boolean getActive(){
		return active;
	}

	public static void setActive(boolean status){
		active = status;
	}

	public static void setAnimationStatus(String status){
		lblVisCreationStatus.setText(status);
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
	
	// Changing the Date - ActionListener from verbinden
	public static void changeDate(){
		firstDatePick.setDate(Setup.begin);
		secondDatePick.setDate(Setup.end);
	}
}
