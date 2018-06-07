package it.polimi.se2018.server_to_client_command;

import it.polimi.se2018.Model;

public class RefreshBoardCommand extends ServerToClientCommand{
    public Model getModel() {
        return model;
    }

    /**
     * That's the unique command that contains an object different from string and integer
     * Contains each player's view of the board.
     */
    private final Model model;

    public RefreshBoardCommand(Model model){
        this.model = model;
    }
}
