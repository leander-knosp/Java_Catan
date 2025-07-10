package de.dhbw.catan.model;

/**
 * Definiert die verschiedenen Typen von Spielfeld-Kacheln (Tiles) auf dem Spielbrett.
 * 
 * Die Typen entsprechen unterschiedlichen Ressourcenfeldern oder speziellen Feldern:
 * - OCEAN: Wasserfeld (Spielfeldrand)
 * - DESERT: Wüstenfeld, produziert keine Ressourcen
 * - PASTURES: Weideland (Wolle)
 * - FOREST: Wald (Holz)
 * - MOUNTAINS: Berge (Erz)
 * - HILLS: Hügel (Lehm)
 * - FIELDS: Felder (Getreide)
 */
public enum TileType {
    OCEAN, DESERT, PASTURES, FOREST, MOUNTAINS, HILLS, FIELDS;

    /**
     * Wandelt den TileType in den entsprechenden ResourceType um.
     * Für Spezialtypen wie OCEAN und DESERT gibt es keinen ResourceType,
     * daher wird in diesen Fällen null zurückgegeben.
     * 
     * @return Der entsprechende ResourceType oder null, falls keiner vorhanden ist.
     */
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
