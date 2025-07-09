package de.dhbw.catan.model;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.shape.Polygon;
import lombok.Data;

/**
 * Repräsentiert ein Spielfeld (Hexfeld) auf dem Spielbrett.
 * 
 * Ein Tile hat eine Form (Hexagon), einen Typ (Ressource oder Wüste)
 * und eine Zahl (NumberToken) zur Ressourcenverteilung und eine Liste angrenzender Knoten (Nodes).
 */
@Data
public class Tile {
    /**
     * Die grafische Form des Hexfeldes.
     */
    private Polygon shape;

    /**
     * Der Typ des Feldes (z.B. Wald, Hügel, Wüste).
     */
    private TileType type;

    /**
     * Die Nummer des Tokens auf dem Feld, die beim Würfeln für Ressourcenverteilung verwendet wird.
     */
    private int numberToken;

    /**
     * Die Liste der angrenzenden Knoten (Ecken), an denen Siedlungen oder Städte gebaut werden können.
     */
    private List<Node> adjacentNodes;

    /**
     * Konstruktor zur Initialisierung eines Spielfeldes.
     * @param shape Die Form des Hexfeldes.
     * @param type Der Typ des Feldes.
     * @param numberToken Die Zahl für die Ressourcenverteilung.
     */
    public Tile(Polygon shape, TileType type, int numberToken) {
        this.shape = shape;
        this.type = type;
        this.numberToken = numberToken;
        this.adjacentNodes = new ArrayList<>();
    }
}
