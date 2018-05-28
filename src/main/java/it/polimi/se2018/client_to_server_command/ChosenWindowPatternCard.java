package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.MVC.Controller;
import it.polimi.se2018.network.ServerConnection;

public class ChosenWindowPatternCard extends ClientToServerCommand{

    private Integer toolNumberChosen;

    private String message;

    public ChosenWindowPatternCard(Integer toolNumberChosen, String message) {
        this.toolNumberChosen = toolNumberChosen;
        this.message = message;
    }
}





