package it.polimi.se2018.network.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    private static RMIServer instance = null;

    private RMIServer() {
    }

    ;

    /**
     * Singleton
     *
     * @return unique instance of RMIServer
     */
    public static RMIServer getInstance() {
        if (instance == null) {
            instance = new RMIServer();
            return instance;
        } else {
            return instance;
        }
    }

    //che numero di porta bisogna usare?
    //va  bene usare lo stesso per socket e RMI?
    public void startRMIServer() {
        try {
            Communicator communicator = new Communicator();
            //default port 1099 as specified in LocateRegistry javaDoc
            //it is possible to change that port passing an int argument to getRegistry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("communicator", communicator);
        } catch (RemoteException e) {
            System.out.println("RMI setup error");
        } catch (AlreadyBoundException e) {
            System.out.println("RMI: alreadyBoundException");
        }
    }

}

