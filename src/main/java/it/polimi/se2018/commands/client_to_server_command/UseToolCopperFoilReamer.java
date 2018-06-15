package it.polimi.se2018.commands.client_to_server_command;

public class UseToolCopperFoilReamer extends ClientToServerCommand{
    /**
     * Tool2 Ignoro restrizione colore
     * As a normal move
     *
     * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer dieposition(from 0 to 20)
     */

    private Integer dieValue;

    private Integer dieDraftPoolPosition;

    public UseToolCopperFoilReamer(String message, Integer dieValue, Integer dieDraftPoolPosition) {
        this.message = message;
        this.dieValue = dieValue;
        this.dieDraftPoolPosition = dieDraftPoolPosition;
    }


}
