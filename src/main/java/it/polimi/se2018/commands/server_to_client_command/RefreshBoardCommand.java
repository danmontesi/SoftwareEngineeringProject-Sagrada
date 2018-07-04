package it.polimi.se2018.commands.server_to_client_command;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.utils.ControllerClientInterface;
import it.polimi.se2018.view.View;

import java.util.ArrayList;
import java.util.List;

public class RefreshBoardCommand extends ServerToClientCommand{
    /**
     * That's the unique command that contains an object different from string and integer
     * Contains each player's view of the board.
     */
    private String privateObjectiveCard;
    private String privateObjectiveCardDescription;
    private List<String> publicObjectiveCards = new ArrayList<>();
    private List<String> publicObjectiveDescription = new ArrayList<>();

    private List<String> toolCards = new ArrayList<>();
    private List<String> toolCardDescription = new ArrayList<>();
    private List<Integer> tokensToolCards; //Ordered

    private List<String> draftpool; //Dice in the format: colorNumber/empty
    private List<String> roundTrack; //Dice in the format: colorNumber/empty

    private List<String> personalWpc; //the first cell is the name of the card, then /Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private Integer personalTokens;
    private String username;

    private List<List<String>> otherPlayersWpcs; //Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private List<Integer> otherPlayersTokens;
    private List<String> otherPlayersUsernames = new ArrayList<>();

    public RefreshBoardCommand(Model model, View view){
        //set private objective (name and description)
        getMyPrivateObjective(model, view);
        //set every username
        getUsernames(model, view);
        //set public cards information
        getPublicCardsInfo(model);
        this.tokensToolCards = model.refreshToolCardTokens();
        this.draftpool = model.getDraftPool().draftpoolPathRepresentation();
        this.roundTrack = model.getRoundTrack().roundtrackPathRepresentation();
        List<List<String>> wpcs = model.refreshWPCs(view);
        List<Integer> tokens = model.refreshPlayerTokens(view);
        this.personalWpc = wpcs.remove(0);
        this.personalTokens = tokens.remove(0);
        this.otherPlayersWpcs = wpcs;
        this.otherPlayersTokens = tokens;
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

    public String getPrivateObjectiveCardDescription() {
        return privateObjectiveCardDescription;
    }

    private void getMyPrivateObjective(Model model, View view){
        for(Player player : model.getGamePlayers()){
            if(view.getUsername().equals(player.getUsername())){
                 privateObjectiveCard = player.getPrivateObjectiveCard().getName();
                 privateObjectiveCardDescription = player.getPrivateObjectiveCard().getDescription();
            }
        }
    }

    private void getUsernames(Model model, View view){
        for (Player player : model.getGamePlayers()){
            if(!view.getUsername().equals(player.getUsername())){
                otherPlayersUsernames.add(player.getUsername());
            } else {
                username = player.getUsername();
            }
        }
    }

    private void getPublicCardsInfo(Model model){
        for(int i = 0; i < model.getExtractedToolCard().size(); i++){
            toolCards.add(model.getExtractedToolCard().get(i).getName());
            toolCardDescription.add(model.getExtractedToolCard().get(i).getDescription());
        }

        for(int i = 0; i <model.getExtractedPublicObjectiveCard().size(); i++){
            publicObjectiveCards.add(model.getExtractedPublicObjectiveCard().get(i).getName());
            publicObjectiveDescription.add(model.getExtractedPublicObjectiveCard().get(i).getDescription());
        }
    }

}
