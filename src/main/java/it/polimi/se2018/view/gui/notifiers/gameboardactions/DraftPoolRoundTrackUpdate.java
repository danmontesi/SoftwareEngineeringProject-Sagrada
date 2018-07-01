package it.polimi.se2018.view.gui.notifiers.gameboardactions;

import java.util.List;

public class DraftPoolRoundTrackUpdate implements GameBoardAction {
    private String type;
    private List<String> dice;

    public DraftPoolRoundTrackUpdate(String type, List<String> dice) {
        this.type = type;
        this.dice = dice;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public String getType() {
        return type;
    }

    public List<String> getDice() {
        return dice;
    }
}
