package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.List;

public class ChooseWindowPatternCardCommand extends ServerToClientCommand{

    private List<Integer> wpcDifficulties;
    private List<List<String>> wpcsInStrings;
    private String privateObjectiveCard;

    /**
     * Contains the names of the four Window Pattern Cards the user can choose from and the assigned Private Objective Card
     */
    public ChooseWindowPatternCardCommand(List<List<String>> wpcsInString, String privateObjectiveCard, List<Integer> wpcDifficulties) {
        this.wpcsInStrings = wpcsInString;
        this.privateObjectiveCard = privateObjectiveCard;
        this.wpcDifficulties = wpcDifficulties;
    }

    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

    public List<List<String>> getWpcsInStrings() {
        return wpcsInStrings;
    }

    public String getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public List<Integer> getWpcDifficulties() {
        return wpcDifficulties;
    }

}
