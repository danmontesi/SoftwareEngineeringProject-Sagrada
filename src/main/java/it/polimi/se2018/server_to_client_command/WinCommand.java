package it.polimi.se2018.server_to_client_command;

import java.util.ArrayList;

public class WinCommand extends ServerToClientCommand{

    private ArrayList<String> scores;

    /**
     * @param scores is in the format NameClass + playerUsername1,score1 + " " + playerUsername2,score2 + " " + ...
     */
    public WinCommand(ArrayList<String> scores){
        this.scores=scores;
    }

}
