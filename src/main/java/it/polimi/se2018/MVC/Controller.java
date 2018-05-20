package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.network.ClientConnection;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.toolcards.CircularCutter;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {

    /**
     * This is the main controller of the game
     * It lives in the Server side, and contains all references to Players and their Connections
     *
     * Has a link to
     * - Model (it modifies the model)
     * - Connections with the Client (to handle ServerToClientCommands)
     */
    private Model model; //Always updated through notify() method of the Model, called every time it is modified

    private HashMap<Player, Connection> playerClientConnectionMap;
    private ArrayList<Player> orderedPlayers;

    private ArrayList<ClientConnection> connectedClients;
    private ArrayList<ClientConnection> disconnectedClients;

    private int roundNumber;
    private Server server;

    public Controller(Model model, ArrayList<Player> orderedPlayers, ArrayList<ClientConnection> connectedClients, int roundNumber, Server server) {
        this.model = model;
        this.orderedPlayers = orderedPlayers;
        this.connectedClients = connectedClients;
        this.roundNumber = roundNumber;
        this.server = server;
    }


    /**
     * do per scontato che il Model sia già stato costruito
     * @param orderedPlayers
     */

    /**
     * Create a match with 4 players. It calls initializePlayers() and setInitialPlayer()
     */
    public void initializeGame() {

    }

    /**
     * Call methods for starting the board of each player
     * including:
     * choose the WPattern Card
     * ...
     */
    public void initializePlayers() {

    }

    /**
     * Inizialise the next player for the turn
     * eventually, if every player played twice, it calls passRound()
     */
    public void passTurn() {
    }

    /**
     * Initialize next Round.
     * eventually calls endGame if rounds are 10
     */
    public void passRound() {
    }


    /**
     * Calls method for initializing a new round
     * -> send to all players a new RefreshBoardCommand
     * -> extract Dices ..
     * -> set first player and
     */
    public void startNewRound() {

        // inglobo metodo del MODEL
        //TODO Controllo che i rounds fatti siano meno di 10, se no chiamo un nuovo round
        // dalla lista dei round sul Model, e gli assegno dei Dice.
        /*
    public void nextRound() {
        if (gameRounds.size() == 0) {
            //notifyWinner();
        } else {
            currentRound = gameRounds.remove(0);
            currentRound.nextPlayer();
        }
    }
    }
    */
    }
        /**
         * Has to start a turn of a new player in a round.
         * if the round has still 2*n turns played, i have to call starNewRound()
         */


    public void startNewTurn(){
        // calls nextPlayer
    }

    /**
     *      *  Assign next currentPlayer to round
     *      *  if currentPlayer == null, currentPlayer will be the first player
     *      *  non serve l'eccezione del doppio turno in quanto può essere applicata solo nella seconda metà del round
     *      *  se siamo nella prima metà del round currentPlayer sarà il successivo nella lista di giocatori;
     *      *
     *      *  se siamo nella seconda metà del round currentPlayer sarà il precedente nella lista di giocatori;
     *      *  se ha già giocato 2 turni si passerà al giocatore ancora dopo
     *      *

     */

    public void nextPlayer() {
        /*
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
    */
    }


    /**
     * Calculate total score of players and determine who is the winner
     */
 /*   public void endGame() {
        // Calls calculatePlayersScore for each player
        //notifies to players if they win or lose
    }

    public int calculatePlayerScore(Player player){
        // PublicObj + PrivateObj - Penalization
    }
*/
    /**
     * Generally returns true if need ad allowance to perform a command
     * @return
     */
    public boolean isAllowed(){
        return true;
    }

    public void sendCommandToAllPlayers(ServerToClientCommand command){

    }

    public void sendCommandToPlayer(Player player, ServerToClientCommand command){

    }

    public void sendCommandToAllPlayersExceptGiven(ServerToClientCommand command, Player p){

    }

    public void notifyPlayerDisconnection(Player player){

    }

    /**
     * update is called by the Model only
     * i have to update every client's model
     */
    @Override
    public void update(Observable o, Object arg) {
        // to all ClientConnections->
        // sendToAllPlayers( new refreshBoardCommand();
    }

    public void notifyWinner(Player p){

    }

    public void notifyLoser(Player p){

    }


    // NETWORK METHODS
    public void run(){

    }

    public void addDisconnectedClient(ClientConnection connection){

    }

    public void reconnectDisconnectedClient(ClientConnection connection){

    }

    /**
     * Those are methods that apply commands arriving from the Client
     * There are 1 method for every class for every command
     *
     * If a command is invalid, I catch an exception and send to the client itself a new AskMoveCommand()
     */
    public void applyClientCommand(ChosenToolCardCommand command){

    }

    public void applyClientCommand(ChosenWindowPatternCardCommand command){

    }


    // TODO : All methods connected to the using of Tool

    public void useTool(CircularCutter toolCard){
        // Applied automatically to the CurrentPlayer
    }
    //TODO ... For every Tool card
/*
    public void startGame(ArrayList<Player> orderedPlayers) {
        // assegno i players al model e l'ordine di gioco
        this.orderedPlayers = orderedPlayers;

        // start i game di tutti (i players sono già salvati, e di standard il primo ad effettuare il turno sarà il primo che si è connesso
        view.initializeAllGames(orderedPlayers.get(0));

        for (Player p : orderedPlayers) { //TODO concurrent
            if (p == orderedPlayers.get(0)) {
                model.nextRound();
                view.startTurn(p);
            } else
                view.waitForYourTurn(p);
        }

        playerViewHashMap.get(playerToInitializeGame).initializeGame();
    }
        // Methods that calls the Client's View...
        // decido chi comincia per primo e chiamo il metodo del suo view


        // Inizializzo la schermata dei giocatori

        // faccio schegliere la carta Pattern a ogni giocatore

        //viewPlayerHashMap.put(one, new Player())...

        // la assegno







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
                    (model.getExtractedToolCard.get(numberOfToolToUse).getTokenCounts()>0 ? 1: 2)) {
                //posso utilizzarlo
                player.decreaseTokens(model.getExtractedToolCard.get(numberOfToolToUse).getTokenCounts()>0 ? 1: 2);
                view.notifyAbleToUseTool(model.getExtractedToolCard.get(numberOfToolToUse), player);
            }
            else {
                view.notifyNotAbleToUseTool(player);
            }
    }


    */


}