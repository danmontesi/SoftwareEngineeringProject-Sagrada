package client.view.gui.notifiers.gameboardactions;

import java.util.List;

public class WPCUpdate implements GameBoardAction {
    private List<String> myWpc;
    private List<List<String>> otherWpcs;

    /**
     * Updates each player's Window pattern Card
     * @param myWpc the player's Window Pattern Card
     * @param otherWpcs all the other users' Window Pattern Cards
     */
    public WPCUpdate(List<String> myWpc, List<List<String>> otherWpcs) {
        this.myWpc = myWpc;
        this.otherWpcs = otherWpcs;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public List<String> getMyWpc() {
        return myWpc;
    }

    public List<List<String>> getOtherWpcs() {
        return otherWpcs;
    }
}
