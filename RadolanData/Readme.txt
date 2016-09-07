INSTALLATION

Die Installation erfolgt in drei Schritte:

1) 	MongoDB installieren. Dazu die exe-Datei ausf�hren. Der Speicherort kann beliebig gew�hlt werden.
2) 	Einen Datenbank-Ordner anlegen, falls keine Datenbank bereits vorhanden ist. MongoDB muss beim
	starten auf einen Ordner verweisen. Dieser Ordner muss auch bereits vorhanden sein; MongoDB kann
	keine Ordner selbst anlegen.
3)	Die Ordnerstruktur f�r das Radolan-Programm an einem beliebigen Ort ablegen.

AUSF�HRUNG

Zum Ausf�hren vom Radolan-Programm muss die JAR-Datei innerhalb der Ordnerstruktur ausgef�hrt werden.
Die Datenbank kann direkt �ber das Programminterface gestartet werden. Sollte es n�tig sein, die Daten-
bank separat zu starten, kann dies per Kommandozeile gemacht werden.
Dazu muss in den Ordner mit den ausf�hrbaren Daten von MongoDB navigiert werden und dort der Befehl
	
	mongod.exe --dbpath C:\\Beispielordner
	
ausgef�hrt werden. Beispielordner ist der Standort der zu verwendenen Datenbank (oder dort wo eine
neue angelegt werden soll). Wird das Kommandozeilen-Programm geschlossen, wird auf MongoDB gestoppt.

BEDIENUNG

Das Radolan-Program startet zun�chst eine Nutzeroberfl�che, die f�r alle Hintergrund-Operationen und
einige Zusatzfunktionen verwendet wird. Sie ist in f�nf Tabs gegliedert:

1)	Einstellungen
	Unter "Datenbank Setup" muss angegeben werden, welcher Ordner f�r die Datenbank verwendet wird und 
	wo die mongod.exe Datei liegt. Diese Informationen k�nnen gespeichert und geladen werden. Es kann 
	auch eingestellt werden,ob beim Starten der Datenbank-Verbindung kann auch MongoDB mitgestartet 
	werden soll. Fallsnicht, wird das Programm von einer bereits gestarteten Instanz von MongoDB ausgehen.
	
	Neue Daten k�nnen beim Unterpunkt "Datenbank Verwalten" eingelesen werden. Dabei muss auf den Ordner
	mit den neuen Daten verwiesen werden. Es kann eingestellt werden, ob bereits vorhandene Eintr�ge
	�berschrieben werden sollen oder nicht.
	Falls die Datenbank von nutzererstellten Eintr�gen bereinigt werden soll oder von Zeitbereichen,
	welche als untersucht markiert wurden und deshalb nicht mehr beachtet werden, k�nnen diese Datenbank-
	eintr�ge hier vollst�ndig gel�scht werden.
	
	Unter "Optionen f�r Bereichs-Berechnung" kann der Bereich, in dem sich die Daten befinden, angegeben
	werden. Dazu werden hier die Polarstereographischen Koordinaten vom nord-westlichen Endpunkt des
	Bereichs angegeben. Diese k�nnen gespeichert und geladen werden.
	
2)	Sucheinschr�nkung: Bereich
	In diesem Tab wird das Gebiet eingestellt, in dem Daten untersucht werden sollen. Beim Programmstart
	ist hier immer der gesamte verf�gbare Bereich angegeben. Dieser kann �ber die einzelnen Werte rechts
	ver�ndert werden, oder indem der Nutzer das Gebiet einzeichnet. Dazu muss "Bereich Einzechnen" gew�hlt
	werden. Dann wird per Klick die obere linke Ecke des neuen Bereichs festgelegt und per Ziehen der Maus
	der Bereich aufgezogen.

