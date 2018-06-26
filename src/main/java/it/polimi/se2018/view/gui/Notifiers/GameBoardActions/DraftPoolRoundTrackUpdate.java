package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

import java.util.ArrayList;

public class DraftPoolRoundTrackUpdate implements GameBoardAction {
    private String type;
    private ArrayList<String> dice;

    public DraftPoolRoundTrackUpdate(String type, ArrayList<String> dice) {
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

    public ArrayList<String> getDice() {
        return dice;
    }
}
