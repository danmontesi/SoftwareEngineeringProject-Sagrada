package it.polimi.se2018.server_to_client_command;

public class AllowedUseToolCommand extends ServerToClientCommand{
    /**
     * The generic Server response to client that wants to use a tool
     * This command just gives to the Client the ok for using tool.
     *
     * The class is sent for every tool EXCEPT for UseToolFirmPastryThinner UseToolFirmPastryBrush,
     * in which the server has to communicate another information to client in order to use the tool
     */

    /**
     * Contains the position of the toolcard to use
     */
    private String message;

    private Integer toolCardPosition;

    public AllowedUseToolCommand(String message, Integer toolCardPosition) {
        this.message = message;
        this.toolCardPosition = toolCardPosition;
    }
}
