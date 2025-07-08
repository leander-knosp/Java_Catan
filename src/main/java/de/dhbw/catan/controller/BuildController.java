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

import java.util.ArrayList;
import java.util.List;

public class BuildController {

    private BoardController boardController;
    private MainGameController mainGameController;
    private Player currentPlayer;

    public void initializePlayer(Player player) {
        this.currentPlayer = player;
        //System.out.println("Player: " + player);
        // Ressourcen initialisieren
        currentPlayer.addResource(ResourceType.BRICK, 10);
        currentPlayer.addResource(ResourceType.GRAIN, 10);
        currentPlayer.addResource(ResourceType.LUMBER, 10);
        currentPlayer.addResource(ResourceType.ORE, 10);
        currentPlayer.addResource(ResourceType.WOOL, 10);
    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
    }

    @FXML
    public void showCornerPoints() {
        List<Node> nodes = boardController.getBoard().getNodes();
        AnchorPane boardPane = boardController.getBoardPane();

        // Alte Kreise entfernen
        boardPane.getChildren().removeIf(node -> "corner".equals(node.getUserData()));

        List<Node> placedNodes = new ArrayList<>();

        for (Node node : nodes) {
            boolean alreadyPlaced = placedNodes.stream().anyMatch(existing -> existing.equals(node));
            if (!alreadyPlaced) {
                placedNodes.add(node);

                final Node currentNode = node;
                Circle circle = new Circle(currentNode.getX(), currentNode.getY(), 10);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.BLACK);
                circle.setCursor(Cursor.HAND);
                circle.setUserData("corner");

                circle.setOnMouseClicked(event -> handleSettlementPlacement(currentNode, circle));
                boardPane.getChildren().add(circle);
            }
        }
    }

    @FXML
    public void showEdgePoints() {
        AnchorPane boardPane = boardController.getBoardPane();
        boardPane.getChildren().removeIf(node -> "edge".equals(node.getUserData()));

        List<Edge> placedEdges = new ArrayList<>();

        for (Edge edge : boardController.getBoard().getEdges()) {
            boolean alreadyPlaced = placedEdges.stream().anyMatch(existing ->
                (existing.getNodeA().equals(edge.getNodeA()) && existing.getNodeB().equals(edge.getNodeB())) ||
                (existing.getNodeA().equals(edge.getNodeB()) && existing.getNodeB().equals(edge.getNodeA()))
            );

            if (!alreadyPlaced && (edge.checkEndpoints(boardPane) || edge.checkConnectedEdge(boardPane))) {
                placedEdges.add(edge);

                Line line = new Line(edge.getNodeA().getX(), edge.getNodeA().getY(),
                        edge.getNodeB().getX(), edge.getNodeB().getY());
                line.setStroke(edge.isOccupied() ? Color.BLUE : Color.GRAY);
                line.setStrokeWidth(5);
                line.setCursor(Cursor.HAND);
                line.setUserData("edge");

                line.setOnMouseClicked(event -> handleRoadPlacement(edge, line));
                boardPane.getChildren().add(line);
            }
        }
    }

    private void handleSettlementPlacement(Node node, Circle circle) {
        if (currentPlayer == null) {
            System.err.println("Kein Spieler initialisiert!");
            return;
        }
        
        AnchorPane boardPane = boardController.getBoardPane();

        // Hole zentralen Node (besser für Vergleich)
        Node boardNode = boardController.getBoard().findExistingNode(circle.getCenterX(), circle.getCenterY());
        if (boardNode == null) {
            System.err.println("Konnte Node nicht im Board finden: " + node.getX() + ", " + node.getY());
            return;
        }

        if (!boardController.getBoard().hasAdjacentBuildings(boardNode)) {
            if (!boardNode.isOccupied()) {
                boolean success = currentPlayer.build(BuildingType.SETTLEMENT);
                mainGameController.updateResourceLabels(currentPlayer);
                if (success) {
                    boardNode.setOwner(currentPlayer);
                    boardNode.setBuildingType(BuildingType.SETTLEMENT);

                    String imagePath = "/images/Catan_House_" + 
                        currentPlayer.getColor() + ".png";
                    
                    Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(55);
                    imageView.setFitHeight(55);
                    imageView.setX(boardNode.getX() - 30);
                    imageView.setY(boardNode.getY() - 30);
                    imageView.setUserData("corner");

                    boardPane.getChildren().remove(circle);
                    boardPane.getChildren().add(imageView);

                    //System.out.println("Node gesetzt an: " + boardNode.getX() + "," + boardNode.getY());
                    //System.out.println("Owner gesetzt: " + boardNode.getOwner());
                } else {
                    System.out.println("Nicht genug Ressourcen für Siedlung.");
                }
            } else {
                System.out.println("Ecke bereits besetzt: " + boardNode);
            }
        }
    }

    private void handleRoadPlacement(Edge edge, Line line) {
        if (!edge.isOccupied()) {
            boolean success = currentPlayer.build(BuildingType.ROAD);
            mainGameController.updateResourceLabels(currentPlayer);
            if (success) {
                Color playerColor;

                try {
                    playerColor = Color.valueOf(currentPlayer.getColor());
                } catch (IllegalArgumentException e) {
                    System.err.println("Ungültiger Farbstring für JavaFX: " + currentPlayer.getColor() + ". Setze auf Schwarz.");
                    playerColor = Color.BLACK;
                }
                
                edge.setOwner(currentPlayer);
                line.setStroke(playerColor);
                line.setUserData("road");

                //System.out.println("Straße auf Kante gesetzt: " + edge);
            } else {
                System.out.println("Nicht genug Ressourcen für Straße.");
            }
        } else {
            System.out.println("Kante bereits besetzt: " + edge);
        }
    }
}
