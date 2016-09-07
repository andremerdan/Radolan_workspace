package gui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.sun.javafx.font.DisposerRecord;
import com.sun.javafx.stage.StagePeerListener.StageAccessor;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;

import threads.FileReadThread;
import data.DataRead;
import data.MongoAccess;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import javax.swing.ImageIcon;

/**
 * 
 * @author Patrick Allan Blair
 *
 */
public class Setup extends JPanel{

	private static final long serialVersionUID = -4555346770086975517L;
	private static JTextField mongodPath;
	private static JTextField databasePath;
	private JTextField readDataPath;
	private static JProgressBar progressBar;
	private static JLabel readPercent;
	private static JLabel currentFileRead;
	private static JCheckBox chckbxberschreiben;
	private static JCheckBox chckbxMongodbMitStarten;
	private static boolean reading = false;
	private static JTextField xPolar;
	private static JTextField yPolar;
	public final JButton verbinden;
	
	public static int yearBeginn;
	public static int yearEnd;
	public static int monthBeginn;
	public static int monthEnd;
	public static Date begin;
	public static Date end;

	
	public Setup() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblDatenbankSetup = new JLabel("Datenbank - Auswahl");
		lblDatenbankSetup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDatenbankSetup = new GridBagConstraints();
		gbc_lblDatenbankSetup.gridwidth = 14;
		gbc_lblDatenbankSetup.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatenbankSetup.gridx = 1;
		gbc_lblDatenbankSetup.gridy = 1;
		add(lblDatenbankSetup, gbc_lblDatenbankSetup);

		JLabel lblMongodbPfad = new JLabel("MongoDB Auswahl");
		GridBagConstraints gbc_lblMongodbPfad = new GridBagConstraints();
		gbc_lblMongodbPfad.insets = new Insets(0, 0, 5, 5);
		gbc_lblMongodbPfad.anchor = GridBagConstraints.EAST;
		gbc_lblMongodbPfad.gridx = 1;
		gbc_lblMongodbPfad.gridy = 2;
		add(lblMongodbPfad, gbc_lblMongodbPfad);

		mongodPath = new JTextField();
		GridBagConstraints gbc_mdPath = new GridBagConstraints();
		gbc_mdPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_mdPath.gridwidth = 7;
		gbc_mdPath.insets = new Insets(0, 0, 5, 5);
		gbc_mdPath.gridx = 2;
		gbc_mdPath.gridy = 2;
		add(mongodPath, gbc_mdPath);

		mongodPath.setColumns(10);
		mongodPath.setText("C:\\Program Files\\MongoDB\\Server\\3.0\\bin");
		JButton mongodSelect = new JButton("Suchen");
		mongodSelect.setIcon(null);
		GridBagConstraints gbc_btnOrdner = new GridBagConstraints();
		gbc_btnOrdner.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOrdner.insets = new Insets(0, 0, 5, 5);
		gbc_btnOrdner.gridx = 9;
		gbc_btnOrdner.gridy = 2;
		add(mongodSelect, gbc_btnOrdner);
		//		GridBagConstraints gbc_yPolar = new GridBagConstraints();
		//		gbc_yPolar.gridwidth = 2;
		//		gbc_yPolar.insets = new Insets(0, 0, 5, 5);
		//		gbc_yPolar.fill = GridBagConstraints.HORIZONTAL;
		//		gbc_yPolar.gridx = 3;
		//		gbc_yPolar.gridy = 17;
		//		add(yPolar, gbc_yPolar);
		
