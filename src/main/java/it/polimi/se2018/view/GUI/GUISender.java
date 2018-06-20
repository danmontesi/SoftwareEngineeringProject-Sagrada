package it.polimi.se2018.view.GUI;

import it.polimi.se2018.commands.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.utils.Observable;

public class GUISender extends Observable {

    GUIView guiView;

    public GUISender(GUIView guiView) {
        this.guiView = guiView;
    }

    public void chosenWindowPatternCardMenu(String wpc) {
        guiView.notify(new ChosenWindowPatternCard(wpc));
        System.out.println("notify called");
        //notify(new ChosenWindowPatternCard(wpc));
    }

}
