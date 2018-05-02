package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.toolcards.CircularCutter;
import it.polimi.se2018.toolcards.Gavel;
import it.polimi.se2018.toolcards.RoughingForceps;
import it.polimi.se2018.toolcards.ToolCard;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Scanner;

public class CLIView extends View{

    Model currentModel;

    private ClientController clientController;

    /**
     * Can be used for mark the status as :
     * Disconnected-
     * Connected
     *
     * or (to Decide)
     *
     *CurrentPlayer
     * notCurrent
     */
    private String playerState;


    /**
     * Initialize Graphic or Command Line User Interface
     */
    public void initializeGame(){
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
    public void startTurn(Model model) {
        this.currentModel = model; //NB: Is a clone come from a Command
        model.toString();
        System.out.println("It's your turn, "+ playerUsename +"!");
        System.out.println("What do you want to do?");
        System.out.println("1 - Place a die");
        System.out.println("2 - Use ToolCard");
        System.out.println("3 - Pass the Turn");
        System.out.println("4 - Disconnect from the game");

        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt();
        //TODO Che succede se inserisce un altro numero?
        switch(c) {
            case 1:
                break;
            case 2:
                chooseToolCardToUse(model.getExtractedToolCard());
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                ;
        }

    }

    private void chooseWindowPatternCard(ArrayList<WindowPatternCard> cards){
        for (WindowPatternCard p : cards) {
            p.toString();
        }
        System.out.println("Choose the card you want:");
        //TODO scanner
        int i=0;
        //TODO end scan

        WindowPatternCard chosen = cards.get(i);

        //send to the server the answer...

    }

    public void chooseToolCardToUse(ArrayList<ToolCard> cards){
        cards.toString(); // TODO better toString
        System.out.println("Which one you want to use?");
        //TODO scanner
        clientController.sendCommand(new ChosenToolCardCommand(2));
    }

    public void changePlayerState(String state){

    }

    public void askPlayerMove(){

    }

    public void showWin(){

    }

    public void showLose(){

    }

    /** use to refresh board & else
     *
     */
    public void notifyOtherPlayerMove(){

    }

    public void showInvalidInput(){

    }

    public void showCorrectAuthenthication(String username){

    }


    public void showIncorrectAuthenthication(String username){

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
    public void update(java.util.Observable o, Object obj){

    }

    public void addObserver(){

    }





    public CLIView(){
        ;
    }



    public void performMove(){
        System.out.println("Chose which die you prefere to pick from the DraftPool");
        currentModel.getCurrentRound.getDraftPool().toString();
        Scanner scan = new Scanner(System.in);
        int dieChoose = scan.nextInt();

        System.out.println("Where do you want to put it? Digit Row and Column");

        Scanner scanRow = new Scanner(System.in);
        int rowChoose = scanRow.nextInt();
        Scanner scanCol = new Scanner(System.in);
        int colChoose = scanCol.nextInt();
        //TODO lo farà SocketConnection
        // connection.performMoveClientToServer(dice, row, col, player)
    }

    public void notifyCorrectMove(){
        System.out.println("The move is correct");
        System.out.println("Now wait until it's your turn");
    }

    /**
     * Tools methods: i call useTool( param)
     * Like a Strategy design pattern, thanks to paternity, it will call the right method
     * @param tool
     */

    public void useTool(RoughingForceps tool){
        // Things depends on the tool to use
    }

    public void useTool(CircularCutter tool){
        // Things depends on the tool to use
    }
    public void useTool(Gavel tool){
        // Things depends on the tool to use
    }
    public void waitForYourTurn(Model model){
        model.toString();
        System.out.println("You have to wait until player" + model.getCurrentRound.getCurrentPlayer() + "finishes");
    }
    public void playersArrived(){

        /*
            waits for a response from VirtualView
         */
    }


    public void notifyAlreadyFourPlayers(){
        System.out.println("There are already 4 players at that game!");
    }

    /**
     * Layout of the Player move choice
     */
    public void startPlayerAction(){

    }


    // TODO EDIT & ADD notify to Model! ( that calls update!)
    // EDIT: ho bisogno di una view e anche una Virtual View per ogni giocatore!!!
    // Il controller invece misa che è uno...!
    public void update(MoveMessage message)
    {
        String resultMsg = message.getBoard().toString();
        if (message.getBoard().isGameOver(message.getPlayer().getMarker())) {
            if (message.getPlayer() == player) {
                resultMsg = resultMsg + "\nHai vinto!";
            } else {
                resultMsg = resultMsg + "\nHai perso!";
            }
        }
        else if(message.getBoard().isFull()){
            resultMsg = resultMsg + "\nParità!";
        }
        if(message.getPlayer() == player){
            resultMsg = resultMsg + "\nAttendi la mossa dell'altro giocatore!";
        }
        else{
            resultMsg = resultMsg + "\nScegli la tua mossa:";
        }
        showMessage(resultMsg);
    }
}
