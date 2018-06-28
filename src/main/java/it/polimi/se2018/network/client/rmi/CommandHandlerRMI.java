package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.network.server.ServerConnection;

public class CommandHandlerRMI implements Runnable {
    @Override
    public void run() {

    }
/*
    public CommandHandlerRMI() {
    }

    @Override
    public void run() {
        while (true) {
            synchronized (RMIClient.getWaiter()) {
                try {
                    RMIClient.getWaiter().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!RMIClient.getCommandQueue().isEmpty()) {
                    System.out.println("Sono fuori");
                    System.out.println("Sono vivo");
                    //connection.getClientController().dispatchCommand(RMIClient.getCommandQueue().poll());
                }
            }

        }
    }
    */
}
