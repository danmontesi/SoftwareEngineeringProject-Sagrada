package it.polimi.se2018.view.cli;

import it.polimi.se2018.exceptions.TimeUpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Every method can throw a TimeUpException when a Timeout occurs from server
 */
public class InputReader{
    private BufferedReader br;
    boolean timeout = false;

    public InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Return null if an IOException is caught
     */
    public String readLine(){
        try{
            while(!br.ready()){
                if(timeout){
                    timeout = false;
                    System.out.println("\nTime's up!");
                    throw new TimeUpException("Time's up for your turn");
                }
            }
            String input = br.readLine();
            return input;
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
    public int readInt(int validInferior, int validSuperior){
        while(true){
            String inputString = readLine();
            if (inputString.matches("\\d")) {
                int input = Integer.parseInt(inputString);
                if ((input < validInferior) || (input > validSuperior)) {
                    System.out.println("Input not compliant to rules");
                } else {
                    return input;
                }
            } else {
                System.out.println("Input is not a number, retry");
            }
        }
    }

    public void setTimeOut() {
        timeout = true;
    }
}
