package it.polimi.se2018.view.CLI;

class ToolcardPair {
    private String toolcardName;
    private Integer tokens;

    public ToolcardPair(String toolcardName, Integer tokens) {
        this.toolcardName = toolcardName;
        this.tokens = tokens;
    }

    public String getToolcardName() {
        return toolcardName;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }
}
