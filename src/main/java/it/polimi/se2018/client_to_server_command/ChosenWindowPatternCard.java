package it.polimi.se2018.client_to_server_command;

public class ChosenWindowPatternCard extends ClientToServerCommand{

    /**
     * Contains the message with NameClass + toolCardName
     */
    private String message;

    public ChosenWindowPatternCard(String message) {
        this.message = message;
    }
}





