package it.polimi.se2018.server_to_client_command;

/**
 * Commands that notify the ClientController that the inserted value are incorrect.
 * The clientController has to call the messageBox with the reapplication of the tool to give player a second chance.
 * to use the tool correctly. Each tool has a message for the re-use of it.
 * All the InvalideUseTool classes has just a String
 */

public class InvalidUseToolCorkLine extends ServerToClientCommand{
    /**
     * As a normal move, no Placement Restriction
     * String contains the die he wants to move and
     * * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

}
