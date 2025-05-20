package de.dhbw.catan.model;

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
}
