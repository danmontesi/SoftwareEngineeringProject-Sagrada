package client;

import shared.commands.client_to_server_command.ClientToServerCommand;
import shared.commands.server_to_client_command.*;
import shared.network_interfaces.ServerConnection;
import shared.utils.ControllerClientInterface;
import shared.utils.Observable;
import shared.utils.Observer;
import shared.View;
import client.view.cli.CLIView;
import client.view.gui.GUIView;

import java.util.logging.Level;
import java.util.logging.Logger;



public class ClientController extends Observable implements Observer, ControllerClientInterface {

    /**
     * The ClientController is the Controller who receives the command from View and send it
     * to the real Controller using a ServerConnection that created it.
     * First role of ClientController is to send commands to Server requesting a move, or a Tool use.
     * Second role is to send commands from Client to Server to the real controller.
     *
     * @author Daniele Montesi
     */

    private View view;
    private String username;
    private ServerConnection connection;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    /**
     * Constructor
     */
    public ClientController(int viewChoice, ServerConnection connection){
        this.connection = connection;
        if (viewChoice == 1){
            this.view = new CLIView(this);
        }
        else{
            this.view = new GUIView(this);
        }
    }

    /**
     * @param command command to send
     */
    public void update(Object command) {
        try {
            ClientToServerCommand castedCommand = (ClientToServerCommand) command;
            LOGGER.log(Level.FINE, "Sto inviando un command ", castedCommand);
            castedCommand.setUsername(username);

            connection.send(castedCommand);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void applyCommand(ChooseWindowPatternCardCommand command){
        view.chooseWindowPatternCardMenu(command.getWpcsInStrings(), command.getPrivateObjectiveCard(), command.getWpcDifficulties());
    }

    @Override
    public void applyCommand(AuthenticatedCorrectlyCommand command){
        this.username = command.getMessage();
        view.correctAuthenthication(command.getMessage());
    }

    @Override
    public void applyCommand(InvalidActionCommand command){
        view.invalidActionMessage(command.getMessage());
    }

    @Override
    public void applyCommand(WinCommand command){
        view.winMessage(command.getScores());

    }

    @Override
    public void applyCommand(LoseCommand command){
        view.loseMessage(command.getPosition(), command.getScores());
    }

    @Override
    public void applyCommand(RefreshBoardCommand command) {
        view.updateBoard(command);
    }


    @Override
    public void applyCommand(StartPlayerTurnCommand command){
        view.startTurnMenu();
    }


    @Override
    public void applyCommand(ContinueTurnCommand command){
        LOGGER.log(Level.FINE, "Arrivo a continueturn sul clientController");
        view.continueTurnMenu(command.hasPerformedMove(),command.hasPerformedTool());
    }

    //Correct use-> performs the move
    //USING OF TOOLS: Correct Move performed -> has to update the View (The answer will be the new model or the move performed)



    @Override
    public void applyCommand(MessageFromServerCommand command){
        view.messageBox(command.getMessage());
    }

    @Override
    public void applyCommand(OtherPlayerTurnCommand command){
        view.otherPlayerTurn(command.getUsername());
    }

    @Override
    public void applyCommand(TimeOutCommand command){
        view.timeOut();
    }

    @Override
    public void dispatchCommand(Object command) {
        ServerToClientCommand castedCommand = (ServerToClientCommand) command;
        LOGGER.log(Level.FINE, "Arrives command", command);
        castedCommand.visit(this);
    }

    @Override
    public void applyCommand(RefreshDraftPoolCommand command) {
        view.updateDraftPool(command);
    }

    @Override
    public void applyCommand(RefreshTokensCommand command) {
        view.updateTokens(command);
    }

    @Override
    public void applyCommand(RefreshWpcCommand command) {
        view.updateWpc(command);
    }

    @Override
    public void applyCommand(RefreshRoundTrackCommand command) {
        view.updateRoundTrack(command);
    }

    @Override
    public void applyCommand(PlayerDisconnectionNotification command) {
        view.playerDisconnection(command.getUsername());
    }

    @Override
    public void applyCommand(StartGameCommand startGameCommand){
        view.startGame();
    }

    @Override
    public void applyCommand(EndGameCommand endGameCommand) {
        view.endGame();
    }

    @Override
    public void applyCommand(AskIncreaseDecrease askIncreaseDecrease) {
        view.askIncreaseDecrease();
    }

    @Override
    public void applyCommand(AskAnotherAction askAnotherAction) {
        view.askAnotherAction();
    }

    @Override
    public void applyCommand(AskDieValue askDieValue) {
        view.askDieValue();
    }

    @Override
    public void applyCommand(AskPlaceDie askPlaceDie) {
        view.askPlaceDie();
    }

    @Override
    public void applyCommand(AskPickDie askPickDie) {
        view.askPickDie(askPickDie.getFrom());
    }

    @Override
    public void applyCommand(NewConnectedPlayerNotification command) {
        view.newConnectedPlayer(command.getUsername());
    }

}

