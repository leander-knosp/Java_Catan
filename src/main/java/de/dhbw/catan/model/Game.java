package de.dhbw.catan.model;

import java.util.List;
import de.dhbw.catan.model.Board;
import de.dhbw.catan.model.Player;

public class Game {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;

    public Game(Board board, List<Player> players, int startingPlayerIndex) {
        this.board = board;
        this.players = players;
        this.currentPlayerIndex = startingPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}
