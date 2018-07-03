package it.polimi.se2018.view.cli.cliState;


import java.util.List;

public class PlayerLight {
    private String username;
    private int tokens;
    private List<String> wpc;

    public PlayerLight() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username.replace("_", " ");
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
