package it.polimi.se2018;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.se2018.Model.instance;


public class Round {

    private DraftPool draftPool;
    private int roundNumber;
    private Player currentPlayer = null;
    private Player firstPlayer;
    private HashMap<String, Integer> countPlayersTurns;
    private int turnCount = 1;
    private ArrayList<Player> gamePlayers;
    private DiceBag diceBag;

/**
 *  Assign next currentPlayer to round
 *  if currentPlayer == null, currentPlayer will be the first player
 */
    public void nextPlayer() {
        int i=0;
        while (!firstPlayer.getUsername().equals(gamePlayers.get(i).getUsername())){
            i++;
        }
        if (currentPlayer == null){
            currentPlayer = firstPlayer;
        } else if (turnCount == 2*gamePlayers.size()){
            firstPlayer = gamePlayers.get(i+1);
            currentPlayer = null;
            instance.nextRound();
        } else if (turnCount < gamePlayers.size()){
            if (gamePlayers.get(i+1) == null){
                currentPlayer = gamePlayers.get(0);
            } else {
                currentPlayer = gamePlayers.get(i+1);
            }
            if (countPlayersTurns.get(currentPlayer.getUsername()) > 1){
                currentPlayer = gamePlayers.get(i+1);
            }
        } else if (turnCount > gamePlayers.size()){
            if (gamePlayers.get(i-1) == null){
                currentPlayer = gamePlayers.get(gamePlayers.size());
            } else {
                currentPlayer = gamePlayers.get(i-1);
            }
        }
    }

    /**
     * Assign the draftPool at the very beginning of the round
     * <p>
     * meanwhile, the round is built when the game starts
     */
    public void setDice() {
        int n = 2*gamePlayers.size()+1;
        for (int i=0; i<n; i++){
            draftPool.placeDie(diceBag.extractDie());
        }
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

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /*
    public HashMap<String, Integer> getCountPlayersTurns() {
        return countPlayersTurns;
    }
    */
}


