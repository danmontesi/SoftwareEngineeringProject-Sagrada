package it.polimi.se2018.toolcards;

import it.polimi.se2018.Player;

/**
 * Describes ToolCards and their behavior.
 * @author Daniele Montesi
 */
public abstract class ToolCard {
    private String name;
    private String description;
    private int tokenCount;

    public int getTokenCount(){
        return tokenCount;
    }

    public void increaseTokens(){
        tokenCount +=1;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
