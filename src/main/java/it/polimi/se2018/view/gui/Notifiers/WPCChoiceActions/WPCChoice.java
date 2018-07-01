package it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions;

import java.util.ArrayList;

public class WPCChoice implements WPCChoiceAction {
    private ArrayList<String> wpcNames;
    private ArrayList<Integer> wpcDifficulties;
    private String privateOC;

    public WPCChoice(ArrayList<String> wpcNames, ArrayList<Integer> wpcDifficulties, String privateObjectiveCard) {
        this.wpcNames = wpcNames;
        this.wpcDifficulties = wpcDifficulties;
        this.privateOC = privateObjectiveCard;
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

    public String getPrivateOC() {
        return privateOC;
    }
}
