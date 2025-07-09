package de.dhbw.catan.model;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import lombok.Data;

/**
 * Repräsentiert einen Knotenpunkt (Ecke) auf dem Spielfeld.
 * Jeder Knoten hat Koordinaten, einen möglichen Besitzer und einen Gebäudetyp.
 */
@Data
public class Node {
    private final double x;
    private final double y;
    private Player owner;
    private BuildingType buildingType;

    private static final double TOLERANCE = 5.0;

    /**
     * Konstruktor für einen Knoten mit X- und Y-Koordinaten.
     * 
     * @param x X-Koordinate des Knotens
     * @param y Y-Koordinate des Knotens
     */
    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vergleicht zwei Knoten basierend auf der Position mit einer Toleranz.
     * 
     * @param obj anderer Knoten
     * @return true, wenn die Knoten nahe genug beieinander liegen, sonst false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Node))
            return false;
        Node other = (Node) obj;
        return Math.abs(this.x - other.x) <= TOLERANCE &&
                Math.abs(this.y - other.y) <= TOLERANCE;
    }

    /**
     * Berechnet den Hashcode des Knotens basierend auf den gerundeten Koordinaten.
     * Die Koordinaten werden dabei auf ein Raster der Größe {@link #TOLERANCE}
     * gerundet, sodass Knoten mit nahe beieinander liegenden Positionen denselben Hashcode erhalten.
     * Dies sorgt für Konsistenz mit der {@link #equals(Object)}-Methode, die eine
     * Toleranz beim Vergleich verwendet.
     *
     * @return ein Hashcode, der Knoten mit ähnlichen Positionen gruppiert
     */
    @Override
    public int hashCode() {
        int precision = (int) TOLERANCE;
        int xRounded = (int) (x / precision);
        int yRounded = (int) (y / precision);
        int result = 17;
        result = 31 * result + xRounded;
        result = 31 * result + yRounded;
        return result;
    }

    /**
     * Prüft, ob der Knoten bereits von einem Spieler besetzt ist.
     * 
     * @return true, wenn besetzt, sonst false
     */
    public boolean isOccupied() {
        return owner != null;
    }

    /**
     * Prüft, ob ein übergebener Knoten Nachbar dieses Knotens ist.
     * Dabei wird die Position relativ zu diesem Knoten anhand vordefinierter
     * Offsets und
     * den Bildpositionen im übergebenen Pane geprüft.
     * 
     * @param node      zu prüfender Nachbarknoten
     * @param boardPane Pane, das die Bild-Elemente der Ecken enthält
     * @return true, wenn der Knoten ein Nachbar ist, sonst false
     */
    public boolean checkNeighbor(Node node, Pane boardPane) {
        int baseX = (int) Math.round(this.x);
        int baseY = (int) Math.round(this.y);

        int[][] offsets = {
                { 0, -93 }, { 81, 47 }, { -81, 47 },
                { 81, -47 }, { -81, -46 }, { 0, 94 }
        };

        for (int[] offset : offsets) {
            int neighborX = baseX + offset[0];
            int neighborY = baseY + offset[1];

            for (javafx.scene.Node n : boardPane.getChildren()) {
                if (n instanceof ImageView && "corner".equals(n.getUserData())) {
                    ImageView img = (ImageView) n;
                    int imgX = (int) Math.round(img.getX() + 30);
                    int imgY = (int) Math.round(img.getY() + 30);
                    int tolerance = 10;

                    if (Math.abs(imgX - neighborX) <= tolerance && Math.abs(imgY - neighborY) <= tolerance) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
