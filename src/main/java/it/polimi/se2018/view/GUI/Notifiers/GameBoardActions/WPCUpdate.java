package it.polimi.se2018.view.GUI.Notifiers.GameBoardActions;

import java.util.ArrayList;

public class WPCUpdate implements GameBoardAction {
    private ArrayList<String> myWpc;
    private ArrayList<ArrayList<String>> otherWpcs;

    public WPCUpdate(ArrayList<String> myWpc, ArrayList<ArrayList<String>> otherWpcs) {
        this.myWpc = myWpc;
        this.otherWpcs = otherWpcs;
    }

    @Override
    public void acceptGameBoardVisitor(GameBoardVisitor gameBoardVisitor) {
        gameBoardVisitor.visitGameBoardAction(this);
    }

    public ArrayList<String> getMyWpc() {
        return myWpc;
    }

    public ArrayList<ArrayList<String>> getOtherWpcs() {
        return otherWpcs;
    }
}
