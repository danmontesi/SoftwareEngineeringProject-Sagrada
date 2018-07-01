package it.polimi.se2018.view.gui.notifiers.gameboardactions;

public class TimeUp implements GameBoardAction {
    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }
}
