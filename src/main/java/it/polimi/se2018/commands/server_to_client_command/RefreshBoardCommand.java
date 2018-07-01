package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.List;

public class RefreshBoardCommand extends ServerToClientCommand{
    /**
     * That's the unique command that contains an object different from string and integer
     * Contains each player's view of the board.
     */
    private String privateObjectiveCard;

    public String getPrivateObjectiveCardDescription() {
        return privateObjectiveCardDescription;
    }

    private String privateObjectiveCardDescription;
    private List<String> publicObjectiveCards;
    private List<String> publicObjectiveDescription;

    private List<String> toolCards;
    private List<String> toolCardDescription;
    private List<Integer> tokensToolCards; //Ordered

    private List<String> draftpool; //Dice in the format: colorNumber/empty
    private List<String> roundTrack; //Dice in the format: colorNumber/empty

    private List<String> personalWpc; //the first cell is the name of the card, then /Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private Integer personalTokens;
    private String username;

    private List<List<String>> otherPlayersWpcs; //Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private List<Integer> otherPlayersTokens;
    private List<String> otherPlayersUsernames;

    public RefreshBoardCommand(String model){
        this.message = model;
    }

    public RefreshBoardCommand(String privateObjectiveCard, String privateObjectiveCardDescription, List<String> publicObjectiveCards, List<String> publicObjectiveDescription, List<String> toolCards, List<String> toolCardDescription, List<Integer> tokensToolCards, List<String> draftpool, List<String> roundTrack, List<String> personalWpc, Integer personalTokens, String username, List<List<String>> otherPlayersWpcs, List<Integer> otherPlayersTokens, List<String> otherPlayersUsernames) {
        this.privateObjectiveCard = privateObjectiveCard;
        this.publicObjectiveCards = publicObjectiveCards;
        this.publicObjectiveDescription = publicObjectiveDescription;
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
        this.privateObjectiveCardDescription = privateObjectiveCardDescription;
    }

    /**
     * Visitor methods, it calls the clientController to perform a move using dynamic binding
     * @param clientController the parameters who calls the dynamic method
     */
    public void visit(ControllerClientInterface clientController) {
        clientController.applyCommand(this);
    }


    public String getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public List<String> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public List<String> getPublicObjectiveDescription() {
        return publicObjectiveDescription;
    }

    public List<String> getToolCards() {
        return toolCards;
    }

    public List<String> getToolCardDescription() {
        return toolCardDescription;
    }

    public List<Integer> getTokensToolCards() {
        return tokensToolCards;
    }

    public List<String> getDraftpool() {
        return draftpool;
    }

    public List<String> getRoundTrack() {
        return roundTrack;
    }

    public List<String> getPersonalWpc() {
        return personalWpc;
    }

    public Integer getPersonalTokens() {
        return personalTokens;
    }

    public String getUsername() {
        return username;
    }

    public List<List<String>> getOtherPlayersWpcs() {
        return otherPlayersWpcs;
    }

    public List<Integer> getOtherPlayersTokens() {
        return otherPlayersTokens;
    }

    public List<String> getOtherPlayersUsernames() {
        return otherPlayersUsernames;
    }
}
