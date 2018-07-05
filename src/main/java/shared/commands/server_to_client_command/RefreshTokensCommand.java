package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

import java.util.List;

public class RefreshTokensCommand extends ServerToClientCommand {
    private List<Integer> otherPlayersTokens;
    private List<Integer> toolCardsTokens;
    private Integer personalTokens;

    /**
     * Contains the tokens associated to each Tool Card and player
     */
    public RefreshTokensCommand(List<Integer> otherPlayersTokens, List<Integer> toolCardsTokens, Integer personalTokens) {
        this.otherPlayersTokens = otherPlayersTokens;
        this.toolCardsTokens = toolCardsTokens;
        this.personalTokens = personalTokens;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public List<Integer> getOtherPlayersTokens() {
        return otherPlayersTokens;
    }

    public Integer getPersonalTokens() {
        return personalTokens;
    }

    public List<Integer> getToolCardsTokens() {
        return toolCardsTokens;
    }
}
