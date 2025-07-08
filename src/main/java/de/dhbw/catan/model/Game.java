package de.dhbw.catan.model;

import java.util.List;

public class Game {
    private List<Player> players;
    private int currentPlayerIndex;

    public Game(List<Player> players, int startingPlayerIndex) {
        this.players = players;
        this.currentPlayerIndex = startingPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }
}
