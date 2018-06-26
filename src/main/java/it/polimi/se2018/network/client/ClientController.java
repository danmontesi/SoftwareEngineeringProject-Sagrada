package it.polimi.se2018.network.client;

import com.google.gson.stream.MalformedJsonException;
import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.network.server.ServerConnection;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.utils.ControllerClientInterface;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.GUI.GUIView;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.cli.CLIView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ClientController extends Observable implements Observer, ControllerClientInterface {

    /**
     * La classe che viene in contatto con la connessione (Socket o RMI)
     *
     * First role of ClientController is to send commands to Server requesting a move, or a Tool use.
     *
     */

    private View view;
    private String username;
    private ServerConnection connection;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());


    /**
     * Contructor:
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

    public void setUsername(String username){
        this.username = username;
    }

    // Main method for sending commands to Server


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
    /*

    public void performMove(Integer draftPoolPosition, Integer schemaPosition){
        //Create the Command and send to Server to wait for validation
        sendCommand(new MoveChoiceDicePlacement("MoveChoiceDicePlacement", schemaPosition, draftPoolPosition));
    }

    public void useToolCard(Integer toolNumber){
        sendCommand(new MoveChoiceToolCard("MoveChoiceToolCard", toolNumber));
    }

    public void passTurn(){
        sendCommand(new MoveChoicePassTurn("MoveChoicePassTurn"));
    }

    public void chosenWindowPatternCard(WindowPatternCard card){
        sendCommand(new ChosenWindowPatternCard(card.getCardName()));
    }

    public void useToolFirmPastryBrushDRAFTPOOL(Integer dieNewValue, Integer dieOldPosition){
        //Gives the chosen DraftPoolDie (position)
        //and the second word says DRAFTPOOL
        sendCommand(new UseToolFirmPastryBrush("DRAFTPOOL", dieNewValue, dieOldPosition , null));

    }
    public void useToolFirmPastryBrushMOVE(Integer dieNewValue, Integer dieOldPosition, Integer diePosition){
        //Gives the old position of the chosen die from DraftPool
        //Gives the new position in the Schema
        //if gives the new Value (received from the server before!!)
        //and the second word says MOVE
        sendCommand(new UseToolFirmPastryBrush("MOVE", dieNewValue, dieOldPosition, diePosition));
    }

    public void useToolFirmPastryThinnerMOVE(Integer dieNewValue, Integer dieOldPosition, Integer diePosition){
        //Gives the position of the old die in the DraftPool (to reinsert in the diceBag
        //Gives the new position in the Schema
        //if gives the new Value (decided by the Client!)
        //and the second word says MOVE
        sendCommand(new UseToolFirmPastryThinner("MOVE", dieNewValue, dieOldPosition, diePosition));
    }

    public void useToolFirmPastryThinnerDRAFTPOOL(Integer dieNewValue, Integer dieOldPosition){
        //Gives the position of the old die in the DraftPool (to reinsert in the diceBag
        //Gives the new position in the Schema
        //if gives the new Value (decided by the Client!)
        //and the second word says MOVE
        sendCommand(new UseToolFirmPastryThinner("DRAFTPOOL", dieNewValue, dieOldPosition, null));
    }
    */

    /**
     * Second role of the ClientController is to receive commands from Server and apply them (to Client)
     *
     * I have multiple methods called applyCommand with a different command as parameter
     * Firstly from calling distinguishCommand(), ClientController understand which kind of event server sent and applies it through calling
     * correct applyCommand() methods
     * @param
     */

    /**
     * USO TOOL:
     * LA RICHIESTA PARTE DAL CLIENTCONTROLLER->
     * Caso Richiedo il dado dalla dicebag
     * Caso ..
     *
     *
     *
     * TUTTI GLI ALTRI:
     * Faccio la richiesta al ClientController, lei mi controlla che ho abbastanza token , e se sì raccoglie i dati per la mossa ufficiale
     * Se la mossa ufficiale parte, -> Costruisco il metodo Richeista
     */

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(ChooseWindowPatternCardCommand command){
        //Splitting the string obtaining the correct Wpc
        String[] words = command.getMessage().split(",");
        ArrayList<WindowPatternCard> wpc = new ArrayList<>();

        //Parse the entire list of wpc, remove all the non occurences
        try{
            ParserWindowPatternCard parser = new ParserWindowPatternCard();
            wpc.add(0, parser.parseCardByName(words[0]));
            wpc.add(1, parser.parseCardByName(words[1]));
            wpc.add(2, parser.parseCardByName(words[2]));
            wpc.add(3, parser.parseCardByName(words[3]));
        } catch (MalformedJsonException e){

        } catch (IOException e){

        }
        view.chooseWindowPatternCardMenu(wpc);
    }

    /**
     * It shows correct authentication printing the message
     * Applies commands coming from the Server, calling the right graphical methods of the View
     * @param command Command received
     */
    @Override
    public void applyCommand(AuthenticatedCorrectlyCommand command){
        //AGGIORNO USERNAME
        this.username = command.getMessage();
        view.correctAuthenthication(command.getMessage());
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(InvalidActionCommand command){
        view.invalidActionMessage(command.getMessage());
    }

    /**
     * The command created an ArrayList of strings in the format "PlayerUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(WinCommand command){
        view.winMessage(command.getScores());

    }

    /**
     * The command created an ArrayList of strings in the format "PlayterUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(LoseCommand command){
        view.loseMessage(command.getPosition(), command.getScores());

    }

    /**
     * Refresh the player model and calls a function of the view that modifies the board with the edits
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(RefreshBoardCommand command) {
        //setPlayerModel(command.getMessage());
        view.update(command);
        //TODO Decidere con alle e nives
    }
    @Override
    public void setPlayerModel(String modelString){
        //TODO: Edit the modelView
        //PROBABLY TO DELETE
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(StartPlayerTurnCommand command){
        view.startTurnMenu();
    }


    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(ContinueTurnCommand command){
        LOGGER.log(Level.FINE, "Arrivo a continueturn sul clientController");
        view.continueTurnMenu(command.canShowMove(),command.canShowTool());
    }

    //Correct use-> performs the move
    //USING OF TOOLS: Correct Move performed -> has to update the View (The answer will be the new model or the move performed)



    @Override
    public void applyCommand(MessageFromServerCommand command){
        view.messageBox(command.message);
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(CorrectUseToolCorkLine command){
        view.corkLineMenu();
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(CorrectUseToolMoveDieNoRestriction command){
        view.moveDieNoRestrictionMenu(command.getCardName());
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(CorrectUseToolFirmPastryBrush1 command){
        Integer valueForDraftPoolDie = command.getDieValue();
        view.firmPastryBrushMenu(valueForDraftPoolDie);

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(CorrectUseToolFirmPastryThinner1 command){
        String[] words = command.getMessage().split(" ");
        Die dieFromDiceBag = new Die(COLOR.valueOf(words[1]), command.getDieValue());
        view.firmPastryThinnerMenu(command.getColor(), command.getDieValue());
    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    @Override
    public void applyCommand(CorrectUseToolWheelsPincher command){
        view.wheelsPincherMenu();

    }

    @Override
    public void applyCommand(CorrectUseToolTwoDiceMove command) {
        view.twoDiceMoveMenu(command.getCardName());
    }

    @Override
    public void applyCommand(CorrectUseToolChangeDieValue command) {
        view.changeDieValueMenu(command.getCardName());

    }

    @Override
    public void applyCommand(CorrectUseToolCircularCutter command) {
        view.circularCutter();
    }


    @Override
    public void applyCommand(OtherPlayerTurnCommand command){
        view.otherPlayerTurn(command.getUsername());
    }

    @Override
    public void applyCommand(TimeOutCommand command){
        LOGGER.log(Level.FINE,"Arriva a clientContorller" );
        view.timeOut();
    }

    @Override
    public void dispatchCommand(Object command) {
        ServerToClientCommand castedCommand = (ServerToClientCommand) command;
        LOGGER.log(Level.FINE, "Arriva il command", command);
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
    public void applyCommand(NewConnectedPlayerNotification command) {
        view.newConnectedPlayer(command.getUsername());
    }

    @Override
    public void applyCommand(PingConnectionTester pingConnectionTester) {
        LOGGER.log(Level.FINEST,"ping from server" );
    }

}

