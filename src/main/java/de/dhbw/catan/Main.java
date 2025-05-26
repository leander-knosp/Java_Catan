package de.dhbw.catan;

import de.dhbw.catan.controller.MainGameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        
        MainGameController mainGameController = new MainGameController();
        mainGameController.showIntroScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
