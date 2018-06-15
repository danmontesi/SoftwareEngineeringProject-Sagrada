package it.polimi.se2018.network.server;

import it.polimi.se2018.Client.View;
import it.polimi.se2018.Model;
import it.polimi.se2018.client_to_server_command.MoveChoicePassTurn;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.server_to_client_command.*;

import java.util.ArrayList;

/**
 * The class represents the virtual view. Is Observable of Controller, Observer of Model
 */
public class VirtualView extends View {

    String username;
    Observer observer; // Il controller TODO: Togli e usa quello dato dall'Observable
    Observable observable; // Il model

    public VirtualView(Observer controller, Model model, String username) {
        this.observer = controller;
        this.observable = model; //this can be omitted
        this.username = username;
        Server.getUserMap().put(username, this);
        System.out.println("Creata virtual view di username: " + username);
    }

    /**
     * Nel VirtualView devono stare:
     * - il metodo notify(un comando) che da' al Controller i comandi che prende dal client
     * poi il Controller deve associare col binding un effetto (grazie al Visitor)
     * -> Il clientToServerCOmmand prende parametro Controller
     *
     * VirtualView ha tutti i metodi di View (di tipo Show(...))
     */

    /**
     * VirtualView can also know when a user is disconnected.
     * Every time virtualView tries to send a command to a disconnectedClient, it directy calls passTurn()
     */

    public void notify(ClientToServerCommand command) { // chiamata dalla rete
        observer.update(command);
    }

    @Override
    public void chooseWindowPatternCardMenu(ArrayList<WindowPatternCard> cards) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else {
            StringBuilder cardNames = new StringBuilder();
            for (WindowPatternCard card : cards)
                cardNames.append(card.getCardName() + ",");
            Server.getConnectedClients().get(username).notifyClient(new ChooseWindowPatternCardCommand(cardNames.toString()));
            //DATE NEL FORMATO nomeCarta nomeCarta nomeCarta
        }
    }

    /**
     * Handle the network calling the connection associated to the username
     */
    @Override
    public void startTurnMenu() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new StartPlayerTurnCommand());
    }

    public void askAuthenticatedCorrectlyMessage(String message) {
        //TODO elimina
    }

    @Override
    public void continueTurnMenu(boolean move, boolean tool) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else {
            System.out.println("SENDING CONTINUETURNMENU");
            Server.getConnectedClients().get(username).notifyClient(new ContinueTurnCommand(move, tool));
        }
    }

    @Override
    public void firmPastryBrushMenu(int value) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolFirmPastryBrush1(value));
    }

    @Override
    public void firmPastryThinnerMenu(String color, int value) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolFirmPastryThinner1(color, value));
    }

    @Override
    public void moveDieNoRestrictionMenu(String cardName) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolMoveDieNoRestriction(cardName));
    }

    @Override
    public void changeDieValueMenu(String cardName) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolChangeDieValue(cardName));
    }

    @Override
    public void twoDiceMoveMenu(String cardName) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolTwoDiceMove(cardName));
    }

    @Override
    public void corkLineMenu() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolCorkLine());
    }

    @Override
    public void wheelsPincherMenu() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolWheelsPincher());
    }

    @Override
    public void circularCutter() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolCircularCutter());
    }

    @Override
    public void invalidActionMessage(String message) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> No action");
        } else {
            System.out.println("SENDING INVALID ACTION");
            //Di qualsiasi tipo:
            // sia per il tool (seguita da una richiesta di uso del tool, di nuovo)
            // sia per il piazzamento di un dado scorretto
            // sia per qualsiasi azione non va bene
            //OSS: il message contiene il messaggio con le informazioni dell'errore
            Server.getConnectedClients().get(username).notifyClient(new InvalidActionCommand(message));
        }
    }

    @Override
    public void loseMessage(Integer position, ArrayList<String> scores) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new LoseCommand(scores, position));
    }

    @Override
    public void winMessage(ArrayList<String> scores) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else
            Server.getConnectedClients().get(username).notifyClient(new WinCommand(scores));
    }

    @Override
    public void timeOut() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(""));
        } else {
            Server.getConnectedClients().get(username).notifyClient(new TimeOutCommand());
        }

    }



    //TODO change without toString()
    public void update(Object model){
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            System.out.println("Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new RefreshBoardCommand((String) model));
    }

    @Override
    public void messageBox(String message) {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            System.out.println("Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new MessageFromServerCommand(message));
    }
}
