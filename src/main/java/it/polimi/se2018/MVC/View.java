package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.toolcards.ToolCard;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View extends Observable implements Observer{ //VIEW: Osservata da Controller, Osserva Model
    private ClientNetworkHandler clientNetworkHandler;
    private String playerUsername;
    private ClientNetworkHandler observer;

    public void addController(ClientNetworkHandler controller){

    }

    /**
     * Initialize Graphic or Command Line User Interface
     */
    public void showStartTurnMessageBox(){
        //Calls chooseWindowPatternCard(..)
    }

    /**
     * Set the board for a new Round
     */
    public void initializeRound(){


    }

    /**
     * Start a turn: menu with possible moves options
     */
    public void startTurn() {

    }

    private void askWindowPatternCard(ArrayList<WindowPatternCard> cards){

    }

    public void showAskUsernamePanel(){

    }

    public void showChooseToolCardPanel(ArrayList<WindowPatternCard> wpc){

    }


    public void askPlayerMove(){

    }

    public void showWin(ArrayList<String> scores){

    }

    public void showLose(Integer position, ArrayList<String> scores){

    }

    /** use to refresh board and else
     *
     */
    public void notifyOtherPlayerMove(){

    }

    public void showInvalidInput(){

    }

    public void showCorrectAuthenthication(String username){

    }


    public void showIncorrectAuthenthication(String message){

    }

    public void showDisconnectedPlayer(Player p){

    }

    public void askLoginInformation(){

    }

    /** Some other player action
     * or the player's one
     */
    public void showActionPerformed(){

    }

    public void update(Model playerModel){ //Osserva il Model e con Update, fa l'update del model locale
        //Calls the right method to update the Graphical Board;
        //The model is already updated by the ClientController, no worries about that
        //In case there is a CLI, does anything
    }

    public void notify(ClientToServerCommand command){ //da passare all'observer
        //observer.update(command);
    }

    public void addObserver(){ //Used just to set the connectionHandler
    }

    public void showWaitForYourTurn(){

    }
    public void showInvalidInput(String message){

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
/*
<<Interface>>


// notify the move of a player
        + playerMove: void
        + showWin(): void
        + showLose(): void
        + askMove(): void
// Has to show: 1- Place a die 2- Use toolcard- 3- Pass turn
        + showInvalidInput(): void
        + refreshBoard(): void
        + askLoginInformation(): void
        + showCorrectAuthentication(String username): void
        + showIncorrectAuthentication(String username): void
        + showDisconnectedPlayer(Player p): void
        + showActionPerformed(): void //TO COMPLETE
        + notify(): void
//notify() notify something to the server depending from the player status



        + addObserver()

        + update(): void
// nel model, va a selezionare le cose da visualizzare.


// This is a comment
// (--) This is a section break
// Line break (\n) enter in-between text for line break
*/