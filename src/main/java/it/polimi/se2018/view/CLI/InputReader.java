package it.polimi.se2018.view.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputReader {

    private boolean timeOut;
    private InputStreamReader isr;
    private BufferedReader reader;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());


    public InputReader() {
        isr = new InputStreamReader(System.in);
        reader = new BufferedReader(isr);
        timeOut = false;
    }

    public String readLine() throws IOException, TimeoutException {
        timeOut = false;
        while (!reader.ready()) {
            if (timeOut) {
                throw new TimeoutException();
            }
            // delay checking timeout
            try {
                Thread.sleep(500);
            } catch (Exception ignore) {

            }
        }

        return reader.readLine();
    }

    public String readLine(int minInput, int maxInput) throws TimeoutException {
        timeOut = false;
        try {
            while (!reader.ready()) {
                if (timeOut) {
                    throw new TimeoutException();
                }
                // delay checking timeout
                try {
                    Thread.sleep(500);
                } catch (Exception ignore) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integer choice = -1;
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Invalid input: has to be an integer between " + minInput + " and " + maxInput);
            try {
                return readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (!(choice <= maxInput && choice >= minInput)) {
            System.out.println("Invalid input: has to be an integer between " + minInput + " and " + maxInput);
            try {
                return readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setTimeOut(boolean value){
        timeOut=value;
    }

}
