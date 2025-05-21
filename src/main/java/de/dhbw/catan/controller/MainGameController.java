package de.dhbw.catan.controller;
import de.dhbw.catan.model.Dice;
import de.dhbw.catan.model.Board;

import lombok.Data;

import de.dhbw.catan.controller.BuildController;

@Data
public class MainGameController {

    private final Dice dice;
    private Board board;
    private BuildController buildController;

    public MainGameController() {
        this.dice = new Dice();
        this.buildController = new BuildController();
    }

    public void setBuildController(BuildController buildController) {
        this.buildController = buildController;
    }

    public void onRollDice() {
        int number = dice.rollDice();
        System.out.println("Rolled: " + number);
        board.distributeResources(number);
    }

    public void callShowCornerPoints () {
        buildController.showCornerPoints();
    }

    public void callShowEdgePoints () {
        buildController.showEdgePoints();
    }

}
