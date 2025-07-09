package de.dhbw.catan.model;

import java.util.EnumMap;
import java.util.Map;

import lombok.Data;


@Data
public class Player {
    private final String name;
    private final String color;
    private final Map<ResourceType, Integer> resources;
    private int roads;
    private int settlements;
    private int cities;
    private int victoryPoints;

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

    public String getColor() {
        return this.color;
    }

    public void addResource(ResourceType type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }

    public boolean hasEnoughResources(BuildingType buildingType) {
        Map<ResourceType, Integer> cost = BuildingCost.getCost(buildingType);
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            if (resources.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

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

    public int getResourceCount(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }

    public void calculateVictoryPoints() {
        victoryPoints = 0;
        victoryPoints += settlements * 1;
        victoryPoints += cities * 2;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}