package it.polimi.se2018.view.cli;

import it.polimi.se2018.exceptions.TimeUpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader{
    private BufferedReader br;
    boolean timeout = false;

    public InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine() throws IOException {
        return readLine(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    public String readLine(int validInferior, int validSuperior) throws IOException{

        while(!br.ready()){
            if(timeout){
                timeout = false;
                System.out.println("\nTime's up!");
                throw new TimeUpException("Time's up for your turn");
            }
        }
        String input = br.readLine();
        if (input.matches("/d")){
            int inputNumber = Integer.parseInt(input);
            if((inputNumber < validInferior) || (inputNumber > validSuperior)){
                throw new IllegalArgumentException("Input not compliant to rules");
            }
        }
        //TODO: e se l'input non è un numero? Bisogna ritornare un'eccezione
        return input;
    }

    public void setTimeOut() {
        timeout = true;
    }
}
