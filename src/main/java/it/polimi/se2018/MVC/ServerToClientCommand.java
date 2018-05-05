package it.polimi.se2018.MVC;

import it.polimi.se2018.network.ClientConnection;

import java.io.Serializable;

public abstract class ServerToClientCommand implements Serializable {
    /**
     * This is the abstract class representing all possible command from Server to Client
     */

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -6460847901998831472L;

        /**
         * Process command.
         *
         * @param connection the client command handler
         */
        public abstract void processCommand(ClientConnection connection);

    }

class AskAuthenticationCommand{

}

class AuthenticatedCorrectlyCommand{

}

class ChooseWindowPatternCardCommand{

}

class ChooseToolCardCommand{

}

class InitializeMatchCommand{

}

class InitializeTurnCommand{

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