		//		JLabel label_5 = new JLabel("Einstellungen speichern?");
		//		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		//		gbc_label_5.anchor = GridBagConstraints.EAST;
		//		gbc_label_5.gridwidth = 2;
		//		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		//		gbc_label_5.gridx = 7;
		//		gbc_label_5.gridy = 17;
		//		add(label_5, gbc_label_5);
		//
		//		JButton btnAreaSpeichern = new JButton("Speichern");
		//		GridBagConstraints gbc_btnAreaSpeichern = new GridBagConstraints();
		//		gbc_btnAreaSpeichern.fill = GridBagConstraints.HORIZONTAL;
		//		gbc_btnAreaSpeichern.insets = new Insets(0, 0, 5, 5);
		//		gbc_btnAreaSpeichern.gridx = 9;
		//		gbc_btnAreaSpeichern.gridy = 17;
		//		add(btnAreaSpeichern, gbc_btnAreaSpeichern);
		//
		//		btnAreaSpeichern.addActionListener(new ActionListener() {
		//
		//			public void actionPerformed(ActionEvent arg0) {
		//				ArrayList<String> list = new ArrayList<String>();
		//				list.add(xPolar.getText());
		//				list.add(yPolar.getText());
		//				DataRead.writeXML(list, "options\\SavedAreaOptions.xml");
		//			}
		//		});
		
