package it.polimi.se2018.client.CLI;

import it.polimi.se2018.client.GUI.GameBoardController;
import it.polimi.se2018.client.View;
import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class CLIView extends View {

    /**
     * CliView receives a clone of current model each time it's Player's turn
     */

    public CLIView(Observer observer){
        register(observer);
        System.out.println("ATTESA DI GIOCATORI...");
        inputReader = new InputReader();
    }

    //TODO Set! username

    private Scanner scan = new Scanner(System.in); // Can be replaced with BufferedReader?

    private InputReader inputReader;

//TODO          PER CHI FA LA VIEW:
//TODO          OGNI METODO DEVE CHIAMARE LA notify() della view, passandole un EVENTO.
//TODO          ognuno dei metodi quì sotto prima chiede l'input dall'utente, poi fa notify(new chosen
    @Override
    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards){
        for (WindowPatternCard card : cards) {
            System.out.println(":- Difficulty = "+ card.getDifficulty()+ "\n");
            System.out.println(card.toString());
        }
        System.out.println("\n\n Which one do you chose?");
        //TODO: menu per numero scorretto
        int chosen = scan.nextInt();
        notify(new ChosenWindowPatternCard(cards.get(chosen).getCardName()));
    }

    @Override
    public void startTurnMenu(){
        //notify( new MOVE / new TOOLUSE / new PASSTURN )
        System.out.println("What do you want to do?");
        System.out.println("1- Place die");
        System.out.println("2- Use Tool");
        System.out.println("3- Pass Turn");
        int choice = 3;
        try {
            try {
                choice = Integer.parseInt(inputReader.readLine());
            } catch (TimeoutException e) {
                System.out.println("Timeout: you will skip this turn");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch(choice){
            case 1:
                System.out.println("Inserisci rispettivamente");
                int draftPos = scan.nextInt();
                int schemaRow = scan.nextInt();
                int schemaCol = scan.nextInt();
                notify(new MoveChoiceDicePlacement("",schemaRow,schemaCol,draftPos));
                break;
            case 2:
                System.out.println("Which tool want you to use?");
                int chosenToolNum = scan.nextInt();
                notify(new MoveChoiceToolCard(chosenToolNum));
                break;
            case 3:
                System.out.println("passed turn");
                notify(new MoveChoicePassTurn(""));
        }
    }

    @Override
    public void otherPlayerTurn(String username) {
        //printother player name fo notify its turn
    }

    @Override
    public void authenticatedCorrectlyMessage(String message) {
        //print that the player authenticated correctly with username
    }


    public void AllowedUseToolMessage(String message){
        //TODO Questo metodo non invia niente, mostra solo il messaggio
    }

    @Override
    public void continueTurnMenu(boolean move, boolean tool){
        //notify( new MOVE / new TOOLUSE / new PASSTURN )
        System.out.println("What do you want to do?");
        System.out.println(move? "1- Place die": "");
        System.out.println(tool? "2- Use Tool": "");
        System.out.println("3- Pass Turn");
        int choice = scan.nextInt();
        switch(choice){
            case 1:
                System.out.println("Inserisci rispettivamente");
                int draftPos = scan.nextInt();
                int schemaRow = scan.nextInt();
                int schemaCol = scan.nextInt();
                notify(new MoveChoiceDicePlacement("",schemaRow,schemaCol,draftPos));
                break;
            case 2:
                System.out.println("Which tool want you to use?");
                break;
            case 3:
                System.out.println("passed turn");
                notify(new MoveChoicePassTurn(""));
        }
    }

    @Override
    public void newConnectedPlayer(String username) {
        //print that player connected

    }

    @Override
    public void playerDisconnection(String username) {
        //print that player disconnected

    }

    @Override
    public void firmPastryBrushMenu(int value){ // Changes a value of a die of draftpool with the one received (value)
        //Notify(new UseToolFirmPastr...(Integer chosenDieDraftpoolIndex, Integer schemaPosition (can be null), boolean placedDie (true if the player place the die)
    }

    //TODO dan
    public void firmPastryThinnerMenu(String color, int value){ // receives a new die from Servfer with Color = color, Value = value. The player has to send one of draftpool dice to swap them
        //Notify(new UseToolFirmPastr...(Integer chosenDieDraftpoolIndex, Integer schemaPosition (where he wants to place the die, can be null), **String color, Integer value***, boolean placedDie (true if the player place the die)
        // ** il server deve ricevere il dado che ha mandato al client perchè non lo salva da nessuna parte
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
        System.out.println(message);
        //TODO. non contiene niente, mostra solo i messaggio
    }

    @Override
    public void loseMessage(Integer position, ArrayList<String> scores){
        //TODO. non contiene niente, mostra solo i messaggio. attento a parsare bene gli score
        System.out.println("You lost! Your rank is " + position + "\n");

        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
        //TODO. non contiene niente, mostra solo i messaggio, attento a parsare bene gli scores
    }

    @Override
    public void winMessage(ArrayList<String> scores){
        System.out.println("Congratulation! You won!");

        System.out.println("Here other players ordered scores:");
        for (String score : scores){
            System.out.println(score);
        }
        //TODO. non contiene niente, mostra solo i messaggio, attento a parsare bene gli scores
    }

    @Override
    public void correctAuthenthication(String username){
        //TODO. non contiene niente, mostra solo i messaggio
    }

    @Override
    public void timeOut() {
        this.inputReader.setTimeOut(true);
    }

    @Override
    public void updateWpc() {
        //TODO
    }

    @Override
    public void updateTokens() {
    //TODO
    }

    @Override
    public void updateRoundTrack() {
    //TODO
    }

    @Override
    public void updateDraftPool() {
    //TODO
    }


    @Override
    public void notify(Object event) {
        for (Observer observer : observers)
            observer.update(event);
    }

    @Override
    public void messageBox(String message) {
        System.out.println(message);
    }

    @Override
    public void update(Object event) {
        //Osserva il Model e con Update, fa l'update del model locale
        //Calls the right method to update the Graphical Board;
        //The model is already updated by the ClientController, no worries about that
        //In case there is a CLI, does anything
        System.out.println("\n->Updating board<-\n");
        ServerToClientCommand command = (ServerToClientCommand) event;
        System.out.println("ricevuto \n"+ command.getMessage());
    }

}
