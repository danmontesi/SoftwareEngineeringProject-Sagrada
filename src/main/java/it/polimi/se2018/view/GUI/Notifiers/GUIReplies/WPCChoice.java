package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

import java.util.ArrayList;

public class WPCChoice implements GUIReply {
    private ArrayList<String> wpcNames;
    private ArrayList<Integer> wpcDifficulties;

    public WPCChoice(ArrayList<String> wpcNames, ArrayList<Integer> wpcDifficulties) {
        this.wpcNames = wpcNames;
        this.wpcDifficulties = wpcDifficulties;
    }

    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public ArrayList<String> getWpcNames() {
        return wpcNames;
    }

    public ArrayList<Integer> getWpcDifficulties() {
        return wpcDifficulties;
    }
}
