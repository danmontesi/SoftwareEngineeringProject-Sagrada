package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.utils.ControllerClientInterface;

import java.util.ArrayList;

public class RefreshBoardCommand extends ServerToClientCommand{
    /**
     * That's the unique command that contains an object different from string and integer
     * Contains each player's view of the board.
     */

    //TODO devono essere final? o basta che non abbiano setter? questo comando mi arriverà fino al client

    private String privateObjectiveCard;

    public String getPrivateObjectiveCardDescription() {
        return privateObjectiveCardDescription;
    }

    private String privateObjectiveCardDescription;
    private ArrayList<String> publicObjectiveCards;
    private ArrayList<String> publicObjectiveDescription;

    private ArrayList<String> toolCards;
    private ArrayList<String> toolCardDescription;
    private ArrayList<Integer> tokensToolCards; //Ordered

    private ArrayList<String> draftpool; //Dice in the format: colorNumber/empty
    private ArrayList<String> roundTrack; //Dice in the format: colorNumber/empty

    private ArrayList<String> personalWpc; //the first cell is the name of the card, then /Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private Integer personalTokens;
    private String username;

    private ArrayList<ArrayList<String>> otherPlayersWpcs; //Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private ArrayList<Integer> otherPlayersTokens;
    private ArrayList<String> otherPlayersUsernames;

    public RefreshBoardCommand(String model){
        this.message = model;
    }

    public RefreshBoardCommand(String privateObjectiveCard, String privateObjectiveCardDescription, ArrayList<String> publicObjectiveCards, ArrayList<String> publicObjectiveDescription, ArrayList<String> toolCards, ArrayList<String> toolCardDescription, ArrayList<Integer> tokensToolCards, ArrayList<String> draftpool, ArrayList<String> roundTrack, ArrayList<String> personalWpc, Integer personalTokens, String username, ArrayList<ArrayList<String>> otherPlayersWpcs, ArrayList<Integer> otherPlayersTokens, ArrayList<String> otherPlayersUsernames) {
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

    public ArrayList<String> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public ArrayList<String> getPublicObjectiveDescription() {
        return publicObjectiveDescription;
    }

    public ArrayList<String> getToolCards() {
        return toolCards;
    }

    public ArrayList<String> getToolCardDescription() {
        return toolCardDescription;
    }

    public ArrayList<Integer> getTokensToolCards() {
        return tokensToolCards;
    }

    public ArrayList<String> getDraftpool() {
        return draftpool;
    }

    public ArrayList<String> getRoundTrack() {
        return roundTrack;
    }

    public ArrayList<String> getPersonalWpc() {
        return personalWpc;
    }

    public Integer getPersonalTokens() {
        return personalTokens;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<ArrayList<String>> getOtherPlayersWpcs() {
        return otherPlayersWpcs;
    }

    public ArrayList<Integer> getOtherPlayersTokens() {
        return otherPlayersTokens;
    }

    public ArrayList<String> getOtherPlayersUsernames() {
        return otherPlayersUsernames;
    }
}
