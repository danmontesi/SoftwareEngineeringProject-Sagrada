package it.polimi.se2018.server_to_client_command;

import com.sun.org.apache.regexp.internal.RE;
import it.polimi.se2018.Model;
import it.polimi.se2018.network.ClientConnection;

import java.io.Serializable;

public abstract class ServerToClientCommand implements Serializable {
    /**
     * This is the abstract class representing all possible command from Server to Client
     */
    private String message;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6460847901998831472L;

    public String getMessage(){
        return message;
    }


    }

class AskAuthenticationCommand{
    /**
     * Send a request for a new Username (probably because the one chosen is incorrect)
     * String that cointains NameClass
     */
    private String message;

}

class AuthenticatedCorrectlyCommand{
    /**
     * Comunicates that the username is correct
     * String with only NameClass
     */
    private String message;


}

class ChooseToolCardCommand{
    /**
     * Request of ToolCard
     * String with only NameClass
     */
    private String message;


}

class InitializeTurnCommand{
    private String message;


}


/** Eccetera
 *
 */
/*
    AskAuthenticationCommand.java
        AskFinishRoundOrDiscardCommand.java	IMPLEMENTED leader cards activation requirements control and ADDED so…	10 months ago
        AskForExcommunicationPaymentCommand.java	IMPLEMENTED leader cards activation requirements control and ADDED so…	10 months ago
        AskForReconnectionCommand.java	SOLVING RECONNECTION errors	10 months ago
        AskMoveCommand.java	IMPLEMENTED leader cards activation requirements control and ADDED so…	10 months ago
        AskPrivilegeChoiceCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        AskSatanMoveCommand.java	FIXED fifth player bug	10 months ago
        AssignColorCommand.java	IMPLEMENTED leader cards activation requirements control and ADDED so…	10 months ago
        AuthenticatedCorrectlyCommand.java	Finished implementing creation and refresh of accounts	10 months ago
        ChatMessageServerCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        ChooseLeaderCardCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        ChooseProductionExchangeEffectsCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        CloseClientCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        InitializeMatchCommand.java	IMPLEMENTED leader cards activation requirements control and ADDED so…	10 months ago
        InitializeTurnCommand.java	GUI branch compromised, do not merge into master from that branch, st…	10 months ago
        InvalidActionCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        InvalidCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        LoseCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        NotifyExcommunicationCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        NotifySatanActionCommand.java	GUI branch compromised, do not merge into master from that branch, st…	10 months ago
        OpponentStatusChangeCommand.java	GUI branch compromised, do not merge into master from that branch, st…	10 months ago
        PlayerDisconnectedCommand.java	MODIFIED: you can take more than 1 privilege in GUI now, saved gamepl…	10 months ago
        PlayerStatusChangeCommand.java	GUI branch compromised, do not merge into master from that branch, st…	10 months ago
        RefreshBoardCommand.java	IMPLEMENTED leader cards activation requirements control and ADDED so…	10 months ago
        RoundTimerExpiredCommand.java	IMPLEMENTED leader cards activation requirements control and ADDED so…	10 months ago
        ServerToClientCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        StartTurnCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        WinCommand.java	Moved tests, Created Automatic Javadoc to Improve later	10 months ago
        WrongPasswordCommand.java

        */


class StartPlayerTurnCommand{
    /**
     * Ask a player to perform a move
     */

}

class InvalidMoveCommand{
    /**
     * Move is incorrect-> Notify it to the player and ask him a new choice from turn menu (not obliged to do move, also tool..)
     */
}

