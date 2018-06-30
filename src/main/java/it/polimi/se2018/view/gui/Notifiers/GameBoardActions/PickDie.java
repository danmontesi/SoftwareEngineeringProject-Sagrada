package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

public class PickDie implements GameBoardAction {
    private String from;

    public PickDie(String from) {
        this.from = from;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public String getFrom() {
        return from;
    }
}
