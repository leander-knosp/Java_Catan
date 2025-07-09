package de.dhbw.catan.model;
import java.util.Random;

public class Dice {

    private static final int MAX_DICE_VALUE = 6;
    private Random random;
    private int firstDice;
    private int secondDice;

    public Dice() {
        random = new Random();
    }

    public int rollDice() {
        firstDice = random.nextInt(MAX_DICE_VALUE) + 1;
        secondDice = random.nextInt(MAX_DICE_VALUE) + 1;
        return firstDice + secondDice;
    }

    public int getFirstDie() {
        return firstDice;
    }

    public int getSecondDie() {
        return secondDice;
    }
}

