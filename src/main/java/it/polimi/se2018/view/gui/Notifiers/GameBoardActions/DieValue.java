package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

public class DieValue implements GameBoardAction {
    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }
}
