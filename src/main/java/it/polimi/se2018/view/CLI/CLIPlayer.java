package it.polimi.se2018.view.CLI;

import it.polimi.se2018.model.WindowPatternCard;

public class CLIPlayer {
    private String username;
    private int tokens;
    private WindowPatternCard wpc;

    public CLIPlayer() {
    }

    public CLIPlayer(String username, int tokens, WindowPatternCard wpc) {
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

    public WindowPatternCard getWpc() {
        return wpc;
    }

    public void setWpc(WindowPatternCard wpc) {
        this.wpc = wpc;
    }
}
