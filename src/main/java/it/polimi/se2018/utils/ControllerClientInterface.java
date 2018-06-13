package it.polimi.se2018.utils;

import it.polimi.se2018.server_to_client_command.*;

/**
 * The interface that let the Client to don't necessarely have the Controller class in its module
 */
public interface ControllerClientInterface{

    //public void applyCommand(ServerToClientCommand command);

    public void applyCommand(MessageFromServerCommand command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(ChooseWindowPatternCardCommand command);

    /**
     * It shows correct authentication printing the message
     * Applies commands coming from the Server, calling the right graphical methods of the View
     * @param command Command received
     */
    public void applyCommand(AuthenticatedCorrectlyCommand command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidActionCommand command);


    public void applyCommand(ContinueTurnCommand command);

    /**
     * The command created an ArrayList of strings in the format "PlayterUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(WinCommand command);

    /**
     * The command created an ArrayList of strings in the format "PlayterUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(LoseCommand command);

    /**
     * Refresh the player model and calls a function of the view that modifies the board with the edits
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(RefreshBoardCommand command);

    public void setPlayerModel(String modelString);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(StartPlayerTurnCommand command);




    //Correct use-> performs the move
    //USING OF TOOLS: Correct Move performed -> has to update the View (The answer will be the new model or the move performed)


    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolCorkLine command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolMoveDieNoRestriction command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryBrush1 command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryThinner1 command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolWheelsPincher command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolTwoDiceMove command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolChangeDieValue command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolCircularCutter command);


    void dispatchCommand(Object event);

}