				mongodSelect.addActionListener(new ActionListener() {
		
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnVal = chooser.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							mongodPath.setText(chooser.getSelectedFile().getPath());
						}
					}
				});
				
						JButton btnPathLaden = new JButton("Laden");
						GridBagConstraints gbc_btnPathLaden = new GridBagConstraints();
						gbc_btnPathLaden.fill = GridBagConstraints.HORIZONTAL;
						gbc_btnPathLaden.insets = new Insets(0, 0, 5, 5);
						gbc_btnPathLaden.gridx = 10;
						gbc_btnPathLaden.gridy = 2;
						add(btnPathLaden, gbc_btnPathLaden);
						
								btnPathLaden.addActionListener(new ActionListener() {
						
									public void actionPerformed(ActionEvent arg0) {
										ArrayList<String> list = DataRead.readXML("options\\SavedPaths.xml");
										if(list.size() == 2){
											mongodPath.setText(list.get(0));
											databasePath.setText(list.get(1));
										}
									}
								});
		
				JButton btnPathSpeichern = new JButton("Speichern");
				GridBagConstraints gbc_btnPathSpeichern = new GridBagConstraints();
				gbc_btnPathSpeichern.anchor = GridBagConstraints.WEST;
				gbc_btnPathSpeichern.insets = new Insets(0, 0, 5, 5);
				gbc_btnPathSpeichern.gridx = 11;
				gbc_btnPathSpeichern.gridy = 2;
				add(btnPathSpeichern, gbc_btnPathSpeichern);
				
						btnPathSpeichern.addActionListener(new ActionListener() {
				
							public void actionPerformed(ActionEvent arg0) {
								ArrayList<String> list = new ArrayList<String>();
								list.add(mongodPath.getText());
								list.add(databasePath.getText());
								DataRead.writeXML(list, "options\\SavedPaths.xml");
							}
						});
						setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblDatenbankSetup, lblMongodbPfad, mongodPath, mongodSelect, databasePath, chckbxMongodbMitStarten, btnPathLaden, btnPathSpeichern, readDataPath, progressBar, readPercent, currentFileRead, chckbxberschreiben}));

		JLabel lblDatenPfad = new JLabel("Datenbank");
		GridBagConstraints gbc_lblDatenPfad = new GridBagConstraints();
		gbc_lblDatenPfad.anchor = GridBagConstraints.EAST;
		gbc_lblDatenPfad.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatenPfad.gridx = 1;
		gbc_lblDatenPfad.gridy = 3;
		add(lblDatenPfad, gbc_lblDatenPfad);

		databasePath = new JTextField();
		GridBagConstraints gbc_dbPath = new GridBagConstraints();
		gbc_dbPath.gridwidth = 7;
		gbc_dbPath.insets = new Insets(0, 0, 5, 5);
		gbc_dbPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_dbPath.gridx = 2;
		gbc_dbPath.gridy = 3;
		add(databasePath, gbc_dbPath);
		databasePath.setColumns(10);
		
				JButton loadDatabaseSelect = new JButton("Laden");
				GridBagConstraints gbc_btnOrdner_1 = new GridBagConstraints();
				gbc_btnOrdner_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnOrdner_1.insets = new Insets(0, 0, 5, 5);
				gbc_btnOrdner_1.gridx = 9;
				gbc_btnOrdner_1.gridy = 3;
				add(loadDatabaseSelect, gbc_btnOrdner_1);
				
						loadDatabaseSelect.addActionListener(new ActionListener() {
				
							public void actionPerformed(ActionEvent arg0) {
								JFileChooser chooser = new JFileChooser();
								chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
								chooser.setDialogTitle("Projekt laden");
								int returnVal = chooser.showOpenDialog(null);
								if(returnVal == JFileChooser.APPROVE_OPTION) {
									databasePath.setText(chooser.getSelectedFile().getPath());
								}
							}
						});
		
		JButton newDatabaseSelect = new JButton("Neue DB");
		GridBagConstraints gbc_btnOrdner_2 = new GridBagConstraints();
		gbc_btnOrdner_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOrdner_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnOrdner_2.gridx = 10;
		gbc_btnOrdner_2.gridy = 3;
		add(newDatabaseSelect, gbc_btnOrdner_2);
		
		newDatabaseSelect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogTitle("Neuen leeren Ordner ausw�hlen");
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					databasePath.setText(chooser.getSelectedFile().getPath());
				}
				
			}
		});

		chckbxMongodbMitStarten = new JCheckBox("MongoDB mit starten?");
		GridBagConstraints gbc_chckbxMongodbMitStarten = new GridBagConstraints();
		gbc_chckbxMongodbMitStarten.anchor = GridBagConstraints.WEST;
		gbc_chckbxMongodbMitStarten.gridwidth = 7;
		gbc_chckbxMongodbMitStarten.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxMongodbMitStarten.gridx = 2;
		gbc_chckbxMongodbMitStarten.gridy = 4;
		add(chckbxMongodbMitStarten, gbc_chckbxMongodbMitStarten);
		
				verbinden = new JButton("Verbinden");
				GridBagConstraints gbc_btnVerbinden = new GridBagConstraints();
				gbc_btnVerbinden.gridwidth = 3;
				gbc_btnVerbinden.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnVerbinden.insets = new Insets(0, 0, 5, 5);
				gbc_btnVerbinden.gridx = 9;
				gbc_btnVerbinden.gridy = 4;
				add(verbinden, gbc_btnVerbinden);
				
						verbinden.addActionListener(new ActionListener() {
				
							public void actionPerformed(ActionEvent arg0) {
								if(verbinden.getText().equals("Verbinden") || verbinden.getText().equals("�ndern")){
									boolean success = MongoAccess.startMongo(mongodPath.getText(), databasePath.getText(), chckbxMongodbMitStarten.isSelected());
									if(chckbxMongodbMitStarten.isSelected() && success){
										verbinden.setText("Trennen");
									} else if(success){
										verbinden.setText("�ndern");
									}
								} else if(verbinden.getText().equals("Trennen")){
									MongoAccess.stopMongoService(Setup.getMongoDPath());
									verbinden.setText("Verbinden");
								}
								setDatePickerDate();
								VisCreator.changeDate();
								SpiralCreator.changeDate();
								GifCreator.changeDate();
							}
						});

		JLabel lblDatenEinlesen = new JLabel("RADOLAN Daten einlesen");
		lblDatenEinlesen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDatenEinlesen = new GridBagConstraints();
		gbc_lblDatenEinlesen.anchor = GridBagConstraints.SOUTH;
		gbc_lblDatenEinlesen.gridwidth = 14;
		gbc_lblDatenEinlesen.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatenEinlesen.gridx = 1;
		gbc_lblDatenEinlesen.gridy = 10;
		add(lblDatenEinlesen, gbc_lblDatenEinlesen);

