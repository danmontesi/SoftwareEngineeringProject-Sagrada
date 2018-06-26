package it.polimi.se2018.view.cli;

import com.sun.media.jfxmedia.logging.Logger;
import it.polimi.se2018.exceptions.TimeUpException;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.cli.cliState.CliState;
import it.polimi.se2018.view.cli.cliState.PublicObjectiveLight;
import it.polimi.se2018.view.cli.cliState.ToolcardLight;

import java.io.IOException;

public class InputManager implements Runnable, Observer {
    private InputReader inputReader;
    private boolean active = true;
    private CliState cliState;
    private CLIPrinter cliPrinter = new CLIPrinter();



    public InputManager(Observable observable){
        inputReader = new InputReader();
        cliState = new CliState();
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
                //nothing
            }
        }
    }

    @Override
    public void update(Object event) {
        cliState = (CliState) event;
    }

    private void manageCommand(String command){
        try{
            switch(command){
                case "1":
                    if(cliState.isYourTurn()){
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
                case "print -c":
                    cliPrinter.printCompleteBoard(cliState);
                    break;
                case "print -pr":
                    System.out.println(cliState.getPrivateObjectiveCard());
                    System.out.println(cliState.getPrivateObjectiveCardDescription());
                    break;
                case "print -pu":
                    for(int i = 0; i < cliState.getPublicObjectiveCards().size(); i++){
                        PublicObjectiveLight card = cliState.getPublicObjectiveCards().get(i);
                        System.out.println(card.getName() + "\n\t" + card.getDescription());
                    }
                    break;
                case "print -t":
                    for(int i = 0; i < cliState.getToolcards().size(); i++){
                        ToolcardLight card = cliState.getToolcards().get(i);
                        System.out.println(String.format("%d) %s - Tokens: %d\n\t%s", i+1, card.getToolcardName(), card.getTokens(), card.getDescription()));
                    }
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
        }
    }


    public void setTimeout(){
        inputReader.setTimeOut();
    }

    private void passTurn(){
        System.out.println("Passed turn");
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
        if(!cliState.isYourTurn()){
            System.out.println("You cannot place a die if it's not your turn");
        } else {

        }
    }
}
