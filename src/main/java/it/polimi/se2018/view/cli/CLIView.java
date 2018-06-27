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

public class CLIView extends View implements Runnable{

    /**
     * CliView receives a clone of current model each time it's Player's turn
     */
    private CLIPrinter cliPrinter = new CLIPrinter();
    private CliState cliState;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private InputReader inputReader = new InputReader();
    private boolean active = true;
    private boolean usedToolCard = true;
    private static final String NOT_YOUR_TURN = "Invalid action: it's not your turn";

    public CLIView(Observer observer){
        super(observer);
        System.out.println("ATTESA DI GIOCATORI...");
        cliState = new CliState();
    }

    //OGNI METODO DEVE CHIAMARE LA notify() della view, passandole un EVENTO

    @Override
    public void chooseWindowPatternCardMenu(List<WindowPatternCard> cards){
        for (WindowPatternCard card : cards) {
            cliPrinter.printWPC(card);
        }
        System.out.println("\n Which one do you chose?");
        for (int i = 0; i < cards.size(); i++){
            System.out.println(i+1 + ")" + cards.get(i).getCardName());
        }
        try {
            int chosen = inputReader.readInt(1, cards.size(), true);
            notify(new ChosenWindowPatternCard(cards.get(chosen - 1).getCardName()));
            System.out.println("You chose: " + cards.get(chosen - 1).getCardName());
        } catch (TimeUpException e){
            System.out.println("Window Pattern Card chosen automatically");
        }
        //avvia l'inputReader
        new Thread(this).start();
    }

    @Override
    public void startTurnMenu(){
        System.out.println("IT'S YOUR TURN!\n");
        continueTurnMenu(true, true);
    }

    @Override
    public void startGame() {
        //useless for cli
    }

    @Override
    public void otherPlayerTurn(String username) {
        cliState.setYourTurn(false);
        System.out.println("It's " + username + "'s turn");
    }

    @Override
    public void authenticatedCorrectlyMessage(String username) {
        this.username = username;
        cliState.getPlayer(0).setUsername(username);
        System.out.println("Authenticated correctly!\nWelcome to Sagrada, " + this.username);
    }

    @Override
    public void continueTurnMenu(boolean move, boolean tool){
        cliState.setYourTurn(true);
        System.out.println("What do you want to do?");
        System.out.println(move ? "1 - Place die" : "");
        System.out.println(tool ? "2 - Use Tool" : "");
        System.out.println("3 - Pass Turn");

    }

    @Override
    public void newConnectedPlayer(String username) {
        System.out.println(username + " just joined the game");
    }

    @Override
    public void playerDisconnection(String username) {
        System.out.println(username + "got disconnected");
    }

    @Override
    public void firmPastryBrushMenu(int value){ // Changes a value of a die of draftpool with the one received (value)
        //Notify(new UseToolFirmPastr...(Integer chosenDieDraftpoolIndex, Integer schemaPosition (can be null), boolean placedDie (true if the player place the die)
    }

    //TODO dan
    public void firmPastryThinnerMenu(String color, int value){ // receives a new die from Servfer with Color = color, Value = value. The player has to send one of draftpool dice to swap them
        //Notify(new UseToolFirmPastr...(Integer chosenDieDraftpoolIndex, Integer schemaPosition (where he wants to place the die, can be null), **String color, Integer value***, boolean placedDie (true if the player place the die)
        // ** il server deve ricevere il dado che ha mandato al view perchè non lo salva da nessuna parte
    }

    @Override
    public void moveDieNoRestrictionMenu(String cardName){
        if (cardName.equals("Eglomise Brush")){
            System.out.println("TOOL USE - Eglomise Brush " +
                    "\n You can move a die without color restriction");
            System.out.println("Select where is the die you want to move in your schema:");
            System.out.println("Select the cell you cant to move the die in");
//            notify(new UseToolMoveDieNoRestriction(cardName, schemaOldPos, schemaNewPos));
        }
        else if (cardName.equals("Copper Foil Reamer")){
            System.out.println("TOOL USE - Copper Foil Reamer " +
                    "\n You can move a die without value restriction");
            System.out.println("Select where is the die you want to move in your schema:");
            System.out.println("Select the cell you cant to move the die in");
//            notify(new UseToolMoveDieNoRestriction(cardName, schemaOldPos, schemaNewPos));
        }

    }

