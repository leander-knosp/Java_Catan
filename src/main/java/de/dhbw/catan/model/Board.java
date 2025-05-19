package de.dhbw.catan.model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Board {
    private List<Tile> tiles;
    private List<AnchorPane> numberTokens;

    public Board(List<Polygon> hexes, List<AnchorPane> tokens) {
        this.tiles = assignTileTypes(hexes);
        this.numberTokens = new ArrayList<>(tokens);
        shuffleBoard();
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
}
