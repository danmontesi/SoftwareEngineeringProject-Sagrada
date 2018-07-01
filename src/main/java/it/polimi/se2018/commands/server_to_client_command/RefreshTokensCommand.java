package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.List;

public class RefreshTokensCommand extends ServerToClientCommand {

    private List<Integer> otherPlayersTokens;
    private List<Integer> toolCardsTokens;
    private Integer personalTokens;

    public List<Integer> getOtherPlayersTokens() {
        return otherPlayersTokens;
    }

    public Integer getPersonalTokens() {
        return personalTokens;
    }

    public List<Integer> getToolCardsTokens() {
        return toolCardsTokens;
    }

    public RefreshTokensCommand(List<Integer> otherPlayersTokens, List<Integer> toolCardsTokens, Integer personalTokens) {

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
