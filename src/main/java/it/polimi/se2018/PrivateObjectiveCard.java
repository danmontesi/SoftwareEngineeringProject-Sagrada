package it.polimi.se2018;

public class PrivateObjectiveCard {

    private String description;
    private COLOR color;

    public int calculateScore(WindowPatternCard w){
        int score = 0;
        for (Cell c : w.getSchema()){
            score += (c.getAssociatedDie().get().getColor() == color? 1 : 0);
        }
        return score;
    }

    public String getDescription(){
        return description;
    }

}
