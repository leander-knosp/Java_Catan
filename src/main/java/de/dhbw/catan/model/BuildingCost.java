package de.dhbw.catan.model;

import java.util.EnumMap;
import java.util.Map;

public class BuildingCost {

    public static Map<ResourceType, Integer> getCost(BuildingType type) {
        Map<ResourceType, Integer> cost = new EnumMap<>(ResourceType.class);

        switch (type) {
            case ROAD -> {
                cost.put(ResourceType.LUMBER, 1);
                cost.put(ResourceType.BRICK, 1);
            }
            case SETTLEMENT -> {
                cost.put(ResourceType.LUMBER, 1);
                cost.put(ResourceType.BRICK, 1);
                cost.put(ResourceType.WOOL, 1);
                cost.put(ResourceType.GRAIN, 1);
            }
            case CITY -> {
                cost.put(ResourceType.GRAIN, 2);
                cost.put(ResourceType.ORE, 3);
            }
            case DEVELOPMENT_CARD -> {
                cost.put(ResourceType.WOOL, 1);
                cost.put(ResourceType.GRAIN, 1);
                cost.put(ResourceType.ORE, 1);
            }
        }

        return cost;
    }
}
