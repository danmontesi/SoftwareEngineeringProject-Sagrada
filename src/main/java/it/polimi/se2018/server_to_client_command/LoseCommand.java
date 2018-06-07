package it.polimi.se2018.server_to_client_command;

import java.util.ArrayList;

public class LoseCommand extends ServerToClientCommand{

    public Integer getPosition() {
        return position;
    }

    private Integer position;

    public ArrayList<String> getScores() {
        return scores;
    }

    private ArrayList<String> scores;
    /**
     * @param scores contains the scores of other players, ordered
     * @param position contains relative position
     */
    public LoseCommand(ArrayList<String> scores, Integer position){
        this.scores=scores;
        this.position=position;
    }

}
