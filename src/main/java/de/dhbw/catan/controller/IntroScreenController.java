package de.dhbw.catan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.dhbw.catan.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import lombok.Data;

@Data
public class IntroScreenController {

    private MainGameController mainGameController;
    private String selectedColor = null;

    @FXML
    private ImageView catan_logo;

    @FXML
    private Circle colChoiceRed, colChoiceBlue, colChoiceYellow, colChoiceGreen;

    @FXML
    private Spinner<Integer> playerSpinner;

    @FXML private HBox playerBox1;
    @FXML private HBox playerBox2;
    @FXML private HBox playerBox3;
    @FXML private HBox playerBox4;


    @FXML private TextField player_1_name;
    @FXML private TextField player_2_name;
    @FXML private TextField player_3_name;
    @FXML private TextField player_4_name;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 3);
        playerSpinner.setValueFactory(valueFactory);
    
        // Listener, der beim Ändern des Wertes reagiert
        playerSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updateVisiblePlayers(newVal));
    
        // Initialanzeige anpassen
        updateVisiblePlayers(playerSpinner.getValue());
    }

    private void updateVisiblePlayers(int playerCount) {
        playerBox1.setVisible(true);
        playerBox1.setManaged(true);
    
        playerBox2.setVisible(true);
        playerBox2.setManaged(true);
    
        playerBox3.setVisible(playerCount >= 3);
        playerBox3.setManaged(playerCount >= 3);
    
        playerBox4.setVisible(playerCount == 4);
        playerBox4.setManaged(playerCount == 4);
    }
    
    

    @FXML
private void onStartGame(ActionEvent event) {
    int playerCount = playerSpinner.getValue();
    boolean hasError = false;

    // Zuerst alle Fehlermarkierungen zurücksetzen
    player_1_name.getStyleClass().remove("text-field-error");
    player_2_name.getStyleClass().remove("text-field-error");
    player_3_name.getStyleClass().remove("text-field-error");
    player_4_name.getStyleClass().remove("text-field-error");

    // Spieler 1
    if (player_1_name.getText().isBlank()) {
        player_1_name.getStyleClass().add("text-field-error");
        hasError = true;
    }

    // Spieler 2
    if (player_2_name.getText().isBlank()) {
        player_2_name.getStyleClass().add("text-field-error");
        hasError = true;
    }

    // Spieler 3
    if (playerCount >= 3 && player_3_name.getText().isBlank()) {
        player_3_name.getStyleClass().add("text-field-error");
        hasError = true;
    }

    // Spieler 4
    if (playerCount == 4 && player_4_name.getText().isBlank()) {
        player_4_name.getStyleClass().add("text-field-error");
        hasError = true;
    }

    if (hasError) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehlende Namen");
        alert.setHeaderText(null);
        alert.setContentText("Bitte gib für alle aktiven Spieler einen Namen ein.");
        alert.showAndWait();
        return;
    }

    // Spieler erstellen
    List<Player> players = new ArrayList<>();
    players.add(new Player(player_1_name.getText(), "Red"));
    players.add(new Player(player_2_name.getText(), "Blue"));
    if (playerCount >= 3) players.add(new Player(player_3_name.getText(), "Green"));
    if (playerCount == 4) players.add(new Player(player_4_name.getText(), "Yellow"));

    mainGameController.startGame(players);
}


    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
    }
}