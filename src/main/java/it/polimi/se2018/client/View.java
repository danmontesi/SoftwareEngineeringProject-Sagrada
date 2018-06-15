package it.polimi.se2018.client;

import it.polimi.se2018.commands.server_to_client_command.ServerToClientCommand;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;

public abstract class View extends Observable implements Observer { //VIEW: Osserva Model, Osservato da Controller

    /**
     * OSS: LA VIEW INCAPSULA DEGLI EVENTI! E POI LI MANDA GRAZIE AL CONTROLLER (update..)
     *
     */
    protected String username;

    public String getUsername() {
        return username;
    }

    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards){

    }

    public void startTurnMenu(){
        //Calls chooseWindowPatternCard(..)
    }

    public void AllowedUseToolMessage(String message){

    }

    public void askAuthenticatedCorrectlyMessage(String message){

    }

    public void continueTurnMenu(boolean move, boolean tool){

    }

    public void correctUseTool(int numTool){
        //Switch che in base al tipo di tool
        //i possibili metodi sono PRIVATI e sono questi quì
    }

    public void firmPastryBrushMenu(int value){

    }

    public void firmPastryThinnerMenu(String color, int value){

    }

    public void moveDieNoRestrictionMenu(String cardName){

    }

    public void changeDieValueMenu(String cardName){

    }

    public void twoDiceMoveMenu(String cardName){

    }

    public void corkLineMenu(){

    }

    public void wheelsPincherMenu(){

    }

    public void circularCutter(){

    }

    public void invalidActionMessage(String message){
        //Di qualsiasi tipo:
        // sia per il tool (seguita da una richiesta di uso del tool, di nuovo)
        // sia per il piazzamento di un dado scorretto
        // sia per qualsiasi azione non va bene
        //OSS: il message contiene il messaggio con le informazioni dell'errore
    }

    public void loseMessage(Integer position, ArrayList<String> scores){

    }

    public void winMessage(ArrayList<String> scores){

    }

    public void correctAuthenthication(String username){

    }


    public void timeOut(){

    }
    // Methods for Obs/Obsvb

    @Override
    public void update(Object event) {
        //Osserva il Model e con Update, fa l'update del model locale
        //Calls the right method to update the Graphical Board;
        //The model is already updated by the ClientController, no worries about that
        //In case there is a CLI, does anything
        ServerToClientCommand command = (ServerToClientCommand) event;
        System.out.println("ricevuto "+ command.getMessage()); // DEVE ESSERE USATO ESCLUSIVAMENTE PER L'AGGIORNAMENTO MODEL
    }

    @Override
    public void notify(Object event){ //da passare all'observer
        //observer.update(command);
    }

    public abstract void messageBox(String message);
}