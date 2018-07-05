package shared.utils;

import shared.commands.server_to_client_command.*;

/**
 * The interface that let the view to don't necessarely have the Controller class in its module
 */
public interface ControllerClientInterface{

    void applyCommand(MessageFromServerCommand command);
    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    void applyCommand(ChooseWindowPatternCardCommand command);

    /**
     * It shows correct authentication printing the message
     * Applies commands coming from the Server, calling the right graphical methods of the View
     * @param command Command received
     */
    void applyCommand(AuthenticatedCorrectlyCommand command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    void applyCommand(InvalidActionCommand command);


    void applyCommand(ContinueTurnCommand command);

    /**
     * The command created an ArrayList of strings in the format "PlayterUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    void applyCommand(WinCommand command);

    /**
     * The command created an ArrayList of strings in the format "PlayterUsername,playerScore"
     * It gives it to the View.
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    void applyCommand(LoseCommand command);

    /**
     * Refresh the player model and calls a function of the view that modifies the board with the edits
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    void applyCommand(RefreshBoardCommand command);

    /**
     * Applies commands coming from the Server, calling the right graphical methods of the View
     */
    void applyCommand(StartPlayerTurnCommand command);

    void applyCommand(OtherPlayerTurnCommand command);

    void applyCommand(TimeOutCommand command);


    void dispatchCommand(Object event);

    void applyCommand(RefreshDraftPoolCommand refreshDraftPoolCommand);

    void applyCommand(RefreshTokensCommand refreshTokensCommand);

    void applyCommand(RefreshWpcCommand refreshWpcCommand);

    void applyCommand(PlayerDisconnectionNotification playerDisconnectionNotification);

    void applyCommand(RefreshRoundTrackCommand refreshRoundTrackCommand);

    void applyCommand(NewConnectedPlayerNotification newConnectedPlayerNotification);

    void applyCommand(StartGameCommand startGameCommand);

    void applyCommand(EndGameCommand endGameCommand);

    void applyCommand(AskIncreaseDecrease askIncreaseDecrease);

    void applyCommand(AskAnotherAction askAnotherAction);

    void applyCommand(AskDieValue askDieValue);

    void applyCommand(AskPlaceDie askPlaceDie);

    void applyCommand(AskPickDie askPickDie);
}
