package de.dhbw.catan.controller;
import de.dhbw.catan.model.Dice;
import de.dhbw.catan.model.Board;

import lombok.Data;

@Data
public class MainGameController {

    private final Dice dice;
    private Board board;

    public MainGameController() {
        this.dice = new Dice();
    }

    public void onRollDice() {
        int number = dice.rollDice();
        System.out.println("Rolled: " + number);
        board.distributeResources(number);
    }


}
