package server.model;

/**
 * Describes Player behavior. Token number can be decreased when they are used. Beside this and the constructor
 * the class only contains getters and setters.
 * @author Daniele Montesi
 */
public class Player {
    private WindowPatternCard windowPatternCard;
    private PrivateObjectiveCard privateObjectiveCard;
    private String username;
    private int tokens;

    /**
     * Constructor: generates a player with a username, a WindowPatternCard (to be set)
     * and a corresponding amount of tokens
     * @param username player username
     */
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

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard){
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public void setWindowPatternCard(WindowPatternCard windowPatternCard){
        this.windowPatternCard = windowPatternCard;
        this.tokens = this.windowPatternCard.getDifficulty();
    }

    /**
     * Decreases the number of the player tokens
     * @param toBeDecreased to be decreased number of tokens
     */
    public void decreaseTokens(int toBeDecreased){
        tokens -= toBeDecreased;
    }
}
