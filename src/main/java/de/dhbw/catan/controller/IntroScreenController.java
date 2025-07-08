package de.dhbw.catan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.dhbw.catan.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        playerBox2.setVisible(true);
        playerBox3.setVisible(playerCount >= 3);
        playerBox4.setVisible(playerCount == 4);
    
        // Verhindert, dass unsichtbare Boxen Platz im Layout einnehmen
        playerBox3.setManaged(playerCount >= 3);
        playerBox4.setManaged(playerCount == 4);
    }

    @FXML
    private void onStartGame(ActionEvent event) {
    int playerCount = playerSpinner.getValue();

    List<Player> players = new ArrayList<>();

    // Spieler 1 (immer dabei)
    String name1 = player_1_name.getText().isBlank() ? "Spieler 1" : player_1_name.getText();
    players.add(new Player(name1, "Red"));

    // Spieler 2 (immer dabei)
    String name2 = player_2_name.getText().isBlank() ? "Spieler 2" : player_2_name.getText();
    players.add(new Player(name2, "Blue"));

    if (playerCount >= 3) {
        String name3 = player_3_name.getText().isBlank() ? "Spieler 3" : player_3_name.getText();
        players.add(new Player(name3, "Green"));
    }

    if (playerCount == 4) {
        String name4 = player_4_name.getText().isBlank() ? "Spieler 4" : player_4_name.getText();
        players.add(new Player(name4, "Yellow"));
    }

    // Debug-Ausgabe
    for (Player p : players) {
        System.out.println("Spieler: " + p.getName() + " - Farbe: " + p.getColor());
    }

    // Spiel starten
    mainGameController.startGame(players);
}

    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
    }
}