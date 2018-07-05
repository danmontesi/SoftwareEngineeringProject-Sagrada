package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

public class ChooseWindowPatternCardCommand extends ServerToClientCommand{

    private String wpcsInStrings;
    private String privateObjectiveCard;

    /**
     * Contains the names of the four Window Pattern Cards the user can choose from and the assigned Private Objective Card
     */
    public ChooseWindowPatternCardCommand(String wpcsInString, String privateObjectiveCard) {
        this.wpcsInStrings = wpcsInString;
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public String getWpcsInStrings() {
        return wpcsInStrings;
    }

    public String getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }
}
