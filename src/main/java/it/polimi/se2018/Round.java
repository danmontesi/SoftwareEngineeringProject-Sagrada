package it.polimi.se2018;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.se2018.Model.instance;


public class Round {

    private int roundNumber;
    private Player currentPlayer = null;
    private Player firstPlayer;
    private HashMap<String, Integer> countPlayersTurns;
    private int turnCount = 1;
    private ArrayList<Player> gamePlayers;
    private DraftPool draftPool;

    public Round(){
        
    }

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

    public void setFirstPlayer(Player firstPlayer) { this.firstPlayer = firstPlayer; }

    public void setRoundNumber(int roundNumber) { this.roundNumber = roundNumber; }

    /*
    public HashMap<String, Integer> getCountPlayersTurns() {
        return countPlayersTurns;
    }
    */
}


