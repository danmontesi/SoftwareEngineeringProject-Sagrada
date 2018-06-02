package it.polimi.se2018.server_to_client_command;

/**
 * Correct application of Tool bu the Client.
 * The server, after having updated the Model/controller as the user wants to, send back to the client a Command with the
 * update to do.
 */


public class CorrectUseToolCopperFoilReamer extends ServerToClientCommand{
    /**
     * Tool2 Ignoro restrizione colore
     * As a normal move
     *
     * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer dieposition(from 0 to 20)
     */


    private String message;

    private Integer dieValue;

    private Integer dieRoundTrackPosition;

}
