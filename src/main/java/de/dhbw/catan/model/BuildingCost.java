package de.dhbw.catan.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Die Klasse BuildingCost liefert die Baukosten für verschiedene Gebäudetypen.
 * 
 * Die Kosten werden als Map von ResourceType auf die benötigte Anzahl angegeben.
 * 
 * Beispiel:
 * - ROAD benötigt 1 Holz (LUMBER) und 1 Ziegel (BRICK)
 * - SETTLEMENT benötigt 1 Holz (LUMBER), 1 Ziegel (BRICK), 1 Wolle (WOOL) und 1 Getreide (GRAIN)
 * - CITY benötigt 3 Getreide (GRAIN) und 3 Erz (ORE)
 */
public class BuildingCost {

    /**
     * Liefert eine Map mit den Ressourcen und Mengen, die zum Bau des übergebenen Gebäudetyps benötigt werden.
     * 
     * @param type Der Typ des Gebäudes (ROAD, SETTLEMENT, CITY)
     * @return Map mit Ressourcen als Schlüssel und benötigter Anzahl als Wert
     */
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
        }

        return cost;
    }
}
