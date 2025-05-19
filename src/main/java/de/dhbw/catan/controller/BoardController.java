package de.dhbw.catan.controller;

import de.dhbw.catan.model.Board;
import de.dhbw.catan.model.Tile;
import de.dhbw.catan.model.TileType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

public class BoardController {

    @FXML private Polygon hexOcean, hexDesert, hexPastures1, hexPastures2, hexPastures3, hexPastures4,
        hexForest1, hexForest2, hexForest3, hexForest4, hexMountains1, hexMountains2, hexMountains3,
        hexHills1, hexHills2, hexHills3, hexFields1, hexFields2, hexFields3, hexFields4;

    @FXML private AnchorPane circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b, circ6a, circ6b, circ8a,
        circ8b, circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12;

    @FXML private AnchorPane boardPane, sidebar;

    private Board board;

    public List<Tile> getTiles() {
        return board.getTiles();
    }

    public AnchorPane getBoardPane() {
        return boardPane;
    }

    @FXML
    public void initialize() {
        loadSubComponent();

        List<Polygon> hexes = List.of(hexPastures1, hexPastures2, hexPastures3, hexPastures4,
                hexForest1, hexForest2, hexForest3, hexForest4, hexMountains1, hexMountains2, hexMountains3,
                hexHills1, hexHills2, hexHills3, hexFields1, hexFields2, hexFields3, hexFields4);

        List<AnchorPane> tokens = List.of(circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b,
                circ6a, circ6b, circ8a, circ8b, circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12);

        board = new Board(hexes, tokens);
        applyImages();
        positionTiles();
    }

    private void positionTiles() {
        List<Double> posX = board.getTiles().stream().map(t -> t.getShape().getLayoutX()).collect(Collectors.toList());
        List<Double> posY = board.getTiles().stream().map(t -> t.getShape().getLayoutY()).collect(Collectors.toList());

        for (int i = 0; i < board.getTiles().size(); i++) {
            Polygon shape = board.getTiles().get(i).getShape();
            AnchorPane token = board.getNumberTokens().get(i);

            shape.setLayoutX(posX.get(i));
            shape.setLayoutY(posY.get(i));
            token.setLayoutX(posX.get(i) - 24);
            token.setLayoutY(posY.get(i) - 24);
        }
    }

    private void applyImages() {
        var patterns = Map.of(
            TileType.OCEAN, new ImagePattern(new Image(getClass().getResource("/images/ocean.png").toExternalForm()), 0, 0, 0.25, 0.25, true),
            TileType.DESERT, new ImagePattern(new Image(getClass().getResource("/images/desert.jpg").toExternalForm())),
            TileType.PASTURES, new ImagePattern(new Image(getClass().getResource("/images/pastures.jpg").toExternalForm())),
            TileType.FOREST, new ImagePattern(new Image(getClass().getResource("/images/forest.jpg").toExternalForm())),
            TileType.MOUNTAINS, new ImagePattern(new Image(getClass().getResource("/images/mountains.jpg").toExternalForm())),
            TileType.HILLS, new ImagePattern(new Image(getClass().getResource("/images/hills.jpg").toExternalForm())),
            TileType.FIELDS, new ImagePattern(new Image(getClass().getResource("/images/fields.jpg").toExternalForm()))
        );

        for (Tile tile : board.getTiles()) {
            tile.getShape().setFill(patterns.get(tile.getType()));
        }

        hexOcean.setFill(patterns.get(TileType.OCEAN));
        hexDesert.setFill(patterns.get(TileType.DESERT));
    }

    @FXML
    public void loadSubComponent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SubComponent.fxml"));
            Parent subComponent = loader.load();
            PointsController pointsController = loader.getController();
            pointsController.setBoardController(this);
            sidebar.getChildren().add(subComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
