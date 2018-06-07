package it.polimi.se2018.utils;

import it.polimi.se2018.server_to_client_command.*;

/**
 * The interface that let the Client to don't necessarely have the Controller class in its module
 */
public interface ControllerClientInterface{

    public void applyCommand(ServerToClientCommand command);

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
    public void applyCommand(InitializeMatchCommand command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidActionCommand command);

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
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(NotifyCredentialsNeeded command);

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
    public void applyCommand(CorrectUseToolCopperFoilReamer command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolCorkLine command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolDiamondSwab command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolEglomiseBrush command);

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
    public void applyCommand(CorrectUseToolFirmPastryBrush2 command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolFirmPastryThinner2 command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolGavel command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolLathekin command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolManualCutter command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolRoughingForceps command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(CorrectUseToolWheelsPincher command);







    //InvalidToolUse-> The ClientController has to call the box that let the player do a second chance


    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolCopperFoilReamer command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolCorkLine command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolDiamondSwab command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolEglomiseBrush command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryBrush1 command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryBrush2 command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryThinner1 command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolFirmPastryThinner2 command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolGavel command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolLathekin command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolManualCutter command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolRoughingForceps command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    public void applyCommand(InvalidUseToolWheelsPincher command);

    void update(ServerToClientCommand command);
}
