package it.polimi.se2018.client.CLI;

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
        while (!reader.ready()) {
            if (timeOut) {
                System.out.println("Timeout: passing turn automatically");
                setTimeOut(false);
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
