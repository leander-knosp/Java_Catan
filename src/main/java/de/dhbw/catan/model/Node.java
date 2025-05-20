package de.dhbw.catan.model;

import javafx.scene.paint.Color;
import de.dhbw.catan.model.BuildingType;
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
}