    @Override
    public void changeDieValueMenu(String cardName){
        /*if (cardName.equals("Roughing Forceps")){
            System.out.println("TOOL USE Increase-Decrease - Roughing Forceps " +
                    "\n TOOL_DESCRIP"); //TODO
            System.out.println("Select the die of Draftpool you want to edit");
            System.out.println("Want you to increase the value? \n 1- Increase" + "\n 2- Decrease"); //TODO: controllo per evitare che il dado scelto sia un 6 e aumenti o sia un 1 e diminuisca
            boolean increase = chosen==1;
            //TODO: Devo stampare il valore aumentato
            System.out.println("Want you to place the new edited die? type: \n 1- Yes \n2- No");
            if (decision==1){
                System.out.println("Where do you want to place the die?");
                Integer schemaPosition = scan.nextInt(); //TODO Controllo preventivo che vada bene la cella selezionata
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, increase));
            }
            else{
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, increase));
            }
        }
        else if (cardName.equals("Diamond Swab")) {
            System.out.println("TOOL USE Increase-Decrease - Diamond Swab " +
                    "\n TOOL_DESCRIP"); //TODO
            System.out.println("Select the die of Draftpool you want to edit");
            Integer draftpoolPos = scan.nextInt(); //TODO controllo preventivo
            System.out.println("The new value is... "); //TODO stampa
            System.out.println("Want you to place the new die? type: \n 1- Yes \n2- No");
            Integer decision = scan.nextInt();
            if (decision == 1) {
                System.out.println("Where do you want to place the die?");
                Integer schemaPosition = scan.nextInt(); //TODO Controllo preventivo che vada bene la cella selezionata
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, true));
            } else {
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, false));
            }
        }*/
    }

    @Override
    public void twoDiceMoveMenu(String cardName){
        System.out.println("YOU Used - " + cardName);
        if (cardName.equals("Manual Cutter")){
            System.out.println("With this tool you have to move 2 dice of same color of a die of the roundTrack. Check it out carefully!");
        } else {
            System.out.println("Move exactly two dice respecting placement restrictions");
        }
        System.out.println("FIRST DIE:");
        int oldPos1 = (selectRow()*4+selectColumn()-1);
        System.out.println("NEW DIE COORDINATES");
        int newPos1 = (selectRow()*4+selectColumn()-1);
        System.out.println("SECOND DIE");
        int oldPos2 = (selectRow()*4+selectColumn()-1);
        System.out.println("NEW DIE COORDINATES");
        int newPos2 = (selectRow()*4+selectColumn()-1);
        notify(new UseToolTwoDicePlacement(cardName, oldPos1, newPos1, oldPos2, newPos2));
        usedToolCard = true;
    }

    @Override
    public void corkLineMenu() {
        System.out.println("Place a die ignoring placement restriction");
        int draftpoolPosition = selectFromDraftPool();
        int row = selectRow();
        int column = selectColumn();
        notify(new UseToolCorkLine(draftpoolPosition-1, row*4+column-1));
        usedToolCard = true;
    }

    @Override
    public void wheelsPincherMenu(){
        System.out.println("Select another die, you will skip next turn");
        int draftpoolPosition = selectFromDraftPool();
        int row = selectRow();
        int column = selectColumn();
        notify(new UseToolWheelsPincher(draftpoolPosition-1, row*4+column-1));
        usedToolCard = true;
    }

