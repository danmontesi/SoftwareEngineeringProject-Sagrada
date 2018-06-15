package it.polimi.se2018.model;

/**
 * Describes ToolCards and their behavior.
 * @author Daniele Montesi
 */
public class ToolCard {
    private String name;
    private String description;
    private int tokenCount;

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
}
