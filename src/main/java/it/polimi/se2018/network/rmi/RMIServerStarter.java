package it.polimi.se2018.network.rmi;

public class RMIServerStarter implements Runnable {
    RMIServer rmiServer;

    public RMIServerStarter() {
        rmiServer = RMIServer.getInstance();
    }

    @Override
    public void run() {
        rmiServer.startRMIServer();
    }
}
