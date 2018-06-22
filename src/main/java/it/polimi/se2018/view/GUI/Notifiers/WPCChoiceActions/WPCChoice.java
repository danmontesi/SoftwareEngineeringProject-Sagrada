package it.polimi.se2018.view.GUI.Notifiers.WPCChoiceActions;

import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.GameBoardAction;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardActions.GameBoardVisitor;

import java.util.ArrayList;

public class WPCChoice implements WPCChoiceAction {
    private ArrayList<String> wpcNames;
    private ArrayList<Integer> wpcDifficulties;

    public WPCChoice(ArrayList<String> wpcNames, ArrayList<Integer> wpcDifficulties) {
        this.wpcNames = wpcNames;
        this.wpcDifficulties = wpcDifficulties;
    }

    @Override
    public void acceptWPCChoiceVisitor(WPCChoiceVisitor wpcChoiceVisitor) {
        wpcChoiceVisitor.visitWPCChoiceAction(this);
    }

    public ArrayList<String> getWpcNames() {
        return wpcNames;
    }

    public ArrayList<Integer> getWpcDifficulties() {
        return wpcDifficulties;
    }
}
