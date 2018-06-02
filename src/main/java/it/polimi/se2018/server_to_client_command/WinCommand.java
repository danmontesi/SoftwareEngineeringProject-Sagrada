package it.polimi.se2018.server_to_client_command;

public class WinCommand extends ServerToClientCommand{
    public Integer getScore() {
        return score;
    }

    private Integer score;

    /**
     * @param message is in the format NameClass + playerUsername1,score1 + " " + playerUsername2,score2 + " " + ...
     * @param score
     */
    public WinCommand(String message, Integer score){
        this.score=score;
        this.message="WinCommand"+ " " + message;
    }

}
