package de.dhbw.catan.model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Data;

@Data
public class Node {
    private final double x;
    private final double y;
    private Player owner;  // null wenn unbesetzt
    private BuildingType buildingType;
    
    public boolean isOccupied() {
        return owner != null;
    }
    
    public boolean checkNeighbor(Node node, Pane boardPane) {
        // 1. Gerundete Basis-Koordinaten des Nodes
        int baseX = (int) Math.round(node.getX());
        int baseY = (int) Math.round(node.getY());
    
        // 2. Liste der gerundeten Verschiebungen zu Nachbarecken
        int[][] offsets = {
            {0, -93},
            {81, 47},
            {-81, 47},
            {81, -47},
            {-81, -46},
            {0, 94}
        };
    
        // 3. Suche nach besetzten Nachbarpositionen
        for (int[] offset : offsets) {
            int neighborX = baseX + offset[0];
            int neighborY = baseY + offset[1];
    
            for (javafx.scene.Node n : boardPane.getChildren()) {
                if (n instanceof ImageView && "corner".equals(n.getUserData())) {
                    ImageView img = (ImageView) n;
    
                    // Die Position des Gebäudes wieder "zurückrechnen" (da X/Y um 30 verschoben wurden)
                    int imgX = (int) Math.round(img.getX() + 30);
                    int imgY = (int) Math.round(img.getY() + 30);
                    int tolerance = 5;
                    if (Math.abs(imgX - neighborX) <= tolerance && Math.abs(imgY - neighborY) <= tolerance) {
                        return true;
                    }
                }
            }
        }
        return false; // Kein Nachbargebäude gefunden
    }    
}
