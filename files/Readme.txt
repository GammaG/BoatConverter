F�r XSXL:



F�r Prompt Exporte "CSV" Format:

In der Quell CSV d�rfen wegen des Parsens keine leeren Spalten, oder leere Felder vorhanden sein.
Au�erdem m�ssen ALLE ";" Zeichen durch einen "." ersetzt werden.

Hat folgenden Grund beim Einlesen einer CSV Datei wird diese als eine String Line eingelesen und die 
Felder durch ";" getrennt. Es muss also an dem ";" ein Split ausgef�hrt werden. 
Leere Zeilen werden einfach nicht erfasst, was es schwierig macht R�ckschl�sse auf den Inhalt der Felder zu ziehen.

In dem Programm �u�ert sich ein Fehler hier in einer Fehlermeldung und einen Abbruch des gesamten Vorganges.

Notwendige Felder f�r den Export sind:
Mitarbeiter
Pfad			
Beschreibung
Datum				
Dauer				
Startzeit				
Ende
				