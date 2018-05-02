package it.polimi.se2018.network;

public class SocketConnection implements ClientConnection{
    /**
     * This class represents the connection itself.
     * it:
     *  - Hear commands from Client (ClientToServerCommand)
     *    by the method (thread) run(), that is like: see down

     *  - hear commands from Server (ServerToClientCommand)
     *    by a method sendCommand()
     *
     */
}

/*****  Run() {
 ClientToServerCommand command;

 while (true) {
 command = null;
 try {
 command = (ClientToServerCommand) inSocket.readObject();

 if (command instanceof ReconnectionAnswerCommand) {
 serverListener.notifyReconnectionAnswer((ReconnectionAnswerCommand) command, this);
 }

 //				System.out.println(command.getClass().getName());

 } catch (ClassNotFoundException | IOException e) {
 close();
 break;
 }
 if (command instanceof RequestClosureCommand)
 closedByClient();

 // commands that can be sent in an asy
 ....*/


/*
 sendCommand(ServerToClientCommand command) throws IOException {
 outSocket.writeUnshared(command);
 outSocket.flush();
 outSocket.reset();
 */