package de.dhbw.catan.controller;

import de.dhbw.catan.Main;
import de.dhbw.catan.model.Dice;
import de.dhbw.catan.model.Player;
import de.dhbw.catan.model.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import de.dhbw.catan.model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class MainGameController {

    // Ressourcen-Karten Rechtecke (gefärbt)
    @FXML private Rectangle grainCard, woolCard, oreCard, brickCard, lumberCard;

    // Labels für Ressourcenanzahl
    @FXML private Label grainCount, woolCount, oreCount, brickCount, lumberCount;

    @FXML private Label diceLabel;

    // Würfel 1 Punkte (aus FXML: first_dotTopLeft, first_dotTopRight, etc.)
    @FXML private Circle first_dotTopLeft, first_dotTopRight, first_dotMiddle, first_dotBottomLeft,
                          first_dotBottomRight, first_dotMidLeft, first_dotMidRight;

    // Würfel 2 Punkte
    @FXML private Circle second_dotTopLeft, second_dotTopRight, second_dotMiddle, second_dotBottomLeft,
                          second_dotBottomRight, second_dotMidLeft, second_dotMidRight;

    // Container Würfel (AnchorPane oder StackPane)
   @FXML private StackPane First_dice, Second_dice;

    @FXML private GridPane someGridPane;

    private final Dice dice;
    private Board board;
    private BuildController buildController;
    private BoardController boardController;
    private int playerCount;

    public MainGameController() {
        this.dice = new Dice();
        this.buildController = new BuildController();
    }

    public void setBuildController(BuildController buildController) {
        this.buildController = buildController;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    /**
     * Zeigt den Intro-Bildschirm an
     */
    public void showIntroScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IntroScreen.fxml"));
            Scene introScene = new Scene(loader.load());

            IntroScreenController introController = loader.getController();
            introController.setMainGameController(this);

            Main.primaryStage.setScene(introScene);
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Startet das Spiel mit gegebener Spielerliste
     */
    public void startGame(List<Player> players) {
        this.playerCount = players.size();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Board.fxml"));
            Scene gameScene = new Scene(loader.load());

            BoardController boardController = loader.getController();
            boardController.setMainGameController(this);
            boardController.initializePlayers(players);

            Main.primaryStage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    /**
     * Wird aufgerufen, wenn ein Würfel geworfen wird (durch Klick)
     */
    @FXML
    public void onRollDice() {
        int total = dice.rollDice();

        int dice1 = dice.getFirstDie();
        int dice2 = dice.getSecondDie();

        diceLabel.setText("Rolled: " + total);

        showFirstDiceNumber(dice1);
        showSecondDiceNumber(dice2);

        if (board != null) {
            board.distributeResources(total);
        }

        if (boardController != null) {
            updateResourceLabels(boardController.getCurrentPlayer());
        } else {
            System.err.println("BoardController ist nicht initialisiert!");
        }
    }

    public void callShowCornerPoints() {
        buildController.showCornerPoints();
    }

    public void callShowEdgePoints() {
        buildController.showEdgePoints();
    }

    /**
     * Aktualisiert die Anzeige der Ressourcenanzahl im UI
     */
    public void updateResourceLabels(Player player) {
        grainCount.setText(String.valueOf(player.getResourceCount(ResourceType.GRAIN)));
        woolCount.setText(String.valueOf(player.getResourceCount(ResourceType.WOOL)));
        oreCount.setText(String.valueOf(player.getResourceCount(ResourceType.ORE)));
        brickCount.setText(String.valueOf(player.getResourceCount(ResourceType.BRICK)));
        lumberCount.setText(String.valueOf(player.getResourceCount(ResourceType.LUMBER)));
    }

    /**
     * Setzt die Sichtbarkeit der Punkte für den ersten Würfel entsprechend der gewürfelten Zahl
     */
    private void showFirstDiceNumber(int number) {
        // Alle Punkte verstecken
        first_dotTopLeft.setVisible(false);
        first_dotTopRight.setVisible(false);
        first_dotMiddle.setVisible(false);
        first_dotBottomLeft.setVisible(false);
        first_dotBottomRight.setVisible(false);
        first_dotMidLeft.setVisible(false);
        first_dotMidRight.setVisible(false);

        // Sichtbarkeit je nach Zahl setzen
        switch (number) {
            case 1 -> first_dotMiddle.setVisible(true);
            case 2 -> {
                first_dotTopLeft.setVisible(true);
                first_dotBottomRight.setVisible(true);
            }
            case 3 -> {
                first_dotTopLeft.setVisible(true);
                first_dotMiddle.setVisible(true);
                first_dotBottomRight.setVisible(true);
            }
            case 4 -> {
                first_dotTopLeft.setVisible(true);
                first_dotTopRight.setVisible(true);
                first_dotBottomLeft.setVisible(true);
                first_dotBottomRight.setVisible(true);
            }
            case 5 -> {
                first_dotTopLeft.setVisible(true);
                first_dotTopRight.setVisible(true);
                first_dotMiddle.setVisible(true);
                first_dotBottomLeft.setVisible(true);
                first_dotBottomRight.setVisible(true);
            }
            case 6 -> {
                first_dotTopLeft.setVisible(true);
                first_dotTopRight.setVisible(true);
                first_dotMidLeft.setVisible(true);
                first_dotMidRight.setVisible(true);
                first_dotBottomLeft.setVisible(true);
                first_dotBottomRight.setVisible(true);
            }
        }
    }

    /**
     * Setzt die Sichtbarkeit der Punkte für den zweiten Würfel entsprechend der gewürfelten Zahl
     */
    private void showSecondDiceNumber(int number) {
        // Alle Punkte verstecken
        second_dotTopLeft.setVisible(false);
        second_dotTopRight.setVisible(false);
        second_dotMiddle.setVisible(false);
        second_dotBottomLeft.setVisible(false);
        second_dotBottomRight.setVisible(false);
        second_dotMidLeft.setVisible(false);
        second_dotMidRight.setVisible(false);

        // Sichtbarkeit je nach Zahl setzen
        switch (number) {
            case 1 -> second_dotMiddle.setVisible(true);
            case 2 -> {
                second_dotTopLeft.setVisible(true);
                second_dotBottomRight.setVisible(true);
            }
            case 3 -> {
                second_dotTopLeft.setVisible(true);
                second_dotMiddle.setVisible(true);
                second_dotBottomRight.setVisible(true);
            }
            case 4 -> {
                second_dotTopLeft.setVisible(true);
                second_dotTopRight.setVisible(true);
                second_dotBottomLeft.setVisible(true);
                second_dotBottomRight.setVisible(true);
            }
            case 5 -> {
                second_dotTopLeft.setVisible(true);
                second_dotTopRight.setVisible(true);
                second_dotMiddle.setVisible(true);
                second_dotBottomLeft.setVisible(true);
                second_dotBottomRight.setVisible(true);
            }
            case 6 -> {
                second_dotTopLeft.setVisible(true);
                second_dotTopRight.setVisible(true);
                second_dotMidLeft.setVisible(true);
                second_dotMidRight.setVisible(true);
                second_dotBottomLeft.setVisible(true);
                second_dotBottomRight.setVisible(true);
            }
        }
    }
}
