package it.polimi.se2018;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.se2018.Model.instance;


public class Round {

    private int roundNumber;
    private Player currentPlayer = null;
    private Player firstPlayer;
    private HashMap<Player, Integer> countPlayersTurns;
    private int turnCount = 1;
    private ArrayList<Player> gamePlayers;
    private DraftPool draftPool;
    private DiceBag diceBag;

    // TODO: Il costruttore deve Inizializzare i dadi della DraftPool e tutti gli attributi
    //
    public Round(int roundNumber, Player firstPlayer, ArrayList<Player> gamePlayers, DiceBag diceBag){
        this.roundNumber = roundNumber;
        this.firstPlayer = firstPlayer;
        this.gamePlayers = gamePlayers;
        this.diceBag = diceBag;
        countPlayersTurns = new HashMap<>();
        for (int i=0; i<gamePlayers.size(); i++){
            countPlayersTurns.put(gamePlayers.get(i), 0);
        }
        draftPool = new DraftPool(diceBag, gamePlayers.size());
    }

    /**
     *  Assign next currentPlayer to round
     *  if currentPlayer == null, currentPlayer will be the first player
     *  non serve l'eccezione del doppio turno in quanto può essere applicata solo nella seconda metà del round
     *  se siamo nella prima metà del round currentPlayer sarà il successivo nella lista di giocatori;
     *
     *  se siamo nella seconda metà del round currentPlayer sarà il precedente nella lista di giocatori;
     *  se ha già giocato 2 turni si passerà al giocatore ancora dopo
     */
    public void nextPlayer() {
        int i=0;
        while (!firstPlayer.getUsername().equals(gamePlayers.get(i).getUsername())){
            i++;
        }
        if (currentPlayer == null){
            currentPlayer = firstPlayer; //primo giocatore
        } else if (turnCount == 2*gamePlayers.size()){ //se siamo a fine round
            firstPlayer = gamePlayers.get(i+1); //predispone firstPlayer per il prossimo round
            currentPlayer = null; //curretnPlayer torna ad essere null (così al prossimo round gli verrà assegnato firstPlayer)
            instance.nextRound(); //chiama il round successivo
        } else if (turnCount < gamePlayers.size()){ //se siamo nella prima metà del round/il primo turno di ogni giocatore
            if (gamePlayers.get(i+1) == null){ //se siamo arrivati a fine lista gamePlayers (lista dei giocatori)
                currentPlayer = gamePlayers.get(0); //currentPlayer sarà il primo giocatore in gamePlayers
            } else {
                currentPlayer = gamePlayers.get(i+1); //currentPlayer sarà il successivo giocatore in gamePlayers
            }
        } else if (turnCount > gamePlayers.size()){ //se siamo nella seconda metà del round/il secondo turno di ogni giocatore
            if (gamePlayers.get(i-1) == null){ //se siamo arrivati all'inizio di gamePlayers
                currentPlayer = gamePlayers.get(gamePlayers.size()); //currentPlayer sarà l'ultimo giocatore in gamePlayers (dato che stiamo andando a ritroso)
            } else {
                currentPlayer = gamePlayers.get(i-1); //altrimenti currentPlayer sarà il giocatore precedente in gamePlayers
            }
            if (countPlayersTurns.get(currentPlayer) > 1){ //se il currentPlayer designato ha già giocato 2 turni
                if (gamePlayers.get(i-1) == null){ //si passa a quello ancora dopo, con i soliti controlli di fine lista
                    currentPlayer = gamePlayers.get(gamePlayers.size());
                } else {
                    currentPlayer = gamePlayers.get(i-1);
                }
            }
        }
        countPlayersTurns.put((currentPlayer), countPlayersTurns.get(currentPlayer)+1);
        turnCount ++;
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
}


