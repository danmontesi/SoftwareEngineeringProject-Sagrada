package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.ArrayList;

public class RefreshRoundTrackCommand extends ServerToClientCommand {

    public ArrayList<String> getRoundTrack() {
        return roundTrack;
    }

    public RefreshRoundTrackCommand(ArrayList<String> roundTrack) {

        this.roundTrack = roundTrack;
    }

    private ArrayList<String> roundTrack; //Dice in the format: colorNumber/empty

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
