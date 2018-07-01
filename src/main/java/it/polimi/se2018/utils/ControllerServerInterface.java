package it.polimi.se2018.utils;

import it.polimi.se2018.commands.client_to_server_command.*;

public interface ControllerServerInterface {

    /**
     * The choice of wpc is always right
     * That method removes the player that chooses its card, and moves it to the List of initialized player.
     * Checks if all players are initialized to call the next Controller Method
     *
     * @param playerUsername the username who is applying the command
     * @param command        the coming command
     */
    public void applyCommand(String playerUsername, ChosenWindowPatternCard command);

    public void applyCommand(String playerUsername, ClientToServerCommand command);


    public void applyCommand(String playerUsername, MoveChoiceToolCard command);

    //TODO: già controllato se è allowed  il player deve essere il current )
    public void applyCommand(String playerUsername, MoveChoiceDiePlacement command);


    //TODO: già controllato se è allowed (il player deve essere il current )
    public void applyCommand(String playerUsername, MoveChoicePassTurn command);

    //Those methods represents the view that uses correctly a tool.
    // The server has to validate the move and edit the model, if the move is correct
    // else, has to call a new Request of re-use of that tool, re-sending a event of AllowedUseToolCommand(usedToolNumber)

    void applyCommand(String playerUsername, ReplyPlaceDie command);

    void applyCommand(String playerUsername, ReplyDieValue command);

    void applyCommand(String playerUsername, ReplyAnotherAction command);

    void applyCommand(String playerUsername, ReplyPickDie command);

    void applyCommand(String playerUsername, ReplyIncreaseDecrease command);

    void applyCommand(String playerUsername, UndoActionCommand command);

}