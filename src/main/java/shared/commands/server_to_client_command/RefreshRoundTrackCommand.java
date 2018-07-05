package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

import java.util.List;

public class RefreshRoundTrackCommand extends ServerToClientCommand {
    private List<String> roundTrack; //Dice in the format: colorNumber/empty

    /**
     * Contains a String representation of the Round Track dice
     */
    public RefreshRoundTrackCommand(List<String> roundTrack) {
        this.roundTrack = roundTrack;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public List<String> getRoundTrack() {
        return roundTrack;
    }
}
