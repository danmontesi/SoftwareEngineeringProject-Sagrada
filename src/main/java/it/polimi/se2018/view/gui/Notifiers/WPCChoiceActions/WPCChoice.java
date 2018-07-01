package it.polimi.se2018.view.gui.Notifiers.WPCChoiceActions;

import java.util.List;

public class WPCChoice implements WPCChoiceAction {
    private List<String> wpcNames;
    private List<Integer> wpcDifficulties;

    public WPCChoice(List<String> wpcNames, List<Integer> wpcDifficulties) {
        this.wpcNames = wpcNames;
        this.wpcDifficulties = wpcDifficulties;
    }

    @Override
    public void acceptWPCChoiceVisitor(WPCChoiceVisitor wpcChoiceVisitor) {
        wpcChoiceVisitor.visitWPCChoiceAction(this);
    }

    public List<String> getWpcNames() {
        return wpcNames;
    }

    public List<Integer> getWpcDifficulties() {
        return wpcDifficulties;
    }
}
