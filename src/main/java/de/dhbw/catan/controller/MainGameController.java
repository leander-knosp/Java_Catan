package de.dhbw.catan.controller;

import de.dhbw.catan.Main;
import de.dhbw.catan.model.Dice;
import de.dhbw.catan.model.Game;
import de.dhbw.catan.model.Player;
import de.dhbw.catan.model.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import de.dhbw.catan.model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import de.dhbw.catan.model.Trade;
import javafx.scene.shape.Circle;
import de.dhbw.catan.model.Game;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class MainGameController {

    @FXML
    private Rectangle grainCard, woolCard, oreCard, brickCard, lumberCard;

    @FXML
    private Label grainCount, woolCount, oreCount, brickCount, lumberCount, diceLabel;

    @FXML
    private Spinner<Integer> woolSpinnerYou, lumberSpinnerYou, oreSpinnerYou, brickSpinnerYou, grainSpinnerYou;

    @FXML
    private Spinner<Integer> woolSpinnerOther, lumberSpinnerOther, oreSpinnerOther, brickSpinnerOther, grainSpinnerOther;

    @FXML
    private Button acceptTradeButton, declineTradeButton;

    @FXML
    private HBox tradeDecisionBox;

    @FXML
    private VBox tradeContainer;

    @FXML
    private Circle redCircle, blueCircle, yellowCircle, greenCircle;

    private final Dice dice;
    private Board board;
    private BuildController buildController;
    private BoardController boardController;
    private int playerCount;
    private String selectedColor;
    private Trade trade;
    private Game game;

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

    public void startGame(List<Player> players) {
        this.playerCount = players.size();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Board.fxml"));
            Scene gameScene = new Scene(loader.load());

            // Setze den MainGameController im BoardController
            this.boardController = loader.getController();
            this.boardController.setMainGameController(this);

            // Spieler an den BoardController übergeben
            boardController.initializePlayers(players);
            Main.primaryStage.setScene(gameScene);

            gameplay();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialPlacement() {
        if (buildController != null && boardController != null) {
            buildController.setBoardController(boardController);
            buildController.setMainGameController(this);
            buildController.showCornerPoints();
        } else {
            System.err.println("Controllers not properly initialized!");
        }
        List<Player> players = game.getPlayers();
        buildController.showCornerPoints();
        for (Player player : players) {
            System.out.println(player.getName() + " platziert seine erste Siedlung und Straße.");

        }
    }

    public void gameplay() {
        if (game == null) {
            System.err.println("Game object is not initialized!");
            return;
        }
        Player currentPlayer = game.getCurrentPlayer();
        initialPlacement();
        // while (currentPlayer.getVictoryPoints() < 10) {
        //     System.out.println(currentPlayer.getName() + " ist am Zug!");
        // }
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public void onRollDice() {
        int number = dice.rollDice();
        diceLabel.setText("Rolled: " + number);
        if (board != null) {
            board.distributeResources(number);
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

    public void updateResourceLabels(Player player) {
        grainCount.setText(String.valueOf(player.getResourceCount(ResourceType.GRAIN)));
        woolCount.setText(String.valueOf(player.getResourceCount(ResourceType.WOOL)));
        oreCount.setText(String.valueOf(player.getResourceCount(ResourceType.ORE)));
        brickCount.setText(String.valueOf(player.getResourceCount(ResourceType.BRICK)));
        lumberCount.setText(String.valueOf(player.getResourceCount(ResourceType.LUMBER)));
    }

    @FXML
    public void initialize() {
        initializeSpinner();
        tradeContainer.setVisible(false);
        tradeDecisionBox.setVisible(false);
    }

    private void initializeSpinner() {
        woolSpinnerYou.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        lumberSpinnerYou.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        oreSpinnerYou.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        brickSpinnerYou.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        grainSpinnerYou.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));

        woolSpinnerOther.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        lumberSpinnerOther.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        oreSpinnerOther.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        brickSpinnerOther.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        grainSpinnerOther.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
    }


    @FXML
    public void trade(){
        Map<ResourceType, Integer> offerFromYou = new HashMap<>();
        offerFromYou.put(ResourceType.WOOL, woolSpinnerYou.getValue());
        offerFromYou.put(ResourceType.LUMBER, lumberSpinnerYou.getValue());
        offerFromYou.put(ResourceType.ORE, oreSpinnerYou.getValue());
        offerFromYou.put(ResourceType.BRICK, brickSpinnerYou.getValue());
        offerFromYou.put(ResourceType.GRAIN, grainSpinnerYou.getValue());

        Map<ResourceType, Integer> requestFromOther = new HashMap<>();
        requestFromOther.put(ResourceType.WOOL, woolSpinnerOther.getValue());
        requestFromOther.put(ResourceType.LUMBER, lumberSpinnerOther.getValue());
        requestFromOther.put(ResourceType.ORE, oreSpinnerOther.getValue());
        requestFromOther.put(ResourceType.BRICK, brickSpinnerOther.getValue());
        requestFromOther.put(ResourceType.GRAIN, grainSpinnerOther.getValue());

        if(selectedColor == null) {
            System.out.println("Bitte wähle eine Farbe aus.");
            return;
        }
        List<Player> playerList = game.getPlayers();
        trade = new Trade(offerFromYou, requestFromOther, selectedColor, playerList);
        if(trade.handleTrade(boardController.getCurrentPlayer())){
            showButtons();
        }
    }

    @FXML
    private void onPlayerSelect(javafx.scene.input.MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();
        redCircle.setStyle("");
        blueCircle.setStyle("");
        yellowCircle.setStyle("");
        greenCircle.setStyle("");

        clickedCircle.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");
        if (clickedCircle == redCircle) {
            selectedColor = "Red";
        } else if (clickedCircle == blueCircle) {
            selectedColor = "Blue";
        } else if (clickedCircle == yellowCircle) {
            selectedColor = "Yellow";
        } else if (clickedCircle == greenCircle) {
            selectedColor = "Green";
        }
    }

    @FXML
    private void onAcceptTrade() {
        Player currentPlayer = boardController.getCurrentPlayer();
        trade.executeTrade(currentPlayer, trade.getTargetPlayer());
        tradeDecisionBox.setVisible(false);
        tradeContainer.setVisible(false);
    }

    @FXML
    private void onDeclineTrade() {
        System.out.println("Trade abgelehnt.");
        tradeDecisionBox.setVisible(false);
    }


    private void showButtons() {
        tradeDecisionBox.setVisible(true);
    }

    @FXML
    private void showTradeMenu(){
        tradeContainer.setVisible(true);
    }
}
