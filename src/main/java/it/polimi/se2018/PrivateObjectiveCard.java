package it.polimi.se2018;

import it.polimi.se2018.exceptions.EmptyCellException;

/**
 * Describes PrivateObjectiveCards behavior. Calculates score depending on WindowPatternCard.
 * @author Alessio Molinari
 */
public class PrivateObjectiveCard {

    private String name;
    private String description;
    private COLOR color;

    public PrivateObjectiveCard(String name, String description, COLOR color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

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

    public String getName() {
        return name;
    }
}
