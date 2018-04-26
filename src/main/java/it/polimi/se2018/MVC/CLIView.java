package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.WindowPatternCard;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Scanner;

public class CLIView implements Observable, Observer{

    private VirtualView virtualView;
    //private Model model;
    private Player assignedPlayer; //TODO pre-assegno subito e lo metto nel model, oppure lo assegno dopo
    //private Controller controller;

    private Model currentModel;

    public CLIView(){
        ;
    }

    public void initializePatternCardChoosingPanel(ArrayList<WindowPatternCard> patternCardsToChooseFrom){
        for (WindowPatternCard p : patternCardsToChooseFrom) {
            p.toString();
        }
        System.out.println("Choose the card you want:");
        //TODO scanner
        int i=0;
        // TODO end scan

        WindowPatternCard chosen = patternCardsToChooseFrom.get(i);

        //send to the server the answer...

    }

    public void initializeChoosingPanel(){
        System.out.println("Welcome to Sagrada");
        System.out.println("you will play a fantastic game!");
        System.out.println("which connection do you prefere to use?");
        System.out.println("0: Socket\n" + "1: RMI");
        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt(); //TODO: pensa se fare una classe che lo implementi oppure no
        switch(c) {
            case 1:
                break;
            case 0:
                break;
            default:
                ;
        }
        System.out.println("waiting for players... wait please");
        virtualView.waitForPlayers();



    }


    public void startTurn(Model model){
        this.currentModel = model;
        model.toString();
        System.out.println("E' il tuo turno, "+ assignedPlayer.getUsername()+"!");
        System.out.println("Cosa vuoi fare?");
        System.out.println("1 - Mossa");
        System.out.println("2 - Passa il turno");
        System.out.println("3 - Usa ToolCard");
        System.out.println("4 - Abbandona il gioco");

        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt(); //TODO: pensa se fare una classe che lo implementi oppure no
        switch(c) {
            case 1:
                performMove();
                break;
            case 0:
                break;
            default:
                ;

        }
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
}
