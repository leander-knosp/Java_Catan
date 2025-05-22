package de.dhbw.catan.model;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import lombok.Data;

@Data
public class Node {
    private final double x;
    private final double y;
    private Player owner;  
    private BuildingType buildingType;

    private static final double TOLERANCE = 5.0;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return Math.abs(this.x - other.x) <= TOLERANCE &&
               Math.abs(this.y - other.y) <= TOLERANCE;
    }

    @Override
    public int hashCode() {
        return 0; // Nicht sinnvoll bei Toleranz, daher nicht mit HashMap nutzen
    }

    public boolean isOccupied() {
        return owner != null;
    }

    public boolean checkNeighbor(Node node, Pane boardPane) {
        int baseX = (int) Math.round(this.x);
        int baseY = (int) Math.round(this.y);

        int[][] offsets = {
            {0, -93}, {81, 47}, {-81, 47},
            {81, -47}, {-81, -46}, {0, 94}
        };

        for (int[] offset : offsets) {
            int neighborX = baseX + offset[0];
            int neighborY = baseY + offset[1];

            for (javafx.scene.Node n : boardPane.getChildren()) {
                if (n instanceof ImageView && "corner".equals(n.getUserData())) {
                    ImageView img = (ImageView) n;
                    int imgX = (int) Math.round(img.getX() + 30);
                    int imgY = (int) Math.round(img.getY() + 30);
                    int tolerance = 10;

                    if (Math.abs(imgX - neighborX) <= tolerance && Math.abs(imgY - neighborY) <= tolerance) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
} // Ende Node
