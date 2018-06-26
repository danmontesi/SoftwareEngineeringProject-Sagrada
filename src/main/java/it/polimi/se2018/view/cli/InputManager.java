package it.polimi.se2018.view.cli;

import com.sun.media.jfxmedia.logging.Logger;
import it.polimi.se2018.exceptions.TimeUpException;
import it.polimi.se2018.utils.Observer;

import java.io.IOException;

public class InputManager implements Runnable, Observer {
    private InputReader inputReader;
    private boolean yourTurn = false;
    private boolean active = true;



    public InputManager() {
        inputReader = new InputReader();
    }

    @Override
    public void run() {
        while(active){
            try {
                String command = inputReader.readLine();
                manageCommand(command);
            } catch (IOException e) {
                Logger.logMsg(1, "Qualcosa è andato storto");
            } catch (TimeUpException e){
                yourTurn = false;
            }
        }
    }

    @Override
    public void update(Object event) {

    }

    private void manageCommand(String command){
        try{
            switch(command){
                case "1":
                    if(yourTurn){
                        System.out.println("Hai scritto a");
                        System.out.println("Scrivi il numero del dado");
                        String dado = inputReader.readLine();
                        System.out.println("Dado letto: " + Integer.parseInt(dado));
                    } else {
                        System.out.println("Non è il tuo turno, mossa non permessa");
                    }
                    break;
                case "2":
                    System.out.println("Hai scritto b");
                    break;
                case "3":
                    passTurn();
                    break;
                case "help":
                    printHelp();
                    break;
                default:
                    System.out.println("Invalid command, print help for further information");
            }
        } catch (IOException e){
            System.out.println("Something went wrong");
        } catch (TimeUpException e){
            yourTurn = false;
        }
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public void setTimeout(){
        inputReader.setTimeOut();
    }

    private void passTurn(){
        System.out.println("Passed turn");
        yourTurn = false;
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

    private void placeDie(){
        if(!yourTurn){
            System.out.println("You cannot place a die if it's not your turn");
        } else {

        }
    }
}
