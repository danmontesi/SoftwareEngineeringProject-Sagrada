package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.MVC.Controller;
import it.polimi.se2018.network.ServerConnection;

public class MoveChoiceToolCardCommand {
    int numberChosen;

    private String message;

    public MoveChoiceToolCardCommand(String message, int numberChosen){
        this.numberChosen = numberChosen;
        this.message = "MoveChoiceToolCardCommand";
    }

}
