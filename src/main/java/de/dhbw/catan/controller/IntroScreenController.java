package de.dhbw.catan.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @FXML
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

        player_1_name.getStyleClass().remove("text-field-error");
        player_2_name.getStyleClass().remove("text-field-error");
        player_3_name.getStyleClass().remove("text-field-error");
        player_4_name.getStyleClass().remove("text-field-error");

        List<String> names = new ArrayList<>();
        names.add(player_1_name.getText().trim());
        names.add(player_2_name.getText().trim());
        if (playerCount >= 3) names.add(player_3_name.getText().trim());
        if (playerCount == 4) names.add(player_4_name.getText().trim());

        for (int i = 0; i < playerCount; i++) {
            if (names.get(i).isEmpty()) {
                hasError = true;
                switch (i) {
                    case 0 -> player_1_name.getStyleClass().add("text-field-error");
                    case 1 -> player_2_name.getStyleClass().add("text-field-error");
                    case 2 -> player_3_name.getStyleClass().add("text-field-error");
                    case 3 -> player_4_name.getStyleClass().add("text-field-error");
                }
            }
        }

        Set<String> uniqueNames = new HashSet<>(names);
        if (uniqueNames.size() < names.size()) {
            hasError = true;
            for (int i = 0; i < playerCount; i++) {
                String currentName = names.get(i);
                if (Collections.frequency(names, currentName) > 1) {
                    switch (i) {
                        case 0 -> player_1_name.getStyleClass().add("text-field-error");
                        case 1 -> player_2_name.getStyleClass().add("text-field-error");
                        case 2 -> player_3_name.getStyleClass().add("text-field-error");
                        case 3 -> player_4_name.getStyleClass().add("text-field-error");
                    }
                }
            }
        }

        if (hasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Player Name Error");
            alert.setHeaderText(null);
            if (uniqueNames.size() < names.size()) {
                alert.setContentText("Please enter a unique name for each player. Duplicate names are not allowed.");
            } else {
                alert.setContentText("Please enter a name for all active players.");
            }
            alert.showAndWait();
            return;
        }

        List<Player> players = new ArrayList<>();
        players.add(new Player(names.get(0), "Red"));
        players.add(new Player(names.get(1), "Blue"));
        if (playerCount >= 3) players.add(new Player(names.get(2), "Green"));
        if (playerCount == 4) players.add(new Player(names.get(3), "Yellow"));

        mainGameController.startGame(players);
    }

    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
    }
}