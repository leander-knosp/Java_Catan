package de.dhbw.catan.controller;

import de.dhbw.catan.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class IntroScreenController {

    @FXML
    private Button startGameButton;

    @FXML
    private void onStartGame(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/catan.fxml"));
            Scene gameScene = new Scene(loader.load());
            Main.primaryStage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
