package it.polimi.se2018.view.cli;

import it.polimi.se2018.commands.client_to_server_command.*;
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
        System.out.println(move ? "1 - Place die" : "");
        System.out.println(tool ? "2 - Use Tool" : "");
        System.out.println("3 - Pass Turn");
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
    public void firmPastryBrushMenu(int value) { // Changes a value of a die of draftpool with the one received (value)
        synchronized (toolcardLock) {
            System.out.println("Youchose: Firm Pastry brush");
            //Notify(new UseToolFirmPastr...(Integer chosenDieDraftpoolIndex, Integer schemaPosition (can be null), boolean placedDie (true if the player place the die)
            System.out.println("sorry, no can do");
            notifyAll();
        }
    }

    public void firmPastryThinnerMenu(String color, int value) { // receives a new die from Servfer with Color = color, Value = value. The player has to send one of draftpool dice to swap them
        synchronized (toolcardLock) {
            System.out.println("You chose: Firm Pastry Thinner");
            //Notify(new UseToolFirmPastr...(Integer chosenDieDraftpoolIndex, Integer schemaPosition (where he wants to place the die, can be null), **String color, Integer value***, boolean placedDie (true if the player place the die)
            // ** il server deve ricevere il dado che ha mandato al view perchè non lo salva da nessuna parte
            System.out.println("sorry, no can do");
            notifyAll();
        }
    }

    @Override
    public void moveDieNoRestrictionMenu(String cardName) {
        synchronized (toolcardLock) {
            System.out.println("You chose: " + cardName);
            if (cardName.equals("Eglomise Brush")) {
                System.out.println("TOOL USE - Eglomise Brush " +
                        "\n You can move a die ignoring color restriction");
            } else if (cardName.equals("Copper Foil Reamer")) {
                System.out.println("TOOL USE - Copper Foil Reamer " +
                        "\n You can move a die ignoring value restriction");
            }
            System.out.println("SELECT DIE TO BE MOVED:");
            int oldSchemaPosition = selectRow() * 4 + selectColumn();
            System.out.println("SELECT THE THE CELL WHERE THE DIE WILL BE MOVED");
            int newSchemaPosition = selectRow() * 4 + selectColumn();
            notify(new UseToolMoveDieNoRestriction(cardName, oldSchemaPosition, newSchemaPosition));
            notifyAll();
        }
    }

    @Override
    public void changeDieValueMenu(String cardName) {
        synchronized (toolcardLock) {
            System.out.println("You chose: " + cardName);
            System.out.println("DRAFTPOOL DIE YOU WANT TO EDIT:");
            int draftpoolPos = selectFromDraftPool();

            if (cardName.equals("Roughing Forceps")) {
                System.out.println("Want you to increase or decrease the value? \n 1- Increase" + "\n 2- Decrease");
                int toolChoice = inputReader.readInt(1, 2);
                //TODO: controllo per evitare che il dado scelto sia un 6 e aumenti o sia un 1 e diminuisca
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, (toolChoice == 1 ? true : false)));
            }
            //Diamond Swab
            else {
                notify(new UseToolChangeDieValue(cardName, draftpoolPos));
            }
            notifyAll();
        }
    }

    @Override
    public void twoDiceMoveMenu(String cardName) {
        synchronized(toolcardLock) {
            System.out.println("You chose: " + cardName);


            System.out.println("YOU Used - " + cardName);
            if (cardName.equals("Manual Cutter")) {
                System.out.println("With this tool you have to move 2 dice of same color of a die of the roundTrack. Check it out carefully!");
            } else {
                System.out.println("Move exactly two dice respecting placement restrictions");
            }
            System.out.println("FIRST DIE:");
            int oldPos1 = (selectRow() * 4 + selectColumn());
            System.out.println("NEW DIE COORDINATES:");
            int newPos1 = (selectRow() * 4 + selectColumn());
            System.out.println("SECOND DIE:");
            int oldPos2 = (selectRow() * 4 + selectColumn());
            System.out.println("NEW DIE COORDINATES:");
            int newPos2 = (selectRow() * 4 + selectColumn());
            notify(new UseToolTwoDicePlacement(cardName, oldPos1, newPos1, oldPos2, newPos2));
            notifyAll();
        }
    }

    @Override
    public void corkLineMenu() {
        synchronized (toolcardLock) {
            System.out.println("You chose: Cork Line");
            System.out.println("Place a die ignoring placement restriction");
            int draftpoolPosition = selectFromDraftPool();
            int row = selectRow();
            int column = selectColumn();
            notify(new UseToolCorkLine(draftpoolPosition, row * 4 + column));
            notifyAll();
        }
    }

    @Override
    public void wheelsPincherMenu() {
        synchronized (toolcardLock){
            System.out.println("You chose: Wheels Pincher");
            System.out.println("Select another die, you will skip next turn");
            int draftpoolPosition = selectFromDraftPool();
            int row = selectRow();
            int column = selectColumn();
            notify(new UseToolWheelsPincher(draftpoolPosition, row * 4 + column));
            notifyAll();

        }
        }

    @Override
    public void circularCutter() {
        synchronized (toolcardLock) {
            System.out.println("You chose: Circular Cutter");
            System.out.println("Swap a die from draftpool with a die from roundtrack");
            int draftpoolPosition = selectFromDraftPool();
            int roundTrackPosition = selectFromRoundTrack();
            notify(new UseToolCircularCutter(draftpoolPosition, roundTrackPosition));
            notifyAll();
        }
    }

    @Override
    public void invalidActionMessage(String message) {
        System.out.println("Invalid action: " + message);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public void loseMessage(Integer position, List<String> scores) {
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
    public void correctAuthenthication(String username) {
        this.username = username;
        System.out.println("Correct authentication!\nWelcome to Sagrada, " + username);
    }

    @Override
    public void timeOut() {
        //this assignment is needed to restore flow control if a timeout occurs during a toolcard use
        synchronized (toolcardLock) {
            toolcardLock.notifyAll();
        }
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
        switch (command) {
            case "1":
                placeDie();
                break;
            case "2":
                useToolcard();
                //until card has been used, flow control is switched to toolcard menu
                synchronized (toolcardLock) {
                    try {
                        toolcardLock.wait();
                    } catch (InterruptedException e) {
                        //nothing
                    }
                    //flow control switches back to thread
                }
                break;
            case "3":
                passTurn();
                break;
            case "print -c":
                cliPrinter.printCompleteBoard(cliState);
                break;
            case "print -pr":
                System.out.println(cliState.getPrivateObjectiveCard());
                System.out.println(cliState.getPrivateObjectiveCardDescription());
                break;
            case "print -pu":
                for (int i = 0; i < cliState.getPublicObjectiveCards().size(); i++) {
                    PublicObjectiveLight card = cliState.getPublicObjectiveCards().get(i);
                    System.out.println(card.getName() + "\n\t" + card.getDescription());
                }
                break;
            case "print -t":
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
                        "1 : Place a die\n" +
                        "2 : Use a toolcard\n" +
                        "3 : Pass your turn\n\n" +
                        "Furthermore, in any moment you can type:\n" +
                        "print -c : print complete board\n" +
                        "print -pr : print your Private Objective Card\n" +
                        "print -pu : print Public Objective Cards\n" +
                        "print -t : print Toolcards\n");
    }

    private void placeDie() {
        if (cliState.isYourTurn()) {
            int draftPos = selectFromDraftPool();
            int schemaRow = selectRow();
            int schemaCol = selectColumn();
            notify(new MoveChoiceDicePlacement(schemaRow, schemaCol, draftPos));
        } else {
            System.out.println(NOT_YOUR_TURN);
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

    private void useToolcard() {
        if (cliState.isYourTurn()) {
            System.out.println("What toolcard do you want to use?\n");
            cliPrinter.printToolcards(cliState);
            int choice = inputReader.readInt(1, cliState.getToolcards().size());
            notify(new MoveChoiceToolCard(choice - 1));
        } else {
            System.out.println(NOT_YOUR_TURN);
        }
    }

    private synchronized int selectFromDraftPool() {
        System.out.println(String.format("Select die position in Draft Pool (number between 1 and %d)", cliState.getDraftpool().size()));
        return inputReader.readInt(1, cliState.getDraftpool().size()) - 1;
    }

    private synchronized int selectFromRoundTrack() {
        System.out.println(String.format("Select die position in Round Track (number between 1 and %d)", cliState.getRoundTrack().size()));
        return inputReader.readInt(1, cliState.getRoundTrack().size()) - 1;
    }

    private synchronized int selectRow() {
        System.out.println("Select row (number between 1 and 4)");
        return inputReader.readInt(1, 4) - 1;
    }

    private synchronized int selectColumn() {
        System.out.println("Select column (number between 1 and 5)");
        return inputReader.readInt(1, 5) - 1;
    }
}