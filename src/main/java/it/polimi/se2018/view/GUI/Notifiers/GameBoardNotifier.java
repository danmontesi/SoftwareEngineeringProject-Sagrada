package it.polimi.se2018.view.GUI.Notifiers;

import it.polimi.se2018.commands.server_to_client_command.RefreshBoardCommand;
import it.polimi.se2018.view.GUI.Notifiers.GameBoardReplies.GameBoardReply;

import java.util.Observable;

public class GameBoardNotifier extends Observable {
    private GameBoardNotifier() {}

    private static class GameBoardNotifierHolder {
        private static final GameBoardNotifier INSTANCE = new GameBoardNotifier();
    }

    public static GameBoardNotifier getInstance() {
        return GameBoardNotifierHolder.INSTANCE;
    }

    public void updateGui() {
        setChanged();
        notifyObservers();
    }

    public void updateGui(GameBoardReply gameBoardReply, RefreshBoardCommand refreshBoardCommand) {
        setChanged();
        notifyObservers(refreshBoardCommand);
    }
}