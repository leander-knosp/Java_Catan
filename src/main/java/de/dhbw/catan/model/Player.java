package de.dhbw.catan.model;

import java.util.EnumMap;
import java.util.Map;

import lombok.Data;

/**
 * Repräsentiert einen Spieler im Spiel.
 * Jeder Spieler hat einen Namen, eine Farbe, Ressourcen, verfügbare Bauobjekte und Punkte.
 */
@Data
public class Player {
    private final String name;
    private final String color;
    private final Map<ResourceType, Integer> resources;
    private int roads;
    private int settlements;
    private int cities;
    private int victoryPoints;

    /**
     * Konstruktor für einen Spieler mit Name und Farbe.
     * Initialisiert alle Ressourcen mit 0 und setzt die Anzahl verfügbarer Bauobjekte.
     * 
     * @param name Name des Spielers
     * @param color Farbe des Spielers
     */
    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.resources = new EnumMap<>(ResourceType.class);
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, 0);
        }
        this.roads = 15;
        this.settlements = 5;
        this.cities = 4;
        this.victoryPoints = 0;
    }

    /**
     * Gibt die Farbe des Spielers zurück.
     * 
     * @return die Farbe als String
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Fügt dem Spieler eine bestimmte Menge einer Ressource hinzu.
     * 
     * @param type Ressourcentyp
     * @param amount Menge, die hinzugefügt wird
     */
    public void addResource(ResourceType type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }

    /**
     * Prüft, ob der Spieler genügend Ressourcen hat, um ein bestimmtes Gebäude zu bauen.
     * 
     * @param buildingType der Gebäudetyp, der gebaut werden soll
     * @return true, wenn genügend Ressourcen vorhanden sind, sonst false
     */
    public boolean hasEnoughResources(BuildingType buildingType) {
        Map<ResourceType, Integer> cost = BuildingCost.getCost(buildingType);
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            if (resources.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Baut ein Gebäude, falls genügend Ressourcen und Bauobjekte verfügbar sind.
     * Zieht dabei die Ressourcen ab und reduziert die Anzahl verfügbarer Bauobjekte.
     * 
     * @param buildingType der Gebäudetyp, der gebaut werden soll
     * @return true, wenn der Bau erfolgreich war, sonst false
     */
    public boolean build(BuildingType buildingType) {
        if (!hasEnoughResources(buildingType)) return false;

        Map<ResourceType, Integer> cost = BuildingCost.getCost(buildingType);
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            resources.put(entry.getKey(), resources.get(entry.getKey()) - entry.getValue());
        }

        switch (buildingType) {
            case ROAD -> {
                if (roads <= 0) return false;
                roads--;
            }
            case SETTLEMENT -> {
                if (settlements <= 0) return false;
                settlements--;
            }
            case CITY -> {
                if (cities <= 0) return false;
                cities--;
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + buildingType);
        } 
        return true;
    }

    /**
     * Gibt die Anzahl der Ressourcen eines bestimmten Typs zurück.
     * 
     * @param type Ressourcentyp
     * @return Menge der Ressourcen dieses Typs
     */
    public int getResourceCount(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }

    /**
     * Berechnet die Siegpunkte basierend auf gebauten Siedlungen und Städten.
     */
    public void calculateVictoryPoints() {
        victoryPoints = 0;
        victoryPoints += settlements * 1;
        victoryPoints += cities * 2;
    }

    /**
     * Gibt die aktuell berechneten Siegpunkte zurück.
     * 
     * @return Siegpunktzahl des Spielers
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }
}