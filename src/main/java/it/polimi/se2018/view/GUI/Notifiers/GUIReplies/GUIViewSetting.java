package it.polimi.se2018.view.GUI.Notifiers.GUIReplies;

import it.polimi.se2018.view.GUI.GUIView;

public class GUIViewSetting implements GUIReply {

    private GUIView guiView;

    public GUIViewSetting(GUIView guiView) {
        this.guiView = guiView;
    }

    @Override
    public void acceptGUIVisitor(GUIVisitor guiVisitor) {
        guiVisitor.visitGUIReply(this);
    }

    public GUIView getGuiView() {
        return guiView;
    }
}
