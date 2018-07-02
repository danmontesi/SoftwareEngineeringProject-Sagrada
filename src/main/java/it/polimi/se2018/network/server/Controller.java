package it.polimi.se2018.network.server;

import it.polimi.se2018.CONSTANTS;
import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.public_obj_cards.PublicObjectiveCard;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.utils.ControllerServerInterface;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.model.ACTION_TYPE.*;

public class Controller implements Observer, ControllerServerInterface { //Observer perchè osserva la View tramite le classi di mezzo (ClientConnection)


    //TODO setta i messaggi
    private static final String WRONG_INDEX = "The cell you selected is wrong or our of index, try again!";
    private static final String WRONG_PLACEMENT = "The placement is incorrect. Check the rules of Sagrada";
    private static final String EMPTY_INDEX = "The cell you selected is empty, try again!";
    private static final String EMPTY_DRAFTPOOL_INDEX = "The draftpool die you selected doesn't exits! Try again";

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private static final String NOT_YOUR_TURN = "It's not your turn, you can't no any action.";

    private Model model;
    private HashMap<String, Player> usernamePlayerMap;
    private HashMap<String, View> userViewMap;
    private List<Player> orderedPlayers;
    private List<Player> uninitializedOrderedPlayers;
    private HashMap<String, Timer> usernameTimerMap;
    private ToolcardData toolcardData;

    public Timer getCheckBlockingTimer() { //just for testing
        return checkBlockingTimer;

    }

    public List<Player> getOrderedPlayers() {
        return orderedPlayers;
    }

    public List<Player> getUninitializedOrderedPlayers() {
        return uninitializedOrderedPlayers;
    }

    private Timer checkBlockingTimer;

    private final Object mutex = new Object();

    private boolean active;

    /**
     * ArrayList that contains the ordered players that has to play
     * is created by the model in its constructor
     */
    private List<List<String>> orderedRoundPlayers;
    private List<String> currentRoundOrderedPlayers;

    /**
     * Represent current player. That is necessary to know which is the player i'm expecting an answer
     */
    private String currentPlayer;
    private boolean hasUsedTool;
    private boolean hasPerformedMove;
    private int timerCostant;

    //List of Actions

