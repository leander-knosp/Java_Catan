package de.dhbw.catan.model;

import java.util.random.Random;

public class Dice {

    private static final int MAX_DICE_VALUE;
    private Random random
    
    public dice() {
        MAX_DICE_VALUE = 13;
        random = new Random();
    }

    public int rollDice() {
        return random.nextInt(MAX_DICE_VALUE) + 2;
    }
}
