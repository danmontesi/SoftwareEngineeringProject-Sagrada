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
    private final List<Action> actions;
    private final boolean reversible;

    public ToolCard(String name, String description, List<Action> actions, boolean reversible) {
        this.name = name;
        this.description = description;
        this.actions = actions;
        this.reversible = reversible;
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
