package de.dhbw.catan.model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import lombok.Data;

/**
 * Repräsentiert eine Kante (Straße) im Spielbrett, die zwei Knoten (Nodes) verbindet.
 * Eine Kante kann von einem Spieler besetzt sein.
 */
@Data
public class Edge {
    private final Node nodeA;
    private final Node nodeB;
    private Player owner;  // null, wenn unbesetzt

    /**
     * Prüft, ob die Kante von einem Spieler besetzt ist.
     * 
     * @return true, wenn die Kante besetzt ist, sonst false
     */
    public boolean isOccupied() {
        return owner != null;
    }

    /**
     * Berechnet die X-Koordinate des Mittelpunkts der Kante.
     * 
     * @return X-Koordinate des Mittelpunkts
     */
    public double getMidX() {
        return (nodeA.getX() + nodeB.getX()) / 2;
    }

    /**
     * Berechnet die Y-Koordinate des Mittelpunkts der Kante.
     * 
     * @return Y-Koordinate des Mittelpunkts
     */
    public double getMidY() {
        return (nodeA.getY() + nodeB.getY()) / 2;
    }
    
    /**
     * Überprüft, ob einer der Endpunkte der Kante mit einem "corner"-Bild in
     * der übergebenen Pane nahe genug übereinstimmt.
     * 
     * @param boardPane Das Pane, das die Spielfeld-Elemente enthält
     * @return true, wenn ein Endpunkt nahe einem "corner"-Bild liegt, sonst false
     */
    public boolean checkEndpoints(Pane boardPane) {
        int tolerance = 5;
    
        // Koordinaten der beiden Endpunkte
        int ax = (int) Math.round(nodeA.getX());
        int ay = (int) Math.round(nodeA.getY());
        int bx = (int) Math.round(nodeB.getX());
        int by = (int) Math.round(nodeB.getY());
    
        for (javafx.scene.Node n : boardPane.getChildren()) {
            if (n instanceof ImageView && "corner".equals(n.getUserData())) {
                ImageView img = (ImageView) n;
                int imgX = (int) Math.round(img.getX() + 30); // Korrektur der Position
                int imgY = (int) Math.round(img.getY() + 30);
    
                boolean matchesA = Math.abs(imgX - ax) <= tolerance && Math.abs(imgY - ay) <= tolerance;
                boolean matchesB = Math.abs(imgX - bx) <= tolerance && Math.abs(imgY - by) <= tolerance;
    
                if (matchesA || matchesB) {
                    return true;
                }
            }
        }
    
        return false;
    }   
    
    /**
     * Prüft, ob die Kante mit einer bestehenden "road"-Linie in der Pane verbunden ist.
     * 
     * @param boardPane Das Pane, das die Spielfeld-Elemente enthält
     * @return true, wenn die Kante an eine vorhandene Straße angrenzt, sonst false
     */
    public boolean checkConnectedEdge(Pane boardPane) {
        int tolerance = 5;
        int ax = (int) Math.round(nodeA.getX());
        int ay = (int) Math.round(nodeA.getY());
        int bx = (int) Math.round(nodeB.getX());
        int by = (int) Math.round(nodeB.getY());
    
        for (javafx.scene.Node n : boardPane.getChildren()) {
            if (n instanceof Line && "road".equals(n.getUserData())) {
                Line line = (Line) n;

                int lx1 = (int) Math.round(line.getStartX());
                int ly1 = (int) Math.round(line.getStartY());
                int lx2 = (int) Math.round(line.getEndX());
                int ly2 = (int) Math.round(line.getEndY());
    
                boolean connectsToA = 
                    (Math.abs(lx1 - ax) <= tolerance && Math.abs(ly1 - ay) <= tolerance) ||
                    (Math.abs(lx2 - ax) <= tolerance && Math.abs(ly2 - ay) <= tolerance);
    
                boolean connectsToB = 
                    (Math.abs(lx1 - bx) <= tolerance && Math.abs(ly1 - by) <= tolerance) ||
                    (Math.abs(lx2 - bx) <= tolerance && Math.abs(ly2 - by) <= tolerance);
    
                if (connectsToA || connectsToB) {
                    return true;
                }
            }
        }
        return false;
    }      
}
