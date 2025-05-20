package de.dhbw.catan.model;

import de.dhbw.catan.model.Player;

public class GameState {
    private static Player currentPlayer;

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }
}
