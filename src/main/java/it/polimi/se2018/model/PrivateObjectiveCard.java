package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.EmptyCellException;

/**
 * Describes PrivateObjectiveCards behavior. Calculates score depending on WindowPatternCard.
 * @author Alessio Molinari
 */
public class PrivateObjectiveCard {

    private String name;
    private String description;
    private COLOR color;

    /**
     * Constructor: generates a Private Objective Card with given name, description and color
     * @param name card name
     * @param description card description
     * @param color card color
     */
    public PrivateObjectiveCard(String name, String description, COLOR color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    /**
     * Calculates the score relative to the private objective
     * @param w considered Window Pattern Card
     * @return calculated score
     */
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
