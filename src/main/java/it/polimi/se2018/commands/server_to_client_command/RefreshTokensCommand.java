package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.ArrayList;

public class RefreshTokensCommand extends ServerToClientCommand {

    private ArrayList<Integer> otherPlayersTokens;
    private ArrayList<Integer> toolCardsTokens;
    private Integer personalTokens;

    public ArrayList<Integer> getOtherPlayersTokens() {
        return otherPlayersTokens;
    }

    public Integer getPersonalTokens() {
        return personalTokens;
    }

    public ArrayList<Integer> getToolCardsTokens() {
        return toolCardsTokens;
    }

    public RefreshTokensCommand(ArrayList<Integer> otherPlayersTokens, ArrayList<Integer> toolCardsTokens, Integer personalTokens) {

        this.otherPlayersTokens = otherPlayersTokens;
        this.toolCardsTokens = toolCardsTokens;
        this.personalTokens = personalTokens;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
