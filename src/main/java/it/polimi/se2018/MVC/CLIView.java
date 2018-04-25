package it.polimi.se2018.MVC;

import it.polimi.se2018.Model;
import it.polimi.se2018.WindowPatternCard;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Scanner;

public class CLIView implements Observable, Observer{

    private VirtualView virtualView;
    //private Model model;
    //private Controller controller;

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
        model.toString();

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
