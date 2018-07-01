package it.polimi.se2018.view.gui.notifiers.gameboardactions;

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
