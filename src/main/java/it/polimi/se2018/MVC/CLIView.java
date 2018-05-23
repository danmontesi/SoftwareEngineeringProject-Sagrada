package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.client_to_server_command.ChosenToolCardCommand;
import it.polimi.se2018.client_to_server_command.UpdateUsernameCommand;
import it.polimi.se2018.toolcards.ToolCard;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView extends View{

    /**
     * CliView receives a clone of current model each time it's Player's turn
     */

    private String playerUsername;
    private Model currentModel;
    private Scanner scan = new Scanner(System.in);


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


    public void addController(ClientController controller){
        this.clientController = controller;
    }
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
        System.out.println("It's your turn, "+ playerUsername +"!");
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
                askToolCardToUse(currentModel.getExtractedToolCard());
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                ;
        }

    }

    private void askWindowPatternCard(ArrayList<WindowPatternCard> cards){
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

    public void askToolCardToUse(ArrayList<ToolCard> cards){
        cards.toString(); // TODO better toString
        System.out.println("Which one you want to use?");
        //TODO scanner
        //clientController.sendCommand(new ChosenToolCardCommand(2));
    }

    public void showAskUsernamePanel(){
        System.out.println("Insert a username:");
        Scanner i = new Scanner(System.in);
        String username = i.next();
        //EDIT
        System.out.println("Faccio send Update...");

        clientController.sendCommand(new UpdateUsernameCommand(username));
    }

    public void showWin(){

    }

    public void showLose(){

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



    public void askPlayerMove(){
        System.out.println("Chose which die you prefere to pick from the DraftPool");
        currentModel.getCurrentRound().getDraftPool().toString();
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

    public void showCorrectMove(){
        System.out.println("The move is correct");
        System.out.println("Now wait until it's your turn");
    }

    public void showWaitForYourTurn(Model model){
        model.toString();
        System.out.println("You have to wait until player" + model.getCurrentRound().getCurrentPlayer() + "finishes");
    }



}
