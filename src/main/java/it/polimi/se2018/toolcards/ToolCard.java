package it.polimi.se2018.toolcards;

import it.polimi.se2018.Player;

public abstract class ToolCard {
    private String description;
    private int tokenCounts;

    public int getTokenCounts(){
        return tokenCounts;
    }

    public void increaseTokens(){
        tokenCounts +=1;
    }

    public abstract void useToolCard(Player player);
}
