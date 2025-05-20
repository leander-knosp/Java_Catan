package de.dhbw.catan.controller;

import de.dhbw.catan.model.Board;
import de.dhbw.catan.model.Edge;
import de.dhbw.catan.model.Node;
import de.dhbw.catan.model.Player;
import de.dhbw.catan.model.BuildingType;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class PointsController {

    private BoardController boardController;
    private Player currentPlayer; // Beispielspieler, solltest du passend setzen

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
        this.currentPlayer = new Player("Spieler 1"); // Beispielinitialisierung
    }

    @FXML
    public void showCornerPoints() {
        AnchorPane boardPane = boardController.getBoardPane();

        // Vorherige Ecken entfernen
        boardPane.getChildren().removeIf(node -> "corner".equals(node.getUserData()));

        for (Node node : boardController.getBoard().getNodes()) {
            Circle circle = new Circle(node.getX(), node.getY(), 10);
            circle.setFill(node.isOccupied() ? Color.RED : Color.WHITE);
            circle.setStroke(Color.BLACK);
            circle.setCursor(Cursor.HAND);
            circle.setUserData("corner");

            circle.setOnMouseClicked(event -> {
                if (!node.isOccupied()) {
                    node.setOwner(currentPlayer);
                    node.setBuildingType(BuildingType.SETTLEMENT);
                    circle.setFill(Color.RED);
                    System.out.println("Gebäude auf Ecke gesetzt: " + node);
                } else {
                    System.out.println("Ecke bereits besetzt: " + node);
                }
            });

            boardPane.getChildren().add(circle);
        }
    }

    @FXML
    public void showEdgePoints() {
        AnchorPane boardPane = boardController.getBoardPane();

        // Vorherige Kanten entfernen
        boardPane.getChildren().removeIf(node -> "edge".equals(node.getUserData()));

        for (Edge edge : boardController.getBoard().getEdges()) {
            Line line = new Line(edge.getNodeA().getX(), edge.getNodeA().getY(),
                                 edge.getNodeB().getX(), edge.getNodeB().getY());
            line.setStroke(edge.isOccupied() ? Color.BLUE : Color.GRAY);
            line.setStrokeWidth(5);
            line.setCursor(Cursor.HAND);
            line.setUserData("edge");

            line.setOnMouseClicked(event -> {
                if (!edge.isOccupied()) {
                    edge.setOwner(currentPlayer);
                    line.setStroke(Color.BLUE);
                    System.out.println("Straße auf Kante gesetzt: " + edge);
                } else {
                    System.out.println("Kante bereits besetzt: " + edge);
                }
            });

            boardPane.getChildren().add(line);
        }
    }
}