    @Override
    public void circularCutter(){
        System.out.println("Swap a die from draftpool with a die from roundtrack");
        int draftpoolPosition = selectFromDraftPool();
        int roundTrackPosition = selectFromRoundTrack();
        notify(new UseToolCircularCutter(draftpoolPosition-1, roundTrackPosition-1));
        usedToolCard = true;
    }

    @Override
    public void invalidActionMessage(String message){
        System.out.println("Invalid action: " + message);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public void loseMessage(Integer position, List<String> scores){
        System.out.println("You lost! Your rank is " + position + "\n");
        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
    }

    @Override
    public void winMessage(List<String> scores){
        System.out.println("Congratulation! You won!");
        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
    }

    @Override
    public void correctAuthenthication(String username){
        this.username=username;
        System.out.println("Correct authentication!\nWelcome to Sagrada, " + username);
    }

    @Override
    public void timeOut() {
        inputReader.setTimeOut();
        cliState.setYourTurn(false);
    }

    @Override
    public void updateWpc(RefreshWpcCommand refreshCommand) {
        cliState.parseRefreshWPC(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public void updateTokens(RefreshTokensCommand refreshCommand) {
        cliState.parseRefreshTokens(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        cliState.parseRefreshRoundTrack(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        cliState.parseRefreshDraftPool(refreshCommand);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public void updateBoard(RefreshBoardCommand refreshCommand) {
        RefreshBoardCommand command = refreshCommand;
        cliState.parseRefreshBoard(command);
        cliPrinter.printSyntheticBoard(cliState);
    }

    @Override
    public void notify(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.setUsername(this.username);
        for (Observer observer : observers){
            observer.update(command);
        }
    }

    @Override
    public void messageBox(String message) {
        System.out.println("Message from Server: " + message);
    }

    @Override
    public void run() {
        while(active){
            String command = inputReader.readLine();
            manageCommand(command);
        }
    }

    private void manageCommand(String command){
        switch (command) {
            case "1":
                placeDie();
                break;
            case "2":
                useToolcard();
                //until card has been used, flow control is switched to toolcard menu
                usedToolCard = false;
                while(!usedToolCard){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        //nothing
                    }
                //flow control switch back to thread
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

    private void printHelp(){
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

    private void deleteInputReader(){
        inputReader.close();
        inputReader = null;
    }

    private void placeDie(){
        if (cliState.isYourTurn()) {
            int draftPos = selectFromDraftPool();
            int schemaCol = selectColumn();
            int schemaRow = selectRow();
            notify(new MoveChoiceDicePlacement(schemaRow - 1, schemaCol - 1, draftPos - 1));
        } else {
            System.out.println(NOT_YOUR_TURN);
        }
    }

    private void passTurn(){
        if(cliState.isYourTurn()){
            System.out.println("Passed turn");
            notify(new MoveChoicePassTurn(username));
        } else {
            System.out.println(NOT_YOUR_TURN);
        }
    }

    private void useToolcard(){
        if(cliState.isYourTurn()){
            System.out.println("What toolcard do you want to use?\n");
            cliPrinter.printToolcards(cliState);
            int choice = inputReader.readInt(1, cliState.getToolcards().size());
            notify(new MoveChoiceToolCard(choice-1));
        } else {
            System.out.println(NOT_YOUR_TURN);
        }
    }

    private int selectFromDraftPool(){
        System.out.println(String.format("Select die position in Draft Pool (number between 1 and %d)", cliState.getDraftpool().size()));
        return inputReader.readInt(1, cliState.getDraftpool().size());
    }

    public int selectFromRoundTrack(){
        System.out.println(String.format("Select die position in Round Track (number between 1 and %d)", cliState.getRoundTrack().size()));
        return inputReader.readInt(1, cliState.getRoundTrack().size());
    }

    private int selectRow(){
        System.out.println("Select row (number between 1 and 4)");
        return inputReader.readInt(1, 4);
    }

    private int selectColumn(){
        System.out.println("Select column (number between 1 and 5)");
        return inputReader.readInt(1, 5);
    }
}
