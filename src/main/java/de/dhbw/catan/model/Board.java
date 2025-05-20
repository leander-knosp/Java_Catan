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
        this.tiles = assignTileTypes(hexes);
        this.numberTokens = new ArrayList<>(tokens);
        shuffleBoard();
        createNodesAndEdges();
    }

    private List<Tile> assignTileTypes(List<Polygon> hexes) {
        List<TileType> TileTypes = new ArrayList<>(List.of(
                TileType.PASTURES, TileType.PASTURES, TileType.PASTURES, TileType.PASTURES,
                TileType.FOREST, TileType.FOREST, TileType.FOREST, TileType.FOREST,
                TileType.MOUNTAINS, TileType.MOUNTAINS, TileType.MOUNTAINS,
                TileType.HILLS, TileType.HILLS, TileType.HILLS,
                TileType.FIELDS, TileType.FIELDS, TileType.FIELDS, TileType.FIELDS
        ));
        Collections.shuffle(TileTypes);

        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < hexes.size(); i++) {
            result.add(new Tile(hexes.get(i), TileTypes.get(i)));
        }
        return result;
    }

    private void shuffleBoard() {
        Collections.shuffle(tiles);
        Collections.shuffle(numberTokens);
    }

    private String keyFromCoords(double x, double y) {
        double precision = 1000.0; // 3 Nachkommastellen
        long rx = Math.round(x * precision);
        long ry = Math.round(y * precision);
        return rx + "_" + ry;
    }
    
    private void createNodesAndEdges() {
        Map<String, Node> uniqueNodes = new HashMap<>();
        Set<String> uniqueEdges = new HashSet<>();
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    
        for (Tile tile : tiles) {
            Polygon hex = tile.getShape();
            List<Double> points = hex.getPoints();
            double layoutX = hex.getLayoutX();
            double layoutY = hex.getLayoutY();
    
            List<Node> tileNodes = new ArrayList<>();
    
            for (int i = 0; i < points.size(); i += 2) {
                double x = points.get(i) + layoutX;
                double y = points.get(i + 1) + layoutY;
    
                String key = keyFromCoords(x, y);
    
                Node node = uniqueNodes.get(key);
                if (node == null) {
                    node = new Node(x, y);
                    uniqueNodes.put(key, node);
                    nodes.add(node);
                }
                tileNodes.add(node);
            }
    
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
    

    private String edgeKey(Node a, Node b) {
        // Sortiert nach Position, damit a-b und b-a als gleich erkannt werden
        String key1 = String.format("%.3f_%.3f", a.getX(), a.getY());
        String key2 = String.format("%.3f_%.3f", b.getX(), b.getY());
        return (key1.compareTo(key2) < 0) ? key1 + "|" + key2 : key2 + "|" + key1;
    }
}
