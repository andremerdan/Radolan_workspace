package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

import data.DataRead;
import data.UserCreatedEntry;

import javax.swing.JCheckBox;

import threads.DatabaseVisSearchThread;
import threads.FileReadThread;
import threads.TextOutputCreationThread;
import threads.VisShowThread;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * 
 * @author Patrick Allan Blair
 *
 */
public class VisCreator extends JPanel{

	private static final long serialVersionUID = 1046658425006633788L;
	private JLabel lblVerwendeterDatensatz;
	@SuppressWarnings("rawtypes")
	private static JComboBox datasetSelectVisualization;

	private static ArrayList<Date> visDateRanges;
	private static String[] timeList;
	private static ArrayList<UserCreatedEntry> entryList = new ArrayList<UserCreatedEntry>();
	private static boolean active = false;
	private JLabel lblVisualisierung;
	private JLabel lblNiederschlagsminimumInMl;
	private JTextField minimumSet;
	private JButton btnSearch;
	private JLabel lblbereinstimmendeZeitbereiche;
	private static JLabel foundDateRanges;
	private JButton btnShow;
	private static JCheckBox chckbxVormarkierteEintrgeIgnorieren;
	private JLabel lblBereitsMarkiert;
	private static JLabel foundEntries;
	private JLabel lblVisualisierungsart;
	@SuppressWarnings("rawtypes")
	private static JComboBox visualizationSelect;
	private JButton btnVisualisierungsbilderLschen;
	private JLabel lblBisherigeBilderFr;
	private JLabel lblAktuellErstellteDatei;
	private static JLabel lblCurrentVisFile;
	private JButton btnAbbrechen;
	private static JLabel lblAnimationStatus;
	private static JLabel lblVisCreationStatus;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JSpinner firstTimePick;
	public static JXDatePicker firstDatePick;
	private JLabel label_4;
	private JLabel label_6;
	private JLabel label_5;
	private JLabel label_7;
	private JSpinner minusTime;
	private JSpinner plusTime;
	private JLabel label_8;
	public static JXDatePicker secondDatePick;
	private JSpinner secondTimePick;
	private JLabel lblGefundeneberschreitungen;
	private static JLabel foundEntryNumber;
	private JButton btnCreateTextOutput;
	private JLabel lblSpeicherpfadFrTextausgabe;
	private JTextField textSpeicherPfad;
	private JButton btnTextOrdner;
	private static Calendar cal;
	public static Date begin;
	public static Date end;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VisCreator(){
		cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2015, 0, 1, 0, 0, 0);


		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 0, 0, 0, 0, 0, 0, 0, 0, 30};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		lblVisualisierung = new JLabel("Visualisierung");
		lblVisualisierung.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblVisualisierung = new GridBagConstraints();
		gbc_lblVisualisierung.gridwidth = 8;
		gbc_lblVisualisierung.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisualisierung.gridx = 1;
		gbc_lblVisualisierung.gridy = 1;
		add(lblVisualisierung, gbc_lblVisualisierung);

		label = new JLabel("Zeitbereich f\u00FCr Suche");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 2;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		add(label, gbc_label);

		label_4 = new JLabel("Zusatz um Funde");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.gridwidth = 2;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 4;
		gbc_label_4.gridy = 3;
		add(label_4, gbc_label_4);

		label_2 = new JLabel("Datum");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 4;
		add(label_2, gbc_label_2);

		label_3 = new JLabel("Uhrzeit");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 4;
		add(label_3, gbc_label_3);

		label_6 = new JLabel("Std:Min");
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

		label_5 = new JLabel("Vor m\u00F6glichen Funden:");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 4;
		gbc_label_5.gridy = 5;
		add(label_5, gbc_label_5);

		SpinnerModel model3 = new SpinnerDateModel();
		minusTime = new JSpinner(model3);
		JComponent editor3 = new JSpinner.DateEditor(minusTime, "HH:mm");
		minusTime.setEditor(editor3);
		minusTime.setValue(cal.getTime());

		GridBagConstraints gbc_minusTime = new GridBagConstraints();
		gbc_minusTime.insets = new Insets(0, 0, 5, 5);
		gbc_minusTime.gridx = 5;
		gbc_minusTime.gridy = 5;
		add(minusTime, gbc_minusTime);

		label_8 = new JLabel("bis");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 1;
		gbc_label_8.gridy = 6;
		add(label_8, gbc_label_8);

		secondDatePick = new JXDatePicker(cal.getTime());
		GridBagConstraints gbc_secondDatePick = new GridBagConstraints();
		gbc_secondDatePick.insets = new Insets(0, 0, 5, 5);
		gbc_secondDatePick.gridx = 1;
		gbc_secondDatePick.gridy = 7;
		add(secondDatePick, gbc_secondDatePick);

		SpinnerModel model2 = new SpinnerDateModel();
		secondTimePick = new JSpinner(model2);
		JComponent editor2 = new JSpinner.DateEditor(secondTimePick, "HH:mm");
		secondTimePick.setEditor(editor2);
		secondTimePick.setValue(cal.getTime());

		GridBagConstraints gbc_secondTimePick = new GridBagConstraints();
		gbc_secondTimePick.insets = new Insets(0, 0, 5, 5);
		gbc_secondTimePick.gridx = 2;
		gbc_secondTimePick.gridy = 7;
		add(secondTimePick, gbc_secondTimePick);

		label_7 = new JLabel("Nach m\u00F6glichen Funden:");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 4;
		gbc_label_7.gridy = 7;
		add(label_7, gbc_label_7);

		SpinnerModel model4 = new SpinnerDateModel();
		plusTime = new JSpinner(model4);
		JComponent editor4= new JSpinner.DateEditor(plusTime, "HH:mm");
		plusTime.setEditor(editor4);
		plusTime.setValue(cal.getTime());

		GridBagConstraints gbc_plusTime = new GridBagConstraints();
		gbc_plusTime.insets = new Insets(0, 0, 5, 5);
		gbc_plusTime.gridx = 5;
		gbc_plusTime.gridy = 7;
		add(plusTime, gbc_plusTime);

		String[] auswahl = {"rw", "ry"};
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(auswahl);

		lblVerwendeterDatensatz = new JLabel("Verwendeter Datensatz:");
		GridBagConstraints gbc_lblVerwendeterDatensatz = new GridBagConstraints();
		gbc_lblVerwendeterDatensatz.gridwidth = 2;
		gbc_lblVerwendeterDatensatz.anchor = GridBagConstraints.EAST;
		gbc_lblVerwendeterDatensatz.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerwendeterDatensatz.gridx = 1;
		gbc_lblVerwendeterDatensatz.gridy = 9;
		add(lblVerwendeterDatensatz, gbc_lblVerwendeterDatensatz);
		
		datasetSelectVisualization = new JComboBox(comboModel);
		GridBagConstraints gbc_datasetSelectVisualization = new GridBagConstraints();
		gbc_datasetSelectVisualization.anchor = GridBagConstraints.WEST;
		gbc_datasetSelectVisualization.insets = new Insets(0, 0, 5, 5);
		gbc_datasetSelectVisualization.gridx = 3;
		gbc_datasetSelectVisualization.gridy = 9;
		add(datasetSelectVisualization, gbc_datasetSelectVisualization);

		chckbxVormarkierteEintrgeIgnorieren = new JCheckBox("Vormarkierte Eintr\u00E4ge Ignorieren?");
		GridBagConstraints gbc_chckbxVormarkierteEintrgeIgnorieren = new GridBagConstraints();
		gbc_chckbxVormarkierteEintrgeIgnorieren.gridwidth = 2;
		gbc_chckbxVormarkierteEintrgeIgnorieren.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVormarkierteEintrgeIgnorieren.gridx = 7;
		gbc_chckbxVormarkierteEintrgeIgnorieren.gridy = 9;
		add(chckbxVormarkierteEintrgeIgnorieren, gbc_chckbxVormarkierteEintrgeIgnorieren);

		lblNiederschlagsminimumInMl = new JLabel("Niederschlagsminimum in mm:");
		GridBagConstraints gbc_lblNiederschlagsminimumInMl = new GridBagConstraints();
		gbc_lblNiederschlagsminimumInMl.gridwidth = 2;
		gbc_lblNiederschlagsminimumInMl.anchor = GridBagConstraints.EAST;
		gbc_lblNiederschlagsminimumInMl.insets = new Insets(0, 0, 5, 5);
		gbc_lblNiederschlagsminimumInMl.gridx = 1;
		gbc_lblNiederschlagsminimumInMl.gridy = 10;
		add(lblNiederschlagsminimumInMl, gbc_lblNiederschlagsminimumInMl);

		minimumSet = new JTextField();
		minimumSet.setColumns(10);
		GridBagConstraints gbc_minimumSet = new GridBagConstraints();
		gbc_minimumSet.gridwidth = 4;
		gbc_minimumSet.insets = new Insets(0, 0, 5, 5);
		gbc_minimumSet.fill = GridBagConstraints.HORIZONTAL;
		gbc_minimumSet.gridx = 3;
		gbc_minimumSet.gridy = 10;
		add(minimumSet, gbc_minimumSet);

		btnSearch = new JButton("Suchen");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 7;
		gbc_btnSearch.gridy = 10;
		add(btnSearch, gbc_btnSearch);

		lblSpeicherpfadFrTextausgabe = new JLabel("Speicherpfad f\u00FCr Textausgabe:");
		GridBagConstraints gbc_lblSpeicherpfadFrTextausgabe = new GridBagConstraints();
		gbc_lblSpeicherpfadFrTextausgabe.gridwidth = 2;
		gbc_lblSpeicherpfadFrTextausgabe.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeicherpfadFrTextausgabe.gridx = 1;
		gbc_lblSpeicherpfadFrTextausgabe.gridy = 11;
		add(lblSpeicherpfadFrTextausgabe, gbc_lblSpeicherpfadFrTextausgabe);

		textSpeicherPfad = new JTextField();
		GridBagConstraints gbc_textSpeicherPfad = new GridBagConstraints();
		gbc_textSpeicherPfad.gridwidth = 4;
		gbc_textSpeicherPfad.insets = new Insets(0, 0, 5, 5);
		gbc_textSpeicherPfad.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSpeicherPfad.gridx = 3;
		gbc_textSpeicherPfad.gridy = 11;
		add(textSpeicherPfad, gbc_textSpeicherPfad);
		textSpeicherPfad.setColumns(10);

		btnTextOrdner = new JButton("Ordner...");
		GridBagConstraints gbc_btnTextOrdner = new GridBagConstraints();
		gbc_btnTextOrdner.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTextOrdner.insets = new Insets(0, 0, 5, 5);
		gbc_btnTextOrdner.gridx = 7;
		gbc_btnTextOrdner.gridy = 11;
		add(btnTextOrdner, gbc_btnTextOrdner);

		lblbereinstimmendeZeitbereiche = new JLabel("Gefundene Zeitbereiche:");
		GridBagConstraints gbc_lblbereinstimmendeZeitbereiche = new GridBagConstraints();
		gbc_lblbereinstimmendeZeitbereiche.gridwidth = 2;
		gbc_lblbereinstimmendeZeitbereiche.anchor = GridBagConstraints.EAST;
		gbc_lblbereinstimmendeZeitbereiche.insets = new Insets(0, 0, 5, 5);
		gbc_lblbereinstimmendeZeitbereiche.gridx = 1;
		gbc_lblbereinstimmendeZeitbereiche.gridy = 13;
		add(lblbereinstimmendeZeitbereiche, gbc_lblbereinstimmendeZeitbereiche);

		
		
		foundDateRanges = new JLabel("n/a");
		GridBagConstraints gbc_foundDateRanges = new GridBagConstraints();
		gbc_foundDateRanges.gridwidth = 4;
		gbc_foundDateRanges.anchor = GridBagConstraints.WEST;
		gbc_foundDateRanges.insets = new Insets(0, 0, 5, 5);
		gbc_foundDateRanges.gridx = 3;
		gbc_foundDateRanges.gridy = 13;
		add(foundDateRanges, gbc_foundDateRanges);

		btnCreateTextOutput = new JButton("Textausgabe");
		GridBagConstraints gbc_btnCreateTextOutput = new GridBagConstraints();
		gbc_btnCreateTextOutput.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreateTextOutput.gridx = 7;
		gbc_btnCreateTextOutput.gridy = 13;
		add(btnCreateTextOutput, gbc_btnCreateTextOutput);

		lblGefundeneberschreitungen = new JLabel("Gefundene \u00DCberschreitungen:");
		GridBagConstraints gbc_lblGefundeneberschreitungen = new GridBagConstraints();
		gbc_lblGefundeneberschreitungen.anchor = GridBagConstraints.EAST;
		gbc_lblGefundeneberschreitungen.gridwidth = 2;
		gbc_lblGefundeneberschreitungen.insets = new Insets(0, 0, 5, 5);
		gbc_lblGefundeneberschreitungen.gridx = 1;
		gbc_lblGefundeneberschreitungen.gridy = 14;
		add(lblGefundeneberschreitungen, gbc_lblGefundeneberschreitungen);

		foundEntryNumber = new JLabel("n/a");
		GridBagConstraints gbc_foundEntryNumber = new GridBagConstraints();
		gbc_foundEntryNumber.anchor = GridBagConstraints.WEST;
		gbc_foundEntryNumber.gridwidth = 4;
		gbc_foundEntryNumber.insets = new Insets(0, 0, 5, 5);
		gbc_foundEntryNumber.gridx = 3;
		gbc_foundEntryNumber.gridy = 14;
		add(foundEntryNumber, gbc_foundEntryNumber);

		lblBereitsMarkiert = new JLabel("Nutzererstellte Eintr\u00E4ge:");
		GridBagConstraints gbc_lblBereitsMarkiert = new GridBagConstraints();
		gbc_lblBereitsMarkiert.gridwidth = 2;
		gbc_lblBereitsMarkiert.anchor = GridBagConstraints.EAST;
		gbc_lblBereitsMarkiert.insets = new Insets(0, 0, 5, 5);
		gbc_lblBereitsMarkiert.gridx = 1;
		gbc_lblBereitsMarkiert.gridy = 15;
		add(lblBereitsMarkiert, gbc_lblBereitsMarkiert);

		foundEntries = new JLabel("n/a");
		GridBagConstraints gbc_foundEntries = new GridBagConstraints();
		gbc_foundEntries.gridwidth = 4;
		gbc_foundEntries.anchor = GridBagConstraints.WEST;
		gbc_foundEntries.insets = new Insets(0, 0, 5, 5);
		gbc_foundEntries.gridx = 3;
		gbc_foundEntries.gridy = 15;
		add(foundEntries, gbc_foundEntries);

		lblVisualisierungsart = new JLabel("Visualisierungsart:");
		GridBagConstraints gbc_lblVisualisierungsart = new GridBagConstraints();
		gbc_lblVisualisierungsart.gridwidth = 2;
		gbc_lblVisualisierungsart.anchor = GridBagConstraints.EAST;
		gbc_lblVisualisierungsart.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisualisierungsart.gridx = 1;
		gbc_lblVisualisierungsart.gridy = 16;
		add(lblVisualisierungsart, gbc_lblVisualisierungsart);

		visualizationSelect = new JComboBox();
		visualizationSelect.setModel(new DefaultComboBoxModel(new String[] {"Diamant", "Pfeil", "Textkreuz"}));
		GridBagConstraints gbc_visualizationSelect = new GridBagConstraints();
		gbc_visualizationSelect.gridwidth = 4;
		gbc_visualizationSelect.anchor = GridBagConstraints.WEST;
		gbc_visualizationSelect.insets = new Insets(0, 0, 5, 5);
		gbc_visualizationSelect.gridx = 3;
		gbc_visualizationSelect.gridy = 16;
		add(visualizationSelect, gbc_visualizationSelect);

		lblAktuellErstellteDatei = new JLabel("Aktuell erstellte Datei:");
		GridBagConstraints gbc_lblAktuellErstellteDatei = new GridBagConstraints();
		gbc_lblAktuellErstellteDatei.gridwidth = 2;
		gbc_lblAktuellErstellteDatei.anchor = GridBagConstraints.EAST;
		gbc_lblAktuellErstellteDatei.insets = new Insets(0, 0, 5, 5);
		gbc_lblAktuellErstellteDatei.gridx = 1;
		gbc_lblAktuellErstellteDatei.gridy = 18;
		add(lblAktuellErstellteDatei, gbc_lblAktuellErstellteDatei);

		lblCurrentVisFile = new JLabel("n/a");
		GridBagConstraints gbc_lblCurrentVisFile = new GridBagConstraints();
		gbc_lblCurrentVisFile.gridwidth = 4;
		gbc_lblCurrentVisFile.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentVisFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentVisFile.gridx = 3;
		gbc_lblCurrentVisFile.gridy = 18;
		add(lblCurrentVisFile, gbc_lblCurrentVisFile);

		btnShow = new JButton("Anzeigen");
		GridBagConstraints gbc_btnShow = new GridBagConstraints();
		gbc_btnShow.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShow.insets = new Insets(0, 0, 5, 5);
		gbc_btnShow.gridx = 7;
		gbc_btnShow.gridy = 18;
		add(btnShow, gbc_btnShow);

		lblAnimationStatus = new JLabel("Visualisierungsstatus:");
		GridBagConstraints gbc_lblAnimationStatus = new GridBagConstraints();
		gbc_lblAnimationStatus.gridwidth = 2;
		gbc_lblAnimationStatus.anchor = GridBagConstraints.EAST;
		gbc_lblAnimationStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnimationStatus.gridx = 1;
		gbc_lblAnimationStatus.gridy = 19;
		add(lblAnimationStatus, gbc_lblAnimationStatus);

		lblVisCreationStatus = new JLabel("n/a");
		GridBagConstraints gbc_lblVisCreationStatus = new GridBagConstraints();
		gbc_lblVisCreationStatus.gridwidth = 4;
		gbc_lblVisCreationStatus.anchor = GridBagConstraints.WEST;
		gbc_lblVisCreationStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisCreationStatus.gridx = 3;
		gbc_lblVisCreationStatus.gridy = 19;
		add(lblVisCreationStatus, gbc_lblVisCreationStatus);

		btnAbbrechen = new JButton("Abbrechen");
		GridBagConstraints gbc_btnAbbrechen = new GridBagConstraints();
		gbc_btnAbbrechen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAbbrechen.insets = new Insets(0, 0, 5, 5);
		gbc_btnAbbrechen.gridx = 7;
		gbc_btnAbbrechen.gridy = 19;
		add(btnAbbrechen, gbc_btnAbbrechen);

		lblBisherigeBilderFr = new JLabel("Bisherige Bilder f\u00FCr Visualisierung L\u00F6schen:");
		GridBagConstraints gbc_lblBisherigeBilderFr = new GridBagConstraints();
		gbc_lblBisherigeBilderFr.gridwidth = 4;
		gbc_lblBisherigeBilderFr.anchor = GridBagConstraints.EAST;
		gbc_lblBisherigeBilderFr.insets = new Insets(0, 0, 0, 5);
		gbc_lblBisherigeBilderFr.gridx = 3;
		gbc_lblBisherigeBilderFr.gridy = 20;
		add(lblBisherigeBilderFr, gbc_lblBisherigeBilderFr);

		btnVisualisierungsbilderLschen = new JButton("L\u00F6schen");
		GridBagConstraints gbc_btnVisualisierungsbilderLschen = new GridBagConstraints();
		gbc_btnVisualisierungsbilderLschen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVisualisierungsbilderLschen.insets = new Insets(0, 0, 0, 5);
		gbc_btnVisualisierungsbilderLschen.gridx = 7;
		gbc_btnVisualisierungsbilderLschen.gridy = 20;
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

		// Suchen Button
		btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int minimum = 4000;
				String collection = datasetSelectVisualization.getSelectedItem().toString();
				try {
					if(collection.equals("rw")){
						minimum = (int) (Double.valueOf(minimumSet.getText()) * 10);
					}
					else {
						minimum = (int) (Double.valueOf(minimumSet.getText()) * 100);
					}
				} catch (NumberFormatException  e) {
					System.out.println("Konvertierung auf Integer fehlgeschlagen");
				}
				
				// Loading icon gif
				Icon loading = new ImageIcon("webpage\\img\\loading.gif");
				
				foundDateRanges.setIcon(loading);
				foundDateRanges.setText(null);
				
				foundEntryNumber.setIcon(loading	);
				foundEntryNumber.setText(null);
				
				foundEntries.setText(null);
				// Open Thread DatabaseVisSearchThread
				Hauptfenster.feedExecutor(new DatabaseVisSearchThread(collection, minimum, getDates(), trailingTime(collection), leadingTime(collection), AreaSelect.getSelectedArea()));
				
			}
		});

		btnShow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int minimum = 4000;
				String collection = datasetSelectVisualization.getSelectedItem().toString();
				try {
					if(collection.equals("rw")) minimum = (int) (Double.valueOf(minimumSet.getText()) * 10);
					else minimum = (int) (Double.valueOf(minimumSet.getText()) * 100);
				} catch (NumberFormatException  e) {
					System.out.println("Konvertierung auf Integer fehlgeschlagen");
				}
				active = true;
				lblVisCreationStatus.setText("Erstellung beginnt");
				Hauptfenster.feedExecutor(new VisShowThread(collection, visualizationSelect.getSelectedIndex(), minimum, visDateRanges, entryList));
			}
		});

		btnTextOrdner.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					textSpeicherPfad.setText(chooser.getSelectedFile().getPath());
				}
			}
		});

		btnCreateTextOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int minimum = 4000;
				String collection = datasetSelectVisualization.getSelectedItem().toString();
				ArrayList<Date> searchRange = getDates();
				long trailing = trailingTime(collection);
				long leading = leadingTime(collection);
				int[] area = AreaSelect.getSelectedArea();
				String path = textSpeicherPfad.getText();
				int count = 0;
				try {
					if(collection.equals("rw")) minimum = (int) (Double.valueOf(minimumSet.getText()) * 10);
					else minimum = (int) (Double.valueOf(minimumSet.getText()) * 100);
					count = Integer.valueOf(foundEntryNumber.getText());
				} catch (NumberFormatException  e) {
					System.out.println("Konvertierung auf Integer fehlgeschlagen");
				}

				Hauptfenster.feedExecutor(new TextOutputCreationThread(collection, minimum, visDateRanges, entryList, count, path, searchRange, trailing, leading, area));
			}
		});
	}
	
	public static void changeDate(){
		firstDatePick.setDate(Setup.begin);
		secondDatePick.setDate(Setup.end);
	}

	public static void setFoundEntryNumbers(long amount){
		foundEntryNumber.setText(String.valueOf(amount));
		foundEntryNumber.setIcon(null);
	}

	public static String[] getVisList(){
		return timeList;
	}

	public static void setVisList(String[] list){
		timeList = list;
	}

	public static void setFoundList(ArrayList<UserCreatedEntry> list){
		entryList = list;
		foundEntries.setText(String.valueOf(entryList.size()));
	}

	public static ArrayList<UserCreatedEntry> getFoundList(){
		return entryList;
	}

	public static void  setDateRanges(ArrayList<Date> list){
		visDateRanges = list;
		foundDateRanges.setText(String.valueOf(visDateRanges.size() / 2));
		foundDateRanges.setIcon(null);
	}

	public static String selectedVisCollection(){
		return datasetSelectVisualization.getSelectedItem().toString();
	}

	public static int getGlyphType(){
		return visualizationSelect.getSelectedIndex();
	}

	public static boolean setToIgnore(){
		return chckbxVormarkierteEintrgeIgnorieren.isSelected();
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

	/**
	 * Generates the milliseconds to be added before any found timeframes.
	 * 
	 * @param collection Which dataset (rw, ry) to use.
	 * @return The trailing time in milliseconds
	 */
	private long trailingTime(String collection){
		long result = 0;
		result = Integer.valueOf(new SimpleDateFormat("HH").format((Date) minusTime.getValue())) * 3600000;
		if(collection.equals("rw")) return result;

		int minute = 0;
		minute = Integer.valueOf(new SimpleDateFormat("mm").format((Date) minusTime.getValue()));
		minute = minute - minute % 5;
		result = result + 300000 * minute;
		return result;
	}

	/**
	 * Generates the milliseconds to be added after any found timeframes.
	 * 
	 * @param collection Which dataset (rw, ry) to use.
	 * @return The leading time in milliseconds
	 */
	private long leadingTime(String collection){
		long result = Integer.valueOf(new SimpleDateFormat("HH").format((Date) plusTime.getValue())) * 3600000;
		if(collection.equals("rw")) return result;

		int minute = 0;
		minute = Integer.valueOf(new SimpleDateFormat("mm").format((Date) plusTime.getValue()));
		minute = minute - minute % 5;
		result = result + 300000 * minute;
		return result;
	}
}
