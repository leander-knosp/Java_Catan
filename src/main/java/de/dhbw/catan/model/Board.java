package de.dhbw.catan.model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import lombok.Data;

import de.dhbw.catan.controller.BoardController;
import java.util.*;

/**
 * Die Klasse Board repräsentiert das Spielbrett für Catan.
 * 
 * Sie enthält alle Spielfelder (Tiles), deren Nummern-Tokens, die Knoten (Nodes) und Kanten (Edges),
 * sowie den Räuber und eine Referenz auf den BoardController zur UI-Steuerung.
 * 
 * Das Board initialisiert die Kacheln mit zufälligen Ressourcen und Nummern, erstellt die Knoten und Kanten,
 * verwaltet den Räuber und verteilt Ressourcen an die Spieler abhängig vom Würfelergebnis.
 */
@Data
public class Board {
    /** Liste der Spielfelder (Kacheln) auf dem Brett */
    private List<Tile> tiles;
    /** Liste der UI-Elemente für Nummern-Tokens der Kacheln */
    private List<AnchorPane> numberTokens;
    /** Liste der Knoten (Ecken der Kacheln) */
    private List<Node> nodes;
    /** Liste der Kanten (Verbindungen zwischen Knoten) */
    private List<Edge> edges;
    /** Der Räuber auf dem Spielbrett */
    private Robber robber;
    /** Controller zur Steuerung der Benutzeroberfläche */
    private BoardController controller;

    /**
     * Konstruktor, der das Spielfeld initialisiert.
     * 
     * @param hexes   Liste der Polygon-Formen der Kacheln (Hex-Felder)
     * @param tokens  Liste der UI-Elemente für die Nummern-Tokens
     */
    public Board(List<Polygon> hexes, List<AnchorPane> tokens) {
        this.numberTokens = new ArrayList<>(tokens);
        this.tiles = assignTileTypes(hexes);
        createNodesAndEdges();
        initializeRobber();
    }

    /**
     * Initialisiert den Räuber auf dem Wüstenfeld (Position 9) und aktiviert ihn.
     */
    private void initializeRobber() {
        robber = new Robber();
        robber.move(9);
        robber.activate();
    }

    /**
     * Weist den Kacheln zufällig Ressourcenarten und Nummern-Tokens zu.
     * Das Wüstenfeld wird an fester Position (Index 9) eingefügt.
     * 
     * @param hexes Liste der Polygon-Kacheln
     * @return Liste der erstellten Tiles mit zugewiesenen Typen und Nummern
     */
    private List<Tile> assignTileTypes(List<Polygon> hexes) {
        List<TileType> resourceTypes = new ArrayList<>(List.of(
            TileType.PASTURES, TileType.PASTURES, TileType.PASTURES, TileType.PASTURES,
            TileType.FOREST, TileType.FOREST, TileType.FOREST, TileType.FOREST,
            TileType.MOUNTAINS, TileType.MOUNTAINS, TileType.MOUNTAINS,
            TileType.HILLS, TileType.HILLS, TileType.HILLS,
            TileType.FIELDS, TileType.FIELDS, TileType.FIELDS, TileType.FIELDS
        ));
    
        Collections.shuffle(resourceTypes);  // Ressourcen mischen
        Collections.shuffle(numberTokens);   // Nummern-Tokens mischen
    
        List<TileType> tileTypes = new ArrayList<>();
        for (int i = 0; i < hexes.size(); i++) {
            if (i == 9) {
                tileTypes.add(TileType.DESERT);
            } else {
                tileTypes.add(resourceTypes.remove(0));
            }
        }
    
        List<Tile> result = new ArrayList<>();
        int tokenIndex = 0;
    
        for (int i = 0; i < hexes.size(); i++) {
            TileType type = tileTypes.get(i);
            int number = -1;
    
            if (type != TileType.DESERT) {
                AnchorPane tokenPane = numberTokens.get(tokenIndex);
    
                javafx.scene.text.Text textNode = null;
                for (var node : tokenPane.getChildren()) {
                    if (node instanceof javafx.scene.text.Text) {
                        textNode = (javafx.scene.text.Text) node;
                        break;
                    }
                }
                if (textNode == null) {
                    throw new NullPointerException("Kein Text-Element in TokenPane bei Index " + tokenIndex);
                }
    
                number = Integer.parseInt(textNode.getText());
                tokenIndex++;
            }
    
            result.add(new Tile(hexes.get(i), type, number));
        }
        return result;
    }
    
    /**
     * Setzt den BoardController zur Steuerung der UI.
     * 
     * @param controller Die Controller-Instanz
     */
    public void setController(BoardController controller) {
        this.controller = controller;
    }

