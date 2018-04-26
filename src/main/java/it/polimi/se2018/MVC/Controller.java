package it.polimi.se2018.MVC;

import it.polimi.se2018.Die;
import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.WindowPatternCard;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Model model;
    private VirtualView view;
    private ArrayList<Player> orderedPlayers;



    public Controller(Model model, CLIView view){
        this.model = model;
        this.view = view;
        view.attach(this);
    }


    /**
     * do per scontato che il Model sia già stato costruito
     * @param orderedPlayers
     */
    public void startGame(ArrayList<Player> orderedPlayers) {
        // assegno i players al model e l'ordine di gioco
        this.orderedPlayers = orderedPlayers;

        // start i game di tutti (i players sono già salvati, e di standard il primo ad effettuare il turno sarà il primo che si è connesso
        view.initializeAllGames(orderedPlayers.get(0));

        for (Player p : orderedPlayers) { //TODO concurrent
            if (p == orderedPlayers.get(0)) {
                model.nextRound();
                view.startTurn(p);
            }
            else
                view.waitForYourTurn(p);
        }

        playerViewHashMap.get(playerToInitializeGame).initializeGame();

        // Methods that calls the Client's View...
        // decido chi comincia per primo e chiamo il metodo del suo view


        // Inizializzo la schermata dei giocatori

        // faccio schegliere la carta Pattern a ogni giocatore

        //viewPlayerHashMap.put(one, new Player())...

        // la assegno


    }

    public void performMoveToServer(Die dieToPlace, int row, int column, Player player) {
        if (model.getCurrentRound.getCurrentPlayer() == player) {
            WindowPatternCard patternCardWhereToPlaceDie = model.getCurrentRound.getCurrentPlayer().getWindowPatternCard();
            if (patternCardWhereToPlaceDie.placeDie(dieToPlace, row, column,
                    false, false, false)) {
                view.notifyCorrectMoveServerToClient(player);
            } else {
                view.notifyIncorrectMoveServerToClient(player);
            }
        } else { // Case it's not player's turn
            view.notifyNotYourTurn();
        }
    }

        public void performToolActionToServer(int numberOfToolToUse, Player player){
            if (player.getTokens() >
                    (model.getExtractedToolCard.get(numberOfToolToUse).getTokenCounts()>0 ? 1: 2)){
                //posso utilizzarlo

                if (patternCardWhereToPlaceDie.placeDie(dieToPlace, row, column,
                        false, false, false)){
                    view.notifyCorrectMoveServerToClient(player);
                }
                else
                {
                    view.notifyIncorrectMoveServerToClient(player);
                }
            }
            else{ // Case it's not player's turn
                view.notifyNotYourTurn();
            }
    }

    @Override
    public void update(Observable o, Object arg) {

    }



    }
}
