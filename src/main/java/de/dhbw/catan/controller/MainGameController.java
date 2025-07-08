package de.dhbw.catan.controller;

import de.dhbw.catan.Main;
import de.dhbw.catan.model.Dice;
import de.dhbw.catan.model.Player;
import de.dhbw.catan.model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class MainGameController {

    private final Dice dice;
    private Board board;
    private BuildController buildController;
    private int playerCount;

    public MainGameController() {
        this.dice = new Dice();
        this.buildController = new BuildController();
    }

    public void setBuildController(BuildController buildController) {
        this.buildController = buildController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/catan.fxml"));
        Scene gameScene = new Scene(loader.load());

        // Setze den MainGameController im BoardController
        BoardController boardController = loader.getController();
        boardController.setMainGameController(this);

        // Spieler an den BoardController übergeben
        boardController.initializePlayers(players);

        Main.primaryStage.setScene(gameScene);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public int getPlayerCount() {
        return this.playerCount;
    }

    public void onRollDice() {
        int number = dice.rollDice();
        System.out.println("Rolled: " + number);
        if (board != null) {
            board.distributeResources(number);
        }
    }

    public void callShowCornerPoints() {
        buildController.showCornerPoints();
    }

    public void callShowEdgePoints() {
        buildController.showEdgePoints();
    }
}