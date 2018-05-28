package it.polimi.se2018.MVC;

import it.polimi.se2018.Die;
import it.polimi.se2018.Model;
import it.polimi.se2018.Player;
import it.polimi.se2018.client_to_server_command.*;
import it.polimi.se2018.network.ClientConnection;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.server_to_client_command.*;

public class ClientNetworkHandler {

    /**
     * La classe che viene in contatto con la connessione (Socket o RMI)
     *
     * First role of ClientController is to send commands to Server requesting a move, or a Tool use.
     *
     */

    private View view;

    private Model playerModel;

    private ClientConnection connection;

    private int token;

    /**
     * Contructor:
     * Instantiates the view
     * @param view The connected view that shows panels to the user
     */
    public ClientNetworkHandler(View view){
        this.view = view;
    }
    // Main method for sending commands to Server

    /**
     * The method sendCommand calls the clientConnection and send the message with the network interface defined in the
     * very beginning.
     * ClientController is transparent to the connection type.
     * @param command
     */
    public void sendCommand(ClientToServerCommand command) {
        try {
            System.out.println("entro nel send ClientController"); //
             connection.sendCommand(command);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Sends a command to Server asking if move perform is correct
     * @param draftPoolPosition
     * @param schemaPosition
     */

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


    /**
     * Second role of the ClientController is to receive commands from Server and apply them (to Client)
     *
     * I have multiple methods called applyCommand with a different command as parameter
     * Firstly from calling distinguishCommand(), ClientController understand which kind of event server sent and applies it through calling
     * correct applyCommand() methods
     * @param
     */

    public void distinguishServerCommand(ServerToClientCommand command){
        String words[] = command.getMessage().split(" ");
        //TODO complete
        switch(words[0]){
            case("Passa Turn"):
                ;
                break;
            case("ok"):
                //applyClientCommand.....//TODO: cambio da serverConnection a Player ogni metodo applyCommand
                ;
                break;
            default:
                ;//InvalidInput
                break;
        }


    }

    public void applyCommand(ServerToClientCommand command){
        System.out.println("Funziona?");
        if (command instanceof NotifyCredentialsNeeded)
            System.out.println("Dinamicamente ok");
        view.showAskUsernamePanel();
    }

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
    public void applyCommand(AuthenticatedCorrectlyCommand command){

    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InitializeMatchCommand command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InitializeTurnCommand command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidCommand command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidInputCommand command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidMoveCommand command){

    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(WinCommand command){

    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(LoseCommand command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(NotifyCredentialsNeeded command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(RefreshBoardCommand command) {

    }

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(StartPlayerTurnCommand command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(StartTurnCommand command){

    }




    //Correct use-> performs the move
    //USING OF TOOLS: Correct Move performed -> has to update the View (The answer will be the new model or the move performed)



    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolCopperFoilReamer command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolCorkLine command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolDiamondSwab command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolEglomiseBrush command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryBrush command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryThinner command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolGavel command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolLathekin command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolManualCutter command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolRoughingForceps command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolWheelsPincher command){

    }







    //InvalidToolUse-> The ClientController has to call the box that let the player do a second chance


    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolCopperFoilReamer command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolCorkLine command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolDiamondSwab command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolEglomiseBrush command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryBrush command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryBrush2 command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryThinner command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolGavel command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolLathekin command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolManualCutter command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolRoughingForceps command){

    }
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolWheelsPincher command){

    }








}
