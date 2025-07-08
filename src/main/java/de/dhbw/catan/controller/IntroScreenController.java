package de.dhbw.catan.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.shape.Circle;

import lombok.Data;

@Data
public class IntroScreenController {

    private MainGameController mainGameController;
    private String selectedColor = null;

    @FXML
    private Circle colChoiceRed, colChoiceBlue, colChoiceYellow, colChoiceGreen;

    @FXML
    private Spinner<Integer> playerSpinner;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 3);
        playerSpinner.setValueFactory(valueFactory);
    }

    @FXML
    private void onStartGame(ActionEvent event) {
        int playerCount = playerSpinner.getValue();
        String playerColor = selectedColor != null ? selectedColor : "Red";
        mainGameController.startGame(playerCount, playerColor);
    }

    @FXML
    private void onChooseColor(javafx.scene.input.MouseEvent event) {
        Circle clickedCircle = (Circle) event.getSource();
        // Borders zurücksetzen
        colChoiceRed.setStyle("");
        colChoiceBlue.setStyle("");
        colChoiceYellow.setStyle("");
        colChoiceGreen.setStyle("");
    
        // Ausgewählten Kreis hervorheben
        clickedCircle.setStyle("-fx-stroke: white; -fx-stroke-width: 3;");
    
        // Farbe merken
        if (clickedCircle == colChoiceRed) {
            selectedColor = "Red";
        } else if (clickedCircle == colChoiceBlue) {
            selectedColor = "Blue";
        } else if (clickedCircle == colChoiceYellow) {
            selectedColor = "YELLOW";
        } else if (clickedCircle == colChoiceGreen) {
            selectedColor = "Green";
        }
    
        //System.out.println("Ausgewählte Farbe: " + selectedColor);
    }

    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
    }
}