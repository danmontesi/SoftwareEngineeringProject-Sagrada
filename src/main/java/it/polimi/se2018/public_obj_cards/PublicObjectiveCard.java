package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.WindowPatternCard;

public abstract class PublicObjectiveCard { //Probably has to be an Interface for correcting binding methods

    private String name;
    private String description;
    private int score;

    public PublicObjectiveCard(String name, String description, int score) {
        this.name = name;
        this.description = description;
        this.score = score;
    }

    /**
     * Calculates the score for a WindowPatternCard given.
     * That is connected to the points assigned for the PublicObjectiveCard, that varies depending from the type of Card
     * The method is called from the Controller during the final score calculation
     * @param w
     * @return
     */
    public int calculateScore(WindowPatternCard w){
        return 0;
    }
}


