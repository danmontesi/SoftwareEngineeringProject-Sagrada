package it.polimi.se2018;

import java.util.ArrayList;

public class Player {
    private WindowPatternCard windowPatternCard;
    private PrivateObjectiveCard privateObjectiveCard;
    private String username;
    private int tokens;
    private int score;

    public int calcuateTotalScore(){

    }

    public int calculatePrivateObjectiveScore(){

    }

    public void calculatePublicObjectiveScore(ArrayList<PublicObjectiveCards> publicObjectiveCards){

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

    public int getScore(){
        return score;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjToBeSet){
        this.privateObjectiveCard = privateObjToBeSet;
    }

    public void setWindowPatternCard(WindowPatternCard patternToBeSet){
        this.windowPatternCard = patternToBeSet;
    }

    public void decreaseTokens(int toBeDecreased){

    }

    public Die chooseDie(DraftPool draftPool){

    }

    public void useTool(String usedToolID){

    }
}
