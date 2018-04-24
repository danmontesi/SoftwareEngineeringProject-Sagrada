package it.polimi.se2018;

import it.polimi.se2018.toolcards.ToolCard;

import java.util.ArrayList;

public class ToolCardDeck {
    private ArrayList<ToolCard> cards;


    //ATTENZIONE!! CONTROLLARE EmptyDeckException !!!!
    public ToolCard extractCard(){
        return cards.remove(0);
    }

}