class InvalidCommand{
    /**
     * The message is a string thast contains only the name "NotifyMyDisconnection".
     * That is used when the player want to left the game. (maybe for reconnection in another way)
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

/**
 * Commands that allows the ClientController to call the next phase of the tool.
 * These commands are sent when the Client show interest in using a tool.
 * If player has enough token, one of these commands are sent to him.
 */
class UseToolCopperFoilReamer{
    /**
     * Just a String
     */
}

class UseToolCorkLine{
     /**
     * Contains just a String
     */

}

class UseToolDiamondSwab{
    /**
     * Contains just a String
     */

}

class UseToolEglomiseBrush{
    /**
     * Contains just a String
     */

}

class UseToolFirmPastryBrush1{
    /**
     * Contains just a String
     */

}

class UseToolFirmPastryBrush2{
    /**
     * Contains just a String
     */

}

class UseToolFirmPastryThinner{
    /**
     * HAS TO GIVE THE PLAYER a die extracted from DiceBag
     * Contains a String with dieColor
     * Integer dieValue
     */

}

class UseToolGavel{
    /**
     * Contains just a String
     */

}

class UseToolLathekin{
    /**
     * Contains just a String
     */

}

class UseToolManualCutter{
    /**
     * Contains just a String
     */

}

class UseToolRoughingForceps{
    /**
     * Contains just a String
     */

}

class UseToolWheelsPincher{
    /**
     * Contains just a String
     */

}


/**
 * Commands that notify the ClientController that the inserted value are incorrect.
 * The clientController has to call the messageBox with the reapplication of the tool to give player a second chance.
 * to use the tool correctly. Each tool has a message for the re-use of it.
 * All the InvalideUseTool classes has just a String
 */

class InvalidUseToolCorkLine{
    /**
     * As a normal move, no Placement Restriction
     * String contains the die he wants to move and
     * * UseToolCopperFoilReamer diecolor
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */

}

class InvalidUseToolCopperFoilReamer{
    /**
     * Just a String
     */

}


class InvalidUseToolDiamondSwab{
    /**
     * Flips a chosen die in the DradftPool (no obliged to place it in the windowPatternCard)
     * The string indicates where the die he wants to change is
     *
     * UseToolDiamondSwab dieColor(referred to the RoundTrack)
     *
     * Integer dievalue
     * Integer diePosition(from 0 to 20)
     */
}

class InvalidUseToolEglomiseBrush{
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

}

class InvalidUseToolFirmPastryBrush{
    /**
     * Decides the die he wants to change the value
     * message = .. dieColor
     *
     * Integer dieValue
     */


}

class InvalidUseToolFirmPastryBrush2{
    /**
     *  Decides if to place the changed value die or if to put in the draftPool
     *  To let the Controller know, the answer message has to indicate PLACE if he wants to place or DRAFTPOOL if not
     *  message = UseToolFirmPastryBrush2 PLACE/DRAFTPOOL
     *
     *  Integer diePosition
     */

}


class InvalidUseToolFirmPastryThinner{
    /**
     * Here, the player decides the value of the picked Die
     *
     * Message = UseToolFirmyPastryThinner2
     * Integer newValue
     *
     * Integer position(from 0 to 20)
     *
     */
}

class InvalidUseToolGavel{
    /**
     * Message contains just message= UseToolGavel
     */

}

class InvalidUseToolLathekin{
    /**
     * Tool 9
     * Exactly as a normal move, String contains just the Name
     * MoveChoiceSimpleDicePlacement diecolor
     *
     * Integer dievalue
     * Integer dieposition(from 0 to 20)
     */

}

class InvalidUseToolManualCutter{
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
}

class InvalidUseToolRoughingForceps{
    /**
     * Has to chose the die from Draftpool and indicates if to increase/decrease the value
     *
     * Message .. dieColor INCREASE/DECREASE
     *
     * Integer dieValue
     */

}

class InvalidUseToolWheelsPincher{
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

}

/**
 * Correct application of Tool bu the Client.
 * The server, after having updated the Model/controller as the user wants to, send back to the client a Command with the
 * update to do.
 */


class CorrectUseToolCopperFoilReamer{
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

class CorrectUseToolCorkLine{
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

class CorrectUseToolDiamondSwab{
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

class CorrectUseToolEglomiseBrush{
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

class CorrectUseToolFirmPastryBrush1{
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

class CorrectUseToolFirmPastryBrush2{
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

class CorrectUseToolFirmPastryThinner1{
    /**
     * This tool has to divided in 2
     * in this, he says he want to use the tool
     *
     * String message with just "UseToolFirmryPastryThinner1
     *
     */

    private String message;

}

class CorrectUseToolFirmPastryThinner2{
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

class CorrectUseToolGavel{
    /**
     * Message contains just message= UseToolGavel
     */

    private String message;

}

class CorrectUseToolLathekin{
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

class CorrectUseToolManualCutter{
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

class CorrectUseToolRoughingForceps{
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

class CorrectUseToolWheelsPincher{
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
