package it.polimi.se2018.view.CLI;


import java.util.List;

public class CLIPlayer {
    private String username;
    private int tokens;
    private List<String> wpc;

    public CLIPlayer() {
    }

    public CLIPlayer(String username, int tokens, List<String> wpc) {
        this.username = username;
        this.tokens = tokens;
        this.wpc = wpc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public List<String> getWpc() {
        return wpc;
    }

    public void setWpc(List<String> wpc) {
        this.wpc = wpc;
    }
}
