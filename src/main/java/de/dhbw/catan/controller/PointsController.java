package de.dhbw.catan.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class PointsController {

    @FXML
    private BoardController BoardController;

    public void setBoardController(BoardController boardController) {
        this.BoardController = boardController;
    }
    

    @FXML
    public void showCornerPoints() {

        // Set<String> uniqueCornerSet = new HashSet<>();
        // List<double[]> allHexCorners = new ArrayList<>();

        // for (Polygon hex : hexList) {
        //     Observablepoints = hex.getPoints();
        //     double layoutX = hex.getLayoutX();
        //     double layoutY = hex.getLayoutY();

        //     for (int i = 0; i < points.size(); i += 2) {
        //         double localX = layoutX + points.get(i);
        //         double localY = layoutY + points.get(i + 1);

        //         javafx.geometry.Point2D scenePoint = hex.localToParent(localX, localY);

        //         double x = scenePoint.getX();
        //         double y = scenePoint.getY();
        //         // Schlüssel zur Identifikation (z. B. gerundet auf 3 Dezimalstellen)
        //         String key = String.format("%.3f_%.3f", x, y);

        //         if (uniqueCornerSet.add(key)) {
        //             allHexCorners.add(new double[] { x, y });
        //             Circle point = new Circle(x, y, 8);
        //             point.setFill(javafx.scene.paint.Color.WHITE);
        //             boardPane.getChildren().add(point);
        //         }
        //     }
        // }

        // for (double[] corner : allHexCorners) {
        //     System.out.println("Ecke bei: x = " + corner[0] + ", y = " + corner[1]);
        // }

        List<Polygon> hexList = BoardController.getHexList();
        AnchorPane boardPane = BoardController.getBoardPane();

        List<double[]> offsets = List.of(
            new double[] { -64.95, -37.5 },
            new double[] { -64.95, 37.5 },
            new double[] { 0, 75.0 },
            new double[] { 64.95, 37.5 },
            new double[] { 64.95, -37.5 },
            new double[] { 0, -75.0 }
        );  
        
        Set<String> uniquePoints = new HashSet<>();
        
        for (int i = 0; i < hexList.size(); i++) {
            for (double[] offset : offsets) {
                double x = hexList.get(i).getLayoutX() + offset[0] * hexList.get(i).getScaleX();
                double y = hexList.get(i).getLayoutY() + offset[1] * hexList.get(i).getScaleY();
        
                String key = String.format("%.3f_%.3f", x, y);
                if (uniquePoints.add(key)) {
                    Circle point = new Circle(x, y, 8);
                    point.setFill(javafx.scene.paint.Color.WHITE);
                    point.setCursor(Cursor.HAND);
                    point.setOnMouseClicked(event -> {
                        boardPane.getChildren().removeIf(node -> node instanceof Circle);
                    });
                    boardPane.getChildren().add(point);
                }
            }
        }         
    }
}