3)	Kreisdarstellung
	Falls nutzererstellte Eintr�ge vorhanden sind, k�nnen diese separat in ihrer eigenen Visualisierung hier
	angezeigt werden. �ber die beiden Datums- und Uhrzeiteinstellungen wird der Zeitintervall eingestellt,
	innerhalb von dem nach Eintr�gen gepr�ft werden soll. "Visualisierungsart" gibt an, wie diese Nutzer-
	eintr�ge angezeigt werden sollen:
		a)	Diamant
			Die Eintr�ge werden als Diamant oder Parallelogram dargestellt. Dessen Breite ist abh�ngig von
			der zeitlichen L�nge des Eintrags und die H�he vom maximalen Niederschlag.
		b)	Pfeil
			Wie bei Diamant, aber als Pfeilstruktur. Die Pfeilrichtung korrespondiert zur angegebenen Wind-
			richtung.
		c)	Textkreuz
			Die Werte vom Eintrag werden textuell angezeigt.
	
	Gefundene Eintr�ge werden in einem Kreisgebilde angezeigt. Jeder Kreis repr�sentiert ein Jahr beginnend
	beim eingestellten Startzeitpunkt im Zentrum der Darstellungen. Von der Mitte ausgehend werden die Kreise
	von Linien geschnitten. Jeder Schnittpunkt ist f�r einen Monat, und dort werden falls vorhanden die
	Nutzereintr�ge visualisiert. Falls zuviele Eintr�ge f�r einen Monat vorhanden sind, wird dort ein Punkt
	eingezeichnet, mit der Anzahl der Eintr�ge daneben. Klickt der Nuter auf den Punkt geht ein separates
	Fenster mit den visualisierten Eintr�gen auf.
	
4)	Visualisierung
	Unter diesem Tab werden die Radolan-Daten durchsucht. Hierf�r wird ein Zeitbereich eingestellt und ein
	minimaler Niederschlagswert, sowie der verwendete Datensatz (rw oder ry). Es k�nnen auch zus�tzliche
	Zeiten angegeben werden. Diese werden um gefundene Ereignisse herum mit visualisiert. Falls Zeitbereiche
	bereits als untersucht markiert wurden, kann auch angegeben werden, diese zu ignorieren.
	
	Nachdem gesucht wurde, werden alle Treffer angegeben. Unterschieden wird zwischen nutzererstellten
	Eintr�gen, wie viele einzelne Treffer gefunden wurden, und in wie vielen Zeitbereichen diese Treffer
	vorkommen. Diese Treffer k�nnen entweder im Webinterface angezeigt werden, oder als Textausgabe
	abgespeichert werden.

5)	Animation Erstellen
	Hier kann ein Zeitintervall und eine minimale Niederschlagsmenge eingestellt werden, die als GIF-Datei
	visualisiert werden sollen.

Das Webinterface f�r die Darstellung der gefundenen Radolan-Daten gliedert sich in drei Bereiche:

1)	Zwei Darstellungsbereiche, in denen die gew�hlten Daten visualisert werden. Unter den Bereichen gibt es
	Optionen f�r das Verschieben der Darstellung, dem Anzeigen von Nutzereintr�gen und dem markieren von Zeit-
	bereichen als untersucht. Gew�hlte Nutzereintr�ge sind aus der Zeitleiste gew�hlte Eintr�ge und aktuell
	bearbeitete Eintr�ge sind die momentan in der Nutzereintragsliste ausgew�hlten Eintr�ge.
2)	Im mittleren Bereich liegt die Nutzereintragsliste. Hier k�nnen Eintr�ge bearbeitet und erstellt werden.
	Windrichtung und Regenzentren werden dabei direkt in die Karte eingezeichnet. Die Windrichtung wird dabei
	erst angezeigt, wenn mindestens ein Zentrum angegeben ist, sie kann aber trotzdem eingestellt werden.
3)	Eine Zeitleiste mit den gefundenen Eintr�gen. Einfache Dateneintr�ge werden mit ihrem Zeitbereich angegeben,
	Nutzereintr�ge mit ihrer Datenvisualisierung. Wird ein Eintrag gew�hlt, wird dieser im aktuellen Darstellungs-
	bereich ge�ffnet.

PROBLEMBEHANDLUNG

Falls MongoDB vom Radolan-Programm gestartet wurde, die Datenbank vom Programm aber weder gelesen werden noch
gestoppt werden kann, dann muss MongoDB manuell gestoppt werden. Dazu wird in der MongoDB-Ordnerstruktur
mongo.exe gestartet. Dies sollte eine Kommandozeileneingabe �ffnen. Hier m�ssen dann zwei Befehle eingeben
werden:

	use admin
	
	db.shutdownServer();
	
Damit sollte der mongod-Prozess gestoppt werden (per Taskmanager pr�fen) und das Radolan-Programm kann normal
weiterverwendet werden.