package it.polimi.se2018.client_to_server_command;

public class UseToolDiamondSwab extends ClientToServerCommand{
    /**
     * Flips a chosen die in the DradftPool (no obliged to place it in the windowPatternCard)
     * The string indicates where the die he wants to change is
     *
     * UseToolDiamondSwab dieColor(referred to the RoundTrack)
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

    private Integer schemaPosition;

    private Integer dieDraftPoolPosition;

    public UseToolDiamondSwab(String message, Integer schemaPosition, Integer dieDraftPoolPosition) {
        this.message = message;
        this.schemaPosition = schemaPosition;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
    }
}
