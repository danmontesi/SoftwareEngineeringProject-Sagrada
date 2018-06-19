package it.polimi.se2018.view.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class InputReader {

    private boolean timeOut;
    private InputStreamReader isr;
    private BufferedReader reader;


    public InputReader() {
        isr = new InputStreamReader(System.in);
        reader = new BufferedReader(isr);
        timeOut=false;
    }

    public String readLine() throws IOException, TimeoutException {
        System.out.println("Dentro readLine");
        timeOut = false; //TODO se lo tolgo il primo turno va avanti perchè non riporto timeOut a false, capire perchè e risolverlo
        while (!reader.ready()) {
            if (timeOut) {
                System.out.println("Timeout: passing turn automatically");
                //setTimeOut(false); //TODO decommenta e elimnina il timeout a linea 23
                throw new TimeoutException();
            }

            // optional delay between polling
            try {
                Thread.sleep(500);
            } catch (Exception ignore) {
            }
        }

        return reader.readLine();
    }

    public void setTimeOut(boolean value){
        timeOut=value;
    }

}
