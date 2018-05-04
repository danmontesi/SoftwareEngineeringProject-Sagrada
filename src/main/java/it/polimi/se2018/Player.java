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

    public int calcuateTotalScore(){
        return calculatePrivateObjectiveScore() - calculatePointPenalization() + tokens;
    }

    public int calculatePrivateObjectiveScore(){
        return privateObjectiveCard.calculateScore(windowPatternCard);
    }

    /** EDIT:
     * Spostato calculatePublicObjScore nel model, in quanto serve il model per sapere le carte pubbliche.
     * aggiunto calculatePenalizationScore()
     */

    public int calculatePointPenalization(){
        int pointsToDecrease=0;
        for (Cell c : this.windowPatternCard.getSchema()){
            pointsToDecrease += (c.getAssociatedDie()== null ? 1 : 0);
        }
        return pointsToDecrease;
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
