package client.view.cli;

import shared.commands.client_to_server_command.*;
import shared.commands.server_to_client_command.*;
import shared.exceptions.TimeUpException;
import shared.utils.Observer;
import shared.View;
import client.view.cli.cliState.CliState;
import client.view.cli.cliState.INPUT_STATE;
import client.view.cli.cliState.PublicObjectiveLight;
import client.view.cli.cliState.ToolcardLight;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages CLI
 * @author Alessio Molinari
 */
public class CLIView extends View implements Runnable {

    private CLIPrinter cliPrinter = new CLIPrinter();
    private CliState cliState;

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private InputReader inputReader = new InputReader();
    private INPUT_STATE currentState = INPUT_STATE.NOT_YOUR_TURN;
    private List<List<String>> wpcsForInitialChoice;

    private boolean placeDieAllowed;
    private boolean toolcardAllowed;

    private static final String NOT_YOUR_TURN = "Invalid action: it's not your turn";

    public CLIView(Observer observer) {
        super(observer);
        System.out.println("ATTESA DI GIOCATORI...");
        cliState = new CliState();
        //avvia l'input reader
        new Thread(this).start();
    }

    @Override
    public void chooseWindowPatternCardMenu(List<List<String>> cards, String privateObjectiveCard, List<Integer> wpcDifficulties) {
        wpcsForInitialChoice = cards;
        currentState = INPUT_STATE.CHOOSE_WPC;
        for (int j = 0; j < cards.size(); j++) {
            System.out.println("Difficulty: " + wpcDifficulties.get(j));
            cliPrinter.printWPC(cards.get(j));
        }

        System.out.println("\nYour Private Objective is: " + privateObjectiveCard);
        System.out.println("\n Which one do you chose?");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(i + 1 + ") " + cards.get(i).get(0));
        }
    }

    @Override
    public synchronized void startTurnMenu() {
        System.out.println("Now it's your turn!");
        continueTurnMenu(false, false);
    }

    @Override
    public void startGame() {
        //useless for cli
    }

    @Override
    public void endGame() {
        currentState = INPUT_STATE.END_GAME;
    }
    @Override
    public synchronized void otherPlayerTurn(String username) {
        currentState = INPUT_STATE.NOT_YOUR_TURN;
        System.out.println("It's " + username + "'s turn");
    }

    @Override
    public synchronized void continueTurnMenu(boolean hasAlreadyMovedDie, boolean hasAlreadyUsedTool) {
        placeDieAllowed = !hasAlreadyMovedDie;
        toolcardAllowed = !hasAlreadyUsedTool;
        currentState = INPUT_STATE.YOUR_TURN;
        cliPrinter.printBasicInformation(cliState, currentState, placeDieAllowed, toolcardAllowed);
    }

    @Override
    public synchronized void newConnectedPlayer(String username) {
        System.out.println(username + " just joined the game");
    }

    @Override
    public synchronized void playerDisconnection(String username) {
        System.out.println(username + " got disconnected");
    }

    @Override
    public synchronized void invalidActionMessage(String message) {
        cliPrinter.printBasicInformation(cliState, currentState, placeDieAllowed, toolcardAllowed);
        System.out.println("Invalid action: " + message);
    }

    @Override
    public synchronized void loseMessage(Integer position, List<String> scores) {
        System.out.println("You lost! :( Your rank is " + position + "\n");
        System.out.println("Here other players ordered scores:");
        for (String score : scores) {
            score = score.replace("_", "\t");
            System.out.println(score);
        }
        System.out.println("Thanks for playing Sagrada!");
    }

    @Override
    public void winMessage(List<String> scores) {
        System.out.println("Congratulation! :) You won!");
        System.out.println("Here other players ordered scores:\n");
        for (String score : scores) {
            score = score.replace("_", "\t");
            System.out.println(score);
        }
        System.out.println("Thanks for playing Sagrada!");
    }

    @Override
    public synchronized void correctAuthenthication(String username) {
        this.username = username;
        System.out.println("Correct authentication!\nWelcome to Sagrada, " + username);
    }

    @Override
    public void timeOut() {
        inputReader.setTimeOut();
    }

    @Override
    public synchronized void updateWpc(RefreshWpcCommand refreshCommand) {
        cliState.parseRefreshWPC(refreshCommand);
        cliPrinter.printBasicInformation(cliState, currentState, placeDieAllowed, toolcardAllowed);
    }

    @Override
    public synchronized void updateTokens(RefreshTokensCommand refreshCommand) {
        cliState.parseRefreshTokens(refreshCommand);
        cliPrinter.printBasicInformation(cliState, currentState, placeDieAllowed, toolcardAllowed);
    }

    @Override
    public synchronized void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        cliState.parseRefreshRoundTrack(refreshCommand);
        cliPrinter.printBasicInformation(cliState, currentState, placeDieAllowed, toolcardAllowed);
    }

    @Override
    public synchronized void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        cliState.parseRefreshDraftPool(refreshCommand);
        cliPrinter.printBasicInformation(cliState, currentState, placeDieAllowed, toolcardAllowed);
    }

    @Override
    public synchronized void updateBoard(RefreshBoardCommand refreshCommand) {
        RefreshBoardCommand command = refreshCommand;
        cliState.parseRefreshBoard(command);
        cliPrinter.printBasicInformation(cliState, currentState, placeDieAllowed, toolcardAllowed);
    }

    @Override
    public void notify(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.setUsername(this.username);
        for (Observer observer : observers) {
            observer.update(command);
        }
    }

    @Override
    public synchronized void messageBox(String message) {
        System.out.println("Message from Server: " + message);
    }

    @Override
    public void run() {
        while (!currentState.equals(INPUT_STATE.END_GAME)) {
            try{
                String input = inputReader.readLine();
                manageCommand(currentState, input);
            } catch (TimeUpException e) {
                //not sure if this catch is needed
            }
        }
    }

    private int draftPoolChoice;
    private int roundTrackChoice;
    private int rowChoice;
    private int columnChoice;
    private String pickDieSource;

    /**
     * Output function
     */
    private void manageCommand(INPUT_STATE currentState, String input) {
        input = input.toLowerCase();
        this.currentState = INPUT_STATE.nextState(currentState, input);

        if (input.equals("u")) {
            if (currentState.toString().contains("REPLY")) {
                notify(new UndoActionCommand());
            } else if (INPUT_STATE.isLocallyReversible(currentState)){
                System.out.println("Action interrupted");
                cliPrinter.printYourTurn(currentState, placeDieAllowed, toolcardAllowed);
            } else {
                System.out.println("You cannot interrupt the action right now");
                this.currentState = currentState;
            }
            return;
        }

        switch (currentState) {
            case YOUR_TURN:
                if (input.equals("p")) {
                    System.out.println("Passing turn");
                    notify(new MoveChoicePassTurn());
                } else if (input.equals("t")) {
                    cliPrinter.printToolcards(cliState);
                    System.out.println("What toolcard do you want to use?\n");
                } else if (input.equals("d")) {
                    System.out.println("Select draft pool index");
                    cliPrinter.printInlineList(cliState.getDraftpool());
                } else {
                    checkPrintBoard(input);
                }
                break;
            case CHOOSE_WPC:
                if (checkCorrectInput(input, 1, 4)) {
                    int chosen = Integer.parseInt(input);
                    notify(new ChosenWindowPatternCard(wpcsForInitialChoice.get(chosen - 1).get(0)));
                    System.out.println("You chose: " + wpcsForInitialChoice.get(chosen - 1).get(0));
                    //free memory
                    wpcsForInitialChoice = null;
                } else {
                    this.currentState = INPUT_STATE.CHOOSE_WPC;
                }
                break;
            case PLACE_DIE_INDEX:
                if (!checkCorrectDraftPool(input)) {
                    this.currentState = INPUT_STATE.YOUR_TURN;
                } else {
                    System.out.println("Select row and column separated by a space");
                }
                break;
            case PLACE_DIE_ROW_COLUMN:
                if (checkRowAndColumn(input)) {
                    notify(new MoveChoiceDiePlacement(rowChoice * 5 + columnChoice, draftPoolChoice));
                } else {
                    this.currentState = INPUT_STATE.YOUR_TURN;
                }
                break;
            case NOT_YOUR_TURN:
                if (actionIsNotAllowedForThisTurn(input)) {
                    System.out.println("It's not your turn: action not allowed");
                } else {
                    checkPrintBoard(input);
                }
                break;
            case TOOLCARD_CHOICE:
                if (checkCorrectInput(input, 1, 3)) {
                    int toolcardChoice = Integer.parseInt(input) - 1;
                    notify(new MoveChoiceToolCard(toolcardChoice));
                } else {
                    this.currentState = INPUT_STATE.YOUR_TURN;
                }
                break;
            case REPLY_ANOTHER_ACTION:
                if (checkCorrectInput(input, 1, 2)) {
                    boolean anotherAction = (input.equals("1"));
                    notify(new ReplyAnotherAction(anotherAction));
                }
                break;
            case REPLY_DIE_VALUE:
                if (checkCorrectInput(input, 1, 6)) {
                    int choice = Integer.parseInt(input);
                    notify(new ReplyDieValue(choice));
                }
                break;
            case REPLY_PICK_DIE:
                replyPickDie(input);
                break;
            case REPLY_PLACE_DIE:
                if (checkRowAndColumn(input)) {
                    notify(new ReplyPlaceDie(rowChoice * 5 + columnChoice));
                }
                break;
            case REPLY_INCREASE_DECREASE:
                if (checkCorrectInput(input, 1, 2)) {
                    notify(new ReplyIncreaseDecrease(input.equals("1")));
                }
                break;
            case END_GAME:
               //game is over
                break;
        }
    }

    /**
     * Checks whether the input corresponds to an allowed command or not
     */
    private boolean actionIsNotAllowedForThisTurn(String input) {
        return input.equals("d") || input.equals("t") || input.equals("p") || input.equals("u");
    }

    /**
     * Prints different board elements or text depending on the input, whether it is the player's turn or not
     */
    private void checkPrintBoard(String input) {
        switch (input) {
            case "-c":
                cliPrinter.printCompleteBoard(cliState);
                break;
            case "-pr":
                System.out.println(cliState.getPrivateObjectiveCard());
                System.out.println(cliState.getPrivateObjectiveCardDescription());
                break;
            case "-pu":
                for (int i = 0; i < cliState.getPublicObjectiveCards().size(); i++) {
                    PublicObjectiveLight card = cliState.getPublicObjectiveCards().get(i);
                    System.out.println(card.getName() + "\n\t" + card.getDescription());
                }
                break;
            case "-t":
                for (int i = 0; i < cliState.getToolcards().size(); i++) {
                    ToolcardLight card = cliState.getToolcards().get(i);
                    System.out.println(String.format("%d) %s - Tokens: %d\n\t%s", i + 1, card.getToolcardName(), card.getTokens(), card.getDescription()));
                }
                break;
            case "help":
                printHelp();
                break;
            default:
                System.out.println("Invalid command, print help for further information");
        }
        cliPrinter.printYourTurn(currentState, placeDieAllowed, toolcardAllowed);
    }

    /**
     * Checks whether the input value is correct or not
     */
    private boolean checkCorrectInput(String inputString, int validInferior, int validSuperior) {
        if (inputString.equals("u")) {
            return false;
        }
        try {
            int input = Integer.parseInt(inputString);
            if ((input < validInferior) || (input > validSuperior)) {
                System.out.println("Input not compliant to rules");
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input is not a number");
        }
        return false;
    }

    /**
     * Checks whether the input is a valid Draft Pool index or not
     */
    private boolean checkCorrectDraftPool(String inputString) {
        if (checkCorrectInput(inputString, 1, cliState.getDraftpool().size())) {
            draftPoolChoice = Integer.parseInt(inputString) - 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the input is a valid Round Track index or not
     */
    private boolean checkCorrectRoundTrack(String inputString) {
        if (checkCorrectInput(inputString, 1, cliState.getRoundTrack().size())) {
            roundTrackChoice = Integer.parseInt(inputString) - 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the input is a valid Window Pattern Card row index or not
     */
    private boolean checkCorrectRow(String inputString) {
        if (checkCorrectInput(inputString, 1, 4)) {
            rowChoice = Integer.parseInt(inputString) - 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the input is a valid Window Pattern Card column index or not
     */
    private boolean checkCorrectColumn(String inputString) {
        if (checkCorrectInput(inputString, 1, 5)) {
            columnChoice = Integer.parseInt(inputString) - 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the input values are valid Window Pattern Card row and column indexes or not
     */
    private boolean checkRowAndColumn(String inputString) {
        String[] rowAndColumn = inputString.split(" ");
        if (inputString.equals("u")) {
            return false;
        }
        if (rowAndColumn.length != 2) {
            System.out.println("Please insert both row and column");
            return false;
        } else if (checkCorrectRow(rowAndColumn[0]) && (checkCorrectColumn(rowAndColumn[1]))) {
            return true;
        }
        return false;
    }

    /**
     * Prints help menu
     */
    private synchronized void printHelp() {
        System.out.println(
                "Here's what you can do:\n" +
                        "If it is your turn you can choose between:\n" +
                        "d : Place a die\n" +
                        "t : Use a toolcard\n" +
                        "p : Pass your turn\n" +
                        "u : undo action\n\n" +
                        "Furthermore, in any moment you can type:\n" +
                        "-c : print complete board\n" +
                        "-pr : print your Private Objective Card\n" +
                        "-pu : print Public Objective Cards\n" +
                        "-t : print Toolcards\n");
    }

    @Override
    public void askAnotherAction() {
        System.out.println("Do you want to perform another action?\n1 - Yes\n2 - No");
        currentState = INPUT_STATE.REPLY_ANOTHER_ACTION;
    }

    @Override
    public void askIncreaseDecrease() {
        System.out.println("Do you want to increase or decrease the value of this die?\n1 - Increase\n2 - Decrease");
        currentState = INPUT_STATE.REPLY_INCREASE_DECREASE;
    }

    @Override
    public void askDieValue() {
        System.out.println("What value do you choose for this die?");
        currentState = INPUT_STATE.REPLY_DIE_VALUE;
    }

    @Override
    public void askPlaceDie() {
        System.out.println("Where do you want to place this die?");
        System.out.println("Insert row and column separated by a space");
        currentState = INPUT_STATE.REPLY_PLACE_DIE;
    }

    @Override
    public void askPickDie(String from) {
        pickDieSource = from;
        System.out.println("Select the die you want to pick");
        currentState = INPUT_STATE.REPLY_PICK_DIE;
        switch (from) {
            case "WPC":
                System.out.println("(From Window Pattern Card:)");
                break;
            case "DP":
                System.out.println("(From Draft Pool:)");
                cliPrinter.printInlineList(cliState.getDraftpool());
                break;
            case "RT":
                System.out.println("(From Round Track:)");
                break;
            default:
        }
    }

    /**
     * Sends die index, after checking input validity
     */
    private void replyPickDie(String input) {
        switch (pickDieSource) {
            case "WPC":
                if (checkRowAndColumn(input)) {
                    notify(new ReplyPickDie(rowChoice * 5 + columnChoice));
                }
                break;
            case "DP":
                if (checkCorrectDraftPool(input)) {
                    notify(new ReplyPickDie(draftPoolChoice));
                }
                break;
            case "RT":
                if (checkCorrectRoundTrack(input)) {
                    notify(new ReplyPickDie(roundTrackChoice));
                }
                break;
            default:
                LOGGER.log(Level.INFO, "Incorrect preference from server: neither  WPC, nor DP, nor RT");
        }
    }
}