package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

public class TurnUpdate implements GameBoardAction {
    private boolean move;
    private boolean tool;

    public TurnUpdate(boolean move, boolean tool) {
        this.move = move;
        this.tool = tool;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public boolean getMove() {
        return move;
    }

    public boolean getTool() {
        return tool;
    }
}
