package it.polimi.se2018;

import java.util.ArrayList;
import java.util.HashMap;

public class Round {

    private DraftPool draftPool;
    private int roundNumber;
    private Player currentPlayer;
    private Player firstPlayer;
    private HashMap<String, Integer> countPlayersTurns;
    private int turnCount;


    public void nextPlayer() {

    }

    /**
     * Assign the draftPool at the very beginning of the round
     * <p>
     * meanwhile, the round is built when the game starts
     */
    public void setDice() {

    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public HashMap<String, Integer> getCountPlayersTurns() {
        return countPlayersTurns;
    }
}

