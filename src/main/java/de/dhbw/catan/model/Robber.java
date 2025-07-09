package de.dhbw.catan.model;

import lombok.Data;

/**
 * Repräsentiert den Räuber im Spiel.
 * 
 * Der Räuber hat eine aktuelle Position auf dem Spielbrett und einen Aktivierungsstatus.
 * Wenn der Räuber aktiv ist, blockiert er die Ressourcenausgabe des entsprechenden Feldes.
 */
@Data
public class Robber {
    /**
     * Position des Räubers auf dem Spielbrett (Index des Feldes).
     * -1 bedeutet, dass der Räuber sich noch nicht auf einem Feld befindet.
     */
    private int position;

    /**
     * Gibt an, ob der Räuber aktiv ist (auf dem Brett platziert).
     */
    private boolean isActive;

    /**
     * Initialisiert den Räuber mit inaktivem Status und keiner Position.
     */
    public Robber() {
        this.isActive = false;
        this.position = -1;
    }

    /**
     * Bewegt den Räuber auf ein neues Feld.
     * @param newPosition Index des neuen Feldes.
     */
    public void move(int newPosition) {
        this.position = newPosition;
    }

    /**
     * Aktiviert den Räuber, wodurch er Ressourcen blockiert.
     */
    public void activate() {
        this.isActive = true;
    }

    /**
     * Deaktiviert den Räuber, wodurch die Blockade aufgehoben wird.
     */
    public void deactivate() {
        this.isActive = false;
    }
}
