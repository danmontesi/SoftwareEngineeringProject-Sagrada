package it.polimi.se2018.view.cli;

import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.commands.client_to_server_command.new_tool_commands.*;
import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.exceptions.TimeUpException;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.cli.cliState.CliState;
import it.polimi.se2018.view.cli.cliState.PublicObjectiveLight;
import it.polimi.se2018.view.cli.cliState.ToolcardLight;

import java.util.List;
import java.util.logging.Logger;

public class CLIView extends View implements Runnable {

    private CLIPrinter cliPrinter = new CLIPrinter();
    private CliState cliState;

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private InputReader inputReader = new InputReader();
    private boolean active = true;
    private final Object toolcardLock = new Object();

    private static final String NOT_YOUR_TURN = "Invalid action: it's not your turn";

    public CLIView(Observer observer) {
        super(observer);
        System.out.println("ATTESA DI GIOCATORI...");
        cliState = new CliState();
    }

    @Override
    public void chooseWindowPatternCardMenu(List<WindowPatternCard> cards) {
        for (WindowPatternCard card : cards) {
            cliPrinter.printWPC(card);
        }
        System.out.println("\n Which one do you chose?");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(i + 1 + ")" + cards.get(i).getCardName());
        }
        try {
            int chosen = inputReader.readInt(1, cards.size(), true);
            notify(new ChosenWindowPatternCard(cards.get(chosen - 1).getCardName()));
            System.out.println("You chose: " + cards.get(chosen - 1).getCardName());
        } catch (TimeUpException e) {
            System.out.println("Window Pattern Card chosen automatically");
        }
        //avvia l'inputReader
        new Thread(this).start();
    }

    @Override
    public synchronized void startTurnMenu() {
        System.out.println("IT'S YOUR TURN!\n");
        continueTurnMenu(true, true);
    }

    @Override
    public void startGame() {
        //useless for cli
    }

    @Override
    public synchronized void otherPlayerTurn(String username) {
        cliState.setYourTurn(false);
        System.out.println("It's " + username + "'s turn");
    }

    @Override
    public synchronized void authenticatedCorrectlyMessage(String username) {
        this.username = username;
        cliState.getPlayer(0).setUsername(username);
        System.out.println("Authenticated correctly!\nWelcome to Sagrada, " + this.username);
    }

    @Override
    public synchronized void continueTurnMenu(boolean move, boolean tool) {
        cliState.setYourTurn(true);
        System.out.println("What do you want to do?");
        System.out.println(move ? "d - Place die" : "");
        System.out.println(tool ? "t - Use Tool" : "");
        System.out.println("p - Pass Turn");
    }

    @Override
    public synchronized void newConnectedPlayer(String username) {
        System.out.println(username + " just joined the game");
    }

    @Override
    public synchronized void playerDisconnection(String username) {
        System.out.println(username + "got disconnected");
    }

    @Override
    public synchronized void invalidActionMessage(String message) {
        System.out.println("Invalid action: " + message);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public synchronized void loseMessage(Integer position, List<String> scores) {
        System.out.println("You lost! Your rank is " + position + "\n");
        System.out.println("Here other players ordered scores:");
        for (String score : scores) {
            System.out.println(score);
        }
    }

    @Override
    public void winMessage(List<String> scores) {
        System.out.println("Congratulation! You won!");
        System.out.println("Here other players ordered scores:");
        for (String score : scores) {
            System.out.println(score);
        }
    }

    @Override
    public synchronized void correctAuthenthication(String username) {
        this.username = username;
        System.out.println("Correct authentication!\nWelcome to Sagrada, " + username);
    }

    @Override
    public void timeOut() {
        inputReader.setTimeOut();
        cliState.setYourTurn(false);
    }

    @Override
    public synchronized void updateWpc(RefreshWpcCommand refreshCommand) {
        cliState.parseRefreshWPC(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public synchronized void updateTokens(RefreshTokensCommand refreshCommand) {
        cliState.parseRefreshTokens(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public synchronized void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        cliState.parseRefreshRoundTrack(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public synchronized void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        cliState.parseRefreshDraftPool(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public synchronized void updateBoard(RefreshBoardCommand refreshCommand) {
        RefreshBoardCommand command = refreshCommand;
        cliState.parseRefreshBoard(command);
        cliPrinter.printSyntheticBoard(cliState);
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
        while (active) {
            String command = inputReader.readLine();
            manageCommand(command);
        }
    }

    private void manageCommand(String command) {
        command = command.toLowerCase();
        switch (command) {
            case "d":
                placeDie();
                break;
            case "t":
                //until card has been used, flow control is switched to toolcard menu
                synchronized (toolcardLock) {
                    try {
                        useToolcard();
                        toolcardLock.wait();
                    } catch (InterruptedException | TimeUpException e) {
                        //nothing
                    }
                    System.out.println("[INFO] LOCK LIBERO!");
                    //flow control switches back to thread
                }
                break;
            case "p":
                passTurn();
                break;
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
    }

    private synchronized void printHelp() {
        System.out.println(
                "Here's what you can do:\n" +
                        "If it is your turn you can choose between:\n" +
                        "d : Place a die\n" +
                        "t : Use a toolcard\n" +
                        "p : Pass your turn\n\n" +
                        "Furthermore, in any moment you can type:\n" +
                        "-c : print complete board\n" +
                        "-pr : print your Private Objective Card\n" +
                        "-pu : print Public Objective Cards\n" +
                        "-t : print Toolcards\n");
    }

    private void placeDie() {
        try {
            if (cliState.isYourTurn()) {
                int draftPos = selectFromDraftPool();
                int schemaRow = selectRow();
                int schemaCol = selectColumn();
                notify(new MoveChoiceDicePlacement(schemaRow * 5 + schemaCol, draftPos));
            } else {
                System.out.println(NOT_YOUR_TURN);
            }
        } catch (TimeUpException e) {
        }
    }

    private void passTurn() {
        if (cliState.isYourTurn()) {
            System.out.println("Passed turn");
            notify(new MoveChoicePassTurn(username));
        } else {
            System.out.println(NOT_YOUR_TURN);
        }
    }

    private void useToolcard() throws TimeUpException {
        if (cliState.isYourTurn()) {
            System.out.println("What toolcard do you want to use?\n");
            cliPrinter.printToolcards(cliState);
            int choice = inputReader.readInt(1, cliState.getToolcards().size(), true);
            notify(new MoveChoiceToolCard(choice - 1));
        } else {
            System.out.println(NOT_YOUR_TURN);
        }
    }

    private synchronized int selectFromDraftPool() {
        System.out.println(String.format("Select die position in Draft Pool (number between 1 and %d)", cliState.getDraftpool().size()));
        return inputReader.readInt(1, cliState.getDraftpool().size(), true) - 1;
    }

    private synchronized int selectFromRoundTrack() {
        System.out.println(String.format("Select die position in Round Track (number between 1 and %d)", cliState.getRoundTrack().size()));
        return inputReader.readInt(1, cliState.getRoundTrack().size(), true) - 1;
    }

    private synchronized int selectRow() {
        System.out.println("Select row (number between 1 and 4)");
        return inputReader.readInt(1, 4, true) - 1;
    }

    private synchronized int selectColumn() {
        System.out.println("Select column (number between 1 and 5)");
        return inputReader.readInt(1, 5, true) - 1;
    }

    @Override
    public void askAnotherAction() {
        System.out.println("Do you want to perform another action?\n1 - Yes\n2 - No");
        int choice = inputReader.readInt(1, 2);
        notify(new ReplyAnotherAction(choice == 1));
    }

    @Override
    public void askIncreaseDecrease() {
        System.out.println("Do you want to increase or decrease the value of this die?\n1 - Increase\n2 - Decrease");
        int choice = inputReader.readInt(1, 2);
        notify(new ReplyIncreaseDecrease(choice == 1));
    }

    @Override
    public void askDieValue() {
        System.out.println("What value do you choose for this die?");
        int choice = inputReader.readInt(1, 6);
        notify(new ReplyDieValue(choice));
    }

    @Override
    public void askPlaceDie() {
        System.out.println("Where do you want to place this die?");
        int row = selectRow();
        int column = selectColumn();
        notify(new ReplyPlaceDie(row * 5 + column));
    }

    @Override
    public void askPickDie(String from) {
        System.out.println("Select the die you want to pick");
        switch (from) {
            case "WPC":
                notify(new ReplyPickDie(selectRow() * 5 + selectColumn()));
                break;
            case "DP":
                notify(new ReplyPickDie(selectFromDraftPool()));
                break;
            case "RT":
                notify(new ReplyPickDie(selectFromRoundTrack()));
                break;
            default:
                //TODO: In caso di source sbagliata?
        }
    }
}