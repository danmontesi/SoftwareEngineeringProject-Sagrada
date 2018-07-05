package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

import java.util.List;

public class WinCommand extends ServerToClientCommand{
    private List<String> scores;

    /**
     * Notifies the user that he won and sends him the ranking
     * @param scores contains the scores of other players, ordered
     */
    public WinCommand(List<String> scores){
        this.scores=scores;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public List<String> getScores() {
        return scores;
    }
}
