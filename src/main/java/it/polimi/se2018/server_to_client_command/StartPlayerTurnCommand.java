package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

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


public class StartPlayerTurnCommand extends ServerToClientCommand{
    /**
     * Ask a player to perform a move
     */

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
