package it.polimi.se2018.view;

import java.util.ArrayList;

public class ExampleBoardStringPaths{

    private String privateObjectiveCard;
    private ArrayList<String> publicObjectiveCards;

    private ArrayList<String> toolCards;
    private ArrayList<Integer> tokensToolCards; //Ordered

    private ArrayList<String> draftpool; //Dice in the format: colorNumber/empty
    private ArrayList<String> roundTrack;

    private ArrayList<String> personalWpc; //""
    private Integer personalTokens;
    private String username;

    private ArrayList<ArrayList<String>> otherPlayersWpcs; //Dice in the format colorNumber/empty or restrictionColor or restrictionValue
    private ArrayList<Integer> otherPlayersTokens;
    private ArrayList<String> otherPlayersUsernames;

    public ExampleBoardStringPaths() {
        this.privateObjectiveCard = "shades_of_yellow";

        this.publicObjectiveCards = new ArrayList<>();
        publicObjectiveCards.add("deep_shade");
        publicObjectiveCards.add("light_shade");
        publicObjectiveCards.add("medium_shade");

        this.toolCards = new ArrayList<>();
        toolCards.add("circular_cutter");
        toolCards.add("copper_foil_reamer");
        toolCards.add("cork_line");

        this.tokensToolCards = new ArrayList<>();
        tokensToolCards.add(2);
        tokensToolCards.add(0);
        tokensToolCards.add(20);

        //Always same size of the (number of players)*2 + 1
        this.draftpool = new ArrayList<>();
        draftpool.add("empty");
        draftpool.add("yellow2");
        draftpool.add("yellow2");
        draftpool.add("violet6");
        draftpool.add("empty");
        draftpool.add("yellow1");
        draftpool.add("red1");

        //always size = 10
        this.roundTrack = new ArrayList<>();
        roundTrack.add("red2");
        roundTrack.add("empty");
        roundTrack.add("empty");
        roundTrack.add("empty");
        roundTrack.add("empty");
        roundTrack.add("empty");
        roundTrack.add("empty");
        roundTrack.add("empty");
        roundTrack.add("empty");
        roundTrack.add("empty");

        //the first is the name of the wpc
        this.personalWpc = new ArrayList<>();
        //the first is the name of the wpc
        personalWpc.add("aurora_sagradis");

        //then, the dice in the format colorNum or empty
        personalWpc.add("red2");
        personalWpc.add("yellow5");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");
        personalWpc.add("empty");

        this.personalTokens = 4;

        this.username = "Alessio";

        //in this game we have 3 players tot
        this.otherPlayersWpcs = new ArrayList<>();
        //for taking it easy, the wpc will be the same for all players...
        this.otherPlayersWpcs.add(personalWpc);
        this.otherPlayersWpcs.add(personalWpc);

        this.otherPlayersTokens = new ArrayList<>();
        otherPlayersTokens.add(2);
        otherPlayersTokens.add(4);

        this.otherPlayersUsernames = new ArrayList<>();
        otherPlayersUsernames.add("Daniele");
        otherPlayersUsernames.add("Nives");
    }

    public String getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public ArrayList<String> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public ArrayList<String> getToolCards() {
        return toolCards;
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
