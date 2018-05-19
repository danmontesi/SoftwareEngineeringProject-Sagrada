package it.polimi.se2018;

import it.polimi.se2018.Exceptions.EmptyCellException;

public class PrivateObjectiveCard {

    private String description;
    private COLOR color;

    public int calculateScore(WindowPatternCard w){
        int score = 0;
        for (Cell c : w.getSchema()){
            try {
                score += (c.getAssociatedDie().getColor() == color? 1 : 0);
            } catch (EmptyCellException e) {
            }
        }
        return score;
    }

    public String getDescription(){
        return description;
    }

}
