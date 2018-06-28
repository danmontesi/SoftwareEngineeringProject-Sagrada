package it.polimi.se2018.network.server;

import it.polimi.se2018.CONSTANTS;
import it.polimi.se2018.commands.client_to_server_command.new_tool_commands.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Observer, ControllerServerInterface { //Observer perchè osserva la View tramite le classi di mezzo (ClientConnection)


    private static final String WRONG_INDEX = "The cell you selected is wrong or our of index, try again!";
    private static final String WRONG_PLACEMENT = "The placement is incorrect. Check the rules of Sagrada";
    private static final String EMPTY_INDEX = "The cell you selected is empty, try again!";
    private static final String EMPTY_DRAFTPOOL_INDEX = "The draftpool die you selected doesn't exits! Try again";

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    private Model model;
    private HashMap<String, Player> usernamePlayerMap;
    private HashMap<String, View> userViewMap;
    private ArrayList<Player> orderedPlayers;
    private ArrayList<Player> uninitializedOrderedPlayers;
    private HashMap<String, Timer> usernameTimerMap;
    private int requiredTokensForLastToolUse;
    private int lastUsedToolCardNum;

    private Die extractedDieForFirmPastryThinner;
    private Integer randomValueForFirmPastryBrush;

    public Map<String, Timer> getUsernameTimerMap() { //just for testing
        return usernameTimerMap;
    }

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

    /**
     * ArrayList that contains the ordered players that has to play
     * is created by the model in its constructor
     */
    private ArrayList<ArrayList<String>> orderedRoundPlayers;
    private ArrayList<String> currentRoundOrderedPlayers;

    /**
     * Represent current player. That is necessary to know which is the player i'm expecting an answer
     */
    private String currentPlayer;
    private boolean hasUsedTool;
    private boolean hasPerformedMove;
    private int timerCostant;

    //List of Actions

    public Controller(List<String> usernameList) {
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

        this.timerCostant = 600000;
        // Now I will start each player's View
        for (String username : usernamePlayerMap.keySet())
            userViewMap.get(username).startGame(); //notifying game starting
    }

    public void initializeGame() {
        //Let people chose their Wpc, and call a method that waits until all chose theirs.
        //Once i receive all -> move to orderedPlayers List
        ArrayList<WindowPatternCard> localWpc;

        //Gives to each a player 4 WindowPatternCard to choose from
        for (Player p : uninitializedOrderedPlayers) {
            // I give the cards (in strings) to the command, and to the method that waits until all players finishes to chose
            localWpc = model.extractWindowPatternCard();
            WindowPatternCard defaultCard = localWpc.get(0); //default wpc in case the player disconnects
            usernamePlayerMap.get(p.getUsername()).setWindowPatternCard(defaultCard);
            LOGGER.log(Level.INFO, "invio command CHOOSEWPC a player:" + p.getUsername());
            userViewMap.get(p.getUsername()).chooseWindowPatternCardMenu(localWpc);
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
                100000); //TODO timer
    }

    /**
     * Initializes all Lists of players for each round, ordered.
     */
    private void assignRoundPlayers(ArrayList<Player> orderedPlayers) {
        orderedRoundPlayers = new ArrayList<>();
        if (orderedPlayers.size() == 4) {
            String c0 = orderedPlayers.get(0).getUsername();
            String c1 = orderedPlayers.get(1).getUsername();
            String c2 = orderedPlayers.get(2).getUsername();
            String c3 = orderedPlayers.get(3).getUsername();
            ArrayList<String> temp0 = new ArrayList<>();
            ArrayList<String> temp1 = new ArrayList<>();
            ArrayList<String> temp2 = new ArrayList<>();
            ArrayList<String> temp3 = new ArrayList<>();
            temp0.add(c0);
            temp0.add(c1);
            temp0.add(c2);
            temp0.add(c3);
            temp0.add(c3);
            temp0.add(c2);
            temp0.add(c1);
            temp0.add(c0);
            temp1.add(c1);
            temp1.add(c2);
            temp1.add(c3);
            temp1.add(c0);
            temp1.add(c0);
            temp1.add(c3);
            temp1.add(c2);
            temp1.add(c1);
            temp2.add(c2);
            temp2.add(c3);
            temp2.add(c0);
            temp2.add(c1);
            temp2.add(c1);
            temp2.add(c0);
            temp2.add(c3);
            temp2.add(c2);
            temp3.add(c3);
            temp3.add(c0);
            temp3.add(c1);
            temp3.add(c2);
            temp3.add(c2);
            temp3.add(c1);
            temp3.add(c0);
            temp3.add(c3);
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp2.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp3.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp2.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp3.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
        } else if (orderedPlayers.size() == 3) {
            String c0 = orderedPlayers.get(0).getUsername();
            String c1 = orderedPlayers.get(1).getUsername();
            String c2 = orderedPlayers.get(2).getUsername();
            ArrayList<String> temp0 = new ArrayList<>();
            ArrayList<String> temp1 = new ArrayList<>();
            ArrayList<String> temp2 = new ArrayList<>();
            temp0.add(c0);
            temp0.add(c1);
            temp0.add(c2);
            temp0.add(c2);
            temp0.add(c1);
            temp0.add(c0);
            temp1.add(c1);
            temp1.add(c2);
            temp1.add(c0);
            temp1.add(c0);
            temp1.add(c1);
            temp1.add(c2);
            temp2.add(c2);
            temp2.add(c0);
            temp2.add(c1);
            temp2.add(c1);
            temp2.add(c0);
            temp2.add(c2);
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp2.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp2.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp2.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
        } else if (orderedPlayers.size() == 2) {
            String c0 = orderedPlayers.get(0).getUsername();
            String c1 = orderedPlayers.get(1).getUsername();
            ArrayList<String> temp0 = new ArrayList<>();
            ArrayList<String> temp1 = new ArrayList<>();
            temp0.add(c0);
            temp0.add(c1);
            temp0.add(c1);
            temp0.add(c0);
            temp1.add(c1);
            temp1.add(c0);
            temp1.add(c0);
            temp1.add(c1);
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp0.clone());
            orderedRoundPlayers.add((ArrayList<String>) temp1.clone());
        } else {
            LOGGER.log(Level.INFO, "Problem in assigning rounds");
        }
    }

    private void startGame() {
        assignRoundPlayers(orderedPlayers);
        model.forceRefreshEntireBoard(null, orderedPlayers);
        startNewRound();
    }

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

    private void sendResultToPlayers(LinkedHashMap<String, Integer> orderedPlayerScores) {
        Set set1 = orderedPlayerScores.entrySet();
        Set set2 = orderedPlayerScores.entrySet();
        ArrayList<String> scoresList = new ArrayList<>();
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

            usernameTimerMap.put(currentPlayer, new Timer());
            usernameTimerMap.get(currentPlayer).schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            LOGGER.log(Level.INFO, "Sending timeout");
                            userViewMap.get(currentPlayer).timeOut();
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
                    System.out.println("Added ti player" + command.getMessage());
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
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        Player current = usernamePlayerMap.get(playerUsername);
        Integer usedToolNumber = command.getNumberChosen(); //Referes to the toolcard used
        ToolCard chosen = model.getExtractedToolCard().get(usedToolNumber);
        int requiredTokens = 1;
        if (chosen.getTokenCount() > 0) {
            requiredTokens = 2;
        }
        if (current.getTokens() > requiredTokens) {
            lastUsedToolCardNum = usedToolNumber;
            requiredTokensForLastToolUse = requiredTokens;
            String toolName = chosen.getName();
            if (toolName.equals("Copper Foil Reamer"))
                userViewMap.get(playerUsername).moveDieNoRestrictionMenu(chosen.getName());
            else if (toolName.equals("Cork Line"))
                userViewMap.get(playerUsername).corkLineMenu();
            else if (toolName.equals("Diamond Swab"))
                userViewMap.get(playerUsername).changeDieValueMenu(chosen.getName());
            else if (toolName.equals("Eglomise Brush"))
                userViewMap.get(playerUsername).moveDieNoRestrictionMenu(chosen.getName());
            else if (toolName.equals("Firm Pastry Brush")) {
                randomValueForFirmPastryBrush = ThreadLocalRandom.current().nextInt(1, 7);
                userViewMap.get(currentPlayer).firmPastryBrushMenu(randomValueForFirmPastryBrush);
            } else if (toolName.equals("Firm Pastry Thinner")) {
                extractedDieForFirmPastryThinner = model.extractDieFromDiceBag();
                userViewMap.get(currentPlayer).firmPastryThinnerMenu(extractedDieForFirmPastryThinner.getColor().toString(), extractedDieForFirmPastryThinner.getValue());
            } else if (toolName.equals("Gavel")) {
                if (currentRoundOrderedPlayers.contains(playerUsername)) { //Can't use the tool, has to be in second turn
                    handlePlayerAfterIncorrectToolUse(playerUsername, "You have to be in your second turn to use this turn. Use it later.");
                } else {
                    model.rollDraftpoolDice();
                    handlePlayerAfterCorrectToolUse(playerUsername, requiredTokensForLastToolUse, false);
                }
            } else if (toolName.equals("Lathekin"))
                userViewMap.get(playerUsername).twoDiceMoveMenu(chosen.getName());
            else if (toolName.equals("Manual Cutter"))
                userViewMap.get(playerUsername).twoDiceMoveMenu(chosen.getName());
            else if (toolName.equals("Roughing Forceps"))
                userViewMap.get(playerUsername).changeDieValueMenu(chosen.getName());
            else if (toolName.equals("Wheels Pincher")) {
                if (!currentRoundOrderedPlayers.contains(playerUsername)) { //Can't use the tool, has to be in first turn
                    handlePlayerAfterIncorrectToolUse(playerUsername, "You have to be in your first turn to use this tool");
                } else {
                    userViewMap.get(playerUsername).wheelsPincherMenu();
                }
            } else
                LOGGER.log(Level.INFO, "Error in toolNames");
        } else { //Not enough tokens
            handlePlayerAfterIncorrectToolUse(playerUsername, "You haven't enough tokens to use this tool! :(");
        }
    }


    private void startUsingTool(int toolNum){
        model.getExtractedToolCard().get(toolNum);
    }

    @Override
    public void applyCommand(String playerUsername, MoveChoiceDicePlacement command) {
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        LOGGER.log(Level.INFO, "Entra in moveChoice");
        try {
            Die toPlace = null;
            toPlace = model.getDraftPool().getDie(command.getDieDraftPoolPosition());
            if (!usernamePlayerMap.get(playerUsername).getWindowPatternCard()
                    .placeDie(toPlace, command.getDieSchemaPosition(),
                            true, true, true)) {
                handlePlayerAfterIncorrectToolUse(playerUsername, WRONG_PLACEMENT);
            } else {
                LOGGER.log(Level.INFO, "Mossa applicata correttamente");
                model.removeDieFromDraftPool(command.getDieDraftPoolPosition());
                hasPerformedMove = true;
                model.setGamePlayers(orderedPlayers);
                userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, hasUsedTool);
            }
        } catch (EmptyCellException e) {
            handlePlayerAfterIncorrectToolUse(playerUsername, EMPTY_DRAFTPOOL_INDEX);
        }
    }


    @Override
    public void applyCommand(String playerUsername, MoveChoicePassTurn command) {
        if (!orderedPlayers.contains(usernamePlayerMap.get(playerUsername))) {
            LOGGER.log(Level.INFO, "Match not started yet: no action");
            return;
        }
        if (!isAllowed(playerUsername)) {
            userViewMap.get(playerUsername).invalidActionMessage("It's not your turn, you cannot do actions");
            return;
        }
        if (extractedDieForFirmPastryThinner != null) { //Case in which a timeout forced the player turn skip -> the die hasn't to be lost
            model.insertDieInDiceBag(extractedDieForFirmPastryThinner);
            extractedDieForFirmPastryThinner = null;
        }

        usernameTimerMap.get(currentPlayer).cancel();
        startNewTurn();
    }

    @Override
    public void applyCommand(String playerUsername, UseToolPlaceDie command) {

    }

    @Override
    public void applyCommand(String playerUsername, UseToolDecideValue command) {

    }

    @Override
    public void applyCommand(String playerUsername, UseToolDecideAnotherOne command) {

    }

    @Override
    public void applyCommand(String playerUsername, UseToolSelectDie command) {

    }

    @Override
    public void applyCommand(String playerUsername, UseToolDecideIncreaseDecrease command) {

    }


    @Override
    public void applyCommand(String playerUsername, UndoActionCommand command) {

    }

    private void handlePlayerAfterCorrectToolUse(String playerUsername, int tokenToDecrease, boolean hasPerformedMove){
        usernamePlayerMap.get(playerUsername).decreaseTokens(tokenToDecrease);
        model.increaseToolCardTokens(lastUsedToolCardNum, tokenToDecrease);
        this.hasUsedTool = true;
        this.hasPerformedMove = hasPerformedMove;
        model.setGamePlayers(orderedPlayers);
        userViewMap.get(playerUsername).continueTurnMenu(hasPerformedMove, true);
    }

    private void handlePlayerAfterIncorrectToolUse(String playerUsername, String messageToSend){
        LOGGER.log(Level.INFO, messageToSend);
        userViewMap.get(playerUsername).invalidActionMessage(messageToSend);
        userViewMap.get(currentPlayer).continueTurnMenu(hasPerformedMove, hasUsedTool);
    }

    @Override
    public void applyCommand(String playerUsername, ClientToServerCommand command) {
        LOGGER.log(Level.INFO, "You shouldn't be here");
    }












    /**
     * Getters
     */

    public int getLastUsedToolCardNum() {
        return lastUsedToolCardNum;
    }

    public Die getExtractedDieForFirmPastryThinner() {
        return extractedDieForFirmPastryThinner;
    }

    public ArrayList<ArrayList<String>> getOrderedRoundPlayers() {
        return orderedRoundPlayers;
    }

    public ArrayList<String> getCurrentRoundOrderedPlayers() {
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


    @Override
    public void update(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.visit(this);
    }
}