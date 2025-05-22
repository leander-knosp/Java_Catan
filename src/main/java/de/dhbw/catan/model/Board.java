package de.dhbw.catan.model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import lombok.Data;

import java.util.*;

@Data
public class Board {
    private List<Tile> tiles;
    private List<AnchorPane> numberTokens;
    private List<Node> nodes;
    private List<Edge> edges;

    public Board(List<Polygon> hexes, List<AnchorPane> tokens) {
        this.numberTokens = new ArrayList<>(tokens);
        shuffleBoard();
        this.tiles = assignTileTypes(hexes);
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
        Collections.shuffle(numberTokens);
    }

    private static final double[][] HEX_OFFSETS = {
        {-64.95, -37.5}, {-64.95, 37.5}, {0, 75.0},
        {64.95, 37.5}, {64.95, -37.5}, {0, -75.0}
    };

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

    private String edgeKey(Node a, Node b) {
        String key1 = String.format("%.3f_%.3f", a.getX(), a.getY());
        String key2 = String.format("%.3f_%.3f", b.getX(), b.getY());
        return (key1.compareTo(key2) < 0) ? key1 + "|" + key2 : key2 + "|" + key1;
    }

    public void distributeResources(int diceRoll) {
        for (Tile tile : tiles) {
            if (tile.getNumberToken() != diceRoll) continue;

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
