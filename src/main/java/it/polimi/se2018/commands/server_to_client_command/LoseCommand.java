package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.ArrayList;
import java.util.List;

public class LoseCommand extends ServerToClientCommand{

    public Integer getPosition() {
        return position;
    }

    private Integer position;

    public List<String> getScores() {
        return scores;
    }

    private List<String> scores;
    /**
     * @param scores contains the scores of other players, ordered
     * @param position contains relative position
     */
    public LoseCommand(List<String> scores, Integer position){
        this.scores=scores;
        this.position=position;
    }
    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }


}
