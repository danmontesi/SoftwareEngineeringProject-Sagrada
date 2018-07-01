package it.polimi.se2018.view.gui.notifiers.gameboardactions;

import java.util.List;

public class TokensUpdate implements GameBoardAction {
    private List<Integer> tcTokens;
    private List<Integer> playersTokens;
    private Integer personalTokens;

    public TokensUpdate(List<Integer> tcTokens, List<Integer> playersTokens, Integer personalTokens) {
        this.tcTokens = tcTokens;
        this.playersTokens = playersTokens;
        this.personalTokens = personalTokens;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public List<Integer> getTcTokens() {
        return tcTokens;
    }

    public List<Integer> getPlayersTokens() {
        return playersTokens;
    }

    public Integer getPersonalTokens() {
        return personalTokens;
    }
}