//		JLabel lblOptionenFrBereichsberechnung = new JLabel("Optionen f\u00FCr Bereichs-Berechnung");
//		lblOptionenFrBereichsberechnung.setFont(new Font("Tahoma", Font.PLAIN, 16));
//		GridBagConstraints gbc_lblOptionenFrBereichsberechnung = new GridBagConstraints();
//		gbc_lblOptionenFrBereichsberechnung.gridwidth = 9;
//		gbc_lblOptionenFrBereichsberechnung.insets = new Insets(0, 0, 5, 5);
//		gbc_lblOptionenFrBereichsberechnung.gridx = 1;
//		gbc_lblOptionenFrBereichsberechnung.gridy = 15;
//		add(lblOptionenFrBereichsberechnung, gbc_lblOptionenFrBereichsberechnung);
//
//		JLabel lblPolarstereographischeXkoordinateOben = new JLabel("Polarstereographische X-Koordinate Oben Links:");
//		GridBagConstraints gbc_lblPolarstereographischeXkoordinateOben = new GridBagConstraints();
//		gbc_lblPolarstereographischeXkoordinateOben.anchor = GridBagConstraints.EAST;
//		gbc_lblPolarstereographischeXkoordinateOben.gridwidth = 2;
//		gbc_lblPolarstereographischeXkoordinateOben.insets = new Insets(0, 0, 5, 5);
//		gbc_lblPolarstereographischeXkoordinateOben.gridx = 1;
//		gbc_lblPolarstereographischeXkoordinateOben.gridy = 16;
//		add(lblPolarstereographischeXkoordinateOben, gbc_lblPolarstereographischeXkoordinateOben);

		xPolar = new JTextField();
		xPolar.setColumns(10);
		xPolar.setText("-523.4622");
 

