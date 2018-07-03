package it.polimi.se2018.view.cli.cliState;

public class ToolcardLight {
    private String toolcardName;
    private String description;
    private Integer tokens;

    ToolcardLight() {
    }

    public String getToolcardName() {
        return toolcardName;
    }

    public void setToolcardName(String toolcardName) {
        this.toolcardName = toolcardName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }
}
