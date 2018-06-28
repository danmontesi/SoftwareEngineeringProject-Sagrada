package it.polimi.se2018.network.server;

import it.polimi.se2018.view.View;
import it.polimi.se2018.commands.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.commands.client_to_server_command.MoveChoicePassTurn;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.commands.server_to_client_command.*;

import java.util.List;

/**
 * The class represents the virtual view. Is Observable of Controller, Observer of Model
 */
public class VirtualView extends View {

    //Observer observer; //Il controller TODO: Togli e usa quello dato dall'Observable
    Observable observable; // Il model
    boolean disconnected;

    public VirtualView(Observer controller, Model model, String username) {
        super(controller);
        this.observable = model; //this can be omitted
        this.username = username;
        this.disconnected = false;
        Server.getUserMap().put(username, this);
        System.out.println("Creata virtual view di username: " + username);
    }

    public VirtualView(Observer controller) {
        super(controller);
    }

    public void setUsername(String username){
        Server.getUserMap().put(username, this);
        this.username=username; //TODO perchè è quì? magari lo tolgo dal costruttore
        System.out.println("Creata virtual view di username: " + username);
    }

    /**
     * Nel VirtualView devono stare:
     * - il metodo notify(un comando) che da' al Controller i comandi che prende dal view
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
        for (Observer o : observers)
            o.update(command);
    }

    @Override
    public void chooseWindowPatternCardMenu(List<WindowPatternCard> cards) {
        if (!Server.getConnectedClients().containsKey(username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> choosing a random Wpc");
            notify(new ChosenWindowPatternCard(cards.get(0).getCardName()));
        }
        else {
            Server.getConnectedClients().get(username).notifyClient(new PingConnectionTester());
            if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
                disconnected=true;
                Server.updateDisconnectedUser(this.username);
                System.out.println("Disconnected-> choosing a random Wpc");
                notify(new ChosenWindowPatternCard(cards.get(0).getCardName()));
            } else {
                StringBuilder cardNames = new StringBuilder();
                for (WindowPatternCard card : cards)
                    cardNames.append(card.getCardName() + ",");
                Server.getConnectedClients().get(username).notifyClient(new ChooseWindowPatternCardCommand(cardNames.toString()));
                //DATE NEL FORMATO nomeCarta nomeCarta nomeCarta
            }
        }
    }

    /**
     * Handle the network calling the connection associated to the username
     */
    @Override
    public void startTurnMenu() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            System.out.println("Disconnected-> Passing automatically turn");
            Server.updateDisconnectedUser(this.username); //Every turn a message saying the player is disconnected and will pass its turn//TODO magari cambia
            disconnected=true;
            Server.updateDisconnectedUser(this.username);
            notify(new MoveChoicePassTurn(username));
        } else {
            if (disconnected){
                Server.requestRefreshBoard(this.username);
                disconnected=false;
            }
            Server.getConnectedClients().get(username).notifyClient(new StartPlayerTurnCommand());
        }
    }

    @Override
    public void startGame() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected->No action");
        } else {
            if (disconnected){
                Server.requestRefreshBoard(this.username);
                disconnected=false;
            }
            Server.getConnectedClients().get(username).notifyClient(new StartGameCommand());
        }
    }

    @Override
    public void endGame() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected->No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new EndGameCommand());
        }
    }

    @Override
    public void otherPlayerTurn(String username) {
        if (Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> No action");
            return;
        } else
        if (disconnected){
            Server.requestRefreshBoard(this.username);
            disconnected=false;
        }
            Server.getConnectedClients().get(this.username).notifyClient(new OtherPlayerTurnCommand(username));

    }

    public void authenticatedCorrectlyMessage(String message) {
        //TODO elimina
    }

    @Override
    public void continueTurnMenu(boolean move, boolean tool) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else {
            System.out.println("SENDING CONTINUETURNMENU");
            Server.getConnectedClients().get(username).notifyClient(new ContinueTurnCommand(move, tool));
        }
    }

    @Override
    public void newConnectedPlayer(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new PlayerDisconnectionNotification(username));
        }
    }


    @Override
    public void playerDisconnection(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> No action");
        } else {
            System.out.println("SENDING CONTINUETURNMENU");
            Server.getConnectedClients().get(username).notifyClient(new NewConnectedPlayerNotification(username));
        }
    }

    @Override
    public void firmPastryBrushMenu(int value) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolFirmPastryBrush1(value));
    }

    @Override
    public void firmPastryThinnerMenu(String color, int value) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolFirmPastryThinner1(color, value));
    }

    @Override
    public void moveDieNoRestrictionMenu(String cardName) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolMoveDieNoRestriction(cardName));
    }

    @Override
    public void changeDieValueMenu(String cardName) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolChangeDieValue(cardName));
    }

    @Override
    public void twoDiceMoveMenu(String cardName) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolTwoDiceMove(cardName));
    }

    @Override
    public void corkLineMenu() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolCorkLine());
    }

    @Override
    public void wheelsPincherMenu() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolWheelsPincher());
    }

    @Override
    public void circularCutter() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else
            Server.getConnectedClients().get(username).notifyClient(new CorrectUseToolCircularCutter());
    }

    @Override
    public void invalidActionMessage(String message) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
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
    public void loseMessage(Integer position, List<String> scores) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> No action");

        } else {
            Server.getConnectedClients().get(username).notifyClient(new LoseCommand(scores, position));
        }
    }

    @Override
    public void winMessage(List<String> scores) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new WinCommand(scores));
    }

    @Override
    public void timeOut() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
            System.out.println("Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(username));
        } else {
            Server.getConnectedClients().get(username).notifyClient(new TimeOutCommand());
        }
    }

    @Override
    public void updateWpc(RefreshWpcCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            System.out.println("Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateTokens(RefreshTokensCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            System.out.println("Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            System.out.println("Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            System.out.println("Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateBoard(RefreshBoardCommand refreshCommand) {

    }

    public void update(Object model){ //TODO Change with all model representation
        //RefreshBoardCommand
        //TODO TOGLI!! PERR ORA SOLO
        if (model instanceof RefreshBoardCommand){
            update((RefreshBoardCommand) model);
            return;
        }
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            System.out.println("Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new RefreshBoardCommand((String) model));
    }

    public void update(RefreshBoardCommand command){ //TODO Change with all model representation
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            System.out.println("Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(command);
    }

    @Override
    public void messageBox(String message) {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            System.out.println("Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new MessageFromServerCommand(message));
    }

}
