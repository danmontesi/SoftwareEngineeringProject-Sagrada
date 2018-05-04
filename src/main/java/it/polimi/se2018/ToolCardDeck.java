package it.polimi.se2018;

import it.polimi.se2018.toolcards.ToolCard;

import java.util.ArrayList;

public class ToolCardDeck {
    private ArrayList<ToolCard> cards;


    //TODO: ATTENZIONE!! CONTROLLARE EmptyDeckException !!
    //anche se forse non è strettamente necessario
    public ToolCard extractCard(){
        return cards.remove(0);
    }
}
