package de.dhbw.catan.model;

import java.util.Random;

public class Dice {

    private static final int MAX_DICE_VALUE = 6;
    private Random random;
    
    public Dice() {
        random = new Random();
    }

    public int rollDice() {
        return random.nextInt(MAX_DICE_VALUE) + 1 + random.nextInt(MAX_DICE_VALUE) + 1;
    }
}
