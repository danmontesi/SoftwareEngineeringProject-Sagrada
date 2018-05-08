package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public abstract class PublicObjectiveCard { //Probably has to be an Interface for correcting binding methods

    private String description;
    private int score;
    private String name;

    public int calculateScore(WindowPatternCard w){
        return 0;
    }
}


