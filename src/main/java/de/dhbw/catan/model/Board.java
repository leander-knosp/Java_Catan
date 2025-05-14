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
        this.tiles = assignResourceTypes(hexes);
        this.numberTokens = new ArrayList<>(tokens);
        shuffleBoard();
    }

    private List<Tile> assignResourceTypes(List<Polygon> hexes) {
        List<ResourceType> resourceTypes = new ArrayList<>(List.of(
                ResourceType.PASTURES, ResourceType.PASTURES, ResourceType.PASTURES, ResourceType.PASTURES,
                ResourceType.FOREST, ResourceType.FOREST, ResourceType.FOREST, ResourceType.FOREST,
                ResourceType.MOUNTAINS, ResourceType.MOUNTAINS, ResourceType.MOUNTAINS,
                ResourceType.HILLS, ResourceType.HILLS, ResourceType.HILLS,
                ResourceType.FIELDS, ResourceType.FIELDS, ResourceType.FIELDS, ResourceType.FIELDS
        ));
        Collections.shuffle(resourceTypes);

        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < hexes.size(); i++) {
            result.add(new Tile(hexes.get(i), resourceTypes.get(i)));
        }
        return result;
    }

    private void shuffleBoard() {
        Collections.shuffle(tiles);
        Collections.shuffle(numberTokens);
    }
}
