INSTALLATION

Die Installation erfolgt in drei Schritte:

1) 	MongoDB installieren. Dazu die exe-Datei ausführen. Der Speicherort kann beliebig gewählt werden.
2) 	Einen Datenbank-Ordner anlegen, falls keine Datenbank bereits vorhanden ist. MongoDB muss beim
	starten auf einen Ordner verweisen. Dieser Ordner muss auch bereits vorhanden sein; MongoDB kann
	keine Ordner selbst anlegen.
3)	Die Ordnerstruktur für das Radolan-Programm an einem beliebigen Ort ablegen.

AUSFÜHRUNG

Zum Ausführen vom Radolan-Programm muss die JAR-Datei innerhalb der Ordnerstruktur ausgeführt werden.
Die Datenbank kann direkt über das Programminterface gestartet werden. Sollte es nötig sein, die Daten-
bank separat zu starten, kann dies per Kommandozeile gemacht werden.
Dazu muss in den Ordner mit den ausführbaren Daten von MongoDB navigiert werden und dort der Befehl
	
	mongod.exe --dbpath C:\\Beispielordner
	
ausgeführt werden. Beispielordner ist der Standort der zu verwendenen Datenbank (oder dort wo eine
neue angelegt werden soll). Wird das Kommandozeilen-Programm geschlossen, wird auf MongoDB gestoppt.

BEDIENUNG

Das Radolan-Program startet zunächst eine Nutzeroberfläche, die für alle Hintergrund-Operationen und
einige Zusatzfunktionen verwendet wird. Sie ist in fünf Tabs gegliedert:

1)	Einstellungen
	Unter "Datenbank Setup" muss angegeben werden, welcher Ordner für die Datenbank verwendet wird und 
	wo die mongod.exe Datei liegt. Diese Informationen können gespeichert und geladen werden. Es kann 
	auch eingestellt werden,ob beim Starten der Datenbank-Verbindung kann auch MongoDB mitgestartet 
	werden soll. Fallsnicht, wird das Programm von einer bereits gestarteten Instanz von MongoDB ausgehen.
	
	Neue Daten können beim Unterpunkt "Datenbank Verwalten" eingelesen werden. Dabei muss auf den Ordner
	mit den neuen Daten verwiesen werden. Es kann eingestellt werden, ob bereits vorhandene Einträge
	überschrieben werden sollen oder nicht.
	Falls die Datenbank von nutzererstellten Einträgen bereinigt werden soll oder von Zeitbereichen,
	welche als untersucht markiert wurden und deshalb nicht mehr beachtet werden, können diese Datenbank-
	einträge hier vollständig gelöscht werden.
	
	Unter "Optionen für Bereichs-Berechnung" kann der Bereich, in dem sich die Daten befinden, angegeben
	werden. Dazu werden hier die Polarstereographischen Koordinaten vom nord-westlichen Endpunkt des
	Bereichs angegeben. Diese können gespeichert und geladen werden.
	
2)	Sucheinschränkung: Bereich
	In diesem Tab wird das Gebiet eingestellt, in dem Daten untersucht werden sollen. Beim Programmstart
	ist hier immer der gesamte verfügbare Bereich angegeben. Dieser kann über die einzelnen Werte rechts
	verändert werden, oder indem der Nutzer das Gebiet einzeichnet. Dazu muss "Bereich Einzechnen" gewählt
	werden. Dann wird per Klick die obere linke Ecke des neuen Bereichs festgelegt und per Ziehen der Maus
	der Bereich aufgezogen.