    public Controller(List<String> usernameList) {
        active=true;
        usernamePlayerMap = new HashMap<>();
        usernameTimerMap = new HashMap<>();
        uninitializedOrderedPlayers = new ArrayList<>();
        userViewMap = new HashMap<>();
        // I have to create the list that connects Usernames and Players and VirtualViews
        for (String username : usernameList) {
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
        this.orderedPlayers = new ArrayList<>();

        this.timerCostant = CONSTANTS.TURN_TIMER;
        // Now I will start each player's View
        for (String username : usernamePlayerMap.keySet())
            userViewMap.get(username).startGame(); //notifying game starting
        initializeGame();
    }

    public Controller(List<String> usernameList, boolean forTesting) {
        active = true;
        usernamePlayerMap = new HashMap<>();
        usernameTimerMap = new HashMap<>();
        uninitializedOrderedPlayers = new ArrayList<>();
        userViewMap = new HashMap<>();
        // I have to create the list that connects Usernames and Players and VirtualViews
        for (String username : usernameList) {
            Player temp = new Player(username);
            uninitializedOrderedPlayers.add(temp);
            usernamePlayerMap.put(username, temp);
        }
        this.model = new Model(uninitializedOrderedPlayers);

        for (String username : usernameList) {
            View tempView = new View(this); //Vedi meglio
            userViewMap.put(username, tempView);
            model.register(tempView);
        }
        this.orderedPlayers = new ArrayList<>();

        this.timerCostant = CONSTANTS.TURN_TIMER;
        // Now I will start each player's View
        for (String username : usernamePlayerMap.keySet())
            userViewMap.get(username).startGame(); //notifying game starting
    }

    public void initializeGame() {
        //Let people chose their Wpc, and call a method that waits until all chose theirs.
        //Once i receive all -> move to orderedPlayers List
        List<WindowPatternCard> localWpc;

        //Gives to each a player 4 WindowPatternCard to choose from
        for (Player p : uninitializedOrderedPlayers) {
            // I give the cards (in strings) to the command, and to the method that waits until all players finishes to chose
            localWpc = model.extractWindowPatternCard();
            WindowPatternCard defaultCard = localWpc.get(0); //default wpc in case the player disconnects
            usernamePlayerMap.get(p.getUsername()).setWindowPatternCard(defaultCard);
            LOGGER.log(Level.INFO, "invio command CHOOSEWPC a player:" + p.getUsername());
            userViewMap.get(p.getUsername()).chooseWindowPatternCardMenu(localWpc, model.getPlayerFromUsername(p.getUsername()).getPrivateObjectiveCard().getName());
        }
        checkBlockingTimer = new Timer(); //General timer for every player. Is starts the game stopping players without waiting the answer
        checkBlockingTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        synchronized (mutex) {
                            LOGGER.log(Level.INFO, "This is last call for chosing Wpc: starting the game with disconnected Players");
                            if (!uninitializedOrderedPlayers.isEmpty()) {
                                for (Player p : uninitializedOrderedPlayers) {
                                    LOGGER.log(Level.INFO, "Dimension of orderedP" + uninitializedOrderedPlayers.size());
                                    userViewMap.get(p.getUsername()).timeOut();
                                    orderedPlayers.add(p);
                                    LOGGER.log(Level.INFO, "Added " + p.getUsername());
                                }
                                uninitializedOrderedPlayers.clear();
                                startGame();
                            }
                        }
                    }
                },
                CONSTANTS.WPC_TIMER); //TODO timer
    }

    /**
     * Initializes all Lists of players for each round, ordered.
     */
    public void assignRoundPlayers(List<Player> orderedPlayers) {
        orderedRoundPlayers = new ArrayList<>();
        int numberOfPlayers = orderedPlayers.size();
        for (int i = 0; i < 10; i++){
            List<String> playersInRound = new ArrayList<>();
            for(int j = 0; j < numberOfPlayers; j++){
                playersInRound.add(orderedPlayers.get((i+j) % numberOfPlayers).getUsername());
            }
            for(int j = numberOfPlayers - 1; j >= 0; j--){
                playersInRound.add(playersInRound.get(j));
            }
            orderedRoundPlayers.add(playersInRound);
        }
    }

    public void startGame() {
        assignRoundPlayers(orderedPlayers);
        model.notifyRefreshBoard(null, orderedPlayers);
        startNewRound();
    }

    void endGame() {
        for (Player p : orderedPlayers) {
            getUserViewMap().get(p.getUsername()).endGame();
        }

        //For each player, create an HashMap calling on every player a Win/Lose command
        HashMap<String, Integer> playerScoreMap = new HashMap<>();
        Integer tempScore;
        for (Player player : orderedPlayers) {
            tempScore = 0;
            for (PublicObjectiveCard card : model.getExtractedPublicObjectiveCard()) {
                tempScore += card.calculateScore(player.getWindowPatternCard());
            }
            tempScore -= penaltyScore(player.getWindowPatternCard());
            playerScoreMap.put(player.getUsername(), tempScore);
        }

        LinkedHashMap<String, Integer> orderedPlayerScores = new LinkedHashMap<>();
        for (int i = 0; i < orderedPlayers.size(); i++) {
            int maxValueInMap = (Collections.max(playerScoreMap.values()));// This will return max value in the Hashmap
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
        List<String> scoresList = new ArrayList<>();
        Iterator i0 = set1.iterator(); //Through iteration, we can better manage the scores of all players
        Iterator i1 = set2.iterator();
        int tempRank = 1;
        while (i0.hasNext()) {
            Map.Entry entry = (Map.Entry) i0.next();
            scoresList.add(tempRank + "_" + entry.getKey().toString() + "_" + entry.getValue().toString());
            tempRank++;
        }
        Integer counter = 0;
        while (i1.hasNext()) {
            Map.Entry me = (Map.Entry) i1.next();
            if (counter == 0) {
                userViewMap.get(me.getKey().toString()).winMessage(scoresList);
            } else {
                userViewMap.get(me.getKey().toString()).loseMessage(counter + 1, scoresList);
            }
            counter++;
        }
        active = false;
    }

    private Integer penaltyScore(WindowPatternCard card) {
        int tempScore = 0;
        for (Cell c : card.getSchema()) {
            tempScore += c.isEmpty() ? 1 : 0;
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
        if (orderedRoundPlayers.isEmpty()) {
            endGame();
        } else {
            LOGGER.log(Level.INFO, "Start round " + (10 - orderedRoundPlayers.size()));
            model.setDraftPool(model.extractDraftPoolDice(orderedPlayers.size()));

            currentRoundOrderedPlayers = orderedRoundPlayers.remove(0);
            currentPlayer = currentRoundOrderedPlayers.remove(0);
            hasPerformedMove = false;
            hasUsedTool = false;

            for (Player p : orderedPlayers) {
                if (!(p.getUsername().equals(currentPlayer))) {
                    userViewMap.get(p.getUsername()).otherPlayerTurn(currentPlayer);
                }
            }

            model.setCurrentPlayer(model.getPlayerFromUsername(currentPlayer));
            model.setCurrentRound(10 - orderedRoundPlayers.size());
            usernameTimerMap.put(currentPlayer, new Timer());
            usernameTimerMap.get(currentPlayer).schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            LOGGER.log(Level.INFO, "Sending timeout");
                            userViewMap.get(currentPlayer).timeOut();
                            if (toolcardData!=null)
                                handlePlayerAfterIncorrectToolUse(currentPlayer, "You haven't finished to use the tool, the changes are restored", false);
                            //TODO se l'utente sta usando un tool, resetto il model
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            startNewTurn();
                        }
                    },
                    timerCostant
            );
            userViewMap.get(currentPlayer).startTurnMenu();
        }
    }

    /**
     * Has to start a turn of a new player in a round.
     * if the round has still 2*n turns played, i have to call starNewRound()
     */
    private void startNewTurn() {
        if (currentRoundOrderedPlayers.isEmpty()) {
            Die temp;
            try {
                temp = model.getLastDie();
                model.putDieOnRoundTrack(temp);
            } catch (EmptyCellException e) {
                LOGGER.log(Level.INFO, "No dice remained! No added die on RoundTrack");
            }
            startNewRound();
        } else {
            currentPlayer = currentRoundOrderedPlayers.remove(0);
            hasPerformedMove = false;
            hasUsedTool = false;

            for (Player p : orderedPlayers) {
                if (!p.getUsername().equals(currentPlayer)) {
                    userViewMap.get(p.getUsername()).otherPlayerTurn(currentPlayer);
                }
            }

            usernameTimerMap.put(currentPlayer, new Timer());
            usernameTimerMap.get(currentPlayer).schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            LOGGER.log(Level.INFO, "Sending timeout");
                            userViewMap.get(currentPlayer).timeOut();
                            if (toolcardData!=null)
                                handlePlayerAfterIncorrectToolUse(currentPlayer, "You haven't finished to use the tool.", false);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            startNewTurn();
                        }
                    },
                    timerCostant
            );
            userViewMap.get(currentPlayer).startTurnMenu();
        }
    }

    private boolean isAllowed(String username) {
        return username.equals(currentPlayer);
    }

    @Override
    public void applyCommand(String playerUsername, ChosenWindowPatternCard command) {
        LOGGER.log(Level.INFO, "entra controller command" + command.getMessage());
        synchronized (mutex) {
            LOGGER.log(Level.INFO, "Dentro il mutex il comm" + command.getMessage());
            if (uninitializedOrderedPlayers.contains(usernamePlayerMap.get(playerUsername))) { //so the timer isn't finished yet
                LOGGER.log(Level.INFO, "Entra nell'if" + command.getMessage());
                ParserWindowPatternCard parser = null;
                try {
                    LOGGER.log(Level.INFO,"Added ti player" + command.getMessage());
                    parser = new ParserWindowPatternCard();
                    usernamePlayerMap.get(playerUsername).setWindowPatternCard(parser.parseCardByName(command.getMessage()));
                } catch (IOException e) {
                    LOGGER.log(Level.INFO, "Bad parser of Wpcs");
                }

                uninitializedOrderedPlayers.remove(usernamePlayerMap.get(playerUsername));
                orderedPlayers.add(usernamePlayerMap.get(playerUsername));
                if (uninitializedOrderedPlayers.isEmpty()) {
                    checkBlockingTimer.cancel();
                    startGame();
                }
            }
        }
    }

    @Override
    public synchronized void applyCommand(String playerUsername, MoveChoiceToolCard command) {
        Player current = usernamePlayerMap.get(playerUsername);
        //check turno giusto del giocatore
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (hasUsedTool) {
            userViewMap.get(playerUsername).invalidActionMessage("You have already used a tool for this turn!");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        if (toolcardData != null){
            System.out.println("Invalid: i sta chiedendo di utilizzare un tool ma ne sta utilizzando un altro");
            userViewMap.get(currentPlayer).invalidActionMessage("You have to finish the tool you are using before do something else!");
            executeAction();
            return;
        }

        //check abbastanza tokens
        Integer usedToolNumber = command.getNumberChosen(); //Referes to the toolcard used
        ToolCard chosen = model.getExtractedToolCard().get(usedToolNumber);
        int requiredTokens = 1;
        if (chosen.getTokenCount() > 0) {
            requiredTokens = 2;
        }
        if (current.getTokens() < requiredTokens) {
            handleCurrentPlayerAfterIncorrectRequest("You don't have enough tokens");
            return;
        }

        //check turno giusto per la toolcard
        if (chosen.getName().equals("Gavel")) {
            if (currentRoundOrderedPlayers.contains(playerUsername)) { //Can't use the tool, has to be in second turn
                handleCurrentPlayerAfterIncorrectRequest("You have to be in your second turn to use this turn. Use it later.");
                return;
            }
        } else if (chosen.getName().equals("Wheels Pincher")) {
            if (!currentRoundOrderedPlayers.contains(playerUsername)) { //Can't use the tool, has to be in first turn
                handleCurrentPlayerAfterIncorrectRequest( "You have to be in your first turn to use this tool");
                return;
            }
        }


        //inizializza toolcardData
        toolcardData = new ToolcardData(chosen.getName(), chosen.getActions());
        toolcardData.setOldModel(model);
        toolcardData.setRequiredTokensForLastToolUse(requiredTokens);
        toolcardData.setLastUsedToolCardNum(usedToolNumber);

        System.out.println("Assegnata lista toolcard: la prima è " + toolcardData.getToolcardActions().get(0));
        //fai cose:
        executeAction();
    }

    //TODO modifica e vedi se sono giusti gli afterTool
    private void handleCurrentPlayerAfterIncorrectRequest(String message) {
        userViewMap.get(currentPlayer).invalidActionMessage(message);
        userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    private void executeAction() {
        if (toolcardData.getToolcardActions().isEmpty()){
            System.out.println("handling for correct use");
            handlePlayerAfterCorrectToolUse(currentPlayer, toolcardData.getRequiredTokensForLastToolUse());
            return;
        }

        Action action = toolcardData.getToolcardActions().get(0);
        switch (action.getType()) {
            case ASK_PICK_DIE:
                userViewMap.get(currentPlayer).askPickDie(action.getParameter());
                break;
            case ASK_PLACE_DIE:
                userViewMap.get(currentPlayer).askPlaceDie();
                break;
            case ASK_INCREASE_DECREASE:
                userViewMap.get(currentPlayer).askIncreaseDecrease();
                break;
            case ASK_DIE_VALUE:
                userViewMap.get(currentPlayer).askDieValue();
                break;
            case ASK_ANOTHER_ACTION:
                userViewMap.get(currentPlayer).askAnotherAction();
                break;
            case DO_PLACE_DIE:
                executeDoPlaceDie(action.getParameter(), action.getParameter2());
                break;
            case DO_INCREASE_DECREASE:
                executeDoIncreaseDecrease();
                break;
            case DO_SWAP:
                executeDoSwap(action.getParameter(), action.getParameter2());
                break;
            case DO_SHUFFLE:
                executeDoShuffle();
                break;
            case DO_SHUFFLE_ALL:
                executeDoShuffleAll();
                break;
            case DO_FLIP:
                executeDoFlip();
                break;
            case DO_SAVE_COLOR:
                executeDoSaveColor();
                break;
            case CHECK_EXISTS_COLOR_RT:
                executeCheckExistsColorRT();
                break;
            case CHECK_SAME_COLOR:
                executeCheckSameColor();
                break;
            case CHECK_POSSIBLE_PLACEMENT:
                executeCheckPossiblePlacement();
                break;
            case CHECK_ANOTHER_ACTION:
                executeCheckAnotherAction();
                break;
            default:
                LOGGER.log(Level.INFO,"Problem nei tipi di Actions!!");
        }
    }

    private void executeCheckAnotherAction() {
        if (toolcardData.isAnotherAction()) {
            toolcardData.getToolcardActions().remove(0);
            executeAction();
        }
        else{
            handlePlayerAfterCorrectToolUse(currentPlayer, toolcardData.getRequiredTokensForLastToolUse());
        }
    }

    //TODO ordered players da settare ogni volta

    private void executeCheckPossiblePlacement() {
        try {
            Die tempDieToCheckPlacement = model.getDraftPool().getCell(toolcardData.getIndexFromDraftpool()).getAssociatedDie();
            if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().isPossibleToPlace(tempDieToCheckPlacement)){
                userViewMap.get(currentPlayer).messageBox("There is no possible placement for the die. It stays in DraftPool");
                handlePlayerAfterCorrectToolUse(currentPlayer, toolcardData.getRequiredTokensForLastToolUse());
            }
            else {
                toolcardData.getToolcardActions().remove(0);
                executeAction();
            }
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Dado non trovato!");
        }
    }

    private void executeCheckSameColor() {
        try {
            COLOR colorToCheck = usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(toolcardData.getIndexFromWPC()).getAssociatedDie().getColor();
            if (toolcardData.getSavedColor()!=colorToCheck){
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(currentPlayer).invalidActionMessage("The color of the chosen die is different from the previous one, try again!");
                userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
            else{
                toolcardData.getToolcardActions().remove(0);
                executeAction();
            }
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Dado non trovato!");
        }
    }

    private void executeCheckExistsColorRT() {
        try {
            COLOR colorToCheck = usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(toolcardData.getIndexFromWPC()).getAssociatedDie().getColor();
            if (model.getRoundTrack().isPresent(colorToCheck)){
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(currentPlayer).invalidActionMessage("The color of the chosen die is different from at least one of the Roundtrack, try again!");
                userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
            else {
                toolcardData.getToolcardActions().remove(0);
                executeAction();
            }
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Dado non trovato! Exists RT");
        }
    }

    private void executeDoSaveColor() {
        int dieIndexToSaveColor = toolcardData.getIndexFromDraftpool();
        try {
            toolcardData.setSavedColor(model.getDraftPool().getDie(dieIndexToSaveColor).getColor());
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Dado non trovato nel metodo executeDoSaveColor");
        }
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }

    private void executeDoFlip() {
        int indexDieToFlip = toolcardData.getIndexFromDraftpool();
        try {
            model.flipDraftPoolDie(indexDieToFlip);
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Dado non trovato nel metodo doFlip");
        }
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }


    private void executeDoShuffleAll() {
        model.rollDraftpoolDice();
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }


    private void executeDoShuffle() {
        try {
            model.rollDraftpoolDie(toolcardData.getIndexFromDraftpool());
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Errore in doShuffle");
        }
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }


    private void executeDoSwap(String parameter, String parameter2) {
        switch (parameter2) {
            //parameter is not useful, is always "RP".
            case "DB":
                Die tempDP = model.removeDieFromDraftPool(toolcardData.getIndexFromDraftpool());
                Die tempDB = model.extractDieFromDiceBag();
                model.setDieOnDraftPool(tempDB, toolcardData.getIndexFromDraftpool());
                model.insertDieInDiceBag(tempDP);
                break;
            case "RT":
                Die temp = model.removeDieFromDraftPool(toolcardData.getIndexFromDraftpool());
                Die tempRT = model.swapDieOnRoundTrack(temp, toolcardData.getIndexFromRoundTrack());
                model.setDieOnDraftPool(tempRT, toolcardData.getIndexFromDraftpool());
                break;
            default:
                LOGGER.log(Level.INFO,"Errore in doSwap, non arriva ne DB ne RT");
                break;
        }
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }
//TODO: se esegue una mossa, devo modificare la variabile


    private void executeDoIncreaseDecrease() {
        if (toolcardData.isIncreaseValue()){
            try {
                model.increaseDraftpoolDieValue(toolcardData.getIndexFromDraftpool(), true);
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                model.increaseDraftpoolDieValue(toolcardData.getIndexFromDraftpool(), false);
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
        }
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }


    private void executeDoPlaceDie(String parameter, String parameter2) {
        Die tempDieToPlace;
        switch (parameter) {
            case "WPC":
                int indexWpc = toolcardData.getIndexFromWPC();
                try {
                    tempDieToPlace = usernamePlayerMap.get(currentPlayer).getWindowPatternCard().removeDie(indexWpc);
                } catch (EmptyCellException e) {
                    e.printStackTrace();
                    System.out.println("Error: Non dovrebbe essere null!");
                    return;
                }
                break;
            case "DP":
                int indexDP = toolcardData.getIndexFromDraftpool();
                tempDieToPlace = model.removeDieFromDraftPool(indexDP);
                System.out.println("RIMOSSO DA DP DADO" + tempDieToPlace.getColor());
                break;
            default:
                LOGGER.log(Level.INFO,"Errore in doSwap, non arriva ne DB ne WPC");
                return;
        }

        switch (parameter2) {
            case "VALUE":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), true, false, true)) {
                    handlePlayerAfterIncorrectToolUse(currentPlayer, "You can't place the die here, check the toolcard effect and retry", false);
                    userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
                break;
            case "COLOR":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), false, true, true)) {
                    handlePlayerAfterIncorrectToolUse(currentPlayer, "You can't place the die here, check the toolcard effect and retry", false);
                    userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
                break;
            case "ADJACENT":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), true, true, false)) {
                    handlePlayerAfterIncorrectToolUse(currentPlayer, "You can't place the die here, check the toolcard effect and retry", false);
                    userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
                break;
            case "NONE":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), true, true, true)) {
                    handlePlayerAfterIncorrectToolUse(currentPlayer, "You can't place the die here, check the toolcard effect and retry", false);
                    userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
                break;
            default:
                LOGGER.log(Level.INFO,"Errore in doSwap, non arriva una stringa conosciuta");
                return;
        }
        System.out.println("PIAZZATO DADO:" + usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(toolcardData.getIndexToWPC()));

        model.setGamePlayers(orderedPlayers);
        toolcardData.getToolcardActions().remove(0);
        executeAction();

    }

    @Override
    public void applyCommand(String playerUsername, MoveChoiceDiePlacement command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (hasPerformedMove) {
            userViewMap.get(playerUsername).invalidActionMessage("You have already performed a move in this turn!");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        if (toolcardData != null){
            System.out.println("Invalid: i sta chiedendo di utilizzare una mossa ma ne sta utilizzando un tool.. altro");
            userViewMap.get(currentPlayer).invalidActionMessage("You have to finish the tool you are using before do something else!");
            executeAction();
        }
        LOGGER.log(Level.INFO, "Entra in moveChoice");
        try {
            Die toPlace = null;
            toPlace = model.getDraftPool().getDie(command.getDieDraftPoolPosition());
            if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                    .placeDie(toPlace, command.getDieSchemaPosition(),
                            true, true, true)) {
                userViewMap.get(playerUsername).invalidActionMessage(WRONG_PLACEMENT);
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            } else {
                LOGGER.log(Level.INFO, "Mossa applicata correttamente");
                model.removeDieFromDraftPool(command.getDieDraftPoolPosition());
                hasPerformedMove = true;
                model.setGamePlayers(orderedPlayers);
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
        } catch (EmptyCellException e) {
            handleCurrentPlayerAfterIncorrectRequest(EMPTY_DRAFTPOOL_INDEX);
        }
    }


    @Override
    public void applyCommand(String playerUsername, MoveChoicePassTurn command) {
        if (!orderedPlayers.contains(usernamePlayerMap.get(playerUsername))) {
            LOGGER.log(Level.INFO, "Match not started yet: no action");
            return;
        }
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }
        if (toolcardData != null){
            System.out.println("Invalid: i sta chiedendo di passare ma sta usando una tool");
            userViewMap.get(currentPlayer).invalidActionMessage("You have to finish the tool you are using before do something else!");
            executeAction();
            return;
        }

        usernameTimerMap.get(currentPlayer).cancel();
        startNewTurn();
    }

    @Override
    public void applyCommand(String playerUsername, ReplyPlaceDie command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (toolcardData == null || !toolcardData.getToolcardActions().get(0).getType().equals(ASK_PLACE_DIE)){
            userViewMap.get(playerUsername).invalidActionMessage("You can't do this action now!");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        //TODO metodo che controlla se la risposta è quella che mi aspetto
        if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard().getCell(command.getPosition()).hasDie()) {
            toolcardData.setIndexToWPC(command.getPosition());
        }
        else{
            handlePlayerAfterIncorrectToolUse(playerUsername, "The cell you've chosen isn't empty!", true);
            return;
        }
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }

    @Override
    public void applyCommand(String playerUsername, ReplyDieValue command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (toolcardData == null || !toolcardData.getToolcardActions().get(0).getType().equals(ASK_DIE_VALUE)){
            userViewMap.get(playerUsername).invalidActionMessage("You can't do this action now!");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        if (toolcardData.getDieValue()>6 || toolcardData.getDieValue()<1){
            handlePlayerAfterIncorrectToolUse(currentPlayer, "Value is incorrect, try again:", true);
            return;
        }
        model.changeDieValueOnDraftPool(toolcardData.getIndexFromDraftpool(), command.getValue());

        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }

    @Override
    public void applyCommand(String playerUsername, ReplyAnotherAction command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (toolcardData == null || !toolcardData.getToolcardActions().get(0).getType().equals(ASK_ANOTHER_ACTION)){
            userViewMap.get(playerUsername).invalidActionMessage("You can't do this action now!");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        toolcardData.setAnotherAction(command.isAnother());

        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }

    @Override
    public void applyCommand(String playerUsername, ReplyPickDie command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (toolcardData == null || !toolcardData.getToolcardActions().get(0).getType().equals(ASK_PICK_DIE)){
            userViewMap.get(playerUsername).invalidActionMessage("You can't do this action now!");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        switch (toolcardData.getToolcardActions().get(0).getParameter()){ //DA' NULL POINTER
            case "WPC":
                if (existsDieInCell("WPC", command.getIndex()))
                    toolcardData.setIndexFromWPC(command.getIndex());
                else {
                    handlePlayerAfterIncorrectToolUse(playerUsername, "The cell you chose is empty, try again", true);
                    return;
                }
                break;
            case "DP":
                if (existsDieInCell("DP", command.getIndex()))
                    toolcardData.setIndexFromDraftpool(command.getIndex());
                else {
                    handlePlayerAfterIncorrectToolUse(playerUsername, "The cell you chose is empty, try again", true);
                    return;
                }
                break;
            case "RT":
                if (existsDieInCell("RT", command.getIndex()))
                    toolcardData.setIndexFromRoundTrack(command.getIndex());
                else {
                    handlePlayerAfterIncorrectToolUse(playerUsername, "The cell you chose is empty, try again", true);
                    return;
                }
                break;
            default:
        }
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }

    private boolean existsDieInCell(String source, Integer index) {
        switch(source){
            case "WPC":
                if (usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(index).hasDie()){
                    return true;
                }
                break;
            case "DP":
                if (model.getDraftPool().getCell(index).hasDie()){
                    return true;
                }
                break;
            case "RT":
                if (model.getRoundTrack().getCell(index).hasDie()){
                    return true;
                }
                break;
            default:
                LOGGER.log(Level.INFO,"Problem in switch case of DP and WPC");
                break;
        }
        return false;
    }

    @Override
    public void applyCommand(String playerUsername, ReplyIncreaseDecrease command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (toolcardData == null || !toolcardData.getToolcardActions().get(0).getType().equals(ASK_INCREASE_DECREASE)){
            userViewMap.get(playerUsername).invalidActionMessage("You can't do this action now!");
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        try {
            int currentValue = model.getDraftPool().getCell(toolcardData.getIndexFromDraftpool()).getAssociatedDie().getValue();
            if ( (currentValue==6 && command.isIncrease())
                    || (currentValue==1 && !command.isIncrease())){
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(currentPlayer).invalidActionMessage("The selected die has 6, you can't increase that value");
                userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
        } catch (EmptyCellException e) {
            System.out.println("Empty cell! Can't happen here");
        }
        toolcardData.setIncreaseValue(command.isIncrease());
        toolcardData.getToolcardActions().remove(0);
        executeAction();
    }


    @Override
    public void applyCommand(String playerUsername, UndoActionCommand command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }
        if ((toolcardData != null)){
            if ((model.getExtractedToolCard().get(toolcardData.getLastUsedToolCardNum()).isReversible())
                    || (toolcardData.getToolcardActions().get(0).getType().equals(ASK_PICK_DIE))) {
                userViewMap.get(playerUsername).messageBox("Correctly got back");
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
            else{ //Not Reversible
                userViewMap.get(playerUsername).invalidActionMessage("You can't go back in this Tool!");
                executeAction();
                //TODO se sto eseguendo un action che non chiede interazioni all'utente? LAsso di tempo piccolissimo, ma pu capitare
            }
        }
    }

    private void handlePlayerAfterCorrectToolUse(String playerUsername, int tokenToDecrease) {
        usernamePlayerMap.get(playerUsername).decreaseTokens(tokenToDecrease);
        model.increaseToolCardTokens(toolcardData.getLastUsedToolCardNum(), tokenToDecrease); //TODO a volte non funziona firmy pastry brush
        this.hasUsedTool = true;
        model.setGamePlayers(orderedPlayers);
        editCurrentPlayerVariables();
        restoreTCGlobalVariables();
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    private void editCurrentPlayerVariables() {
        for (Action action : model.getExtractedToolCard().get(toolcardData.getLastUsedToolCardNum()).getActions()) {
            if (action.getType().equals(DO_PLACE_DIE) && action.hasParameter() && action.getParameter().equals("DP") && toolcardData.getToolcardActions().isEmpty()) {
                hasPerformedMove = true;
                System.out.println("Ha un piazzamento, setto performedMove = true");
            }
        }
        hasUsedTool = true;
    }

    //TODO gestisci il caso in cui l'utente sbaglia e nel frattempo arriva un timeout!
    private void handlePlayerAfterIncorrectToolUse(String playerUsername, String messageToSend, boolean sendContinue) {
        if (!sendContinue) {
            //Case in which is a timeOut
            if (model.getExtractedToolCard().get(toolcardData.getLastUsedToolCardNum()).isReversible()) {
                userViewMap.get(playerUsername).invalidActionMessage(messageToSend);
                resetModel();
                System.out.println("RESETTO MODEL DOPO AZIONE INVALIDA");
                restoreTCGlobalVariables();
            } else {
                //not reversible -> token decreased
                //stop eventual actions in progress
                usernamePlayerMap.get(playerUsername).decreaseTokens(toolcardData.getRequiredTokensForLastToolUse());
                model.increaseToolCardTokens(toolcardData.getLastUsedToolCardNum(), toolcardData.getRequiredTokensForLastToolUse());
                userViewMap.get(playerUsername).invalidActionMessage(messageToSend + "\n" + "ToolCard use completed");
                model.setGamePlayers(orderedPlayers);
                restoreTCGlobalVariables();
            }
        } else {
            //sending continue of tool use
            LOGGER.log(Level.INFO, messageToSend);
            userViewMap.get(playerUsername).invalidActionMessage(messageToSend + "\n" + "Try again:");
            executeAction();
        }
    }

    private void resetModel() {
        if (toolcardData==null){
            LOGGER.log(Level.INFO,"ERROR: The tool is finished, no data in toolcardData");
            return;
        }
        Model temp = toolcardData.removeOldModel();
        System.out.println("SONO UGUALI= " + temp.equals(model) + "!");
        model = temp;//toolcardData.removeOldModel();
        model.notifyRefreshBoard(null, orderedPlayers);
    }


    private void restoreTCGlobalVariables() {
        toolcardData=null;
    }

    @Override
    public void applyCommand(String playerUsername, ClientToServerCommand command) {
        LOGGER.log(Level.INFO, "You shouldn't be here");
    }


    /**
     * Getters
     */


    public List<List<String>> getOrderedRoundPlayers() {
        return orderedRoundPlayers;
    }

    public List<String> getCurrentRoundOrderedPlayers() {
        return currentRoundOrderedPlayers;
    }

    public boolean isHasUsedTool() {
        return hasUsedTool;
    }

    public boolean isHasPerformedMove() {
        return hasPerformedMove;
    }

    public String getCurrentPlayer() {

        return currentPlayer;
    }


    public Model getModel() {
        return model;
    }

    Map<String, View> getUserViewMap() {
        return userViewMap;
    }


    public boolean isActive() {
        return active;
    }

    public void setInactive(){
        active = false;
        for (String username : usernameTimerMap.keySet()){
            usernameTimerMap.get(username).cancel();
        }
        usernameTimerMap.clear(); //TODO: metti un unico timer
    }

    @Override
    public void update(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.visit(this);
    }
}