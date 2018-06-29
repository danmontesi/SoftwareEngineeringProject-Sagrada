package it.polimi.se2018.model;

import java.util.List;

/**
 * Describes ToolCards and their behavior.
 * @author Daniele Montesi
 */
public class ToolCard {
    private String name;
    private String description;
    private int tokenCount;
    private List<Action> actions;

    public ToolCard(String name, String description, List<Action> actions) {
        this.name = name;
        this.description = description;
        this.actions = actions;
    }

    //lo tengo solo perchè per ora per far compilare GameMatchTest
    public ToolCard(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getTokenCount(){
        return tokenCount;
    }

    public void increaseTokens(int toBeIncreased){
        tokenCount +=toBeIncreased;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Action> getActions() {
        return actions;
    }
}
