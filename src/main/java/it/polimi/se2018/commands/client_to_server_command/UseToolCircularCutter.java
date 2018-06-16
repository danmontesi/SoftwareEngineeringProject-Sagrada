package it.polimi.se2018.commands.client_to_server_command;

import it.polimi.se2018.utils.ControllerServerInterface;

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

    public Integer getDieDraftPoolPosition() {
        return dieDraftPoolPosition;
    }

    public Integer getDieRoundTrackPosition() {
        return dieRoundTrackPosition;
    }

    private Integer dieRoundTrackPosition;

    public void visit(ControllerServerInterface observer){
        observer.applyCommand(getUsername(), this);
    }

}
