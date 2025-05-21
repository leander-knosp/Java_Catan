package de.dhbw.catan.model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import lombok.Data;

import java.util.*;

@Data
public class Board {
    private List<Tile> tiles;
    private List<AnchorPane> numberTokens;

    private List<Node> nodes; // Ecken
    private List<Edge> edges; // Kanten

    public Board(List<Polygon> hexes, List<AnchorPane> tokens) {
        this.numberTokens = new ArrayList<>(tokens);
        this.tiles = assignTileTypes(hexes);
        shuffleBoard();
        createNodesAndEdges();
    }

    private List<Tile> assignTileTypes(List<Polygon> hexes) {
        List<TileType> tileTypes = new ArrayList<>(List.of(
                TileType.PASTURES, TileType.PASTURES, TileType.PASTURES, TileType.PASTURES,
                TileType.FOREST, TileType.FOREST, TileType.FOREST, TileType.FOREST,
                TileType.MOUNTAINS, TileType.MOUNTAINS, TileType.MOUNTAINS,
                TileType.HILLS, TileType.HILLS, TileType.HILLS,
                TileType.FIELDS, TileType.FIELDS, TileType.FIELDS, TileType.FIELDS
        ));
        Collections.shuffle(tileTypes);
    
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < hexes.size(); i++) {
            AnchorPane tokenPane = numberTokens.get(i);
            if (tokenPane == null) {
                throw new NullPointerException("TokenPane ist null bei Index " + i);
            }
    
            // Hier den Text-Knoten suchen:
            javafx.scene.text.Text textNode = null;
            for (var node : tokenPane.getChildren()) {
                if (node instanceof javafx.scene.text.Text) {
                    textNode = (javafx.scene.text.Text) node;
                    break;
                }
            }
            if (textNode == null) {
                throw new NullPointerException("Kein Text-Element in TokenPane bei Index " + i);
            }
    
            int number = Integer.parseInt(textNode.getText());
            result.add(new Tile(hexes.get(i), tileTypes.get(i), number));
        }
        return result;
    }
    
    private void shuffleBoard() {
        Collections.shuffle(tiles);
        Collections.shuffle(numberTokens);
    }

    private static final double[][] HEX_OFFSETS = {
        {-64.95, -37.5}, {-64.95, 37.5}, {0, 75.0},
        {64.95, 37.5}, {64.95, -37.5}, {0, -75.0}
    };

    private void createNodesAndEdges() {
        Map<String, Node> uniqueNodes = new HashMap<>();
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

                String key = keyFromCoords(x, y);

                Node node = uniqueNodes.get(key);
                if (node == null) {
                    node = new Node(x, y);
                    uniqueNodes.put(key, node);
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
                    Edge edge = new Edge(a, b);
                    edges.add(edge);
                    uniqueEdges.add(edgeKey);
                }
            }
        }
    }

    private String keyFromCoords(double x, double y) {
        return String.format("%.3f_%.3f", x, y);
    }

    private String edgeKey(Node a, Node b) {
        String key1 = keyFromCoords(a.getX(), a.getY());
        String key2 = keyFromCoords(b.getX(), b.getY());
        return (key1.compareTo(key2) < 0) ? key1 + "|" + key2 : key2 + "|" + key1;
    }

    public void distributeResources(int diceRoll) {
        for (Tile tile : tiles) {
            if (tile.getNumberToken() != diceRoll) continue;
    
            ResourceType resource = tile.getType().toResourceType();

            if (resource == null) continue;
    
            for (Node node : tile.getAdjacentNodes()) {
                if (node.getBuildingType() == BuildingType.SETTLEMENT) {
                    Player player = node.getOwner();
                    int amount = node.getBuildingType() == BuildingType.CITY ? 2 : 1;
                    player.addResource(resource, amount);
                }
            }
        }
    }    
    
}
