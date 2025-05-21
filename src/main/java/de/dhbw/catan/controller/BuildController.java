package de.dhbw.catan.controller;

import de.dhbw.catan.model.Edge;
import de.dhbw.catan.model.Node;
import de.dhbw.catan.model.Player;
import de.dhbw.catan.model.ResourceType;
import de.dhbw.catan.model.BuildingType;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildController {

    private BoardController boardController;
    private Player currentPlayer; // Beispielspieler, solltest du passend setzen

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
        this.currentPlayer = new Player("Spieler 1");
        currentPlayer.addResource(ResourceType.BRICK, 10);
        currentPlayer.addResource(ResourceType.GRAIN, 10);
        currentPlayer.addResource(ResourceType.LUMBER, 10);
        currentPlayer.addResource(ResourceType.ORE, 10);
        currentPlayer.addResource(ResourceType.WOOL, 10);
    }

    @FXML
    public void showCornerPoints() {
        List<Node> nodes = boardController.getBoard().getNodes();
        AnchorPane boardPane = boardController.getBoardPane();

        // Vorherige Ecken entfernen
        boardPane.getChildren().removeIf(node -> "corner".equals(node.getUserData()));

        Set<String> uniquePoints = new HashSet<>();

        for (Node node : nodes) {
            String key = String.format("%.3f_%.3f", node.getX(), node.getY());
            if (uniquePoints.add(key)) {
                Circle circle = new Circle(node.getX(), node.getY(), 10);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.BLACK);
                circle.setCursor(Cursor.HAND);
                circle.setUserData("corner");

                circle.setOnMouseClicked(event -> {
                    if (!node.checkNeighbor(node, boardPane)) {
                        if (!node.isOccupied()) {
                            boolean success = currentPlayer.build(BuildingType.SETTLEMENT);
                            if (success) {
                                node.setOwner(currentPlayer);
                                node.setBuildingType(BuildingType.SETTLEMENT);

                                // Settlement Bild anzeigen
                                Image image = new Image(getClass().getResource("/images/Catan_HausRot.png").toExternalForm());
                                ImageView imageView = new ImageView(image);
                                imageView.setFitWidth(55);
                                imageView.setFitHeight(55);
                                imageView.setX(node.getX() - 30);
                                imageView.setY(node.getY() - 30);
                                imageView.setUserData("corner");

                                boardPane.getChildren().remove(circle); // Kreis entfernen
                                boardPane.getChildren().add(imageView);

                                System.out.println("Gebäude auf Ecke gesetzt: " + node);
                            } else {
                                System.out.println("Nicht genug Ressourcen für Siedlung.");
                            }
                        } else {
                            System.out.println("Ecke bereits besetzt: " + node);
                        }
                    }
                });

                boardPane.getChildren().add(circle);
            }
        }
    }

    @FXML
    public void showEdgePoints() {
        AnchorPane boardPane = boardController.getBoardPane();

        // Vorherige Kanten entfernen
        boardPane.getChildren().removeIf(node -> "edge".equals(node.getUserData()));

        Set<String> uniqueEdges = new HashSet<>();

        for (Edge edge : boardController.getBoard().getEdges()) {
            String key = edgeKey(edge.getNodeA(), edge.getNodeB());
            if (edge.checkEndpoints(boardPane) || edge.checkConnectedEdge(boardPane)) {
                if (uniqueEdges.add(key)) {
                    Line line = new Line(edge.getNodeA().getX(), edge.getNodeA().getY(),
                                        edge.getNodeB().getX(), edge.getNodeB().getY());
                    line.setStroke(edge.isOccupied() ? Color.BLUE : Color.GRAY);
                    line.setStrokeWidth(5);
                    line.setCursor(Cursor.HAND);
                    line.setUserData("edge");

                    line.setOnMouseClicked(event -> {
                        if (!edge.isOccupied()) {
                            boolean success = currentPlayer.build(BuildingType.ROAD);
                            if (success) {
                                edge.setOwner(currentPlayer);
                                line.setStroke(Color.BLUE);
                                line.setUserData("road");
                                System.out.println("Straße auf Kante gesetzt: " + edge);
                            } else {
                                System.out.println("Nicht genug Ressourcen für Straße.");
                            }
                        } else {
                            System.out.println("Kante bereits besetzt: " + edge);
                        }
                    });
                    boardPane.getChildren().add(line);
                }
            }
        }
    }

    private String edgeKey(Node a, Node b) {
        String key1 = String.format("%.3f_%.3f", a.getX(), a.getY());
        String key2 = String.format("%.3f_%.3f", b.getX(), b.getY());
        return (key1.compareTo(key2) < 0) ? key1 + "|" + key2 : key2 + "|" + key1;
    }
}
