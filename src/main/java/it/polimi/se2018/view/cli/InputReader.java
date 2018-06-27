package it.polimi.se2018.view.cli;

import it.polimi.se2018.exceptions.TimeUpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Every method can throw a TimeUpException when a Timeout occurs from server
 */
public class InputReader{
    private BufferedReader br;
    private boolean timeout = false;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine(){
       return readLine(false);
    }

    /**
     * Return null if an IOException is caught
     */
    public String readLine(boolean throwException){
        try{
            while(!br.ready()){
                if(timeout){
                    timeout = false;
                    System.out.println("\nTime's up!");
                    if(throwException){
                        throw new TimeUpException("Time's up!");
                    }
                }
            }
            return br.readLine();
        } catch (IOException e){
            return null;
        }
    }

    /**
     * Return null if an IOException is caught
     */
    public int readInt(){
        return readInt(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    /**
     * Return null if an IOException is caught
     */
    public int readInt(int validInferior, int validSuperior, boolean throwException){
        while (true) {
            String inputString = readLine(throwException);
            try {
                int input = Integer.parseInt(inputString);
                if ((input < validInferior) || (input > validSuperior)) {
                    System.out.println("Input not compliant to rules");
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number, retry");
            }
        }
    }

    public int readInt(int validInferior, int validSuperior){
        return readInt(validInferior, validSuperior, false);
    }

        public void setTimeOut() {
        timeout = true;
    }

    public void close() {
        try {
            br.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
