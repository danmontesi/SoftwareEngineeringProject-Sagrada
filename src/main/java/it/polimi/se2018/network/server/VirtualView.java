package it.polimi.se2018.network.server;

import it.polimi.se2018.MVC.View;
import it.polimi.se2018.Model;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.server_to_client_command.*;

import java.util.ArrayList;

/**
 * The class represents the virtual view. Is Observable of Controller, Observer of Model
 */
public class VirtualView extends View{

    String username;
    Observer observer; // Il controller TODO: Togli e usa quello dato dall'Observable
    Observable observable; // Il model

    public VirtualView(Observer controller, Model model, String username){
        this.observer = controller;
        this.observable = model; //this can be omitted
        this.username = username;
        Server.getUserMap().put(username, this);
        System.out.println("Creata virtual view di username: "+username);
    }

    /**
     * Nel VirtualView devono stare:
     * - il metodo notify(un comando) che da' al Controller i comandi che prende dal client
     * poi il Controller deve associare col binding un effetto (grazie al Visitor)
     * -> Il clientToServerCOmmand prende parametro Controller
     *
     * VirtualView ha tutti i metodi di View (di tipo Show(...))
     */

    public void notify(ClientToServerCommand command){ // chiamata dalla rete
        observer.update(command);
    }

    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards){
        StringBuilder cardNames = new StringBuilder();
        for (WindowPatternCard card : cards)
            cardNames.append(card.getCardName()+",");
        Server.getConnectedClients().get(username).notifyClient(new ChooseWindowPatternCardCommand(cardNames.toString()));
        //DATE NEL FORMATO nomeCarta nomeCarta nomeCarta
    }

    /**
     * Handle the network calling the connection associated to the username
     */
    public void startTurnMenu(){
        Server.getConnectedClients().get(username).notifyClient(new StartPlayerTurnCommand());
    }

    public void AllowedUseToolMessage(String message, Integer toolPosition){
        Server.getConnectedClients().get(username).notifyClient(new AllowedUseToolCommand("Allowed", toolPosition));
    }

    public void askAuthenticatedCorrectlyMessage(String message){
        //TODO elimina
    }

    public void continueTurnMenu(boolean move, boolean tool){
        System.out.println("SENDING CONTINUETURNMENU");
        Server.getConnectedClients().get(username).notifyClient(new ContinueTurnCommand(move, tool));
    }

    public void correctUseTool(int numTool){
        Model temp = (Model) observable;
        temp.getExtractedToolCard().get(numTool);
        //SWITCH DEPENDENT TO THE CARD NAME
    }

    public void firmPastryBrushMenu(int value){
        Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolFirmPastryBrush1(value));
    }

    public void firmPastryThinnerMenu(String color, int value){
        Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolFirmPastryThinner1(color, value));
    }

    /*** ONLY in VIEW EXTENDS IN CLIENT
     private void copperFoilReamerMenu(){

     }

     private void corkLineMenu(){

     }

     private void diamondSwabMenu(){

     }

     private void eglomiseBrushMenu(){

     }

     private void firmPastryBrushMenu(){

     }

     private void firmPastryThinnerMenu(){

     }

     private void gavelMenu(){

     }

     private void lathekin(){

     }

     private void manuelCutter(){

     }

     private void roughingForceps(){

     }

     private void wheelsPincher(){

     }
     */

    public void invalidActionMessage(String message){
        System.out.println("SENDING INVALIDACTION");
        //Di qualsiasi tipo:
        // sia per il tool (seguita da una richiesta di uso del tool, di nuovo)
        // sia per il piazzamento di un dado scorretto
        // sia per qualsiasi azione non va bene
        //OSS: il message contiene il messaggio con le informazioni dell'errore
        Server.getConnectedClients().get(username).notifyClient(new InvalidActionCommand(message));
    }

    @Override
    public void loseMessage(Integer position, ArrayList<String> scores){
        Server.getConnectedClients().get(username).notifyClient(new LoseCommand(scores,position));
    }

    @Override
    public void winMessage(ArrayList<String> scores) {
        Server.getConnectedClients().get(username).notifyClient(new WinCommand(scores));
    }

    //TODO change without toString()
    public void update(Object model){
        //RefreshBoardCommand
        Server.getConnectedClients().get(username).notifyClient(new RefreshBoardCommand((String) model));
    }
}
