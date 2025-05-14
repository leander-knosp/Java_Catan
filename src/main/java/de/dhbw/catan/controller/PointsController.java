package de.dhbw.catan.controller;

import de.dhbw.catan.model.Tile;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PointsController {

    private BoardController boardController;

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    @FXML
    public void showCornerPoints() {
        List<Tile> tiles = boardController.getTiles();
        AnchorPane boardPane = boardController.getBoardPane();

        List<double[]> offsets = List.of(
            new double[]{ -64.95, -37.5 }, new double[]{ -64.95, 37.5 }, new double[]{ 0, 75.0 },
            new double[]{ 64.95, 37.5 }, new double[]{ 64.95, -37.5 }, new double[]{ 0, -75.0 }
        );

        Set<String> uniquePoints = new HashSet<>();

        for (Tile tile : tiles) {
            Polygon hex = tile.getShape();
            for (double[] offset : offsets) {
                double x = hex.getLayoutX() + offset[0] * hex.getScaleX();
                double y = hex.getLayoutY() + offset[1] * hex.getScaleY();

                String key = String.format("%.3f_%.3f", x, y);
                if (uniquePoints.add(key)) {
                    Circle point = new Circle(x, y, 8);
                    point.setFill(javafx.scene.paint.Color.WHITE);
                    point.setCursor(Cursor.HAND);
                    point.setOnMouseClicked(event -> boardPane.getChildren().removeIf(n -> n instanceof Circle));
                    boardPane.getChildren().add(point);
                }
            }
        }
    }
}
