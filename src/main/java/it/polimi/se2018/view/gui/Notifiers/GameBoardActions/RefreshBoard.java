package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;

public class RefreshBoard implements GameBoardAction {
    private RefreshBoardCommand modelRepresentation;

    public RefreshBoard(RefreshBoardCommand modelRepresentation) {
        this.modelRepresentation = modelRepresentation;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public RefreshBoardCommand getModelRepresentation() {
        return modelRepresentation;
    }
}
