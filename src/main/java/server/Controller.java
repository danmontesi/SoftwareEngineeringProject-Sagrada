package server;

import shared.CONSTANTS;
import shared.commands.client_to_server_command.*;
import shared.exceptions.EmptyCellException;
import server.model.*;
import server.model.public_obj_cards.PublicObjectiveCard;
import server.parser.ParserWindowPatternCard;
import shared.utils.ControllerServerInterface;
import shared.utils.Observer;
import shared.View;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static server.model.ACTION_TYPE.*;

/**
 * This is the main Controller of the game.
 * It manages game logic
 * @author  Daniele Montesi
 */
public class Controller implements Observer, ControllerServerInterface { //Observer perchè osserva la View tramite le classi di mezzo (ClientConnection)

    private static final String EMPTY_CELL = "The cell you selected is wrong or our of index, try again";
    private static final String WRONG_PLACEMENT = "The placement is incorrect. Check the rules of Sagrada";
    private static final String EMPTY_DRAFTPOOL_INDEX = "The draft pool die you selected doesn't exits. Try again";
    private static final String WRONG_GIVEN_VALUE = "The value you sent is incorrect";
    private static final String NOT_FINISHED_TOOLUSE = "You haven't finished to use the tool";

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private static final String NOT_YOUR_TURN = "It's not your turn, you can't do any action.";
    private static final String ALREADY_MOVE_PERFORMED = "You have already performed a move" ;
    private static final String STILL_USING_TOOL = "You haven't finished to use a ToolCard";
    private static final String ALREADY_TOOL_USED = "You have already used a tool for this turn";
    private static final String INCORRECT_REPLY = "You can't do that now";
    private static final String CANT_UNDO_TOOL = "You can't go back with this tool!";
    private static final String CORRECT_UNDO_TOOL = "Correctly got back!";
    private static final String COMPLETED_TOOL_USE = "ToolCard use completed";

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

    private List<List<String>> orderedRoundPlayers;
    private List<String> currentRoundOrderedPlayers;
    private String currentPlayer;
    private boolean hasUsedTool;
    private boolean hasPerformedMove;
    private int timerCostant;

    //List of Actions

    /**
     * Constructor
     * @param usernameList list of players usernames
     * @param doNotStartGame specifies to not start the game, just build the Model and View (not VirtualView) and set Players
     */
    public Controller(List<String> usernameList, boolean doNotStartGame) {
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

        if (doNotStartGame){
            for (String username : usernameList) {
                View tempView = new View(this);
                userViewMap.put(username, tempView);
                model.register(tempView);
                tempView.setUsername(username);
            }
            this.timerCostant = 10000000; //is not necessary for testing
        }
        else {
            for (String username : usernameList) {
                View tempView = new VirtualView(this, username);
                userViewMap.put(username, tempView);
                model.register(tempView);
            }
            this.timerCostant = CONSTANTS.TURN_TIMER;
        }

        this.orderedPlayers = new ArrayList<>();
        // Now I will start each player's View
        for (String username : usernamePlayerMap.keySet())
            userViewMap.get(username).startGame(); //notifying game starting

        if(!doNotStartGame)
            initializeGame();
    }