//		JLabel label_4 = new JLabel("Voreinstellungen Laden?");
//		GridBagConstraints gbc_label_4 = new GridBagConstraints();
//		gbc_label_4.anchor = GridBagConstraints.EAST;
//		gbc_label_4.gridwidth = 2;
//		gbc_label_4.insets = new Insets(0, 0, 5, 5);
//		gbc_label_4.gridx = 7;
//		gbc_label_4.gridy = 16;
//		add(label_4, gbc_label_4);
//
//		JButton btnAreaLaden = new JButton("Laden");
//		GridBagConstraints gbc_btnAreaLaden = new GridBagConstraints();
//		gbc_btnAreaLaden.fill = GridBagConstraints.HORIZONTAL;
//		gbc_btnAreaLaden.insets = new Insets(0, 0, 5, 5);
//		gbc_btnAreaLaden.gridx = 9;
//		gbc_btnAreaLaden.gridy = 16;
//		add(btnAreaLaden, gbc_btnAreaLaden);
//
//		btnAreaLaden.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//				ArrayList<String> list = DataRead.readXML("options\\SavedAreaOptions.xml");
//				if(list.size() == 4){
//					xPolar.setText(list.get(0));
//					yPolar.setText(list.get(1));
//				}
//			}
//		});
//
//		JLabel lblPolarstereographischeYkoordinateOben = new JLabel("Polarstereographische Y-Koordinate Oben Links:");
//		GridBagConstraints gbc_lblPolarstereographischeYkoordinateOben = new GridBagConstraints();
//		gbc_lblPolarstereographischeYkoordinateOben.anchor = GridBagConstraints.EAST;
//		gbc_lblPolarstereographischeYkoordinateOben.gridwidth = 2;
//		gbc_lblPolarstereographischeYkoordinateOben.insets = new Insets(0, 0, 5, 5);
//		gbc_lblPolarstereographischeYkoordinateOben.gridx = 1;
//		gbc_lblPolarstereographischeYkoordinateOben.gridy = 17;
//		add(lblPolarstereographischeYkoordinateOben, gbc_lblPolarstereographischeYkoordinateOben);

		yPolar = new JTextField();
		yPolar.setColumns(10);
		yPolar.setText("-3758.645");

		chckbxMongodbMitStarten.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(chckbxMongodbMitStarten.isSelected() && verbinden.getText().equals("�ndern")){
					verbinden.setText("Trennen");
				}
				else if(!chckbxMongodbMitStarten.isSelected() && verbinden.getText().equals("Trennen")){
					verbinden.setText("�ndern");
				}
			}
		});
		chckbxMongodbMitStarten.setSelected(true);
																
																//		JLabel lblDatenPfad_1 = new JLabel("Einlesen:");
																//		GridBagConstraints gbc_lblDatenPfad_1 = new GridBagConstraints();
																//		gbc_lblDatenPfad_1.anchor = GridBagConstraints.EAST;
																//		gbc_lblDatenPfad_1.insets = new Insets(0, 0, 5, 5);
																//		gbc_lblDatenPfad_1.gridx = 1;
																//		gbc_lblDatenPfad_1.gridy = 9;
																//		add(lblDatenPfad_1, gbc_lblDatenPfad_1);
																
																		readDataPath = new JTextField();
																		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
																		gbc_textField_2.gridwidth = 7;
																		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
																		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
																		gbc_textField_2.gridx = 2;
																		gbc_textField_2.gridy = 11;
																		add(readDataPath, gbc_textField_2);
																		readDataPath.setColumns(10);
														
																JButton readDataSelect = new JButton("Auswahl");
																GridBagConstraints gbc_btnAuswahl = new GridBagConstraints();
																gbc_btnAuswahl.fill = GridBagConstraints.HORIZONTAL;
																gbc_btnAuswahl.insets = new Insets(0, 0, 5, 5);
																gbc_btnAuswahl.gridx = 9;
																gbc_btnAuswahl.gridy = 11;
																add(readDataSelect, gbc_btnAuswahl);
																
																		readDataSelect.addActionListener(new ActionListener() {
																
																			public void actionPerformed(ActionEvent arg0) {
																				JFileChooser chooser = new JFileChooser();
																				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
																				int returnVal = chooser.showOpenDialog(null);
																				if(returnVal == JFileChooser.APPROVE_OPTION) {
																					readDataPath.setText(chooser.getSelectedFile().getPath());
																				}
																			}
																		});
												
														progressBar = new JProgressBar();
														progressBar.setValue(100);
														GridBagConstraints gbc_progressBar = new GridBagConstraints();
														gbc_progressBar.gridwidth = 6;
														gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
														gbc_progressBar.insets = new Insets(0, 0, 5, 5);
														gbc_progressBar.gridx = 2;
														gbc_progressBar.gridy = 12;
														add(progressBar, gbc_progressBar);
														
																readPercent = new JLabel("100%");
																GridBagConstraints gbc_lblreadPercent = new GridBagConstraints();
																gbc_lblreadPercent.insets = new Insets(0, 0, 5, 5);
																gbc_lblreadPercent.gridx = 8;
																gbc_lblreadPercent.gridy = 12;
																add(readPercent, gbc_lblreadPercent);
												
														JButton dataRead = new JButton("Einlesen");
														GridBagConstraints gbc_btnEinlesen = new GridBagConstraints();
														gbc_btnEinlesen.fill = GridBagConstraints.HORIZONTAL;
														gbc_btnEinlesen.insets = new Insets(0, 0, 5, 5);
														gbc_btnEinlesen.gridx = 9;
														gbc_btnEinlesen.gridy = 12;
														add(dataRead, gbc_btnEinlesen);
														
														dataRead.addActionListener(new ActionListener() {

															public void actionPerformed(ActionEvent arg0) {
																if(!reading) readData();
															}
														});
										
												currentFileRead = new JLabel("Aktuelle Datei: n/a");
												GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
												gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
												gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
												gbc_lblNewLabel.gridx = 2;
												gbc_lblNewLabel.gridy = 13;
												add(currentFileRead, gbc_lblNewLabel);
								
										//No function implemented
										chckbxberschreiben = new JCheckBox("\u00DCberschreiben");
										GridBagConstraints gbc_chckbxberschreiben = new GridBagConstraints();
										gbc_chckbxberschreiben.insets = new Insets(0, 0, 5, 5);
										gbc_chckbxberschreiben.gridx = 9;
										gbc_chckbxberschreiben.gridy = 13;
										add(chckbxberschreiben, gbc_chckbxberschreiben);
						
								JLabel lblNutzeintrgeLschen = new JLabel("Nutzeintr\u00E4ge L\u00F6schen:");
								GridBagConstraints gbc_lblNutzeintrgeLschen = new GridBagConstraints();
								gbc_lblNutzeintrgeLschen.gridwidth = 2;
								gbc_lblNutzeintrgeLschen.anchor = GridBagConstraints.EAST;
								gbc_lblNutzeintrgeLschen.insets = new Insets(0, 0, 5, 5);
								gbc_lblNutzeintrgeLschen.gridx = 7;
								gbc_lblNutzeintrgeLschen.gridy = 14;
								add(lblNutzeintrgeLschen, gbc_lblNutzeintrgeLschen);
						
								JButton btnUsrEntryLschen = new JButton("L\u00F6schen");
								btnUsrEntryLschen.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
									}
								});
								GridBagConstraints gbc_btnUsrEntryLschen = new GridBagConstraints();
								gbc_btnUsrEntryLschen.fill = GridBagConstraints.HORIZONTAL;
								gbc_btnUsrEntryLschen.insets = new Insets(0, 0, 5, 5);
								gbc_btnUsrEntryLschen.gridx = 9;
								gbc_btnUsrEntryLschen.gridy = 14;
								add(btnUsrEntryLschen, gbc_btnUsrEntryLschen);
								
										btnUsrEntryLschen.addActionListener(new ActionListener() {
								
											public void actionPerformed(ActionEvent arg0) {
												MongoAccess.dropCollection("found");
											}
										});
				
						JLabel lblBegrenzungseintrgeLschen = new JLabel("Begrenzungseintr\u00E4ge L\u00F6schen:");
						GridBagConstraints gbc_lblBegrenzungseintrgeLschen = new GridBagConstraints();
						gbc_lblBegrenzungseintrgeLschen.gridwidth = 2;
						gbc_lblBegrenzungseintrgeLschen.anchor = GridBagConstraints.EAST;
						gbc_lblBegrenzungseintrgeLschen.insets = new Insets(0, 0, 5, 5);
						gbc_lblBegrenzungseintrgeLschen.gridx = 7;
						gbc_lblBegrenzungseintrgeLschen.gridy = 15;
						add(lblBegrenzungseintrgeLschen, gbc_lblBegrenzungseintrgeLschen);
				
						JButton btnNegativLschen = new JButton("L\u00F6schen");
						GridBagConstraints gbc_btnNegativLschen = new GridBagConstraints();
						gbc_btnNegativLschen.fill = GridBagConstraints.HORIZONTAL;
						gbc_btnNegativLschen.insets = new Insets(0, 0, 5, 5);
						gbc_btnNegativLschen.gridx = 9;
						gbc_btnNegativLschen.gridy = 15;
						add(btnNegativLschen, gbc_btnNegativLschen);
				
						btnNegativLschen.addActionListener(new ActionListener() {
				
							public void actionPerformed(ActionEvent arg0) {
								MongoAccess.dropCollection("negatives");
							}
						});
	}
	
	// Set the date of the region
	public static void setDatePickerDate() {

		String path = databasePath.getText();
		File year = new File(path + "\\RadolanTextFiles\\rw");
		
		//Get Year
		if(year.exists()){
			lookDateInFolder(year);
		}
		
		File firstMonth = new File(path + "\\RadolanTextFiles\\rw\\" + yearBeginn);
		if(year.exists()){
			lookDateInFolder(firstMonth);
		}
		
		File lastMonth = new File(path + "\\RadolanTextFiles\\rw\\" + yearEnd);
		if(year.exists()){
			lookDateInFolder(lastMonth);
		}
		
		if(year.exists()){
			String.format("%02d", monthBeginn);
			String.format("%02d", monthEnd);
			
			String startDate = (yearBeginn + "-" + monthBeginn + "-" + "01");
			
			// Get the last Day of a Monnth
			YearMonth yearMonthObject = YearMonth.of(yearEnd, monthEnd);
			int daysInMonth = yearMonthObject.lengthOfMonth();
			String endDate = (yearEnd + "-" + monthEnd + "-" + daysInMonth);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				begin = df.parse(startDate);
				end = df.parse(endDate);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			System.out.println(begin);
			System.out.println(end);
		}
	}

	public static void lookDateInFolder(File file){
		
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return new File(dir, name).isDirectory();
			}
		});
		
		System.out.println(Arrays.toString(directories));
		
		//Cast String Array to Int Array
		int[] numbers = new int[directories.length];
			for(int i = 0; i<directories.length; i++){
				numbers[i] = Integer.parseInt(directories[i]);
			}
			
			//assign first element of an array to largest and smallest
	        int smallest = numbers[0];
	        int largetst = numbers[0];
	       
	        for(int i=1; i< numbers.length; i++)
	        {
	                if(numbers[i] > largetst)
	                        largetst = numbers[i];
	                else if (numbers[i] < smallest)
	                        smallest = numbers[i];
	        }
	        
	        System.out.println("Largest Number is : " + largetst);
	        System.out.println("Smallest Number is : " + smallest);
	        if(smallest<100){
	        	String fileName = file.getName();
	        	int fileNameInt = Integer.parseInt(fileName);
	        	if(fileNameInt == yearEnd){
	        		monthEnd = largetst+1;
	    		}
	        	if(fileNameInt == yearBeginn){
	        		monthBeginn = smallest+1;
	        	}        	
	        }else{
	        	yearBeginn = smallest;
	        	yearEnd = largetst;
	        }
	}
	
	
	// Set the Progressbar
	public void readData(){
		System.out.println("Preparing Progress Bar");
		progressBar.setValue(0);
		readPercent.setText("0%");
		System.out.println("Starting Read Process");
		Hauptfenster.feedExecutor(new FileReadThread(readDataPath.getText(), chckbxberschreiben.isSelected()));
	}

	public static void readCompletion(){
		progressBar.setValue(100);
		readPercent.setText("100%");
		currentFileRead.setText("Aktuelle Datei: n/a");
		//Change the Date
		setDatePickerDate();
	}

	public static void updateFileRead(String file){
		currentFileRead.setText(file);
	}

	public static void addPercent(){
		progressBar.setValue(progressBar.getValue() + 1);
		readPercent.setText(progressBar.getValue() + "%");

	}

	public static String getMongoDPath(){
		return mongodPath.getText();
	}

	public static String getDatabasePath(){
		return databasePath.getText();
	}

	public static void switchReadingState(){
		reading = !reading;
	}
	
	public static double getXPolar(){
		try {
			return Double.valueOf(xPolar.getText());
		} catch (NumberFormatException  e) {
			System.out.println("Konvertierung auf Integer fehlgeschlagen");
			return 0;
		}
	}

	public static double getYPolar(){
		try {
			return Double.valueOf(yPolar.getText());
		} catch (NumberFormatException  e) {
			System.out.println("Konvertierung auf Integer fehlgeschlagen");
			return 0;
		}
	}

	public static boolean getShutdownMongo(){
		return chckbxMongodbMitStarten.isSelected();
	}
}