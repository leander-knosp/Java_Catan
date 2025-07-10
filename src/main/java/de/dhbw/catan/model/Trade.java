package de.dhbw.catan.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * Repräsentiert einen Handelsvorgang zwischen Spielern.
 * 
 * Ermöglicht das Anbieten und Anfordern von Ressourcen zwischen einem aktuellen Spieler und einem Zielspieler.
 */
@Data
public class Trade {

    private List<Player> allPlayers; // Alle Spieler im Spiel
    private Map<ResourceType, Integer> offerFromYou; // Ressourcen, die der aktuelle Spieler anbietet
    private Map<ResourceType, Integer> requestFromOther; // Ressourcen, die vom Zielspieler angefragt werden
    private String targetPlayerColor; // Farbe des Zielspielers
    private Player targetPlayer; // Referenz auf den Zielspieler

    /**
     * Konstruktor für den Handel.
     * 
     * @param offerFromYou Ressourcen, die angeboten werden
     * @param requestFromOther Ressourcen, die angefragt werden
     * @param targetPlayerColor Farbe des Zielspielers
     * @param allPlayers Liste aller Spieler im Spiel
     */
    public Trade(Map<ResourceType, Integer> offerFromYou,
                 Map<ResourceType, Integer> requestFromOther,
                 String targetPlayerColor, List<Player> allPlayers) {
        this.offerFromYou = offerFromYou;
        this.requestFromOther = requestFromOther;
        this.targetPlayerColor = targetPlayerColor;
        this.allPlayers = allPlayers;
    }

    private void setTargetPlayer(Player targetPlayer){
        this.targetPlayer = targetPlayer;
    }

    /**
     * Gibt den Zielspieler zurück.
     * 
     * @return der Zielspieler
     */
    public Player getTargetPlayer() {
        return this.targetPlayer;
    }

    /**
     * Überprüft, ob der Handel möglich ist und setzt den Zielspieler.
     * 
     * @param currentPlayer Der Spieler, der den Handel initiiert
     * @return true wenn der Handel möglich ist, sonst false
     */
    public boolean handleTrade(Player currentPlayer){
        for (Player player : allPlayers) {
            if (player.getColor().equals(targetPlayerColor) && !player.equals(currentPlayer)) {
                Player targetPlayer = player;
                this.setTargetPlayer(targetPlayer);
    
                if (canTrade(currentPlayer, targetPlayer)) {
                    return true;
                } else {
                    System.out.println("Handel nicht möglich – Ressourcen fehlen.");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Führt den Handel zwischen aktuellem Spieler und Zielspieler aus.
     * Ressourcen werden entsprechend getauscht.
     * 
     * @param currentPlayer Der Spieler, der Ressourcen anbietet
     * @param targetPlayer Der Spieler, der Ressourcen gibt
     */
    public void executeTrade(Player currentPlayer, Player targetPlayer) {
        for (Map.Entry<ResourceType, Integer> entry : offerFromYou.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            currentPlayer.addResource(resourceType, -amount);
        }

        for (Map.Entry<ResourceType, Integer> entry : requestFromOther.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            currentPlayer.addResource(resourceType, amount);
        }

        for (Map.Entry<ResourceType, Integer> entry : requestFromOther.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            targetPlayer.addResource(resourceType, -amount);
        }

        for (Map.Entry<ResourceType, Integer> entry : offerFromYou.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            targetPlayer.addResource(resourceType, amount);
        }
    }

    /**
     * Überprüft, ob beide Spieler genug Ressourcen besitzen, um den Handel durchzuführen.
     * 
     * @param currentPlayer Der Spieler, der Ressourcen anbietet
     * @param targetPlayer Der Spieler, von dem Ressourcen angefragt werden
     * @return true, wenn beide Spieler genügend Ressourcen haben, sonst false
     */
    private boolean canTrade(Player currentPlayer, Player targetPlayer) {
        for (Map.Entry<ResourceType, Integer> entry : offerFromYou.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            if (currentPlayer.getResourceCount(resourceType) < amount) {
                return false;
            }
        }

        for (Map.Entry<ResourceType, Integer> entry : requestFromOther.entrySet()) {
            ResourceType resourceType = entry.getKey();
            int amount = entry.getValue();
            if (targetPlayer.getResourceCount(resourceType) < amount) {
                return false;
            }
        }

        return true;
    }
}
