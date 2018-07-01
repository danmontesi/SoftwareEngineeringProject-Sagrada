package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.List;

public class RefreshWpcCommand extends ServerToClientCommand {


    private List<String> personalWpc; //Dice in the format: colorNumber/empty

    private List<List<String>> otherPlayersWpcs; //Dice in the format colorNumber/empty or restrictionColor or restrictionValue

    public List<String> getPersonalWpc() {
        return personalWpc;
    }

    public List<List<String>> getOtherPlayersWpcs() {
        return otherPlayersWpcs;
    }

    public RefreshWpcCommand(List<String> personalWpc, List<List<String>> otherPlayersWpcs) {

        this.personalWpc = personalWpc;
        this.otherPlayersWpcs = otherPlayersWpcs;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
