package it.polimi.se2018.MatchTest.viewTest;

import it.polimi.se2018.model.COLOR;
import org.junit.Test;

public class CliTest {
    String table[][] = new String[17][5];

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Test
    public void provaTable(){
        insertDieValue(table, 0, 0, 3, COLOR.GREEN);
        insertDieValue(table, 0, 1, 1, COLOR.RED);
        insertDieValue(table, 0, 2, 4, COLOR.VIOLET);
        insertDieValue(table, 1, 0, 5, COLOR.BLUE);
        printTable(table);
    }

    private void insertDieValue(String table[][], int row, int column, int value, COLOR color){
        int i = row*4;
        String background;
        String reset = ANSI_RESET;
        switch(color){
            case YELLOW:
                background = ANSI_YELLOW_BACKGROUND;
                break;
            case GREEN:
                background = ANSI_GREEN_BACKGROUND;
                break;
            case BLUE:
                background = ANSI_BLUE_BACKGROUND;
                break;
            case VIOLET:
                background = ANSI_PURPLE_BACKGROUND;
                break;
            case RED:
                background = ANSI_RED_BACKGROUND;
                break;
            default:
                background = ANSI_RESET;
        }
        
        String die[] = new String[3];
        
        switch (value){
            case 1:
                die[0] = "       ";
                die[1] = "   o   ";
                die[2] = "       ";
                break;
            case 2:
                die[0] = "       ";
                die[0] = " o   o ";
                die[0] = "       ";
                break;
            case 3:
                die[0] = "     o ";
                die[1] = "   o   ";
                die[2] = " o     ";
                break;
            case 4:
                die[0] = " o   o ";
                die[1] = "       ";
                die[2] = " o   o ";
                break;
            case 5:
                die[0] = " o   o ";
                die[1] = "   o   ";
                die[2] = " o   o ";
                break;
            case 6:
                die[0] = " o   o ";
                die[1] = " o   o ";
                die[2] = " o   o ";
                break;
        }
        
        for(int j = 0; j < 3; j++){
            table[i+j][column]= background;
            table[i+j][column]+= die[j];
            table[i+j][column]+= reset;
            table[i+j][column]+= "|";
        }
        table[i+3][column] = "-------";
    }

    private void printTable(String table[][]){
        for (int i = 0; i < table.length; i++){
            String line = "";
            for(int j = 0; j < table[i].length; j++){
                line = line + table[i][j];
            }
            System.out.println(line);
//            System.out.print('\n');
        }
    }


}
