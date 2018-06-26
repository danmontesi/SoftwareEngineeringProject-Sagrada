package it.polimi.se2018.view.gui.Notifiers.GameBoardActions;

import java.util.ArrayList;

public class TokensUpdate implements GameBoardAction {
    private ArrayList<Integer> tcTokens;
    private ArrayList<Integer> playersTokens;
    private Integer personalTokens;

    public TokensUpdate(ArrayList<Integer> tcTokens, ArrayList<Integer> playersTokens, Integer personalTokens) {
        this.tcTokens = tcTokens;
        this.playersTokens = playersTokens;
        this.personalTokens = personalTokens;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public ArrayList<Integer> getTcTokens() {
        return tcTokens;
    }

    public ArrayList<Integer> getPlayersTokens() {
        return playersTokens;
    }

    public Integer getPersonalTokens() {
        return personalTokens;
    }
}
