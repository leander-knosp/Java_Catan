package de.dhbw.catan.model;

import javafx.scene.shape.Polygon;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tile {
    private Polygon shape;
    private TileType type;
}
