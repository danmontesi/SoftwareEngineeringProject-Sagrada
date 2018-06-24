package it.polimi.se2018.view.CLI;

import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.clientModel.ClientModel;
import it.polimi.se2018.view.clientModel.PlayerLight;
import it.polimi.se2018.view.clientModel.PublicObjectiveLight;
import it.polimi.se2018.view.clientModel.ToolcardLight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLIView extends View {

    /**
     * CliView receives a clone of current model each time it's Player's turn
     */

    public CLIView(Observer observer){
        register(observer);
        System.out.println("ATTESA DI GIOCATORI...");
        inputReader = new InputReader();
        cliModel = new ClientModel();
    }

    private Scanner scan = new Scanner(System.in); // Can be replaced with BufferedReader?
    private InputReader inputReader;
    private CLIPrinter cliPrinter = new CLIPrinter();
    private ClientModel cliModel;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    //OGNI METODO DEVE CHIAMARE LA notify() della view, passandole un EVENTO

    @Override
    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards){
        for (WindowPatternCard card : cards) {
            cliPrinter.printWPC(card);
        }
        System.out.println("\n Which one do you chose?");
        for (int i = 0; i < cards.size(); i++){
            System.out.println(i+1 + ")" + cards.get(i).getCardName());
        }
        try {
            int chosen = Integer.parseInt(inputReader.readLine());
            notify(new ChosenWindowPatternCard(cards.get(chosen - 1).getCardName()));
            System.out.println("Hai scelto: " + cards.get(chosen - 1).getCardName());
        } catch (TimeoutException e) {
            LOGGER.log(Level.INFO, "Timeout: your wpc is chosen randomly");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void startTurnMenu(){
        System.out.println("IT'S YOUR TURN!\n");
        continueTurnMenu(true, true);
    }

    @Override
    public void startGame() {
        //useless for CLI
    }

    @Override
    public void otherPlayerTurn(String username) {
        System.out.println("It's " + username + "'s turn");
    }

    @Override
    public void authenticatedCorrectlyMessage(String username) {
        this.username=username;
        cliModel.getPlayer(0).setUsername(username);
        System.out.println("Authenticated correctly!\nWelcome to Sagrada, " + this.username);
    }


    public void AllowedUseToolMessage(String message){
        LOGGER.log(Level.INFO, "Toolcard used correctly");
    }

    @Override
    public void continueTurnMenu(boolean move, boolean tool){

        boolean performedAction = false;
        int choice;
        System.out.println("What do you want to do?");
        System.out.println(move ? "1 - Place die" : "");
        System.out.println(tool ? "2 - Use Tool" : "");
        System.out.println("3 - Pass Turn");
        try {
            choice = Integer.parseInt(inputReader.readLine());
        } catch (TimeoutException e) {
            LOGGER.log(Level.INFO, "Timeout: you will skip this turn");
            return;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return;
        }
        while(!performedAction){
            switch(choice){
                case 1:
                    System.out.println(String.format("Select die position in Draft Pool (number between 1 and %d)", cliModel.getDraftpool().size()));
                    int draftPos = scan.nextInt();
                    System.out.println("Select row (number between 1 and 4)");
                    int schemaRow = scan.nextInt();
                    System.out.println("Select column (number between 1 and 5)");
                    int schemaCol = scan.nextInt();
                    notify(new MoveChoiceDicePlacement(schemaRow - 1,schemaCol - 1,draftPos - 1));
                    performedAction = true;
                    break;
                case 2:
                    System.out.println("Which tool want you to use?");
                    int chosenToolNum = scan.nextInt();
                    notify(new MoveChoiceToolCard(chosenToolNum));
                    performedAction = true;
                    break;
                case 3:
                    System.out.println("Passed turn");
                    notify(new MoveChoicePassTurn(cliModel.getPlayer(0).getUsername()));
                    performedAction = true;
                    break;
                default:
                    System.out.println("Incorrect action, please select a number among the valid ones in menu");
            }
        }

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
            Integer schemaOldPos = scan.nextInt();
            System.out.println("Select the cell you cant to move the die in");
            Integer schemaNewPos = scan.nextInt();
            notify(new UseToolMoveDieNoRestriction(cardName, schemaOldPos, schemaNewPos));
        }
        else if (cardName.equals("Copper Foil Reamer")){
            System.out.println("TOOL USE - Copper Foil Reamer " +
                    "\n You can move a die without value restriction");
            System.out.println("Select where is the die you want to move in your schema:");
            Integer schemaOldPos = scan.nextInt();
            System.out.println("Select the cell you cant to move the die in");
            Integer schemaNewPos = scan.nextInt();
            notify(new UseToolMoveDieNoRestriction(cardName, schemaOldPos, schemaNewPos));
        }

    }

    @Override
    public void changeDieValueMenu(String cardName){
        if (cardName.equals("Roughing Forceps")){
            System.out.println("TOOL USE Increase-Decrease - Roughing Forceps " +
                    "\n TOOL_DESCRIP"); //TODO
            System.out.println("Select the die of Draftpool you want to edit");
            Integer draftpoolPos = scan.nextInt(); //TODO controllo preventivo
            System.out.println("Want you to increase the value? \n 1- Increase" + "\n 2- Decrease"); //TODO: controllo per evitare che il dado scelto sia un 6 e aumenti o sia un 1 e diminuisca
            Integer chosen = scan.nextInt();
            boolean increase = chosen==1;
            //TODO: Devo stampare il valore aumentato
            System.out.println("Want you to place the new edited die? type: \n 1- Yes \n2- No");
            Integer decision = scan.nextInt();
            if (decision==1){
                System.out.println("Where do you want to place the die?");
                Integer schemaPosition = scan.nextInt(); //TODO Controllo preventivo che vada bene la cella selezionata
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, schemaPosition, increase));
            }
            else{
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, null, increase));
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
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, schemaPosition, true));
            } else {
                notify(new UseToolChangeDieValue(cardName, draftpoolPos, null, false));
            }
        }
    }
    @Override
    public void twoDiceMoveMenu(String cardName){
        System.out.println("YOU Used - " + cardName);
        if (cardName.equals("Manual Cutter")){
            System.out.println("With this tool you have to move 2 dice of same color of a die of the roundTrack. Check it out carefully!");
        }
        System.out.println("Select the index of first die you want to move");
        Integer oldPos1 = scan.nextInt();
        System.out.println("Select the index of the cell where you want to move it:");
        Integer newPos1 = scan.nextInt();
        System.out.println("Select next die");
        Integer oldPos2 = scan.nextInt();
        System.out.println("Select the index of the cell where you want to move");
        Integer newPos2 = scan.nextInt();
        notify(new UseToolTwoDicePlacement(cardName, oldPos1, newPos1, oldPos2, newPos2));
    }

    @Override
    public void corkLineMenu(){ //Move with no placement restriction
        // notify(UseToolCorkLine(Integer draftpoolPosition, integer schemaPosition)
    }

    // Place another die instead of 1 per turn. Skip next turn
    @Override
    public void wheelsPincherMenu(){
        //notify(new UseToolWheelsPincher(Integer drafpoolDieIndex, Integer schemaIndex)
    }

    @Override
    public void circularCutter(){ //Swap a die of draftpool with a die of roundtrack
        //notify(new UseToolCircularCutter(Integer draftpoolIndex, Integer roundtrackIndex)

    }

    @Override
    public void invalidActionMessage(String message){
        System.out.println("Invalid action: " + message);
        printSyntheticBoard();
    }

    @Override
    public void loseMessage(Integer position, ArrayList<String> scores){
        System.out.println("You lost! Your rank is " + position + "\n");

        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
    }

    @Override
    public void winMessage(ArrayList<String> scores){
        System.out.println("Congratulation! You won!");

        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
    }

    @Override
    public void correctAuthenthication(String username){
        this.username=username;
        System.out.println("Correct authentication!\nWelcome to Sagrada, " + cliModel.getPlayer(0).getUsername());
    }

    @Override
    public synchronized void timeOut() {
        this.inputReader.setTimeOut(true);
    }

    @Override
    public void updateWpc(RefreshWpcCommand refreshCommand) {
        cliModel.parseRefreshWPC(refreshCommand);
        printSyntheticBoard();
    }

    @Override
    public void updateTokens(RefreshTokensCommand refreshCommand) {
        cliModel.parseRefreshTokens(refreshCommand);
        printSyntheticBoard();
    }

    @Override
    public void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        cliModel.parseRefreshRoundTrack(refreshCommand);
        printSyntheticBoard();
    }

    @Override
    public void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        cliModel.parseRefreshDraftPool(refreshCommand);
        printSyntheticBoard();
    }


    @Override
    public void notify(Object event) {
        ClientToServerCommand command = (ClientToServerCommand) event;
        command.setUsername(this.username);
        for (Observer observer : observers)
            observer.update(command);
    }

    @Override
    public void messageBox(String message) {
        System.out.println("Message from Server: " + message);
    }

    @Override
    //update entire board
    public void update(Object event) {
        RefreshBoardCommand command = (RefreshBoardCommand) event;
        cliModel.parseRefreshBoard(command);
        printSyntheticBoard();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }



    private void printSyntheticBoard(boolean clearScreen){
        if (clearScreen){
            clearScreen();
        }
        System.out.println("Round Track:\n");
        cliPrinter.printInlineList(cliModel.getRoundTrack());
        System.out.println("Draft Pool:\n");
        cliPrinter.printInlineList(cliModel.getDraftpool());
        PlayerLight me = cliModel.getPlayer(0);
        System.out.println(me.getUsername() + " - Tokens: " + me.getTokens());
        cliPrinter.printWPC(me.getWpc());
    }

    private synchronized void printSyntheticBoard(){
        printSyntheticBoard(true);
    }

    private void printToolcards(){
        System.out.println("Toolcards:");
        for(int i = 0; i < cliModel.getToolcards().size(); i++){
            ToolcardLight card = cliModel.getToolcards().get(i);
            System.out.println(String.format("%d) %s - Tokens: %d", i+1, card.getToolcardName(), card.getTokens()));
            System.out.println("\t" + card.getDescription());
        }
    }

    private void printPublicObjectiveCards(){
        System.out.println("Public Objective Cards:");
        for(int i = 0; i < cliModel.getPublicObjectiveCards().size(); i++){
            PublicObjectiveLight card = cliModel.getPublicObjectiveCards().get(i);
            System.out.println(card.getName()+ "\n\t" + card.getDescription());
        }
    }

    private synchronized void printCompleteBoard(){
        clearScreen();
        printPublicObjectiveCards();
        printToolcards();

        for (int i = 1; i < cliModel.getAllPlayers().size(); i++){
            PlayerLight player = cliModel.getPlayer(i);
            System.out.println(player.getUsername() + " - Tokens: " + player.getTokens());
            cliPrinter.printWPC(player.getWpc());
            System.out.println("\n");
        }
        printSyntheticBoard(false);
    }



}
