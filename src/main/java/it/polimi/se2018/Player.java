package it.polimi.se2018;

public class Player {
    private WindowPatternCard windowPatternCard;
    private PrivateObjectiveCard privateObjectiveCard;
    private String username;
    private int tokens;


    public Player(String username) {
        this.windowPatternCard = null;
        this.username = username;
        this.tokens = 0;
    }

    public WindowPatternCard getWindowPatternCard(){
        return windowPatternCard;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard(){
        return privateObjectiveCard;
    }

    public String getUsername(){
        return username;
    }

    public int getTokens(){
        return tokens;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjToBeSet){
        this.privateObjectiveCard = privateObjToBeSet;
    }

    public void setWindowPatternCard(WindowPatternCard patternToBeSet){
        this.windowPatternCard = patternToBeSet;
        this.tokens = this.windowPatternCard.getDifficulty();
    }

    public void decreaseTokens(int toBeDecreased){
        tokens -= toBeDecreased;
    }
}
