package de.dhbw.catan.model;

public enum TileType {
    OCEAN, DESERT, PASTURES, FOREST, MOUNTAINS, HILLS, FIELDS;

    public ResourceType toResourceType() {
        return switch (this) {
            case FOREST -> ResourceType.LUMBER;
            case FIELDS -> ResourceType.GRAIN;
            case HILLS -> ResourceType.BRICK;
            case MOUNTAINS -> ResourceType.ORE;
            case PASTURES -> ResourceType.WOOL;
            default -> null;
        };
    }
}
