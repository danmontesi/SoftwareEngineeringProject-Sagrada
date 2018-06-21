package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

import java.util.ArrayList;

public class WPCUpdate implements GUIReply {
    private ArrayList<String> myWpc;
    private ArrayList<ArrayList<String>> otherWpcs;

    public WPCUpdate(ArrayList<String> myWpc, ArrayList<ArrayList<String>> otherWpcs) {
        this.myWpc = myWpc;
        this.otherWpcs = otherWpcs;
    }

    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public ArrayList<String> getMyWpc() {
        return myWpc;
    }

    public ArrayList<ArrayList<String>> getOtherWpcs() {
        return otherWpcs;
    }
}
