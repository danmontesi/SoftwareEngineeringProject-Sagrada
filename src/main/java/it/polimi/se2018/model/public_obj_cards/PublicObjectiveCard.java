package it.polimi.se2018.model.public_obj_cards;

import it.polimi.se2018.model.WindowPatternCard;

/**
 * Describes PublicObjectiveCards and their behavior. Calculates score depending on WindowPatternCard.
 * @author Alessio Molinari
 */
public abstract class PublicObjectiveCard { //Probably has to be an Interface for correcting binding methods

    protected String name;
    protected String description;
    protected Integer score;

    public PublicObjectiveCard(String name, String description, Integer score) {
        this.name = name;
        this.description = description;
        this.score = score;
    }

    public PublicObjectiveCard(){

    }
    /**
     * Calculates the score for a given WindowPatternCard.
     * That is connected to the points assigned for the PublicObjectiveCard, that varies depending from the type of Card
     * The method is called from the Controller during the final score calculation
     * @param w given WindowPatternCard
     * @return associated score
     */
    public int calculateScore(WindowPatternCard w){
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getScore() {
        return score;
    }
}

