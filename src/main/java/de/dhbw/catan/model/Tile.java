package de.dhbw.catan.model;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.shape.Polygon;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Tile {
    private Polygon shape;
    private TileType type;
    private int numberToken;
    private List<Node> adjacentNodes;

    public Tile(Polygon shape, TileType type, int numberToken) {
        this.shape = shape;
        this.type = type;
        this.numberToken = numberToken;
        this.adjacentNodes = new ArrayList<>();
    }
    
}
