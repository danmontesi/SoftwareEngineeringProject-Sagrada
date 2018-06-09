package it.polimi.se2018.network.server;

import it.polimi.se2018.*;
import it.polimi.se2018.MVC.View;
import it.polimi.se2018.utils.ControllerServerInterface;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.client_to_server_command.*;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;
import it.polimi.se2018.toolcards.ToolCard;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//TODO has to extend controllerInterface to avoid access of Controller from Client
public class Controller implements Observer, ControllerServerInterface { //Observer perchè osserva la View tramite le classi di mezzo (ClientConnection)

    /**
     * This is the main controller of the game
     * It lives in the Server side, and contains all references to Players and their Connections
     *
     * Has a link to
     * - Model (it modifies the model)
     * - Connections with the Client (to handle ServerToClientCommands)
     */

    private Model model;

    private HashMap<String, Player> usernamePlayerMap;

    private HashMap<String, View> userViewMap;

    private ArrayList<Player> orderedPlayers;

    private ArrayList<Player> unitializedOrderedPlayers;

    /**
     * ArrayList that contains the ordered players that has to play
     * is created by the model in its constructor
     */
    private ArrayList<ArrayList<Player>> orderedRoundPlayers;

    private ArrayList<Player> currentRoundOrderedPlayers;

    /**
     * Represent current player. That is necessary to know which is the player i'm expecting an answer
     */
    private Player currentPlayer;
    private boolean hasUsedTool;
    private boolean hasPerformedMove;

    private ArrayList<ClientConnection> disconnectedClients;

    private int roundNumber;//Maybe to be deleted, substituted by orderedRoundPlayers.size()

    private Server server;

    /**
     * The controller receives a list of the Usernames of the connected players.
     * The controller receives the connected Connection though the static HashMap of the Server
     * The connection between users and Connection is connected in the Server only, so when there is a disconnection the server
     * knows. Server is static, so has reasy access from the Controller
     * @param usernameList
     */
    public Controller(ArrayList<String> usernameList) {
        usernamePlayerMap = new HashMap<>();
        unitializedOrderedPlayers = new ArrayList<>();
        userViewMap = new HashMap<>();
        // I have to create the list that connects Usernames and Players and VirtualViews
        for (String username : usernameList){
            Player temp = new Player(username);
            unitializedOrderedPlayers.add(temp);
            usernamePlayerMap.put(username, temp);
        }

        this.model = new Model(unitializedOrderedPlayers);

        for (String username : usernameList) {
            View tempView = new VirtualView(this, model, username);
            userViewMap.put(username, tempView);
            model.register(tempView);
        }

        this.roundNumber = 0;
        this.orderedPlayers= new ArrayList<>();

        // Now I will start each player's View
        initializeGame();
    }

    /**
     * It calls initializePlayers() and setInitialPlayer()
     */
    private void initializeGame() {
        //Let people chose their Wpc, and call a method that waits until all chose theirs.
        //Once i receive all -> move to orderedPlayers List
        ArrayList<WindowPatternCard> localWpc;
        //Gives to each a player 4 WindowPatternCard to choose from
        for(Player p: unitializedOrderedPlayers){
            StringBuilder localNamesWpc = new StringBuilder();
            // I give the cards (in strings) to the command, and to the method that waits until all players finishes to chose
            localWpc = model.extractWindowPatternCard();
            System.out.println("invio command CHOOSEWPC a player:" + p.getUsername());
            userViewMap.get(p.getUsername()).chooseWindowPatternCardMenu(localWpc);
        }
    }