    /**
     * Allows the players to choose their Window Pattern Cards and calls a method that waits until everyone has chosen.
     * Once the Controller received the player's choice, it moves his username to orderedPlayers List
     */
    public void initializeGame() {
        List<WindowPatternCard> localWpc;

        for (Player p : uninitializedOrderedPlayers) {
            // I give the cards (in strings) to the command, and to the method that waits until all players finished choosing
            localWpc = model.extractWindowPatternCard();

            List<List<String>> localWpcsString = new ArrayList<>();
            List<Integer> wpcDifficulties = new ArrayList<>();
            for (int i = 0; i < localWpc.size(); i++) {
                localWpcsString.add(localWpc.get(i).wpcPathRepresentation());
                wpcDifficulties.add(localWpc.get(i).getDifficulty());
            }

            WindowPatternCard defaultCard = localWpc.get(0); //default wpc in case the player disconnects
            usernamePlayerMap.get(p.getUsername()).setWindowPatternCard(defaultCard);
            userViewMap.get(p.getUsername()).chooseWindowPatternCardMenu(localWpcsString, model.getPlayerFromUsername(p.getUsername()).getPrivateObjectiveCard().getName(), wpcDifficulties);
        }
        checkBlockingTimer = new Timer(); //Is a unique timer for every player. Is starts the game stopping players without waiting the answer
        checkBlockingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (mutex) {
                    LOGGER.log(Level.INFO, "Last call for choosing Wpc: starting the game with disconnected Players");
                    if (!uninitializedOrderedPlayers.isEmpty()) {
                        for (Player p : uninitializedOrderedPlayers) {
                            userViewMap.get(p.getUsername()).timeOut();
                            orderedPlayers.add(p);
                            LOGGER.log(Level.INFO, "Ordered players: added after timeout: " + p.getUsername());
                        }
                        uninitializedOrderedPlayers.clear();
                        startGame();
                    }
                }
            }
        },
        CONSTANTS.WPC_TIMER);
    }

    /**
     * Initializes all lists of players for each round, ordered
     */
    private void assignRoundPlayers(List<Player> orderedPlayers) {
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

    /**
     * Starts game
     */
    private void startGame() {
        assignRoundPlayers(orderedPlayers);
        model.notifyRefreshBoard(orderedPlayers);
        startNewRound();
    }

    /**
     * Ends game and sends game outcome and ranking to players
     */
    private void endGame() {
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

    /**
     * Sends game outcome to players
     */
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

    /**
     * Calculates empty Window Pattern Card cells penalties
     */
    private Integer penaltyScore(WindowPatternCard card) {
        int tempScore = 0;
        for (Cell c : card.getSchema()) {
            tempScore += c.isEmpty() ? 1 : 0;
        }
        return tempScore;
    }

    /**
     * Starts a new Round
     */
    private void startNewRound() {
        if (orderedRoundPlayers.isEmpty()) {
            endGame();
        } else {
            model.setDraftPool(orderedPlayers.size());
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
                        LOGGER.log(Level.INFO, "Sending timeout to Player "+ currentPlayer);
                        userViewMap.get(currentPlayer).timeOut();
                        if (toolcardData!=null) {
                            handlePlayerAfterIncorrectToolUse(currentPlayer, NOT_FINISHED_TOOLUSE, false);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
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
     * Starts a new turn
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
            model.setCurrentPlayer(model.getPlayerFromUsername(currentPlayer));

            usernameTimerMap.put(currentPlayer, new Timer());
            usernameTimerMap.get(currentPlayer).schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        LOGGER.log(Level.INFO, "Sending timeout");
                        userViewMap.get(currentPlayer).timeOut();
                        if (toolcardData!=null)
                            handlePlayerAfterIncorrectToolUse(currentPlayer, NOT_FINISHED_TOOLUSE, false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
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
     * Checks whether it is the player's turn or not
     */
    private boolean isAllowed(String username) {
        return username.equals(currentPlayer);
    }

    @Override
    public void applyCommand(String playerUsername, ChosenWindowPatternCard command) {
        synchronized (mutex) {
            if (uninitializedOrderedPlayers.contains(usernamePlayerMap.get(playerUsername))) { //means the timer isn't finished yet
                ParserWindowPatternCard parser;
                try {
                    parser = new ParserWindowPatternCard();
                    usernamePlayerMap.get(playerUsername).setWindowPatternCard(parser.parseCardByName(command.getMessage()));
                } catch (IOException e) {
                    LOGGER.log(Level.INFO, "Error in json of Wpcs");
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

        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (hasUsedTool) {
            userViewMap.get(playerUsername).invalidActionMessage(ALREADY_TOOL_USED);
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        if (hasPerformedMove) {
            for (Action action : model.getExtractedToolCard().get(command.getNumberChosen()).getActions()) {
                if (action.getType().equals(DO_PLACE_DIE) && action.hasParameter() && action.getParameter().equals("DP")) {
                    userViewMap.get(playerUsername).invalidActionMessage(ALREADY_MOVE_PERFORMED + "\nYou can't use a tool that let you move a die");
                    userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
                    return;
                }
            }
        }

        if (toolcardData != null) {
            userViewMap.get(currentPlayer).invalidActionMessage(STILL_USING_TOOL);
            executeAction();
            return;
        }

        Integer usedToolNumber = command.getNumberChosen(); //Referres to the ToolCard used
        ToolCard chosen = model.getExtractedToolCard().get(usedToolNumber);
        int requiredTokens = 1;
        if (chosen.getTokenCount() > 0) {
            requiredTokens = 2;
        }

        if (current.getTokens() < requiredTokens) {
            handleCurrentPlayerAfterIncorrectRequest("You don't have enough tokens");
            return;
        }

        //check the correct turn of the player before the use of the tool
        if (chosen.getName().equals("Gavel")) {
            if (currentRoundOrderedPlayers.contains(playerUsername)) {
                handleCurrentPlayerAfterIncorrectRequest("You have to be in your second turn to use this turn");
                return;
            }
        } else if (chosen.getName().equals("Wheels Pincher") && !currentRoundOrderedPlayers.contains(playerUsername)) {
            handleCurrentPlayerAfterIncorrectRequest("You have to be in your first turn to use this tool");
            return;
        }

        //initializes the container of toolCard information for this turn
        toolcardData = new ToolcardData(chosen.getName(), chosen.getActions(), current);
        toolcardData.setRequiredTokensForLastToolUse(requiredTokens);
        toolcardData.setLastUsedToolCardNum(usedToolNumber);

        executeAction();
    }


    /**
     * Handles incorrect requests from the current player
     * @param message message to send
     */
    private void handleCurrentPlayerAfterIncorrectRequest(String message) {
        userViewMap.get(currentPlayer).invalidActionMessage(message);
        userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Executes the Tool Cards actions.
     * Each Tool Card is composed of micro actions.
     * Every micro action has a type:
     * ASK_-: asks the player for information and waits until he replies. If a timeout occurs newTurn is called
     * CHECK_: checks the player's reply correctness
     * DO_-: does actions depending on the specific micro action
     * This method checks the type and calls the proper methods to execute the micro action.
     * When a micro action is finished, the stack of actions pushes and calls this method, as long as the list has elements
     */
    private void executeAction() {
        if (toolcardData.getToolCardActions().isEmpty()){
            handlePlayerAfterCorrectToolUse(currentPlayer, toolcardData.getRequiredTokensForLastToolUse());
            return;
        }
        Action action = toolcardData.getToolCardActions().get(0);
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
                LOGGER.log(Level.INFO,"Problem in json types");
        }
    }

    /**
     * Checks whether the player decided to do another action or not
     */
    private void executeCheckAnotherAction() {
        if (toolcardData.isAnotherAction()) {
            toolcardData.getToolCardActions().remove(0);
            executeAction();
        } else{
            handlePlayerAfterCorrectToolUse(currentPlayer, toolcardData.getRequiredTokensForLastToolUse());
        }
    }

    /**
     * Checks whether it is possible to place the selected die or not
     */
    private void executeCheckPossiblePlacement() {
        try {
            Die tempDieToCheckPlacement = model.getDraftPool().getCell(toolcardData.getIndexFromDraftPool()).getAssociatedDie();
            if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().isPossibleToPlace(tempDieToCheckPlacement)){
                userViewMap.get(currentPlayer).messageBox("There is no possible placement for the new die. It stays in DraftPool");
                handlePlayerAfterCorrectToolUse(currentPlayer, toolcardData.getRequiredTokensForLastToolUse());
            } else {
                toolcardData.getToolCardActions().remove(0);
                executeAction();
            }
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Can't happen, checked before");
        }
    }

    /**
     * Checks whether the selected die's color is colorToCheck
     */
    private void executeCheckSameColor() {
        try {
            COLOR colorToCheck = usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(toolcardData.getIndexFromWPC()).getAssociatedDie().getColor();
            if (toolcardData.getSavedColor().equals(colorToCheck)){
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(currentPlayer).invalidActionMessage("The color of the chosen die is different from the previous one, try again!");
                userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
            } else{
                toolcardData.getToolCardActions().remove(0);
                executeAction();
            }
        } catch (EmptyCellException e) {
            LOGGER.log(Level.INFO,"Can't happen, checked before");
        }
    }

    /**
     * Checks whether there is a die on the Round Track the same color as the selected die
     */
    private void executeCheckExistsColorRT() {
        try {
            COLOR colorToCheck = usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(toolcardData.getIndexFromWPC()).getAssociatedDie().getColor();
            if (!model.getRoundTrack().isPresent(colorToCheck)){
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(currentPlayer).invalidActionMessage("The color of the chosen die is different from at least one of the Roundtrack, try again!");
                userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
            } else {
                toolcardData.getToolCardActions().remove(0);
                executeAction();
            }
        } catch (EmptyCellException e) {
            LOGGER.log(Level.WARNING,"Error in empty cell, can't happen, checked before ");
        }
    }

    /**
     * Saves the selected die's color
     */
    private void executeDoSaveColor() {
        int dieIndexToSaveColor = toolcardData.getIndexFromDraftPool();
        try {
            toolcardData.setSavedColor(model.getDraftPool().getDie(dieIndexToSaveColor).getColor());
        } catch (EmptyCellException e) {
            LOGGER.log(Level.WARNING," Error in empty cell, can't happen, checked before");
        }
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    /**
     * Flips the selected die
     */
    private void executeDoFlip() {
        int indexDieToFlip = toolcardData.getIndexFromDraftPool();
        try {
            model.flipDraftPoolDie(indexDieToFlip);
        } catch (EmptyCellException e) {
            LOGGER.log(Level.WARNING,"Error in empty cell, can't happen, checked before");
        }
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    /**
     * Shuffles all dice in the Draft Pool
     */
    private void executeDoShuffleAll() {
        model.rollDraftpoolDice();
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    /**
     * Shuffles the selected die
     */
    private void executeDoShuffle() {
        try {
            model.rollDraftpoolDie(toolcardData.getIndexFromDraftPool());
        } catch (EmptyCellException e) {
            LOGGER.log(Level.WARNING,"Error that can't happen, checked before ");
        }
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    /**
     * Swaps the selected dice
     * @param parameter first source (always DP, useless for now, but left for future implementations (other Toolcards, new rules, ..)
     * @param parameter2 second die
     */
    private void executeDoSwap(String parameter, String parameter2) {
        switch (parameter2) {
            case "DB":
                Die tempDP = model.removeDieFromDraftPool(toolcardData.getIndexFromDraftPool());
                Die tempDB = model.extractDieFromDiceBag();
                model.setDieOnDraftPool(tempDB, toolcardData.getIndexFromDraftPool());
                model.insertDieInDiceBag(tempDP);
                break;
            case "RT":
                Die temp = model.removeDieFromDraftPool(toolcardData.getIndexFromDraftPool());
                Die tempRT = model.swapDieOnRoundTrack(temp, toolcardData.getIndexFromRoundTrack());
                model.setDieOnDraftPool(tempRT, toolcardData.getIndexFromDraftPool());
                break;
            default:
                LOGGER.log(Level.WARNING,"Error, caused by json");
                break;
        }
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    /**
     * Increases or decreases the selected die's value depending on the player's choice
     */
    private void executeDoIncreaseDecrease() {
        if (toolcardData.isIncreaseValue()){
            try {
                model.increaseDraftpoolDieValue(toolcardData.getIndexFromDraftPool(), true);
            } catch (EmptyCellException e) {
                LOGGER.log(Level.WARNING,"Error that can't happen, checked before!");
            }
        } else{
            try {
                model.increaseDraftpoolDieValue(toolcardData.getIndexFromDraftPool(), false);
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
        }
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    /**
     * Place the die from the index saved, depending form the parameter given
     * @param parameter the source of the die to remove
     * @param parameter2 the restriction to ignore during the check placement
     */
    private void executeDoPlaceDie(String parameter, String parameter2) {
        Die tempDieToPlace;
        switch (parameter) {
            case "WPC":
                int indexWpc = toolcardData.getIndexFromWPC();
                try {
                    tempDieToPlace = usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(indexWpc).removeDie();
                } catch (EmptyCellException e) {
                    LOGGER.log(Level.WARNING,"Error that can't happen, checked before");
                    return;
                }
                break;
            case "DP":
                int indexDP = toolcardData.getIndexFromDraftPool();
                try {
                    tempDieToPlace = model.getDraftPool().getDie(indexDP);
                } catch (EmptyCellException e) {
                    LOGGER.log(Level.WARNING," Error that can't happen, checked before");
                    return;
                }
                break;
            default:
                LOGGER.log(Level.INFO, "Error in json ");
                return;
        }

        boolean isPlaced = true;

        switch (parameter2) {
            case "VALUE":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), true, false, true)) {
                    isPlaced = false;
                }
                break;
            case "COLOR":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), false, true, true)) {
                    isPlaced = false;
                }
                break;
            case "ADJACENT":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), true, true, false)) {
                    isPlaced = false;
                }
                break;
            case "NONE":
                if (!usernamePlayerMap.get(currentPlayer).getWindowPatternCard().placeDie(tempDieToPlace, toolcardData.getIndexToWPC(), true, true, true)) {
                    isPlaced = false;
                }
                break;
            default:
                LOGGER.log(Level.INFO, "Error in json");
                return;
        }

        if (parameter.equals("WPC")) {
            int indexWpc = toolcardData.getIndexFromWPC();
            usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(indexWpc).setAssociatedDie(tempDieToPlace);
            model.setGamePlayers(orderedPlayers);
        }
        if (!isPlaced) {
            handlePlayerAfterIncorrectToolUse(currentPlayer, WRONG_PLACEMENT, false);
            userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        switch (parameter) {
            case "WPC":
                int indexWpc = toolcardData.getIndexFromWPC();
                toolcardData.setIndexDieBeforeMoved(toolcardData.getIndexFromWPC());
                try {
                    usernamePlayerMap.get(currentPlayer).getWindowPatternCard().removeDie(indexWpc);
                    model.setGamePlayersNoRefresh(orderedPlayers);
                } catch (EmptyCellException e) {
                    LOGGER.log(Level.WARNING,"Error that can't happen, checked before");
                }
                break;
            case "DP":
                int indexDP = toolcardData.getIndexFromDraftPool();
                toolcardData.setIndexDieBeforeMoved(toolcardData.getIndexFromDraftPool());
                model.removeDieFromDraftPool(indexDP);
                break;
            default:
                LOGGER.log(Level.INFO, " Error in json");
                return;
        }

        toolcardData.setSource(parameter);
        toolcardData.setIndexMovedDie(toolcardData.getIndexToWPC());
        toolcardData.setHasDoneMove();
        model.setGamePlayers(orderedPlayers);
        toolcardData.getToolCardActions().remove(0);
        executeAction();

    }

    @Override
    public void applyCommand(String playerUsername, MoveChoiceDiePlacement command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }

        if (hasPerformedMove) {
            userViewMap.get(playerUsername).invalidActionMessage(ALREADY_MOVE_PERFORMED);
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        if (toolcardData != null){
            userViewMap.get(currentPlayer).invalidActionMessage(NOT_FINISHED_TOOLUSE);
            executeAction();
        }

        try {
            Die toPlace = null;
            toPlace = model.getDraftPool().getDie(command.getDieDraftPoolPosition());
            if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                    .placeDie(toPlace, command.getDieSchemaPosition(),
                            true, true, true)) {
                userViewMap.get(playerUsername).invalidActionMessage(WRONG_PLACEMENT);
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            } else {
                LOGGER.log(Level.INFO,"Move correctly performed");
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
            userViewMap.get(currentPlayer).invalidActionMessage(NOT_FINISHED_TOOLUSE);
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
        if (toolcardData == null || !toolcardData.getToolCardActions().get(0).getType().equals(ASK_PLACE_DIE)){
            userViewMap.get(playerUsername).invalidActionMessage(INCORRECT_REPLY);
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }

        if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard().getCell(command.getPosition()).hasDie()) {
            toolcardData.setIndexToWPC(command.getPosition());
        } else{
            handlePlayerAfterIncorrectToolUse(playerUsername, WRONG_PLACEMENT, true);
            return;
        }
        toolcardData.getToolCardActions().remove(0);
        executeAction();

    }

    @Override
    public void applyCommand(String playerUsername, ReplyDieValue command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }
        if (toolcardData == null || !toolcardData.getToolCardActions().get(0).getType().equals(ASK_DIE_VALUE)){
            userViewMap.get(playerUsername).invalidActionMessage(INCORRECT_REPLY);
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        if (!(toolcardData.getDieValue()>6 || toolcardData.getDieValue()<1)){
            handlePlayerAfterIncorrectToolUse(currentPlayer, WRONG_GIVEN_VALUE + "Try again:", true);
            return;
        }
        model.changeDieValueOnDraftPool(toolcardData.getIndexFromDraftPool(), command.getValue());
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    @Override
    public void applyCommand(String playerUsername, ReplyAnotherAction command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }
        if (toolcardData == null || !toolcardData.getToolCardActions().get(0).getType().equals(ASK_ANOTHER_ACTION)){
            userViewMap.get(playerUsername).invalidActionMessage(INCORRECT_REPLY);
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        toolcardData.setAnotherAction(command.isAnother());
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }

    @Override
    public void applyCommand(String playerUsername, ReplyPickDie command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }
        if (toolcardData == null || !toolcardData.getToolCardActions().get(0).getType().equals(ASK_PICK_DIE)){
            userViewMap.get(playerUsername).invalidActionMessage(NOT_FINISHED_TOOLUSE);
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        switch (toolcardData.getToolCardActions().get(0).getParameter()){ //DA' NULL POINTER
            case "WPC":
                if (existsDieInCell("WPC", command.getIndex()))
                    toolcardData.setIndexFromWPC(command.getIndex());
                else {
                    handlePlayerAfterIncorrectToolUse(playerUsername,EMPTY_CELL + " Try again:", true);
                    return;
                }
                break;
            case "DP":
                if (existsDieInCell("DP", command.getIndex()))
                    toolcardData.setIndexFromDraftPool(command.getIndex());
                else {
                    handlePlayerAfterIncorrectToolUse(playerUsername, EMPTY_CELL + "Try again: ", true);
                    return;
                }
                break;
            case "RT":
                if (existsDieInCell("RT", command.getIndex()))
                    toolcardData.setIndexFromRoundTrack(command.getIndex());
                else {
                    handlePlayerAfterIncorrectToolUse(playerUsername, EMPTY_CELL + "Try again:", true);
                    return;
                }
                break;
            default:
        }
        toolcardData.getToolCardActions().remove(0);
        executeAction();
    }


    @Override
    public void applyCommand(String playerUsername, ReplyIncreaseDecrease command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage(NOT_YOUR_TURN);
            return;
        }
        if (toolcardData == null || !toolcardData.getToolCardActions().get(0).getType().equals(ASK_INCREASE_DECREASE)){
            userViewMap.get(playerUsername).invalidActionMessage(INCORRECT_REPLY);
            userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            return;
        }
        try {
            int currentValue = model.getDraftPool().getCell(toolcardData.getIndexFromDraftPool()).getAssociatedDie().getValue();
            if ( (currentValue==6 && command.isIncrease())
                    || (currentValue==1 && !command.isIncrease())){
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(currentPlayer).invalidActionMessage("The selected die has value '6', you can't increase that value");
                userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
                return;
            }
        } catch (EmptyCellException e) {
            LOGGER.log(Level.WARNING,"Empty cell can't happen here, it was checked before");
        }
        toolcardData.setIncreaseValue(command.isIncrease());
        toolcardData.getToolCardActions().remove(0);
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
                    || (toolcardData.getToolCardActions().get(0).getType().equals(ASK_PICK_DIE))) {
                userViewMap.get(playerUsername).messageBox(CORRECT_UNDO_TOOL);
                resetModel();
                restoreTCGlobalVariables();
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
            else{ //Not Reversible
                userViewMap.get(playerUsername).invalidActionMessage(CANT_UNDO_TOOL);
                if (toolcardData.getToolCardActions().get(0).getType().toString().contains("ASK")) {
                    executeAction();
                }
            }
        }
    }


    @Override
    public void applyCommand(String playerUsername, ClientToServerCommand command) {
        LOGGER.log(Level.INFO, "You shouldn't be here!");
    }


    /**
     * Updates tokens after a Tool Card use and allows the turn to go on
     */
    private void handlePlayerAfterCorrectToolUse(String playerUsername, int tokenToDecrease) {
        //If the tool is Wheels pincher, i have to remove the username to the list of next username turns
        if (toolcardData.getToolCardName().equals("Wheels Pincher")){
            currentRoundOrderedPlayers.remove(playerUsername);
        }
        usernamePlayerMap.get(playerUsername).decreaseTokens(tokenToDecrease);
        model.increaseToolCardTokens(toolcardData.getLastUsedToolCardNum(), tokenToDecrease);
        this.hasUsedTool = true;
        model.setGamePlayers(orderedPlayers);
        editCurrentPlayerVariables();
        restoreTCGlobalVariables();
        userViewMap.get(playerUsername).messageBox(COMPLETED_TOOL_USE);
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    /**
     * Updates the players variables
     */
    private void editCurrentPlayerVariables() {
        for (Action action : model.getExtractedToolCard().get(toolcardData.getLastUsedToolCardNum()).getActions()) {
            if (action.getType().equals(DO_PLACE_DIE) && action.hasParameter() && action.getParameter().equals("DP") && toolcardData.getToolCardActions().isEmpty()) {
                hasPerformedMove = true;
            }
        }
        hasUsedTool = true;
    }

    /**
     * Updates variables after an incorrect Tool Card use and allows the turn to go on
     */
    private void handlePlayerAfterIncorrectToolUse(String playerUsername, String messageToSend, boolean sendContinue) {
        if (!sendContinue) {
            //Case in which is a timeOut
            if (model.getExtractedToolCard().get(toolcardData.getLastUsedToolCardNum()).isReversible()) {
                userViewMap.get(playerUsername).invalidActionMessage(messageToSend);
                resetModel();
                restoreTCGlobalVariables();
            } else {
                //not reversible -> token decreased
                //stop eventual actions in progress
                usernamePlayerMap.get(playerUsername).decreaseTokens(toolcardData.getRequiredTokensForLastToolUse());
                model.increaseToolCardTokens(toolcardData.getLastUsedToolCardNum(), toolcardData.getRequiredTokensForLastToolUse());
                userViewMap.get(playerUsername).invalidActionMessage(messageToSend + "\n" + COMPLETED_TOOL_USE);
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

    /**
     * Reset the previous game state after an incorrect action
     */
    private void resetModel() {
        if (toolcardData==null){
            return;
        }
        if (toolcardData.hasDoneMove()) {
            Die temp = null; //indice WPC ORA
            try {
                temp = usernamePlayerMap.get(currentPlayer).getWindowPatternCard().removeDie(toolcardData.getIndexMovedDie());
            } catch (EmptyCellException e) {
                System.out.println("Should not happen, just checked before");
            }
            switch (toolcardData.getSource()) {
                case "WPC":
                    int indexWpc = toolcardData.getIndexDieBeforeMoved();
                    usernamePlayerMap.get(currentPlayer).getWindowPatternCard().getCell(indexWpc).setAssociatedDie(temp);
                    break;
                case "DP":
                    int indexDP = toolcardData.getIndexDieBeforeMoved();
                    model.setDieOnDraftPool(temp, indexDP);
                    break;
                default:
                    LOGGER.log(Level.INFO, "Error in json file");
                    return;
            }
            model.setGamePlayers(orderedPlayers);
        }
    }

    /**
     * Checks whether there is a die on the given cell or not
     * @param source Window Pattern Card, Draft Pool or Round Track
     * @param index cell position
     * @return true if there is a die on the cell, false otherwise
     */
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
                LOGGER.log(Level.INFO,"Problem in json");
                break;
        }
        return false;
    }

    /**
     * Restores toolcardData
     */
    private void restoreTCGlobalVariables() {
        toolcardData=null;
    }

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

    boolean isActive() {
        return active;
    }

    void setInactive(){
        active = false;
        for (String username : usernameTimerMap.keySet()){
            usernameTimerMap.get(username).cancel();
        }
        usernameTimerMap.clear();
    }

    public Map<String, Timer> getUsernameTimerMap() {
        return usernameTimerMap;
    }

    @Override
    public void update(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.visit(this);
    }

}