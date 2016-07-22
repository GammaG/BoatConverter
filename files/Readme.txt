Für XSXL:



Für Prompt Exporte "CSV" Format:

In der Quell CSV dürfen wegen des Parsens keine leeren Spalten, oder leere Felder vorhanden sein.
Außerdem müssen ALLE ";" Zeichen durch einen "." ersetzt werden.

Hat folgenden Grund beim Einlesen einer CSV Datei wird diese als eine String Line eingelesen und die 
Felder durch ";" getrennt. Es muss also an dem ";" ein Split ausgeführt werden. 
Leere Zeilen werden einfach nicht erfasst, was es schwierig macht Rückschlüsse auf den Inhalt der Felder zu ziehen.

In dem Programm äußert sich ein Fehler hier in einer Fehlermeldung und einen Abbruch des gesamten Vorganges.

Notwendige Felder für den Export sind:
Mitarbeiter
Pfad			
Beschreibung
Datum				
Dauer				
Startzeit				
Ende
				