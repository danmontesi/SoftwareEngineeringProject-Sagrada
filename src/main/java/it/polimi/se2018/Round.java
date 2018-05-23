package it.polimi.se2018;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Describes Round behavior. Besides the constructor, the class only contains getters and setters.
 * @author Nives Migotto
 */
public class Round {

    private int roundNumber;
    private Player currentPlayer;
    private Player firstPlayer;
    private HashMap<Player, Integer> countPlayersTurns;
    private int turnCount = 1;
    private ArrayList<Player> gamePlayers;
    private DraftPool draftPool;
    private DiceBag diceBag;

    // TODO: Il costruttore deve Inizializzare i dadi della DraftPool e tutti gli attributi

    /**
     * Constructor: generates a round by instantiating
     * a diceBag
     * a HashMap and turn counter that keep count of the played turns
     * first player to help the rounds transitions
     * @param roundNumber round number
     * @param firstPlayer first player of the round
     * @param gamePlayers list of game players
     * @param diceBag diceBag
     */
    public Round(int roundNumber, Player firstPlayer, ArrayList<Player> gamePlayers, DiceBag diceBag){
        this.roundNumber = roundNumber;
        this.firstPlayer = firstPlayer;
        this.gamePlayers = gamePlayers;
        this.diceBag = diceBag;
        currentPlayer = null;
        countPlayersTurns = new HashMap<>();
        for (int i=0; i<gamePlayers.size(); i++){
            countPlayersTurns.put(gamePlayers.get(i), 0);
        }
        draftPool = new DraftPool(diceBag, gamePlayers.size());
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

    public DraftPool getDraftPool() {
        return draftPool;
    }
}


