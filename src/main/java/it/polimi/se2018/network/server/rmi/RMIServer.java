package it.polimi.se2018.network.server.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    private static int port = 1099;



    public void RMIStartListening(){

        System.setProperty("java.rmi.server.hostname","127.0.0.1");

        try {
            Registry registry = LocateRegistry.createRegistry(port);
            RMIServerImplementation serverImplementation = new RMIServerImplementation();
            registry.rebind("RMIImplementation", serverImplementation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
