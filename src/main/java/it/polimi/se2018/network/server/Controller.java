package it.polimi.se2018.network.server;

import it.polimi.se2018.exceptions.WrongCellIndexException;
import it.polimi.se2018.view.CLI.CLIView;
import it.polimi.se2018.view.View;
import it.polimi.se2018.model.*;
import it.polimi.se2018.utils.ControllerServerInterface;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.model.public_obj_cards.PublicObjectiveCard;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//TODO has to extend controllerInterface to avoid access of Controller from view
public class Controller implements Observer, ControllerServerInterface { //Observer perchè osserva la View tramite le classi di mezzo (ClientConnection)

    public Model getModel() {
        return model;
    }

    public HashMap<String, View> getUserViewMap() {
        return userViewMap;
    }

    /**
     * This is the main controller of the game
     * It lives in the Server side, and contains all references to Players and their Connections
     *
     * Has a link to
     * - Model (it modifies the model)
     * - Connections with the view (to handle ServerToClientCommands)
     */

    private Model model;
    private HashMap<String, Player> usernamePlayerMap;
    private HashMap<String, View> userViewMap;
    private ArrayList<Player> orderedPlayers;
    private ArrayList<Player> uninitializedOrderedPlayers;
    private HashMap<Player,Timer> playerTimerMap;
    private int requiredTokensForLastToolUse;
    private ToolCard lastUsedToolCard;
    private Die extractedDieForFirmyPastryThinner;
    private Timer checkBlockingTimer;
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
    private int timerCostant;

    //TODO:
    //TODO OGNI MODIFICA DEI GIOCATORI orderedPlayers, DEVO RIASSEGNARLI AL MODEL
    //TODO OGNI VOLTA DEVO CONTROLLARE SE E' RISPETTATA LA CONDIZIONE DI CURRENT
    //TODO
    /**
     * The controller receives a list of the Usernames of the connected players.
     * The controller receives the connected Connection though the static HashMap of the Server
     * The connection between users and Connection is connected in the Server only, so when there is a disconnection the server
     * knows. Server is static, so has reasy access from the Controller
     * @param usernameList
     */
    public Controller(ArrayList<String> usernameList) {
        usernamePlayerMap = new HashMap<>();
        playerTimerMap = new HashMap<>();
        uninitializedOrderedPlayers = new ArrayList<>();
        userViewMap = new HashMap<>();
        // I have to create the list that connects Usernames and Players and VirtualViews
        for (String username : usernameList){
            Player temp = new Player(username);
            uninitializedOrderedPlayers.add(temp);
            usernamePlayerMap.put(username, temp);
        }
        this.model = new Model(uninitializedOrderedPlayers);

        for (String username : usernameList) {
            View tempView = new VirtualView(this, model, username);
            userViewMap.put(username, tempView);
            model.register(tempView);
        }
        this.orderedPlayers= new ArrayList<>();

        this.timerCostant = 600000; //TODO set better
        // Now I will start each player's View
        for (String username : usernamePlayerMap.keySet())
            userViewMap.get(username).startGame(); //notifying game starting
        initializeGame();
    }

    public Controller(ArrayList<String> usernameList, boolean forTesting) {
        usernamePlayerMap = new HashMap<>();
        playerTimerMap = new HashMap<>();
        uninitializedOrderedPlayers = new ArrayList<>();
        userViewMap = new HashMap<>();
        // I have to create the list that connects Usernames and Players and VirtualViews
        for (String username : usernameList){
            Player temp = new Player(username);
            uninitializedOrderedPlayers.add(temp);
            usernamePlayerMap.put(username, temp);
        }
        this.model = new Model(uninitializedOrderedPlayers);

        for (String username : usernameList) {
            View tempView = new CLIView(this); //Vedi meglio
            userViewMap.put(username, tempView);
            model.register(tempView);
        }
        this.orderedPlayers= new ArrayList<>();

        this.timerCostant = 600000; //TODO set better
        // Now I will start each player's View
        for (String username : usernamePlayerMap.keySet())
            userViewMap.get(username).startGame(); //notifying game starting

        initializeGame();
    }

