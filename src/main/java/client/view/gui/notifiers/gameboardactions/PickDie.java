package client.view.gui.notifiers.gameboardactions;

public class PickDie implements GameBoardAction {
    private String from;

    /**
     * Allows the user to pick a die, specifying where he should pick it from (Window Pattern Card, Draft Pool or Round Track)
     */
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
