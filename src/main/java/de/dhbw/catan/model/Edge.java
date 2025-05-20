package de.dhbw.catan.model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Data;

@Data
public class Edge {
    private final Node nodeA;
    private final Node nodeB;
    private Player owner;  // null wenn unbesetzt

    public boolean isOccupied() {
        return owner != null;
    }

    public double getMidX() {
        return (nodeA.getX() + nodeB.getX()) / 2;
    }

    public double getMidY() {
        return (nodeA.getY() + nodeB.getY()) / 2;
    }
    
    public boolean checkEndpoints(Pane boardPane) {
        int tolerance = 5;
    
        // Hole die Koordinaten der beiden Endpunkte
        int ax = (int) Math.round(nodeA.getX());
        int ay = (int) Math.round(nodeA.getY());
        int bx = (int) Math.round(nodeB.getX());
        int by = (int) Math.round(nodeB.getY());
    
        for (javafx.scene.Node n : boardPane.getChildren()) {
            if (n instanceof ImageView && "corner".equals(n.getUserData())) {
                ImageView img = (ImageView) n;
                int imgX = (int) Math.round(img.getX() + 30); // Rückverschiebung
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
