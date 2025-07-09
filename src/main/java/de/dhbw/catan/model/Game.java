package de.dhbw.catan.model;

import java.util.List;

/**
 * Repräsentiert das Spiel mit einer Liste von Spielern und verwaltet den aktuellen Spieler.
 */
public class Game {
    private List<Player> players;
    private int currentPlayerIndex;

    /**
     * Konstruktor für das Spiel.
     * 
     * @param players Liste der teilnehmenden Spieler
     * @param startingPlayerIndex Index des Spielers, der das Spiel beginnt
     */
    public Game(List<Player> players, int startingPlayerIndex) {
        this.players = players;
        this.currentPlayerIndex = startingPlayerIndex;
    }

    /**
     * Gibt die Liste aller Spieler zurück.
     * 
     * @return Liste der Spieler
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gibt den aktuellen Spieler zurück.
     * 
     * @return Der Spieler, der gerade am Zug ist
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Wechselt zum nächsten Spieler in der Liste.
     * Wenn das Ende der Liste erreicht ist, wird wieder der erste Spieler ausgewählt.
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}
