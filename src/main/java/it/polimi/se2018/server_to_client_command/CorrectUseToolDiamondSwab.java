package it.polimi.se2018.server_to_client_command;

public class CorrectUseToolDiamondSwab extends ServerToClientCommand{
    /**
     * Flips a chosen die in the DradftPool (no obliged to place it in the windowPatternCard)
     * The string indicates where the die he wants to change is
     *
     * UseToolDiamondSwab dieColor(referred to the RoundTrack)
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

    private String message;

    private Integer dieValue;

    private Integer dieRoundTrackPosition;
}
