package it.polimi.se2018.view.gui.notifiers.wpcchoiceactions;

import java.util.List;

public class WPCChoice implements WPCChoiceAction {
    private List<String> wpcNames;
    private List<Integer> wpcDifficulties;
    private String privateOC;

    /**
     * Contains the information about the four Window Pattern Cards the user can choose between and the associated Private Objective Card
     */
    public WPCChoice(List<String> wpcNames, List<Integer> wpcDifficulties, String privateObjectiveCard) {
        this.wpcNames = wpcNames;
        this.wpcDifficulties = wpcDifficulties;
        this.privateOC = privateObjectiveCard;
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

    public String getPrivateOC() {
        return privateOC;
    }
}
