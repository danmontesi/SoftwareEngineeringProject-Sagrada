package it.polimi.se2018.view.gui.notifiers.gameboardactions;

import java.util.List;

public class DraftPoolRoundTrackUpdate implements GameBoardAction {
    private String type;
    private List<String> dice;

    /**
     * Updates Draft Pool ot Round Track dice, depending on parameter type
     * @param type if it is DP, Draft Pool will be updated, if it is RT Round Track
     * @param dice list of string representation of dice
     */
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
