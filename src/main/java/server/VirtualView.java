package server;

import shared.View;
import shared.commands.client_to_server_command.ChosenWindowPatternCard;
import shared.commands.client_to_server_command.MoveChoicePassTurn;
import shared.utils.Observer;
import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.commands.server_to_client_command.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class represents the virtual view.
 * The role of this class is to emulate the real View the controller want to communicate with.
 * It is Observable of Controller, Observer of Model
 * It just send messages through the network to the real View
 *
 * @author Daniele Montesi
 */
public class VirtualView extends View {

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public VirtualView(Observer controller, String username) {
        super(controller);
        this.username = username;
        Server.getUserMap().put(username, this);
    }

    public VirtualView(Observer controller) {
        super(controller);
    }

    @Override
    public void setUsername(String username) {
        Server.getUserMap().put(username, this);
        this.username = username;
    }

    public void notify(ClientToServerCommand command) {
        for (Observer o : observers)
            o.update(command);
    }

    @Override
    public void chooseWindowPatternCardMenu(List<List<String>> cards, String privateObjectiveCard, List<Integer> wpcDifficulties) {
        if (!Server.getConnectedClients().containsKey(username)) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> choosing a random Wpc");
            notify(new ChosenWindowPatternCard(cards.get(0).get(0)));
        } else {
            Server.getConnectedClients().get(username).notifyClient(new PingConnectionTester());
            if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
                Server.updateDisconnectedUser(this.username, false);
                LOGGER.log(Level.INFO, this.username + " Disconnected-> choosing a random Wpc");
                notify(new ChosenWindowPatternCard(cards.get(0).get(0)));
            } else {
                Server.getConnectedClients().get(username).notifyClient(new ChooseWindowPatternCardCommand(cards, privateObjectiveCard, wpcDifficulties));
            }
        }
    }

    @Override
    public void startTurnMenu() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected -> Passing automatically turn");
            Server.updateDisconnectedUser(this.username, true);
            notify(new MoveChoicePassTurn(this.username));
        } else {
            Server.getConnectedClients().get(username).notifyClient(new StartPlayerTurnCommand());
        }
    }

    @Override
    public void startGame() {
        if (!Server.getConnectedClients().containsKey(this.username)) {
            LOGGER.log(Level.INFO, this.username + " Disconnected->No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new StartGameCommand());
        }
    }

    @Override
    public void endGame() {
        if (!Server.getConnectedClients().containsKey(this.username)) {
            LOGGER.log(Level.INFO, this.username + " Disconnected->No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new EndGameCommand());
        }
    }

    @Override
    public void otherPlayerTurn(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) {
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(this.username).notifyClient(new OtherPlayerTurnCommand(username));
        }
    }

    @Override
    public void continueTurnMenu(boolean hasAlreadyMovedDie, boolean hasAlreadyUsedTool) {
        if (!Server.getConnectedClients().containsKey(this.username)) {
            LOGGER.log(Level.INFO, this.username + " Disconnected-> Passing automatically turn");
            notify(new MoveChoicePassTurn(this.username));
        } else {
            Server.getConnectedClients().get(username).notifyClient(new ContinueTurnCommand(hasAlreadyMovedDie, hasAlreadyUsedTool));
        }
    }

    @Override
    public void newConnectedPlayer(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) {
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new PlayerDisconnectionNotification(username));
        }
    }

    @Override
    public void playerDisconnection(String username) {
        if (!Server.getConnectedClients().containsKey(this.username)) {
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new NewConnectedPlayerNotification(username));
        }
    }

    @Override
    public void invalidActionMessage(String message) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new InvalidActionCommand(message));
        }
    }

    @Override
    public void loseMessage(Integer position, List<String> scores) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");

        } else {
            Server.getConnectedClients().get(username).notifyClient(new LoseCommand(scores, position));
        }
    }

    @Override
    public void winMessage(List<String> scores) {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new WinCommand(scores));
    }

    @Override
    public void timeOut() {
        if (!Server.getConnectedClients().containsKey(this.username)) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else {
            Server.getConnectedClients().get(username).notifyClient(new TimeOutCommand());
        }
    }

    @Override
    public void updateWpc(RefreshWpcCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected -> No updating model ");
        } else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateTokens(RefreshTokensCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected -> No updating model");
        } else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateRoundTrack(RefreshRoundTrackCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected -> No updating model");
        } else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateDraftPool(RefreshDraftPoolCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + "Disconnected -> No updating model");
        } else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }

    @Override
    public void updateBoard(RefreshBoardCommand refreshCommand) {
        //RefreshBoardCommand
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + "Disconnected -> No updating model");
        } else
            Server.getConnectedClients().get(username).notifyClient(refreshCommand);
    }


    @Override
    public void messageBox(String message) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new MessageFromServerCommand(message));
    }

    @Override
    public void askAnotherAction() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new AskAnotherAction());
    }

    @Override
    public void askIncreaseDecrease() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new AskIncreaseDecrease());
    }

    @Override
    public void askDieValue() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new AskDieValue());
    }

    @Override
    public void askPlaceDie() {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new AskPlaceDie());
    }

    @Override
    public void askPickDie(String from) {
        if (Server.getConnectedClients().get(username) == null) { //disconnected
            LOGGER.log(Level.INFO, this.username + " Disconnected-> No action");
        } else
            Server.getConnectedClients().get(username).notifyClient(new AskPickDie(from));
    }

}
