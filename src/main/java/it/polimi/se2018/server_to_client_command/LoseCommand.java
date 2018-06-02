package it.polimi.se2018.server_to_client_command;

public class LoseCommand extends ServerToClientCommand{

    public Integer getPosition() {
        return position;
    }

    private Integer position;

    /**
     * @param message contains the scores of other players, ordered
     * @param score
     * @param position
     */
    public LoseCommand(String message, Integer score, Integer position){
        this.position=score;
        this.message="LoseCommand" + " " + message;
    }

}
