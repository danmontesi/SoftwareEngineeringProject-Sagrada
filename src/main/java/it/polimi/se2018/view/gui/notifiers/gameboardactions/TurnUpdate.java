package it.polimi.se2018.view.gui.notifiers.gameboardactions;

public class TurnUpdate implements GameBoardAction {
    private boolean DieMoved;
    private boolean ToolUsed;

    public TurnUpdate(boolean DieMoved, boolean ToolUsed) {
        this.DieMoved = DieMoved;
        this.ToolUsed = ToolUsed;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public boolean isDieMoved() {
        return DieMoved;
    }

    public boolean isToolUsed() {
        return ToolUsed;
    }
}
