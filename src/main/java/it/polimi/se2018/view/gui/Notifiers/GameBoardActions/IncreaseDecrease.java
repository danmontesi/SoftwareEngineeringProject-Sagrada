package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

public class IncreaseDecrease implements GameBoardAction {
    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }
}