    /** Verschiebungen für die Position der Knoten relativ zum Hex-Polygon */
    private static final double[][] HEX_OFFSETS = {
        {-64.95, -37.5}, {-64.95, 37.5}, {0, 75.0},
        {64.95, 37.5}, {64.95, -37.5}, {0, -75.0}
    };

    /**
     * Erzeugt alle Knoten (Nodes) und Kanten (Edges) des Spielbretts basierend auf den Kacheln.
     * Vermeidet doppelte Knoten und Kanten durch entsprechende Überprüfungen.
     */
    private void createNodesAndEdges() {
        Set<Node> uniqueNodes = new HashSet<>();
        Set<String> uniqueEdges = new HashSet<>();
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        for (Tile tile : tiles) {
            Polygon hex = tile.getShape();
            double layoutX = hex.getLayoutX();
            double layoutY = hex.getLayoutY();
            double scaleX = hex.getScaleX();
            double scaleY = hex.getScaleY();

            List<Node> tileNodes = new ArrayList<>();

            for (double[] offset : HEX_OFFSETS) {
                double x = layoutX + offset[0] * scaleX;
                double y = layoutY + offset[1] * scaleY;

                Node node = new Node(x, y);
                boolean isNew = true;
                for (Node existing : uniqueNodes) {
                    if (existing.equals(node)) {
                        node = existing;
                        isNew = false;
                        break;
                    }
                }
                if (isNew) {
                    uniqueNodes.add(node);
                    nodes.add(node);
                }

                tileNodes.add(node);
            }

            tile.setAdjacentNodes(tileNodes);

            for (int i = 0; i < tileNodes.size(); i++) {
                Node a = tileNodes.get(i);
                Node b = tileNodes.get((i + 1) % tileNodes.size());

                String edgeKey = edgeKey(a, b);
                if (!uniqueEdges.contains(edgeKey)) {
                    edges.add(new Edge(a, b));
                    uniqueEdges.add(edgeKey);
                }
            }
        }
    }

    /**
     * Erzeugt einen eindeutigen Schlüssel für eine Kante zwischen zwei Knoten,
     * um Duplikate zu vermeiden (unabhängig von der Reihenfolge der Knoten).
     * 
     * @param a Erster Knoten
     * @param b Zweiter Knoten
     * @return String-Schlüssel für die Kante
     */
    private String edgeKey(Node a, Node b) {
        String key1 = String.format("%.3f_%.3f", a.getX(), a.getY());
        String key2 = String.format("%.3f_%.3f", b.getX(), b.getY());
        return (key1.compareTo(key2) < 0) ? key1 + "|" + key2 : key2 + "|" + key1;
    }

    /**
     * Verteilt Ressourcen an Spieler abhängig vom Würfelergebnis.
     * Bei einer 7 wird der Räuber aktiviert und die Räuber-Oberfläche angezeigt.
     * 
     * @param diceRoll Das Ergebnis des Würfelwurfs
     */
    public void distributeResources(int diceRoll) {
        if (diceRoll == 7) {
            robber.activate();
            if (controller != null) {
                controller.showRobberOverlay();
            }
            return;
        }
    
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (robber.isActive() && robber.getPosition() == i) {
                continue;
            }
    
            int numberToken = tile.getNumberToken();
            if (numberToken != diceRoll) continue;
    
            ResourceType resource = tile.getType().toResourceType();
            if (resource == null) continue;
    
            for (Node node : tile.getAdjacentNodes()) {
                if (node.getOwner() != null && node.getBuildingType() != null) {
                    int amount = (node.getBuildingType() == BuildingType.CITY) ? 2 : 1;
                    node.getOwner().addResource(resource, amount);
                }
            }
        }
    }
    
    /**
     * Prüft, ob an einen gegebenen Knoten bereits ein Nachbargebäude (besetzter Knoten) angrenzt.
     * 
     * @param node Der zu prüfende Knoten
     * @return true, wenn ein benachbartes Gebäude existiert, sonst false
     */
    public boolean hasAdjacentBuildings(Node node) {
        for (Edge edge : edges) {
            if (edge.getNodeA().equals(node)) {
                if (edge.getNodeB().isOccupied()) return true;
            } else if (edge.getNodeB().equals(node)) {
                if (edge.getNodeA().isOccupied()) return true;
            }
        }
        return false;
    }

    /**
     * Sucht einen bestehenden Knoten anhand von Koordinaten.
     * 
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return Der gefundene Knoten oder null, falls keiner existiert
     */
    public Node findExistingNode(double x, double y) {
        Node searchNode = new Node(x, y);
        for (Node node : nodes) {
            if (node.equals(searchNode)) {
                return node;
            }
        }
        return null;
    }
}