    /**
     * It calls initializePlayers() and setInitialPlayer()
     */
    private void initializeGame(){
        //Let people chose their Wpc, and call a method that waits until all chose theirs.
        //Once i receive all -> move to orderedPlayers List
        ArrayList<WindowPatternCard> localWpc;
        //Gives to each a player 4 WindowPatternCard to choose from
        for(Player p: uninitializedOrderedPlayers){
            StringBuilder localNamesWpc = new StringBuilder();
            // I give the cards (in strings) to the command, and to the method that waits until all players finishes to chose
            localWpc = model.extractWindowPatternCard();
            WindowPatternCard defaultCard = localWpc.get(0); //default wpc in case the player disconnects
            usernamePlayerMap.get(p.getUsername()).setWindowPatternCard(defaultCard);
            System.out.println("invio command CHOOSEWPC a player:" + p.getUsername());
            userViewMap.get(p.getUsername()).chooseWindowPatternCardMenu(localWpc);
        }
        checkBlockingTimer = new Timer(); //General timer for every player. Is starts the game stopping players without waiting the answer
        checkBlockingTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("This is last call for chosing Wpc: starting the game with disconnected Players");
                        if (!uninitializedOrderedPlayers.isEmpty()) {
                            for (Player p : uninitializedOrderedPlayers) {
                                System.out.println("Dimension of orderedP" + uninitializedOrderedPlayers.size());
                                userViewMap.get(p.getUsername()).timeOut();
                                orderedPlayers.add(p);
                                System.out.println("Arrato "+ p.getUsername());
                            }
                            uninitializedOrderedPlayers.clear();
                            startGame();
                        }
                    }
                },
                timerCostant+100000);//TODO è una PROVA
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
            orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());orderedRoundPlayers.add((ArrayList<Player>)temp2.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp3.clone());orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp2.clone());orderedRoundPlayers.add((ArrayList<Player>)temp3.clone());orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());
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
            orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());orderedRoundPlayers.add((ArrayList<Player>)temp2.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());orderedRoundPlayers.add((ArrayList<Player>)temp2.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());orderedRoundPlayers.add((ArrayList<Player>)temp2.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());
        }
        else if (orderedPlayers.size()==2) {
            Player c0 = orderedPlayers.get(0);
            Player c1 = orderedPlayers.get(1);
            ArrayList<Player> temp0 = new ArrayList<>();
            ArrayList<Player> temp1 = new ArrayList<>();
            temp0.add(c0);temp0.add(c1);temp0.add(c1);temp0.add(c0);
            temp1.add(c1);temp1.add(c0);temp1.add(c0);temp1.add(c1);
            orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());orderedRoundPlayers.add((ArrayList<Player>)temp0.clone());
            orderedRoundPlayers.add((ArrayList<Player>)temp1.clone());
        }
        else{
            System.out.println("problema");
        }
    }

    private void startGame() {
        model.notifyRefreshBoard();
        assignRoundPlayers(orderedPlayers);
        startNewRound();
    }

    private void endGame(){
        //For each player, create an HashMap calling on every player a Win/Lose command
        HashMap<String, Integer> playerScoreMap = new HashMap<>();
        Integer tempScore;
        for (Player player : orderedPlayers) {
            tempScore=0;
            for (PublicObjectiveCard card : model.getExtractedPublicObjectiveCard()){
                tempScore+= card.calculateScore(player.getWindowPatternCard());
            }
            tempScore-= penalityScore(player.getWindowPatternCard());
            playerScoreMap.put(player.getUsername(), tempScore);
            //TODO: Manca altro da calcolare? come facciamo con i punteggi sul tabellone?
        }

        LinkedHashMap<String, Integer> orderedPlayerScores = new LinkedHashMap<>();
        for (int i = 0; i < orderedPlayers.size(); i++) {
            int maxValueInMap=(Collections.max(playerScoreMap.values()));// This will return max value in the Hashmap
            for (Map.Entry<String, Integer> entry : playerScoreMap.entrySet()) {  // Iterates through hashmap
                if (entry.getValue() == maxValueInMap) {
                    orderedPlayerScores.put(entry.getKey(), playerScoreMap.remove(entry.getKey()));   // Assign a new Entry in the LinkedHashMap
                    break; //Exit for new research of max
                }
            }
        }
        sendResultToPlayers(orderedPlayerScores);
    }

    private void sendResultToPlayers(LinkedHashMap<String, Integer> orderedPlayerScores) {
        Set set1 = orderedPlayerScores.entrySet();
        Set set2 = orderedPlayerScores.entrySet();
        ArrayList<String> scoresList = new ArrayList<>();
        Iterator i0 = set1.iterator(); //Through iteration, we can better manage the scores of all players
        Iterator i1 = set2.iterator();
        int tempRank=1;
        while (i0.hasNext()) {
            Map.Entry entry = (Map.Entry) i0.next();
            scoresList.add(tempRank + "_" + entry.getKey().toString() + "_" + entry.getValue().toString());
            tempRank++;
        }
        Integer counter=0;
        while (i1.hasNext()) {
            Map.Entry me = (Map.Entry) i1.next();
            System.out.println("Io sono: " + me.getKey() );
                if (counter == 0) {
                userViewMap.get(me.getKey().toString()).winMessage(scoresList);
            }
            else {
                userViewMap.get(me.getKey().toString()).loseMessage(counter+1, scoresList);
            }
            counter++;
        }
    }

    private Integer penalityScore(WindowPatternCard card){
        int tempScore=0;
        for(Cell c : card.getSchema()){
            tempScore += c.isEmpty()? 1 : 0;
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
            System.out.println("Start turn "+ (10 - orderedRoundPlayers.size()) + 1);
            model.setDraftPool(model.extractDraftPoolDice(orderedPlayers.size()));

            //TODO fallo apparire solo una volta con wpcs (aggiorna Tutta la board, non serve)
            model.setPlayerWpcs(orderedPlayers); //Used for notify eventual modifics of wpcs to the Players
            //initialize DraftPool
            //Start a new round-> pick the first of the RoundList
            currentRoundOrderedPlayers=orderedRoundPlayers.remove(0);
            currentPlayer=currentRoundOrderedPlayers.remove(0);
            System.out.println("current è " + currentPlayer.getUsername());
            System.out.println("Prima di chiamare StartTurnMenu");
            hasPerformedMove=false;
            hasUsedTool=false;

            System.out.println("CURRENT E'" + currentPlayer.getUsername());

            for (Player p : orderedPlayers){
                if (!(p.getUsername().equals(currentPlayer.getUsername()))) {
                    userViewMap.get(p.getUsername()).otherPlayerTurn(p.getUsername());
                }
            }

            playerTimerMap.put(usernamePlayerMap.get(currentPlayer.getUsername()), new Timer());
            playerTimerMap.get(usernamePlayerMap.get(currentPlayer.getUsername())).schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Sending timeout");
                            userViewMap.get(currentPlayer.getUsername()).timeOut();
                        }
                    },
                    timerCostant
                    //TODO IMPOSTA
            );
            userViewMap.get(currentPlayer.getUsername()).startTurnMenu();
        }
    }

    /**
     * Has to start a turn of a new player in a round.
     * if the round has still 2*n turns played, i have to call starNewRound()
     */
    private void startNewTurn(){
        //Case in which everybody has played in the round
        if (currentRoundOrderedPlayers.isEmpty()){
            startNewRound();
        }
        else{
            currentPlayer=currentRoundOrderedPlayers.remove(0);
            hasPerformedMove=false;
            hasUsedTool=false;

            for (Player p : orderedPlayers){
                if (!(p.getUsername().equals(currentPlayer.getUsername()))) {
                    userViewMap.get(p.getUsername()).otherPlayerTurn(p.getUsername());
                }
            }

            playerTimerMap.put(usernamePlayerMap.get(currentPlayer.getUsername()), new Timer());
            playerTimerMap.get(usernamePlayerMap.get(currentPlayer.getUsername())).schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Sending timeout");
                            userViewMap.get(currentPlayer.getUsername()).timeOut();
                        }
                    },
                    timerCostant
            );
            userViewMap.get(currentPlayer.getUsername()).startTurnMenu();
        }
    }

    /**
     * Generally returns true if need ad allowance to perform a command
     * @return true if the currentPlayer is the one given
     */
    private boolean isAllowed(String username){
        return username.equals(currentPlayer.getUsername());
    }

    /**
     * The choice of wpc is always right
     * That method removes the player that chooses its card, and moves it to the List of initialized player.
     * Checks if all players are initialized to call the next Controller Method
     * @param playerUsername the username who is applying the command
     * @param command the coming command
     */
    @Override
    public synchronized void applyCommand(String playerUsername, ChosenWindowPatternCard command){
        System.out.println("entra controller command"+command.getMessage());
        ParserWindowPatternCard parser = null;
        try {
            parser = new ParserWindowPatternCard();
            usernamePlayerMap.get(playerUsername).setWindowPatternCard(parser.parseCardByName(command.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (uninitializedOrderedPlayers.contains(usernamePlayerMap.get(playerUsername))) {
            uninitializedOrderedPlayers.remove(usernamePlayerMap.get(playerUsername));
            orderedPlayers.add(usernamePlayerMap.get(playerUsername));
            if (uninitializedOrderedPlayers.isEmpty()) {
                checkBlockingTimer.cancel();
                startGame();
            }
        }
    }


    @Override
    public synchronized void applyCommand(String playerUsername, MoveChoiceToolCard command){
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        Player current= usernamePlayerMap.get(playerUsername);
        Integer usedToolNumber = command.getNumberChosen(); //Referes to the toolcard used
        ToolCard chosen = model.getExtractedToolCard().get(usedToolNumber);
        int requiredTokens=1;
        if (chosen.getTokenCount()>0){
            requiredTokens=2;
        }
        if (current.getTokens()>requiredTokens) {
            lastUsedToolCard = chosen;
            requiredTokensForLastToolUse = requiredTokens;
            String toolName = chosen.getName();
                if (toolName.equals("Copper Foil Reamer"))
                    userViewMap.get(playerUsername).moveDieNoRestrictionMenu(chosen.getName());
                else if (toolName.equals("Cork Line"))
                    userViewMap.get(playerUsername).moveDieNoRestrictionMenu(chosen.getName());
                else if (toolName.equals("Diamond Swab"))
                    userViewMap.get(playerUsername).moveDieNoRestrictionMenu(chosen.getName());
                else if (toolName.equals("Eglomise Brush"))
                    userViewMap.get(playerUsername).moveDieNoRestrictionMenu(chosen.getName());
                else if (toolName.equals("Firm Pastry Brush"))
                    userViewMap.get(currentPlayer.getUsername()).firmPastryBrushMenu(ThreadLocalRandom.current().nextInt(1, 7));
                else if (toolName.equals("Firm Pastry Thinner")) {
                    extractedDieForFirmyPastryThinner = model.extractDieFromDiceBag();
                    userViewMap.get(currentPlayer.getUsername()).firmPastryThinnerMenu(extractedDieForFirmyPastryThinner.getColor().toString(), extractedDieForFirmyPastryThinner.getValue());
                }
                else if (toolName.equals("Gavel")) {
                if (currentRoundOrderedPlayers.contains(usernamePlayerMap.get(playerUsername))) { //Can't use the tool, has to be in second turn
                    userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You have to be in your second turn to use the tool");
                    userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                } else {
                    model.rollDraftpoolDice();
                    current.decreaseTokens(requiredTokens);
                    chosen.increaseTokens(requiredTokens);
                    userViewMap.get(currentPlayer.getUsername()).messageBox("Correctly used the tool, dice are re-rolled");
                    hasUsedTool = true;
                    userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasPerformedMove);
                }
            }
            else if (toolName.equals("Lathekin"))
                    userViewMap.get(playerUsername).twoDiceMoveMenu(chosen.getName());
            else if (toolName.equals("Manual Cutter"))
                    userViewMap.get(playerUsername).twoDiceMoveMenu(chosen.getName());
            else if (toolName.equals("Roughing Forceps"))
                    userViewMap.get(playerUsername).changeDieValueMenu(chosen.getName());
            else if (toolName.equals("Wheels Pincher")) {
                if (!currentRoundOrderedPlayers.contains(usernamePlayerMap.get(playerUsername))) { //Can't use the tool, has to be in first turn
                    userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You have to be in your first turn to use the tool");
                    userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                } else {
                    userViewMap.get(playerUsername).wheelsPincherMenu();
                }
            }
            else
                System.out.println("Error in toolNames");
            }
        else{ //Not enough tokens
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You haven't enough tokens to use this tool");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove,hasUsedTool);
        }
    }


    @Override
    public void applyCommand(String playerUsername, MoveChoiceDicePlacement command){
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        try {
            Die toPlace = null;
            try{
                model.getDraftPool().getDie(command.getDieDraftPoolPosition());
            }
            catch (WrongCellIndexException e){
                System.out.println("Wrong cell: sending invalid");
                userViewMap.get(playerUsername).invalidActionMessage("Incorrect draftpool index, try again");
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
            boolean exit = usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                    .placeDie(toPlace, command.getDieSchemaRowPosition(), command.getDieSchemaColPosition(), true, true, true);
            if (!exit) {
                userViewMap.get(playerUsername).invalidActionMessage("Incorrect move"); //TODO Sarebbe bello scrivere anche il motivo della mossa incorretta, magari creo un metodo apposito per ogni controllo piazzamentog
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
            else {
                System.out.println("Mossa applicata correttamente");
                model.removeDieFromDraftPool(command.getDieDraftPoolPosition());
                hasPerformedMove = true;
                model.setGamePlayers(orderedPlayers);
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
        }catch (EmptyCellException e){
            e.printStackTrace();
            System.out.println("Empty cell, sending an IncorrectMoveCommand");
            userViewMap.get(playerUsername).invalidActionMessage("The Draftpool cell you selected is empty, try again");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
        }
        //When I arrive here, the move is already performed
    }

    @Override
    public void applyCommand(String playerUsername, MoveChoicePassTurn command){
        if (!orderedPlayers.contains(usernamePlayerMap.get(playerUsername))){
            System.out.println("Match not started yet: no action");
            return;
        }
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        if (extractedDieForFirmyPastryThinner!=null) { //Case in which a timeout forced the player turn skip -> the die hasn't to be lost
            model.insertDieInDiceBag(extractedDieForFirmyPastryThinner);
            extractedDieForFirmyPastryThinner=null;
        }
        playerTimerMap.get(usernamePlayerMap.get(currentPlayer.getUsername())).cancel();
        startNewTurn();
    }

    //Those methods represents the view that uses correctly a tool.
    // The server has to validate the move and edit the model, if the move is correct
    // else, has to call a new Request of re-use of that tool, re-sending a event of AllowedUseToolCommand(usedToolNumber)

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     */
    //MOSSA SENZA RESTRIZIONE POSIZIONE E DEVONO ESSERE NON ADIACENTI
    @Override
    public void applyCommand(String playerUsername, UseToolCorkLine command){
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        Die temp = null;
        try {
            temp = model.getDraftPool().getDie(command.getDieDraftPoolPosition());
        } catch (EmptyCellException e) {
            e.printStackTrace();
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Invalid index, try again");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        //TODO mosssa speciale , ragiona se devo creare un altro metodo o va bene questo
        if (!currentPlayer.getWindowPatternCard().placeDie(temp, command.getSchemaPosition(), true, true, false)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Invalid move, try again");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     */
    @Override
    public void applyCommand(String playerUsername, UseToolTwoDicePlacement command){
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        if (command.getCardName().equals("Manual Cutter")) {
            Player current = usernamePlayerMap.get(playerUsername);
            if (!current.getWindowPatternCard().getCell(command.getSchemaOldPosition1()).hasDie() || !current.getWindowPatternCard().getCell(command.getSchemaOldPosition2()).hasDie()) {
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Invalid index, try again");
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
            Die die1 = null, die2 = null;
            try {
                die1 = current.getWindowPatternCard().getCell(command.getSchemaOldPosition1()).getAssociatedDie();
                die2 = current.getWindowPatternCard().getCell(command.getSchemaOldPosition2()).getAssociatedDie();

            } catch (EmptyCellException e) {
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Invalid index! Try again");
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
            if (die1.getColor() == die2.getColor()) {
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Your dice has different color! Try again");
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            } else try {
                if (!model.getRoundTrack().isPresent(die1.getColor())) {
                    userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("The RoundTrack hasn't the chosen color, try with another ones");
                    userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
            } catch (EmptyCellException e) {
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Invalid index! Try again");
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
        }

        Player current = usernamePlayerMap.get(playerUsername);
        if (!current.getWindowPatternCard().getCell(command.getSchemaOldPosition1()).hasDie() || !current.getWindowPatternCard().getCell(command.getSchemaOldPosition2()).hasDie()) {
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Invalid index! Try again");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        try {
            if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard().move2Dice(command.getSchemaOldPosition1(),
                    command.getSchemaNewPosition1(), command.getSchemaOldPosition2(), command.getSchemaNewPosition2(), true, true, true)) {
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place the die here! Try again");
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
        } catch (EmptyCellException e) {
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("Invalid index! Try again");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    @Override
    public void applyCommand(String playerUsername, UndoActionCommand command) {
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     */
    @Override
    public void applyCommand(String playerUsername, UseToolMoveDieNoRestriction command) {
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        Player current = usernamePlayerMap.get(playerUsername);
        if (!current.getWindowPatternCard().getCell(command.getSchemaOldPosition()).hasDie()) {
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("The index given is incorrect, try again!");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        try {
            if (command.getCardName().equalsIgnoreCase("Eglomise Brush")) {
                if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard().switchDie(command.getSchemaOldPosition(),
                        command.getSchemaNewPosition(), false, true, true)) {
                    userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place the die here! Try again");
                    userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
            }
            else if (command.getCardName().equalsIgnoreCase("Copper Foil Reamer")){
                if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard().switchDie(command.getSchemaOldPosition(),
                        command.getSchemaNewPosition(), true, false, true)) {
                    userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place the die here! Try again");
                    userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
            }
        } catch (EmptyCellException e) {
            e.printStackTrace();
        }
        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     * BRUSH: decide the new value
     */
    @Override
    public void applyCommand(String playerUsername , UseToolFirmPastryBrush command){
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        Die tempOk;
        try {
            tempOk = model.getDraftPool().getDie(command.getDieDraftpoolPosition());
        } catch (EmptyCellException e) {
            e.printStackTrace();
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("The chosen cell index from draftpool is incorrect");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        tempOk.setValue(command.getDieValue());
        String[] words = command.getMessage().split(" ");
        if (words[0].equals("MOVE")){
            if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard().placeDie(tempOk
                    , command.getDieSchemaPosition(), true, true, true)){
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place the die here! Automatically put the die on draftpool"); //TODO forse do la possibilità di rifare la mssa
                model.changeDieValueOnDraftPool(command.getDieDraftpoolPosition(), command.getDieValue());
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
            else {
                model.removeDieFromDraftPool(command.getDieDraftpoolPosition());
                hasPerformedMove = true;
            }
        }
        else{ //default: draftpool
            model.changeDieValueOnDraftPool(command.getDieDraftpoolPosition(), command.getDieValue());
        }

        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;

        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     * THINNER: die from DiceBag
     */
    @Override
    public void applyCommand(String playerUsername , UseToolFirmPastryThinner command){
        if (!isAllowed(playerUsername) || extractedDieForFirmyPastryThinner==null){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        Die toReinsert = model.removeDieFromDraftPool(command.getDieOldPosition());
        model.insertDieInDiceBag(toReinsert);
        extractedDieForFirmyPastryThinner.setValue(command.getDieNewValue());
        String[] words = command.getMessage().split(" ");
        if (words[0].equals("MOVE")){
            if (usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                    .placeDie(extractedDieForFirmyPastryThinner,
                    command.getDiePosition(), true, true, true)){
                usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
                lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
                hasUsedTool = true;
                hasPerformedMove = true;
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
                extractedDieForFirmyPastryThinner=null;
                return;
            }
            else{
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place the die here! Automatically put the die on DraftPool");//TODO forse richiama il metodo per fargli provare ancora
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
        }
        //default: draftpool
        model.setDieOnDraftPool(extractedDieForFirmyPastryThinner, command.getDieOldPosition());
        extractedDieForFirmyPastryThinner=null;
        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     */
    @Override
    public void applyCommand(String playerUsername ,UseToolChangeDieValue command){ //Has to place it!
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        Die temp = null;
        try {
            temp = model.getDraftPool().getDie(command.getDraftPoolPosition());
        } catch (EmptyCellException e) {
            e.printStackTrace();
        }

        if (command.getCardName().equals("Roughing Forceps")){
            if (command.isIncrease()){
                //checking number <6
                try {
                    if(usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                            .getCell(command.getDraftPoolPosition()).getAssociatedDie().getValue()<6){
                        //correct
                        if (!usernamePlayerMap.get(currentPlayer.getUsername()).getWindowPatternCard()
                                .placeDie(temp, command.getSchemaPosition(), true, true, true)){
                            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place the die here! Try again");
                            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                            return;
                        }
                        else{
                            model.removeDieFromDraftPool(command.getDraftPoolPosition());
                        }
                    }
                    else{
                        userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("The die has value 6, can't be increased");
                        userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                        return;
                    }
                } catch (EmptyCellException e) {
                    e.printStackTrace();
                }
            }
            else{ //default: decrease
                try {
                    if(usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                            .getCell(command.getDraftPoolPosition()).getAssociatedDie().getValue()>1){
                        //correct
                        if (!usernamePlayerMap.get(currentPlayer.getUsername()).getWindowPatternCard()
                                .placeDie(temp, command.getSchemaPosition(), true, true, true)){
                            //invalid: undo action
                            //continueturncommand
                            return;
                        }
                        else{
                            model.removeDieFromDraftPool(command.getDraftPoolPosition());
                        }
                    }
                    else{
                        userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("The die has value 1, can't be decreased");
                        userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                        return;
                    }
                } catch (EmptyCellException e) {
                    e.printStackTrace();
                }
            }
        }
        //default: Diamond Swab
        else{
            temp.flip();
            if (!usernamePlayerMap.get(currentPlayer.getUsername()).getWindowPatternCard()
                    .placeDie(temp, command.getSchemaPosition(), true, true, true)){
                userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place TODO");
                userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
            else{
                model.removeDieFromDraftPool(command.getDraftPoolPosition());
            }
        }
        //TODO aggiungo set dei player al model?
        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;
        hasPerformedMove = true;
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);

    }

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     */
    @Override
    public void applyCommand(String playerUsername, UseToolCircularCutter command){
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }

        //TODO check correct die position not null

        Die tempFromDraftPool = model.removeDieFromDraftPool(command.getDieDraftPoolPosition());
        Die tempFromRoundTrack = model.swapDieOnRoundTrack(tempFromDraftPool, command.getDieRoundTrackPosition());
        model.setDieOnDraftPool(tempFromRoundTrack, command.getDieDraftPoolPosition());
        //from roundtrack to draftpool
        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Applies commands coming from the view, answering with correct/incorrect command responses
     */
    //Skip next turn
    @Override
    public void applyCommand(String playerUsername, UseToolWheelsPincher command){
        if (!isAllowed(playerUsername)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        //Already checked that the player is in his first turn
        Die toPlace1=null, toPlace2=null;
        try {
            toPlace1 = model.getDraftPool().getDie(command.getDieDraftPoolPosition2());
            toPlace2 = model.getDraftPool().getDie(command.getDieDraftPoolPosition2());
        } catch (EmptyCellException e) {
            e.printStackTrace();
        }

        if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard().place2Die(toPlace1, toPlace2,
                command.getDieSchemaPosition1(), command.getDieSchemaPosition2(),
                true, true, true)){
            userViewMap.get(currentPlayer.getUsername()).invalidActionMessage("You can't place the dice there! Try Again");
            userViewMap.get(currentPlayer.getUsername()).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        model.removeDieFromDraftPool(command.getDieDraftPoolPosition1());
        model.removeDieFromDraftPool(command.getDieDraftPoolPosition2());
        usernamePlayerMap.get(currentPlayer.getUsername()).decreaseTokens(requiredTokensForLastToolUse);
        lastUsedToolCard.increaseTokens(requiredTokensForLastToolUse);
        hasUsedTool = true;
        hasPerformedMove = true;
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }


    @Override
    public void applyCommand(String playerUsername ,ClientToServerCommand command){
        System.out.println("You shouldn't be here");
    }

    @Override
    public void update(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        try {
            command.visit(this);
        } catch (EmptyCellException e) {
            e.printStackTrace();
        }
    }
}