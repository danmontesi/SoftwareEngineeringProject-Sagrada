package client.view.gui.notifiers.gameboardactions;

public class TurnUpdate implements GameBoardAction {
    private boolean DieMoved;
    private boolean ToolUsed;

    /**
     * Allows the player to go on with is turn depending on which actions he already performed
     * @param DieMoved true if the user already performed a regular move, false otherwise
     * @param ToolUsed true if the user already used a Tool Card, false otherwise
     */
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
