package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.RoundTrack;
import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.ArrayList;

public class RefreshBoardCommand extends ServerToClientCommand{
    /**
     * That's the unique command that contains an object different from string and integer
     * Contains each player's view of the board.
     */

    private String privateObjectiveCard;
    private ArrayList<String> publicObjectiveCards;
    private ArrayList<String> publicObjectiveDescription;
    private ArrayList<Integer> tokensPublicObjective; //Ordered based on poc order

    private ArrayList<String> toolCards;
    private ArrayList<String> toolCardDescription;
    private ArrayList<Integer> tokensToolCards; //Ordered

    private ArrayList<String> draftpool; //Dice in the format: colorNumber/empty
    private ArrayList<String> roundTrack;

    private ArrayList<String> personalWpc; //""
    private Integer personalTokens;
    private String username;

    private ArrayList<String> otherPlayersWpcs; //Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private ArrayList<Integer> otherPlayersTokens;
    private ArrayList<Integer> otherPlayersUsernames;

    public RefreshBoardCommand(String model){
        this.message = model;
    }

    public RefreshBoardCommand(String privateObjectiveCard, ArrayList<String> publicObjectiveCards, ArrayList<String> publicObjectiveDescription, ArrayList<Integer> tokensPublicObjective, ArrayList<String> toolCards, ArrayList<String> toolCardDescription, ArrayList<Integer> tokensToolCards, ArrayList<String> draftpool, ArrayList<String> roundTrack, ArrayList<String> personalWpc, Integer personalTokens, String username, ArrayList<String> otherPlayersWpcs, ArrayList<Integer> otherPlayersTokens, ArrayList<Integer> otherPlayersUsernames) {
        this.privateObjectiveCard = privateObjectiveCard;
        this.publicObjectiveCards = publicObjectiveCards;
        this.publicObjectiveDescription = publicObjectiveDescription;
        this.tokensPublicObjective = tokensPublicObjective;
        this.toolCards = toolCards;
        this.toolCardDescription = toolCardDescription;
        this.tokensToolCards = tokensToolCards;
        this.draftpool = draftpool;
        this.roundTrack = roundTrack;
        this.personalWpc = personalWpc;
        this.personalTokens = personalTokens;
        this.username = username;
        this.otherPlayersWpcs = otherPlayersWpcs;
        this.otherPlayersTokens = otherPlayersTokens;
        this.otherPlayersUsernames = otherPlayersUsernames;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }

}
