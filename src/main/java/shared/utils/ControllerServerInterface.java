package shared.utils;

import shared.commands.client_to_server_command.*;

public interface ControllerServerInterface {

    /**
     * This is the ControllerServerInterface
     * All methods are implemented by the main Controller of the game
     * This class exists in order to let Server be independent to the Client
     */

    void applyCommand(String playerUsername, ChosenWindowPatternCard command);

    void applyCommand(String playerUsername, ClientToServerCommand command);

    void applyCommand(String playerUsername, MoveChoiceToolCard command);

    void applyCommand(String playerUsername, MoveChoiceDiePlacement command);

    void applyCommand(String playerUsername, MoveChoicePassTurn command);

    void applyCommand(String playerUsername, ReplyPlaceDie command);

    void applyCommand(String playerUsername, ReplyDieValue command);

    void applyCommand(String playerUsername, ReplyAnotherAction command);

    void applyCommand(String playerUsername, ReplyPickDie command);

    void applyCommand(String playerUsername, ReplyIncreaseDecrease command);

    void applyCommand(String playerUsername, UndoActionCommand command);

}