3)	Kreisdarstellung
	Falls nutzererstellte Einträge vorhanden sind, können diese separat in ihrer eigenen Visualisierung hier
	angezeigt werden. Über die beiden Datums- und Uhrzeiteinstellungen wird der Zeitintervall eingestellt,
	innerhalb von dem nach Einträgen geprüft werden soll. "Visualisierungsart" gibt an, wie diese Nutzer-
	einträge angezeigt werden sollen:
		a)	Diamant
			Die Einträge werden als Diamant oder Parallelogram dargestellt. Dessen Breite ist abhängig von
			der zeitlichen Länge des Eintrags und die Höhe vom maximalen Niederschlag.
		b)	Pfeil
			Wie bei Diamant, aber als Pfeilstruktur. Die Pfeilrichtung korrespondiert zur angegebenen Wind-
			richtung.
		c)	Textkreuz
			Die Werte vom Eintrag werden textuell angezeigt.
	
	Gefundene Einträge werden in einem Kreisgebilde angezeigt. Jeder Kreis repräsentiert ein Jahr beginnend
	beim eingestellten Startzeitpunkt im Zentrum der Darstellungen. Von der Mitte ausgehend werden die Kreise
	von Linien geschnitten. Jeder Schnittpunkt ist für einen Monat, und dort werden falls vorhanden die
	Nutzereinträge visualisiert. Falls zuviele Einträge für einen Monat vorhanden sind, wird dort ein Punkt
	eingezeichnet, mit der Anzahl der Einträge daneben. Klickt der Nuter auf den Punkt geht ein separates
	Fenster mit den visualisierten Einträgen auf.
	
4)	Visualisierung
	Unter diesem Tab werden die Radolan-Daten durchsucht. Hierfür wird ein Zeitbereich eingestellt und ein
	minimaler Niederschlagswert, sowie der verwendete Datensatz (rw oder ry). Es können auch zusätzliche
	Zeiten angegeben werden. Diese werden um gefundene Ereignisse herum mit visualisiert. Falls Zeitbereiche
	bereits als untersucht markiert wurden, kann auch angegeben werden, diese zu ignorieren.
	
	Nachdem gesucht wurde, werden alle Treffer angegeben. Unterschieden wird zwischen nutzererstellten
	Einträgen, wie viele einzelne Treffer gefunden wurden, und in wie vielen Zeitbereichen diese Treffer
	vorkommen. Diese Treffer können entweder im Webinterface angezeigt werden, oder als Textausgabe
	abgespeichert werden.

5)	Animation Erstellen
	Hier kann ein Zeitintervall und eine minimale Niederschlagsmenge eingestellt werden, die als GIF-Datei
	visualisiert werden sollen.

Das Webinterface für die Darstellung der gefundenen Radolan-Daten gliedert sich in drei Bereiche:

1)	Zwei Darstellungsbereiche, in denen die gewählten Daten visualisert werden. Unter den Bereichen gibt es
	Optionen für das Verschieben der Darstellung, dem Anzeigen von Nutzereinträgen und dem markieren von Zeit-
	bereichen als untersucht. Gewählte Nutzereinträge sind aus der Zeitleiste gewählte Einträge und aktuell
	bearbeitete Einträge sind die momentan in der Nutzereintragsliste ausgewählten Einträge.
2)	Im mittleren Bereich liegt die Nutzereintragsliste. Hier können Einträge bearbeitet und erstellt werden.
	Windrichtung und Regenzentren werden dabei direkt in die Karte eingezeichnet. Die Windrichtung wird dabei
	erst angezeigt, wenn mindestens ein Zentrum angegeben ist, sie kann aber trotzdem eingestellt werden.
3)	Eine Zeitleiste mit den gefundenen Einträgen. Einfache Dateneinträge werden mit ihrem Zeitbereich angegeben,
	Nutzereinträge mit ihrer Datenvisualisierung. Wird ein Eintrag gewählt, wird dieser im aktuellen Darstellungs-
	bereich geöffnet.

PROBLEMBEHANDLUNG

Falls MongoDB vom Radolan-Programm gestartet wurde, die Datenbank vom Programm aber weder gelesen werden noch
gestoppt werden kann, dann muss MongoDB manuell gestoppt werden. Dazu wird in der MongoDB-Ordnerstruktur
mongo.exe gestartet. Dies sollte eine Kommandozeileneingabe öffnen. Hier müssen dann zwei Befehle eingeben
werden:

	use admin
	
	db.shutdownServer();
	
Damit sollte der mongod-Prozess gestoppt werden (per Taskmanager prüfen) und das Radolan-Programm kann normal
weiterverwendet werden.