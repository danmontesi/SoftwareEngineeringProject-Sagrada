package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes ToolCards and their behavior.
 * @author Daniele Montesi
 */
public class ToolCard {
    private String name;
    private String description;
    private int tokenCount = 0;
    private final List<Action> actions;
    private final boolean reversible;

    /**
     * Constructor: generates a Tool Card with given name, description, list of actions and reversible flag
     * @param name card name
     * @param description card description
     * @param actions list of actions the card allows to perform
     * @param reversible flag that indicates whether the Tool Card use can be reversed
     */
    public ToolCard(String name, String description, List<Action> actions, boolean reversible) {
        this.name = name;
        this.description = description;
        this.actions = actions;
        this.reversible = reversible;
    }

    public int getTokenCount(){
        return tokenCount;
    }

    /**
     * Increases the tokens number
     * @param toBeIncreased the amount the tokens number will be increased by
     */
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
        List<Action> actionsCopy = new ArrayList<>();
        actionsCopy.addAll(actions);
        return actionsCopy;
    }

    public boolean isReversible() {
        return reversible;
    }
}
