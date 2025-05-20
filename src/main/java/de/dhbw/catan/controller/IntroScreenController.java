package de.dhbw.catan.controller;

import de.dhbw.catan.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.shape.Circle;
import java.io.IOException;

import lombok.Data;

@Data
public class IntroScreenController {

    private String selectedColor = null;

    @FXML
    private Button startGameButton;

    @FXML
    private ComboBox<String> colorComboBox;

    @FXML
    private Circle colChoiceRed, colChoiceBlue, colChoiceBrown, colChoiceGreen;

    @FXML
    private Spinner<Integer> playerSpinner;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 3); // min=2, max=4, initial=3
        playerSpinner.setValueFactory(valueFactory);
        colorComboBox.getItems().addAll("Red", "Blue", "Green", "Yellow");
        colorComboBox.setValue("Red");
    }

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

    @FXML
    private void onChooseColor(javafx.scene.input.MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();
    
        // Borders zurücksetzen
        colChoiceRed.setStyle("");
        colChoiceBlue.setStyle("");
        colChoiceBrown.setStyle("");
        colChoiceGreen.setStyle("");
    
        // Ausgewählten Kreis hervorheben
        clickedCircle.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");
    
        // Farbe merken
        if (clickedCircle == colChoiceRed) {
            selectedColor = "Red";
        } else if (clickedCircle == colChoiceBlue) {
            selectedColor = "Blue";
        } else if (clickedCircle == colChoiceBrown) {
            selectedColor = "Brown";
        } else if (clickedCircle == colChoiceGreen) {
            selectedColor = "Green";
        }
    
        System.out.println("Ausgewählte Farbe: " + selectedColor);
    }    
}
