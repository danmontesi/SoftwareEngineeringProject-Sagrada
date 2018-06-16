package it.polimi.se2018.commands.client_to_server_command;

public class UseToolCircularCutter extends ClientToServerCommand{
    /**
     * Scambio dado con quello nel Roundtrack
     *
     * message = UseToolCircularCutter dieDraftPoolColor
     *
     * Integer dieValue
     *
     * Integer dieRoundTrackPosition
     */


    private Integer dieDraftPoolPosition;

    public UseToolCircularCutter(Integer dieDraftPoolPosition, Integer dieRoundTrackPosition) {
        this.dieDraftPoolPosition = dieDraftPoolPosition;
        this.dieRoundTrackPosition = dieRoundTrackPosition;
    }

    private Integer dieRoundTrackPosition;

}
