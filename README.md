# Java-Catan
## Prerunning meassurements:

### On Windows:
1. Check if you have java installed with: `java --version` | skip to step 3
2. If its not install download here: https://www.java.com/de/download/manual.jsp
3. Check if you have Apache Maven installed and set up with: `mvn -v` | skip to step 8
4. If not set up download here: https://maven.apache.org/download.cgi
5. Add Maven folder to PATH Env Var, better explained here: https://phoenixnap.com/kb/install-maven-windows
6. Restart Terminal
7. Run both `java --version` and `mvn -v` to verify 
8. Ready to run!

### On MacOS:
1. Check Java installation: `java --version`| skip to step 3
2. If not installed: `brew install openjdk`
3. Check Maven installation: `mvn -v`| skip to step 7
4. If not installed: `brew install maven`
6. Restart Terminal
5. Run both `java --version` and `mvn -v` again to verify
7. Ready to run!

### On Linux:
1. Check if you have Java installed with: `java --version` | skip to step 3
2. If not installed (Ubuntu/Debian): `sudo apt install openjdk-17-jdk`
3. Check if Maven is installed with: `mvn -v` | skip to step 7
4. If not installed: `sudo apt install maven`
5. Restart Terminal
6. Run both `java --version` and `mvn -v` to verify
7. Ready to run!


## How to Run:
1. Run `mvn javafx:run`



## Klassen

- **Spiel**: Steuert den Spielablauf.
- **Spieler**: Repräsentiert einen Spieler (Ressourcen, Bauwerke, Karten).
- **Ressource (Enum)**: HOLZ, LEHM, GETREIDE, WOLLE, ERZ.
- **Entwicklungskarte**: Ritter, Monopol, Straßenausbau, etc.
- **Spielfeld / Spielbrett**: Enthält Hexfelder, Knoten, Kanten.
- **HexFeld**: Ressourcenfeld mit Würfelzahl.
- **Kante**: Verbindung zwischen Knoten (für Straßen).
- **Knoten**: Ort für Siedlungen/Städte.
- **Würfel**: Generiert Zahlen zwischen 2–12.
- **Räuber**: Blockiert Ressourcen, stiehlt Karten.
- **Handel**: Abwicklung von Tauschgeschäften.
