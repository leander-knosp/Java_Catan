# Spielablauf Catan

## Chronologische Abfolge
#### Spielvorbereitung
1. Spieler anlegen
2. Spielfeld erstellen
    - Hexagons verteilen
    - Zahlenchips platzieren
    - Räuber setzen
3. Startaufstellung
    - jeder Spieler 2x Siedlung mit je einer Straße
    - Verteilung bei 3 Spielern: A, B, C, C, B, A
#### Hauptschleife
Solange kein Spieler 10 Siegpunkte erreicht hat, kommt jeder Spieler nacheineinander im Kreis dran.
**Zugabfolge**:
1. Würfeln
    - Zwei 6 Punkte Würfel
    - gewürfelte, addierte Augenzahl - an entsprechenden Hexagon anliegende Siedlungen erhalten den Rohstoff einfach
    - gewürfelte, addierte Augenzahl - an entsprechenden Hexagon anliegende Städte erhalten den Rohstoff zweifach
2. Räuber
    - wird bei gewürfelter 7 aktiviert
    - Spieler mit >7 Rohstoffen müssen Rohstoffe abwerfen mit folgender Formel:
        - a = Rohstoffanzahl
        - a gerade: a/2 Karten abwerfen
        - a ungerade: (a-1)/2 Karten abwerfen
    - Räuber darf auf beliebiges Feld gestellt werden (!= aktuelles Feld)
    - Feld auf dem der Räuber steht ist blockiert (=bei gewürfelter Zahl bekommen angrenzende Spieler keine Rohstoffe)
    - Aktiver Spieler darf eine Karte eines an dem Feld angrenzenden Spielers ziehen
3. Handeln
    - Aktiver Spieler kann Angebot an andere Spieler machen (Bsp: 2 Wolle für 1 Erz)
        - andere Spieler können Ablehnen/Annehmen
    - Aktiver Spieler kann Angebot an Bank machen (wird immer genommen)
        - nur gleiche Rohstoffe dürfen genutzt werden (Bsp: 3 Stroh)
        - Standartmäßig: 4 zu 1 Trade (alle Rohstoffe)
        - Bei ?-Hafen: 3 zu 1 Trade (alle Rohstoffe)
        - Bei Rohstoff-Hafen: 2 zu 1 Trade des entsprechenden Rohstoffs
4. Bauen
    - Möglichkeiten & Kosten
        - Straße: 1 Holz & 1 Lehm
        - Siedlung: 1 Holz, 1 Lehm, 1 Wolle, 1 Stroh
        - Stadt: 2 Stroh, 3 Erz
    - Stadt zu Stadt immer 2 Kanten abstand
    - Straße & Stadt müssen immer an eigenes angebunden sein
    - unterbrochene Straßen durch andere Siedlungen zählen als nicht mehr zusammenhängend
    - Stadt kann nur eine bereits vorhandene Siedlung ersetzen
#### Spielende
Das Spiel ist vorbei, sobald ein Spieler 10 Siegpunkte erreicht hat -->Spiel endet sofort

## Klassen

- BoardController
- PlayerController
- RessourceController
- TradeController
- BuildController
- TurnController
- VictoryCheck?