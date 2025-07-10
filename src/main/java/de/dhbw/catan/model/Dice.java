package de.dhbw.catan.model;

import java.util.Random;

/**
 * Klasse zur Darstellung von zwei Würfeln und zum Ausführen eines Wurfes.
 * 
 * Es werden zwei sechsseitige Würfel simuliert und die Summe der Augenzahlen
 * zurückgegeben.
 */
public class Dice {

    private static final int MAX_DICE_VALUE = 6;
    private Random random;
    
    /**
     * Konstruktor, der die Zufallszahlengenerierung initialisiert.
     */
    public Dice() {
        random = new Random();
    }

    /**
     * Führt einen Würfelwurf mit zwei sechsseitigen Würfeln aus und gibt die
     * Summe der beiden Würfelergebnisse zurück.
     * 
     * @return Summe der Augenzahlen von zwei Würfeln (2 bis 12)
     */
    public int rollDice() {
        return random.nextInt(MAX_DICE_VALUE) + 1 + random.nextInt(MAX_DICE_VALUE) + 1;
    }
}
