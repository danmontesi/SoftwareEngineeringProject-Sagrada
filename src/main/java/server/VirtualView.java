package server;

import server.Server;
import shared.View;
import shared.commands.client_to_server_command.ChosenWindowPatternCard;
import server.model.Model;
import shared.commands.client_to_server_command.MoveChoicePassTurn;
import shared.utils.Observable;
import shared.utils.Observer;
import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.commands.server_to_client_command.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class represents the virtual view. It is Observable of Controller, Observer of Model
 * @author Daniele Montesi
 */
public class VirtualView extends View {

    Observable observable; // Il model
    boolean disconnected;

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public VirtualView(Observer controller, String username) {
        super(controller);
        this.username = username;
        this.disconnected = false;
        Server.getUserMap().put(username, this);
            LOGGER.log(Level.INFO, "Creata virtual view di username: " + username);
    }

    public VirtualView(Observer controller) {
        super(controller);
    }

    public void setUsername(String username){
        Server.getUserMap().put(username, this);
        this.username=username; //TODO perchè è quì? magari lo tolgo dal costruttore
            LOGGER.log(Level.INFO, "Creata virtual view di username: " + username);
    }

    /* Nel VirtualView devono stare:
       - il metodo notify(un comando) che da' al Controller i comandi che prende dal view
       poi il Controller deve associare col binding un effetto (grazie al Visitor)
       -> Il clientToServerCOmmand prende parametro Controller
       VirtualView ha tutti i metodi di View (di tipo Show(...))
       VirtualView can also know when a user is disconnected.
       Every time virtualView tries to send a command to a disconnectedClient, it directy calls passTurn() */

    public void notify(ClientToServerCommand command) { // chiamata dalla rete
        for (Observer o : observers)
            o.update(command);
    }

    @Override
    public void chooseWindowPatternCardMenu(List<List<String>> cards, String privateObjectiveCard, List<Integer> wpcDifficulties) {
        if (!Server.getConnectedClients().containsKey(username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> choosing a random Wpc");
            notify(new ChosenWindowPatternCard(cards.get(0).get(0)));
        }
        else {
            Server.getConnectedClients().get(username).notifyClient(new PingConnectionTester());
            if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
                disconnected=true;
                Server.updateDisconnectedUser(this.username);
                    LOGGER.log(Level.INFO, "Disconnected-> choosing a random Wpc");
                notify(new ChosenWindowPatternCard(cards.get(0).get(0)));
            } else {
                Server.getConnectedClients().get(username).notifyClient(new ChooseWindowPatternCardCommand(cards, privateObjectiveCard, wpcDifficulties));
                //DATE NEL FORMATO nomeCarta nomeCarta nomeCarta
            }
        }
    }


    //TODO rimuovi la call del metodo updateDisconnectedUser
    @Override
    public void startTurnMenu() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
                LOGGER.log(Level.INFO, "Disconnected-> Passing automatically turn");
            Server.updateDisconnectedUser(this.username); //Every turn a message saying the player is disconnected and will pass its turn//TODO magari cambia
            disconnected=true;
            Server.updateDisconnectedUser(this.username);
            notify(new MoveChoicePassTurn());
        } else {
            if (disconnected){
                Server.refreshBoardAndNotifyReconnectedPlayer(this.username);
                disconnected=false;
            }
            Server.getConnectedClients().get(username).notifyClient(new StartPlayerTurnCommand());
        }
    }

    @Override
    public void startGame() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected->No action");
        } else {
            if (disconnected){
                Server.refreshBoardAndNotifyReconnectedPlayer(this.username);
                disconnected=false;
            }
            Server.getConnectedClients().get(username).notifyClient(new StartGameCommand());
        }
    }

    @Override
    public void endGame() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected->No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new EndGameCommand());
        }
    }

    @Override
    public void otherPlayerTurn(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(this.username).notifyClient(new OtherPlayerTurnCommand(username));
        }
    }

    @Override
    public void continueTurnMenu(boolean hasAlreadyMovedDie, boolean hasAlreadyUsedTool) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn());
        } else {
                LOGGER.log(Level.INFO, "SENDING CONTINUETURNMENU");
            Server.getConnectedClients().get(username).notifyClient(new ContinueTurnCommand(hasAlreadyMovedDie, hasAlreadyUsedTool));
        }
    }

    @Override
    public void newConnectedPlayer(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new PlayerDisconnectionNotification(username));
        }
    }

//TODO NON LA STO USANDO, DEVO USARLA PER QUANDO UN UTENTE SI RICONNETTE
    @Override
    public void playerDisconnection(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> No action");
        } else {
                LOGGER.log(Level.INFO, "SENDING CONTINUETURNMENU");
            Server.getConnectedClients().get(username).notifyClient(new NewConnectedPlayerNotification(username));
        }
    }

    @Override
    public void invalidActionMessage(String message) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> No action");
        } else {
                LOGGER.log(Level.INFO, "SENDING INVALID ACTION_TYPE");
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
                LOGGER.log(Level.INFO, "Disconnected-> No action");

        } else {
            Server.getConnectedClients().get(username).notifyClient(new LoseCommand(scores, position));
        }
    }

    @Override
    public void winMessage(List<String> scores) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new WinCommand(scores));
    }

    @Override
    public void timeOut() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn());
        } else {
            Server.getConnectedClients().get(username).notifyClient(new TimeOutCommand());
        }
    }

    @Override
    public void updateWpc(RefreshWpcCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateTokens(RefreshTokensCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateBoard(RefreshBoardCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            LOGGER.log(Level.INFO, "Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    public void update(RefreshBoardCommand command){ //TODO Change with all model representation
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected -> No updating model");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(command);
    }

    @Override
    public void messageBox(String message) {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
                LOGGER.log(Level.INFO, "Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new MessageFromServerCommand(message));
    }

    @Override
    public void askAnotherAction() {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            LOGGER.log(Level.INFO, "Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new AskAnotherAction());
    }

    @Override
    public void askIncreaseDecrease() {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            LOGGER.log(Level.INFO, "Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new AskIncreaseDecrease());
    }

    @Override
    public void askDieValue() {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            LOGGER.log(Level.INFO, "Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new AskDieValue());
    }

    @Override
    public void askPlaceDie() {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            LOGGER.log(Level.INFO, "Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new AskPlaceDie());
    }

    @Override
    public void askPickDie(String from) {
        if (Server.getConnectedClients().get(username)==null){ //disconnected
            disconnected=true;
            LOGGER.log(Level.INFO, "Disconnected-> No action");
        }
        else
            Server.getConnectedClients().get(username).notifyClient(new AskPickDie(from));
    }
}
