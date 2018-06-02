package it.polimi.se2018.client_to_server_command;

import it.polimi.se2018.network.ServerConnection;

public class ChosenWindowPatternCard extends ClientToServerCommand{

    /**
     * Contains the message with NameClass + toolCardName
     */
    private String message;

    public ChosenWindowPatternCard(String message) {
        this.message = message;
    }
}





