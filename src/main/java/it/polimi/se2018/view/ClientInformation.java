package it.polimi.se2018.view;

public class ClientInformation {
    private static String Username;
    private static String wpc;
    private static String privateOC;
    private Integer tokensNumber;

    public static String getUsername() {
        return Username;
    }

    public static String getWpc() {
        return wpc;
    }

    public Integer getTokensNumber() {
        return tokensNumber;
    }

    public static String getPrivateOC() {
        return privateOC;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static void setPrivateOC(String privateOC) {
        ClientInformation.privateOC = privateOC;
    }

    public void setTokensNumber(Integer tokensNumber) {
        this.tokensNumber = tokensNumber;
    }

    public static void setWpc(String wpc) {
        ClientInformation.wpc = wpc;
    }
}
