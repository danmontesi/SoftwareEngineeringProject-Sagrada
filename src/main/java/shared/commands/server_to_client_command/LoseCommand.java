package shared.commands.server_to_client_command;

import shared.utils.ControllerClientInterface;

import java.util.List;

public class LoseCommand extends ServerToClientCommand{
    private Integer position;
    private List<String> scores;

    /**
     * Notifies the user that he lost and sends him the ranking
     * @param scores the scores of other players, ordered
     * @param position the user's position
     */
    public LoseCommand(List<String> scores, Integer position){
        this.scores=scores;
        this.position=position;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public Integer getPosition() {
        return position;
    }

    public List<String> getScores() {
        return scores;
    }
}
