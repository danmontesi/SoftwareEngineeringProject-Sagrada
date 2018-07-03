package it.polimi.se2018.view.cli;

import it.polimi.se2018.exceptions.TimeUpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages CLI input reading
 * Every method can throw a TimeUpException when a Timeout occurs from server
 * @author Alessio Molinari
 */
public class InputReader{
    private BufferedReader br;
    private boolean timeout = false;

    InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String readLine(){
       return readLine(false);
    }

    /**
     * Reads an input line
     * Return null if an IOException is caught
     */
    private String readLine(boolean throwException){
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
     * Reads a number and checks its validity
     * Return null if an IOException is caught
     */
    private int readInt(int validInferior, int validSuperior, boolean throwException){
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

    /**
     * Reads a number and checks its validity
     */
    private int readInt(int validInferior, int validSuperior){
        return readInt(validInferior, validSuperior, false);
    }

    void setTimeOut() {
        timeout = true;
    }

}
