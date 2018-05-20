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

    public int calculateScore(WindowPatternCard w){
        return 0;
    }
}


