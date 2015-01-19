Praktikum 4
===========

Abgabe von : 

* Moritz Spindelhirn
* Marjan Bachtiari


Quellenangaben: 
----------------

Es wurden keine Fremdquellen verwendet.

Bearbeitungszeitraum: 
----------------------

* 6 Stunden ( MSTH )
* 2 Stunden ( Tests, Dokumentation )

Minimaler-Spannbaum-Heuristik:
---------------
Der Graph muss ein Graph vom Typ ```WeightedGraph``` sein und mit ```VisitableWeightedEdge``` Kantentypen und ```VisitableVertex``` als Vertextyp nutzen. Diese bieten die Möglichkeit Kanten als Besucht zu markieren.

Inital wird mit Hilfe des Kruskal Algorithmus der Minimale Spannbaum erstellt.
Danach werden die Kanten des MST verdoppelt.  
Abschließend wird die Optimierung vorgenommen. Dazu wird erst eine Kante gefunden die durch den MST verdoppelt wurde. Wenn eine solche Kante gefunden wurde wird von der Quelle und dem Ziel der Kante eine weitere Kante mit Kopie gesucht, die zu einem anderen Knoten geht.  
Wenn eine solche zweite Kante gefunden wurde werden die beiden gefundenen Kanten entfernt und durch eine direkte Verbindung zwischen den beiden äußeren Knoten verbunden.

### Kruskal
MSTH nutzt diesen Algorithmus zum Erstellen des Minimalen Spannbaums.  
Der Algorithmus sortiert alle Kanten absteigend nach Kantengewicht. Danach wird diese Liste abgearbeitet. Dabei wird die oberste Kante der Liste in den MST eingefügt, wenn dadurch kein Kreis erzeugt wird.

Nächstgelegner-Knoten-Algorithmus:
-------------

Vergleich:
----------

Für 100 Durchläufe mit einem Graphen mit 25 Knoten folgender Vergleich ergibt sich für die durchschnittsliche Laufzeit.

```
??????
```

Graphenerstellung:
-------------------

Bei der Erstellung von Graphen muss nur darauf geachtet werden, dass der Basiswert für Knotengewichte höher ist als die die zufällige Flukturation.  
So ist z.B. in unserer Implementierung der Basiswert 10 und es wird ein zufälliger Wert zwischen 0 und 5 addiert. Somit kann auch wenn der direkte Weg 15 ist man niemals über einen dritten Knoten einen kürzeren Weg finden, da dieser immer mindestens 20 ist.