    /**
     * Initializes all Lists of players for each round, ordered.
     */
    private void assignRoundPlayers(ArrayList<Player> orderedPlayers){
        orderedRoundPlayers = new ArrayList<>();
        if (orderedPlayers.size()==4) {
            Player c0 = orderedPlayers.get(0);
            Player c1 = orderedPlayers.get(1);
            Player c2 = orderedPlayers.get(2);
            Player c3 = orderedPlayers.get(3);
            ArrayList<Player> temp0 = new ArrayList<>();
            ArrayList<Player> temp1 = new ArrayList<>();
            ArrayList<Player> temp2 = new ArrayList<>();
            ArrayList<Player> temp3 = new ArrayList<>();
            temp0.add(c0);temp0.add(c1);temp0.add(c2);temp0.add(c3);temp0.add(c3);temp0.add(c2);temp0.add(c1);temp0.add(c0);
            temp1.add(c1);temp1.add(c2);temp1.add(c3);temp1.add(c0);temp1.add(c0);temp1.add(c3);temp1.add(c2);temp1.add(c1);
            temp2.add(c2);temp2.add(c3);temp2.add(c0);temp2.add(c1);temp2.add(c1);temp2.add(c0);temp2.add(c3);temp2.add(c2);
            temp3.add(c3);temp3.add(c0);temp3.add(c1);temp3.add(c2);temp3.add(c2);temp3.add(c1);temp3.add(c0);temp3.add(c3);
            orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);orderedRoundPlayers.add(temp2);
            orderedRoundPlayers.add(temp3);orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);
            orderedRoundPlayers.add(temp2);orderedRoundPlayers.add(temp3);orderedRoundPlayers.add(temp0);
            orderedRoundPlayers.add(temp1);
        }
        else if (orderedPlayers.size()==3) {
            Player c0 = orderedPlayers.get(0);
            Player c1 = orderedPlayers.get(1);
            Player c2 = orderedPlayers.get(2);
            ArrayList<Player> temp0 = new ArrayList<>();
            ArrayList<Player> temp1 = new ArrayList<>();
            ArrayList<Player> temp2 = new ArrayList<>();
            temp0.add(c0);temp0.add(c1);temp0.add(c2);temp0.add(c2);temp0.add(c1);temp0.add(c0);
            temp1.add(c1);temp1.add(c2);temp1.add(c0);temp1.add(c0);temp1.add(c1);temp1.add(c2);
            temp2.add(c2);temp2.add(c0);temp2.add(c1);temp2.add(c1);temp2.add(c0);temp2.add(c2);
            orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);orderedRoundPlayers.add(temp2);
            orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);orderedRoundPlayers.add(temp2);
            orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);orderedRoundPlayers.add(temp2);
            orderedRoundPlayers.add(temp0);
        }
        else if (orderedPlayers.size()==2) {
            Player c0 = orderedPlayers.get(0);
            Player c1 = orderedPlayers.get(1);
            ArrayList<Player> temp0 = new ArrayList<>();
            ArrayList<Player> temp1 = new ArrayList<>();
            temp0.add(c0);temp0.add(c1);temp0.add(c1);temp0.add(c0);
            temp1.add(c1);temp1.add(c0);temp1.add(c0);temp1.add(c1);
            orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);orderedRoundPlayers.add(temp0);
            orderedRoundPlayers.add(temp1);orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);
            orderedRoundPlayers.add(temp0);orderedRoundPlayers.add(temp1);orderedRoundPlayers.add(temp0);
            orderedRoundPlayers.add(temp1);
        }
        else{
            System.out.println("problema");
        }
    }


    /**
     * Call methods for starting the board of each player
     * including:
     * choose the WPattern Card
     * ...
     */
    private void startGame() {
        assignRoundPlayers(orderedPlayers);
        startNewRound();
    }


    private void endGame(){
        //For each player, create an HashMap calling on every player a Win/Lose command
        HashMap<Player, Integer> playerScoreMap = new HashMap<>();
        Integer tempScore;
        for (Player player : orderedPlayers) {
            tempScore=0;
            for (PublicObjectiveCard card : model.getExtractedPublicObjectiveCard()){
                tempScore+= card.calculateScore(player.getWindowPatternCard());
            }
            tempScore-= penalityScore(player.getWindowPatternCard());
            playerScoreMap.put(player, tempScore);
            //TODO: Manca altro da calcolare? come facciamo con i punteggi sul tabellone?
        }

        LinkedHashMap<Player, Integer> orderedPlayerScores = new LinkedHashMap<>();
        for (int i = 0; i < orderedPlayers.size(); i++) {
            int maxValueInMap=(Collections.max(playerScoreMap.values()));// This will return max value in the Hashmap
            for (Map.Entry<Player, Integer> entry : playerScoreMap.entrySet()) {  // Iterates through hashmap
                if (entry.getValue() == maxValueInMap) {
                    orderedPlayerScores.put(entry.getKey(), playerScoreMap.remove(entry.getKey()));   // Assign a new Entry in the LinkedHashMap
                }
                break; //Exit for new research of max
            }
        }
        sendResultToPlayers(orderedPlayerScores);
    }

    private void sendResultToPlayers(LinkedHashMap<Player, Integer> orderedPlayerScores) {
        Set set = orderedPlayerScores.entrySet();
        // Get an iterator
        String scores = "";

        ArrayList<String> scoresList = new ArrayList<>();

        Iterator i0 = set.iterator();
        while (i0.hasNext()) {
            Map.Entry entry = (Map.Entry) i0.next();
            // Player Username + player score //TODO Controlla se funziona!!
            scoresList.add(entry.getKey().toString() + "," + entry.getValue().toString());
        }

        //DATE COME ARRAYLIST
        Iterator i = set.iterator();
        Integer counter=0;
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            if (counter == 0) {
                userViewMap.get(me).winMessage(scoresList);
            }
            else {
                userViewMap.get(me).loseMessage(counter, scoresList);
            }
            counter++;
        }
    }

    private Integer penalityScore(WindowPatternCard card){
        //Counting all empty blocks
        int tempScore=0;
        for(Cell c : card.getSchema()){
            try {
                tempScore += c.getAssociatedDie()==null? 1 : 0;
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
        }
        return tempScore;
    }



    /**
     * Calls method for initializing a new round
     * - send to all players a new RefreshBoardCommand
     * - extract Dice ..
     * - set first player and
     */
    private void startNewRound() {
        if (orderedRoundPlayers.isEmpty()){
            endGame();
        }
        else{
            model.setDraftPool(model.extractDraftPoolDice(orderedPlayers.size()));
            model.setGamePlayers(orderedPlayers); //Used for notify eventual modifics of wpcs to the Players
            //initialize DraftPool
            //Start a new round-> pick the first of the RoundList
            currentRoundOrderedPlayers=orderedRoundPlayers.remove(0);
            currentPlayer=currentRoundOrderedPlayers.remove(0);
            // DA FARE???? TODO MODIFICA
            //first player
            System.out.println("Prima di chiamare StartTurnMenu");
            hasPerformedMove=false;
            hasUsedTool=false;
            userViewMap.get(currentPlayer.getUsername()).startTurnMenu();
        }
    }
    /**
     * Has to start a turn of a new player in a round.
     * if the round has still 2*n turns played, i have to call starNewRound()
     */
    private void startNewTurn(){
        //Case that everybody has played in the round
        if (currentRoundOrderedPlayers.isEmpty()){
            startNewRound();
        }
        else{
            currentPlayer=currentRoundOrderedPlayers.remove(0);
            userViewMap.get(currentPlayer.getUsername()).startTurnMenu();
        }
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
     * @return true if the currentPlayer is the one given
     */
    public boolean isAllowed(Player player){
        return player==currentPlayer;
    }
/*
    private void sendCommandToAllPlayers(ServerToClientCommand command){
        for (String username : usernamePlayerMap.keySet() ) {
            if (Server.getConnectedClients().keySet().contains(username))
                Server.getConnectedClients().get(username).notifyClient(command);
        }
    }

    private void sendCommandToPlayer(Player player, ServerToClientCommand command){
        System.out.println("invio comando da controller: " + command.getMessage());
        Server.getConnectedClients().get(player.getUsername()).notifyClient(command);
    }


*/

/*
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

    /**
     * The choice of wpc is always right
     * That method removes the player that chooses its card, and moves it to the List of initialized player.
     * Checks if all players are initialized to call the next Controller Method
     * @param playerUsername the username who is applying the command
     * @param command the coming command
     */
    public synchronized void applyCommand(String playerUsername, ChosenWindowPatternCard command){
        ParserWindowPatternCard parser = null;
        try {
            parser = new ParserWindowPatternCard();
            usernamePlayerMap.get(playerUsername).setWindowPatternCard(parser.parseCardByName(command.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        unitializedOrderedPlayers.remove(usernamePlayerMap.get(playerUsername)); //TODO what happens can't find it?
        orderedPlayers.add(usernamePlayerMap.get(playerUsername));


        if (unitializedOrderedPlayers.isEmpty()) {
            startGame();
        }
    }

    /**
     * The first word is the
     * @param playerUsername
     * @param command
     */
    //TODO: vedi se è necessario controllare che il player sia Current o non posso fare altre mosse e automaticamente il player da cui ricevo il comando è current
    public synchronized void applyCommand(String playerUsername, MoveChoiceToolCard command){
        Player current= usernamePlayerMap.get(playerUsername);
        Integer usedToolNumber = command.getNumberChosen(); //Referes to the toolcard used
        ToolCard chosen = model.getExtractedToolCard().get(usedToolNumber);
        int requiredTokens=1;
        if (chosen.getTokenCount()>0){
            requiredTokens=2;
        }
        if (current.getTokens()>requiredTokens){
            current.decreaseTokens(requiredTokens);
            chosen.increaseTokens(requiredTokens);
            if (chosen.getName().equals("FirmPastryThinner")){
                // Extract a die
                Die die = model.getDiceBag().extractDie(); //TODO Dove tengo questa informazione? mi fido del controller?
                userViewMap.get(currentPlayer.getUsername()).firmPastryThinnerMenu(die.getColor().toString() , die.getValue() );
            }
            else if (chosen.getName().equals("FirmPastryBrush")){
                userViewMap.get(currentPlayer.getUsername()).firmPastryBrushMenu(ThreadLocalRandom.current().nextInt(1, 7));
            }
            else{
                userViewMap.get(currentPlayer.getUsername()).correctUseTool(usedToolNumber);
            }
        }
        else{
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You haven't enough tokens to use this tool");
            userViewMap.get(currentPlayer.getUsername()).startTurnMenu();
        }

    }


    public void applyCommand(String playerUsername, MoveChoiceDicePlacement command){
        //TODO Controllo username di current RICORDANDO CHE I VERI PLAYER SONO SALVATI SU orderedPlayers
        try {
            Die toPlace = model.getDraftPool().getDie(command.getDieDraftPoolPosition());
            boolean exit = usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                    .placeDie(toPlace, command.getDieSchemaRowPosition(), command.getDieSchemaColPosition(), true, true, true);
            if (!exit){
                throw new EmptyCellException(); //TODO Funziona? deve eseguire le linee di codice sotto
            }
        }catch (EmptyCellException e){
            e.printStackTrace();
            System.out.println("Empty cell, sending an IncorrectMoveCommand");
            userViewMap.get(playerUsername).invalidActionMessage("The Draftpool cell you selected is empty, try again");
            userViewMap.get(playerUsername).continueTurnMenu(!hasPerformedMove,!hasUsedTool);
        }
        //When I arrive here, the move is already performed
        System.out.println("Mossa applicata correttamente");
        //TODO devo rimuovere il dado mosso dalla draftpool!
        //model.getDraftPool().getDie(command.getDieDraftPoolPosition());

        hasPerformedMove=true;
        model.setGamePlayers(orderedPlayers);
        userViewMap.get(playerUsername).continueTurnMenu(!hasPerformedMove, !hasUsedTool);
    }

    public void applyCommand(String playerUsername, MoveChoicePassTurn command){
        startNewTurn();
    }


    //Those methods represents the view that uses correctly a tool.
    // The server has to validate the move and edit the model, if the move is correct
    // else, has to call a new Request of re-use of that tool, re-sending a event of AllowedUseToolCommand(usedToolNumber)

    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername, UseToolCopperFoilReamer command){
        String message = command.getMessage();
    }


    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    //MOSSA SENZA RESTRIZIONE POSIZIONE E DEVONO ESSERE NON ADIACENTI
    public void applyCommand(String playerUsername,UseToolCorkLine command){
        //WindowPatternCard card = currentPlayer.getWindowPatternCard().placeDie(model.getDraftPool().getDie(command.getDieDraftPoolPosition()),  )
        //boolean correctMove = usernamePlayerMap.get(playerUsername).getWindowPatternCard().placeDie()
    }
    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername ,UseToolDiamondSwab command){

    }
    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername ,UseToolEglomiseBrush command){

    }

    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     * BRUSH: decide the new value
     */
    public void applyCommand(String playerUsername ,UseToolFirmPastryBrush command){
        String[] words = command.getMessage().split(" ");
        if (words[0].equals("MOVE")){
            //TODO edit the schema, perform the move with the new value of the dice of the DraftPool
        }
        else{ //default: draftpool
            //TODO just change the value die in the DraftPool, call the menu of Player
        }
    }

    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     * THINNER: die from DiceBag
     */
    public void applyCommand(String playerUsername , UseToolFirmPastryThinner command){
        String[] words = command.getMessage().split(" ");
        if (words[0].equals("MOVE")){
            //TODO edit the model, reinsert the die in the draftpool in the dicebag
        }
        else{ //default: draftpool
            //TODO change the die in the DraftPool, reinsert the old one in the diceBag
        }

    }

    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername ,UseToolGavel command){

    }
    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername ,UseToolLathekin command){

    }
    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername ,UseToolManualCutter command){

    }
    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername ,UseToolRoughingForceps command){

    }
    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */

    public void applyCommand(String playerUsername ,UseToolWheelsPincher command){

    }

    public void applyCommand(String playerUsername ,ClientToServerCommand command){
        System.out.println("You shouldn't be here");
    }


    @Override
    public void update(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.visit(this);
    }
}