package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.List;

public class RefreshRoundTrackCommand extends ServerToClientCommand {

    public List<String> getRoundTrack() {
        return roundTrack;
    }

    public RefreshRoundTrackCommand(List<String> roundTrack) {

        this.roundTrack = roundTrack;
    }

    private List<String> roundTrack; //Dice in the format: colorNumber/empty

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
