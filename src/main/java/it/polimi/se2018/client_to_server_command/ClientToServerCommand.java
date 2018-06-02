package it.polimi.se2018.client_to_server_command;

import java.io.Serializable;

public class ClientToServerCommand implements Serializable {

    private String message;

    private static final long serialVersionUID = -6460847931998831472L;
    /**
     * Represent all possible methods from Client to Server
     * They are constructed by the View
     *
     * EVERY CLASS HAS AT LEAST A STRING METHOD THAT SPECIFIES THE NAME OF THE COMMAND
     * e.g.
     * String = "MoveChoicePassTurn ..... (eccetera)"
     */

    public ClientToServerCommand(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}



class MoveChoicePassTurn{
    /**
     * Move performed in case there is no way to place a correct die in the wpc
     */
    private String message;

}

class MoveChoiceSimpleDicePlacement{
    /**
     * Move performed that specifies in a String attribute ordered, the Die type the user want to move (he chose it from the draftPool by clicking it
     * the string will be parsed in this way:
     *
     * MoveChoiceSimpleDicePlacement diecolor
     *
     * Integer dievalue
     * Integer dieposition(from 0 to 20)
     *
     * e.g.
     * "MoveChoiceSimpleDicePlacement GREEN"
     *
     * Integer dievalue=3
     * Integer dieposition=2 (corrisponde alla riga 0 colonna 2)
     *
     * The controller server-side will check if the die is present in the DraftPool
     */
    private String message;

    private Integer dieValue;

    private Integer dieRoundTrackPosition;
}

class NotifyPlayerDisconnection{
    /**
     * The message is a string thast contains only the name "NotifyPlayerDisconnection".
     * Other players are notified
     * That is used when a player want to left the game. (maybe for reconnection in another way)
     */
    private String message;

    private Integer dieValue;

    private Integer dieRoundTrackPosition;


}

class UseTool{

    /**
     * Request of useing a tool.
     * The indicated tool used is written in the String message (name of the card Tool to use)
     */
}
/*
LEGENDA TOOL
Tool1: aumenta il valore di un dado che scegli dalla riserva di 1
Tool2 : Muovo un dado senza restrizioni di colore
Tool3: " " senza restizioni di valore (numero) del dado
Tool4: Muovi 2 dadi con tutte le restrizioni standard
Tool5: scambio un dado della riserva con uno del roundTrack
Tool6: scegli un dado della riserva e ritiralo, poi devi posizionarlo
Tool7: rilancio i dadi della riserva
Tool8: anticipa il secondo turno del round
Tool9: pongo(dalla riserva) un dado senza restrizione di Adiacenza
Tool10: flip the die
Tool11: metti un dado nel sacco dadi, poi prendine uno e scegli il valore, poi piazzalo!
Tool12: muovi uno o due dadi dello stesso colore di un dado a scelta del RoundTrack,  sul tuo Pattern in un'altra posizione che rispetti tutte le restrizioni
*/


class UseToolCopperFoilReamer{
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

class UseToolCorkLine{
    /**
     * As a normal move, no Placement Restriction
     * String contains the die he wants to move and
     * * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

    private String message;

    private Integer dieValue;

    private Integer dieRoundTrackPosition;
}

class UseToolDiamondSwab{
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

class UseToolEglomiseBrush{
    /**
     * Move a die in your schema without color restrictions
     *
     * As a normal move with 2 positions
     *
     * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer currentPosition(from 0 to 20)
     *
     * Integer nextPosition
     */

    private String message;

    private Integer dieValue;

    private Integer currentPosition;

    private Integer nextPosition;

}

class UseToolFirmPastryBrush1{
    /**
     * Decides the die of DraftPool he wants to roll again
     * message = .. dieColor
     *
     * Integer dieValue
     */

    private String message;

    private Integer dieValue;

    private Integer dieRoundTrackPosition;
}

class UseToolFirmPastryBrush2{
    /**
     *
     * WITH THIS I HAVE TO GIVE THE PLAYER the new value
     * he show it and then:
     *
     *  Decides if to place the changed value die or if to put in the draftPool
     *  To let the Controller know, the answer message has to indicate PLACE if he wants to place or DRAFTPOOL if not
     *  message = UseToolFirmPastryBrush2 PLACE/DRAFTPOOL
     *
     *  Integer diePosition
     */

    private String message;

    private Integer dieRoundTrackPosition;
}

class UseToolFirmPastryThinner1{
    /**
     * This tool has to divided in 2
     * in this, he says he want to use the tool
     *
     * String message with just "UseToolFirmryPastryThinner1
     *
     */

    private String message;

}

class UseToolFirmPastryThinner2{
    /**
     *
     * I HAVE TO GIVE THE PLAYER THE DIE PICKED FROM DICEBAG
     * String dieColor
     *
     * Integer dieValue
     * then->
     * Here, the player decides the value of the picked Die
     *
     * Message = UseToolFirmyPastryThinner2
     * Integer newValue
     *
     * Integer position(from 0 to 20)
     *
     */
    private String message;

    private Integer dieNewValue;

    private Integer diePosition;
}

class UseToolGavel{
    /**
     * Message contains just message= UseToolGavel
     */

    private String message;

}

class UseToolLathekin{
    /**
     * Tool 9
     * Exactly as a normal move, String contains just the Name
     * MoveChoiceSimpleDicePlacement diecolor
     *
     * Integer dievalue
     * Integer dieposition(from 0 to 20)
     */

    private String message;

    private Integer dieValue;

    private Integer diePosition;
}

class UseToolManualCutter{
    /**
     * Tool that moves 2 same color dice
     *
     * message = UseToolManualCutter dieColor
     *
     * Integer die1CurrentPosition (in the schema)
     * Integer die2CurrentPosition (in the schema)
     *
     * Integer dieNextPosition1
     * Integer dieNextPosition2
     */

    private String message;

    private Integer dieNextPosition2;

    private Integer dieNextPosition1;

    private Integer dieCurrentPosition1;

    private Integer dieCurrentPosition2;
}

class UseToolRoughingForceps{
    /**
     * Has to chose the die from Draftpool and indicates if to increase/decrease the value
     *
     * Message .. dieColor
     *
     * Integer dieValue
     */
    private String message;

    private Integer dieValue;

}

class UseToolWheelsPincher{
    /**
     * Muovi 2 dadi in uno stesso turno
     * String that let you do 2 dice moves, and let you skip the turn
     *
     * is like 2 moves
     *
     * String message = "UseToolWheelsPincher dieColor1 dieColor2
     * Integer dievalue1
     * Integer diePosition1(from 0 to 20)
     *
     * Integer dievalue2
     * Integer diePosition2(from 0 to 20)
     */

    private String message;

    private Integer dieValue1;

    private Integer dieSchemaPosition1;

    private Integer dieSchemaPosition2;

    private Integer dieRoundTrackPosition2;

}