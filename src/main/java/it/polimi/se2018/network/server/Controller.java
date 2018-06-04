package it.polimi.se2018.network.server;

import it.polimi.se2018.*;
import it.polimi.se2018.client_to_server_command.*;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.network.client.ClientConnection;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;
import it.polimi.se2018.server_to_client_command.*;
import it.polimi.se2018.toolcards.CircularCutter;
import it.polimi.se2018.toolcards.ToolCard;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Controller{ //Observer perchè osserva la View tramite le classi di mezzo (ClientConnection)

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

    private ArrayList<Player> orderedPlayers;

    private ArrayList<Player> unitializedOrderedPlayers;

    private Thread waitingWPCChoiceThread;

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

    private ArrayList<ClientConnection> disconnectedClients;

    private int roundNumber;

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

        int i = 0;
        unitializedOrderedPlayers = new ArrayList<>();

        //TODO vedi se funziona

        // I have to create the list that connects Usernames and Players
        while (!usernameList.isEmpty()){
            System.out.println("Entra n"+ i);
            unitializedOrderedPlayers.add(new Player(usernameList.get(i)));
            i++;
        }
        this.model = new Model(unitializedOrderedPlayers);
        this.roundNumber = 0;

        // Now I will start each player's View
        initializeGame();
    }

    /**
     * It calls initializePlayers() and setInitialPlayer()
     */
    public void initializeGame() {
        //Let people chose their Wpc, and call a method that waits until all chose theirs.
        //Once i receive all -> move to orderedPlayers List
        ArrayList<WindowPatternCard> localWpc;
        //Gives to each a player 4 WindowPatternCard to choose from
        for(Player p: unitializedOrderedPlayers){
            StringBuilder localNamesWpc = new StringBuilder();
            // I give the cards (in strings) to the command, and to the method that waits until all players finishes to chose
            localWpc = model.extractWindowPatternCard();
            for (WindowPatternCard card : localWpc)
                localNamesWpc.append(card.getCardName()).append(" ");
            sendCommandToPlayer(p, new ChooseWindowPatternCardCommand(localNamesWpc.toString()));
        }
    }


    /**
     * Initializes all Lists of players for each round, ordered.
     */
    public void assignRoundPlayers(ArrayList<Player> orderedPlayers){
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
     * Method that uses the Model to update view's personal model
     * @param model
     */
    public void update(Model model){
        sendCommandToAllPlayers(new RefreshBoardCommand(model,
                "RefreshBoardCommand"));
    }


    /**
     * Call methods for starting the board of each player
     * including:
     * choose the WPattern Card
     * ...
     */
    public void startGame() {
        assignRoundPlayers(orderedPlayers);
        startNewRound();
    }


    public void endGame(){
        //For each player, create an HashMap calling on every player a Win/Lose command
        HashMap<Player, Integer> playerScoreMap = new HashMap<>();
        Integer tempScore;
        for (Player player : orderedPlayers) {
            tempScore=0;
            for (PublicObjectiveCard card :model.getExtractedPublicObjectiveCard()){
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

        Iterator i0 = set.iterator();
        while (i0.hasNext()) {
            Map.Entry entry = (Map.Entry) i0.next();
            // Player Username + player score //TODO Controlla se funziona!!
            scores += entry.getKey().toString() + "," + entry.getValue().toString();
        }

        Iterator i = set.iterator();
        Integer counter=0;
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            if (counter == 0)
                sendCommandToPlayer((Player) me.getKey(), new WinCommand(scores, (Integer) me.getValue()));
            else
                sendCommandToPlayer((Player) me.getKey(), new LoseCommand(scores, (Integer) me.getValue(), counter));
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
            //initialize DraftPool
            model.setDraftPool(model.extractDraftPoolDice(orderedPlayers.size()));
            //Start a new round-> pick the first of the RoundList
            currentRoundOrderedPlayers=orderedRoundPlayers.remove(0);
            currentPlayer=currentRoundOrderedPlayers.remove(0);

            update(model);
            //first player
            sendCommandToPlayer(currentPlayer, new StartPlayerTurnCommand());
        }
    }
    /**
     * Has to start a turn of a new player in a round.
     * if the round has still 2*n turns played, i have to call starNewRound()
     */
    private void startNewTurn(){
        update(model);
        //Case that everybody has played in the round
        if (currentRoundOrderedPlayers.size()==0){
            startNewRound();
        }
        else{
            currentPlayer=currentRoundOrderedPlayers.remove(0);
            sendCommandToPlayer(currentPlayer, new StartPlayerTurnCommand());
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

    private void sendCommandToAllPlayers(ServerToClientCommand command){
        for (ClientConnection connection : Server.getControllerClientConnectionMap().get(this))
                connection.notifyClient(command);
    }

    private void sendCommandToPlayer(Player player, ServerToClientCommand command){
        Server.getConnectedClients().get(player.getUsername()).notifyClient(command);

    }

    private void sendCommandToAllPlayersExceptGiven(ServerToClientCommand command, Player p){
        for (ClientConnection connection : Server.getControllerClientConnectionMap().get(this))
            if (connection != Server.getUsernameConnectionMap().get(p.getUsername()))
                connection.notifyClient(command);
    }




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
     * Those are methods that apply commands arriving from the Client
     * There are 1 method for every class for every command
     *
     * If a command is invalid, I catch an exception and send to the client itself a new AskMoveCommand()
     */
    void distinguishClientCommand(String clientUsername, ClientToServerCommand command){
        String words[] = command.getMessage().split(" ");
        //TODO complete
        switch(words[1]){
            case("UseToolCopperFoilReamer"):
                ;
                break;
            case("UseToolCorkLine"):
                ;
                break;
            case("UseToolDiamondSwab"):
                ;
                break;
            case("UseToolEglomiseBrush"):
                ;
                break;
            case("UseToolFirmPastryBrush1"):
                ;
                break;
            case("UseToolFirmPastryBrush2"):
                ;
                break;
            case("UseToolFirmPastryThinner1"):
                ;
                break;
            case("UseToolFirmPastryThinner2"):
                ;
                break;
            case("UseToolGavel"):
                ;
                break;
            case("UseToolLathekin"):
                ;
                break;
            case("UseToolManualCutter"):
                ;
                break;
            case("UseToolRoughingForceps"):
                ;
                break;
            case("UseToolWheelsPincher"):
                ;
                break;
            case("ChosenWindowPatternCard"):
                applyCommand(clientUsername , (ChosenWindowPatternCard) command);
                ;
                break;
            case("MoveChoiceToolCard"):
                applyCommand(clientUsername, (MoveChoiceToolCard) command);
                break;
        }

    }






    /**
     * The choice of wpc is always right
     * That method removes the player that chooses its card, and moves it to the List of initialized player.
     * Checks if all players are initialized to call the next Controller Method
     * @param playerUsername the username who is applying the command
     * @param command the coming command
     */
    public void applyCommand(String playerUsername, ChosenWindowPatternCard command){
        String[] words = command.getMessage().split(" ");
        ParserWindowPatternCard parser = new ParserWindowPatternCard();
        usernamePlayerMap.get(playerUsername).setWindowPatternCard(parser.getCardFromName(words[1]));
        unitializedOrderedPlayers.remove(usernamePlayerMap.get(playerUsername)); //TODO what happens can't find it?
        orderedPlayers.add(usernamePlayerMap.get(playerUsername));

        if (unitializedOrderedPlayers.size()==0)
            startGame();
    }

    /**
     * The first word is the
     * @param playerUsername
     * @param command
     */
    //TODO: vedi se è necessario controllare che il player sia Current o non posso fare altre mosse e automaticamente il player da cui ricevo il comando è current
    public void applyCommand(String playerUsername, MoveChoiceToolCard command){
        Player current= usernamePlayerMap.get(playerUsername);
        Integer usedToolNumber = command.getNumberChosen();
        ToolCard chosen = model.getExtractedToolCard().get(usedToolNumber);
        int requiredTokens=1;
        if (chosen.getTokenCount()>0){
            requiredTokens=2;
        }
        if (current.getTokens()>requiredTokens){
            current.decreaseTokens(requiredTokens);
            if (chosen.getName().equals("FirmPastryThinner")){
                // Extract a die
                Die die = model.getDiceBag().extractDie(); //TODO Dove tengo questa informazione? mi fido del controller?
                sendCommandToPlayer(current, new CorrectUseToolFirmPastryThinner1(die.getColor().toString() , die.getValue() ));
            }
            else if (chosen.getName().equals("FirmPastryBrush")){
                sendCommandToPlayer(current, new CorrectUseToolFirmPastryBrush1("", ThreadLocalRandom.current().nextInt(1, 7)));
            }
            else{
                sendCommandToPlayer(current, new AllowedUseToolCommand("", usedToolNumber));
            }
        }
        else{
            sendCommandToPlayer(current, new InvalidMoveCommand("You haven't enough tokens to use this tool"));
        }

    }



    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername, UseToolCopperFoilReamer command){
        String message = command.getMessage();

    }
    /**
     * Applies commands coming from the Client, answering with correct/incorrect command responses
     */
    public void applyCommand(String playerUsername,UseToolCorkLine command){

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
        if (words[1].equals("MOVE")){
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
        if (words[1].equals("MOVE")){
            //TODO edit the model, reinsert the die in the draftpool in the dicebag
        }
        else{ //default: draftpool
            //TODO change the die in the DraftPool, reinsertt the old one in the diceBag
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


}