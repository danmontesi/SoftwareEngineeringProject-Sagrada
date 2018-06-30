package it.polimi.se2018.view.cli;

import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.exceptions.NoSuchColorException;
import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.view.cli.cliState.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class CLIPrinter {

    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";

    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    CLIPrinter() {
    }

    public synchronized void printYourTurn(INPUT_STATE state, boolean placeDieAllowed, boolean toolcardAllowed){
        if (state.equals(INPUT_STATE.YOUR_TURN)){
            System.out.println("\nWhat do you want to do?");
            System.out.println(placeDieAllowed ? "d - Place die" : "");
            System.out.println(toolcardAllowed ? "t - Use Tool" : "");
            System.out.println("p - Pass Turn");
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printSyntheticBoard(CliState cliState, boolean clearScreen){
        if (clearScreen){
            clearScreen();
        }
        System.out.println("Round Track:\n");
        printInlineList(cliState.getRoundTrack());
        System.out.println("Draft Pool:\n");
        printInlineList(cliState.getDraftpool());
        PlayerLight me = cliState.getPlayer(0);
        System.out.println(me.getUsername() + " - Tokens: " + me.getTokens());
        printWPC(me.getWpc());
    }

    public synchronized void printSyntheticBoard(CliState cliState){
        printSyntheticBoard(cliState,true);
    }

    public void printToolcards(CliState cliState){
        System.out.println("Toolcards:");
        for(int i = 0; i < cliState.getToolcards().size(); i++){
            ToolcardLight card = cliState.getToolcards().get(i);
            System.out.println(String.format("%d) %s - Tokens: %d", i+1, card.getToolcardName(), card.getTokens()));
            System.out.println("\t" + card.getDescription());
        }
    }

    public void printPublicObjectiveCards(CliState cliState){
        System.out.println("Public Objective Cards:");
        for(int i = 0; i < cliState.getPublicObjectiveCards().size(); i++){
            PublicObjectiveLight card = cliState.getPublicObjectiveCards().get(i);
            System.out.println(card.getName()+ "\n\t" + card.getDescription());
        }
    }

    public synchronized void printCompleteBoard(CliState cliState){
        clearScreen();
        printPublicObjectiveCards(cliState);
        printToolcards(cliState);

        for (int i = 1; i < cliState.getAllPlayers().size(); i++){
            PlayerLight player = cliState.getPlayer(i);
            System.out.println(player.getUsername() + " - Tokens: " + player.getTokens());
            printWPC(player.getWpc());
            System.out.println("\n");
        }
        printSyntheticBoard(cliState,false);
    }

    /**
     * Used only to print DraftPool and RoundTrack
     */
    void printInlineList(List<String> list){
        String[][] table = new String[5][list.size()+1];
        addWPCBorders(table);
        for (int i = 0; i < list.size(); i++){
            insertStringInTable(table, 0, i, list.get(i));
        }
        printTable(table);
        System.out.println("\n");
    }

    void printWPC(List<String> stringWpc){
        String[][] table = new String[17][6];
        addWPCBorders(table);
        System.out.println(stringWpc.get(0) + "\n");

        for (int i = 1; i < stringWpc.size(); i++){
            int j = i-1;
            int row = j/5;
            int column = j%5;
            //ma siamo sicuri che non ci vada table =...??
            insertStringInTable(table, row, column, stringWpc.get(i));
        }
        printTable(table);
        System.out.println("\n");
    }

    private void addWPCBorders(String[][] table){
        for(int i = 0; i < table.length; i++){
            if(i%4 != 0){
                table[i][0] = "|";
            } else {
                table[i][0] = "-";
            }
        }
        for(int i = 1; i < table[0].length; i++){
            table[0][i] = "--------";
        }
    }

    void printWPC(WindowPatternCard wpc){
        String[][] table = new String[17][6];
        addWPCBorders(table);
        System.out.println(wpc.getCardName() + " - " + wpc.getDifficulty() + "\n");

        for(int i = 0; i < wpc.getSchema().size(); i++){
            int row = wpc.getCell(i).getRow();
            int column = wpc.getCell(i).getColumn();
            Cell cell = wpc.getCell(i);
            try {
                Die die = cell.getAssociatedDie();
                insertDieValue(table, row, column, die.getValue(), die.getColor());
            } catch (EmptyCellException e) {
                if (cell.getColorConstraint() != null){
                    insertDieValue(table, row, column, cell.getColorConstraint());
                }
                else if (cell.getValueConstraint() != null){
                    insertDieValue(table, row, column, cell.getValueConstraint());
                } else {
                    insertDieValue(table, row, column);
                }
            }
        }
        printTable(table);
        System.out.println("\n");
    }

    private String[][] insertStringInTable(String[][] table, int row, int column, String string){
        String[] die = string.split("_");
        if(die[0].equals("empty")){
            insertDieValue(table, row, column);
        } else {
            if(die.length == 2){
                try {
                    insertDieValue(table, row, column , Integer.parseInt(die[1]), COLOR.stringToColor(die[0]));
                } catch (NoSuchColorException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            } else {
                try{
                    insertDieValue(table, row, column , COLOR.stringToColor(die[0]));
                } catch (NoSuchColorException e){
                    insertDieValue(table, row, column, Integer.parseInt(die[0]));
                }
            }
        }
        return table;
    }

    private void insertDieValue(String[][] table, int row, int column, int value, COLOR color){
        String background = stringColorBackground(color);
        String[] die = stringDieValue(value);
        addStringToMatrix(table, row, column, background, die, true);
    }

    private void insertDieValue(String[][] table, int row, int column, int value){
        String[] die = stringDieValue(value);
        addStringToMatrix(table, row, column, ANSI_RESET, die);
    }

    private void insertDieValue(String[][] table, int row, int column, COLOR color){
        String background = stringColorBackground(color);
        String[] die = stringDieValue(0);
        addStringToMatrix(table, row, column, background, die);
    }

    private void insertDieValue(String[][] table, int row, int column){
        String[] die = stringDieValue(0);
        addStringToMatrix(table, row, column, ANSI_RESET, die);
    }

    private String stringColorBackground(COLOR color){
        String background;
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
        return background;
    }

    private String[] stringDieValue(int value){
        String[] die = new String[3];

        switch (value){
            case 1:
                die[0] = "       ";
                die[1] = "   o   ";
                die[2] = "       ";
                break;
            case 2:
                die[0] = "     o ";
                die[1] = "       ";
                die[2] = " o     ";
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
            default:
                die[0] = "       ";
                die[1] = "       ";
                die[2] = "       ";
        }
        return die;
    }

    //sinceramente dovrebbe stampare i pallini neri sui dadi colorati: in realtà sono bianchi ma hanno abbastanza constrasto
    private void addStringToMatrix(String[][] table, int row, int column, String background, String[] die, boolean printBlack){
        int i = row*4;
        i++;
        column++;
        for(int j = 0; j < 3; j++){
            table[i+j][column]= background;
            if(printBlack){
                table[i+j][column]+= ANSI_BLACK;
            }
            table[i+j][column]+= die[j];
            table[i+j][column]+= ANSI_RESET;
            table[i+j][column]+= "|";
        }
        table[i+3][column] = "--------";
    }

    private void addStringToMatrix(String[][] table, int row, int column, String background, String[] die) {
        addStringToMatrix(table, row, column, background, die, false);
    }

    private void printTable(String[][] table){
        StringBuilder line;
        for (int i = 0; i < table.length; i++){
            line = new StringBuilder();
            for(int j = 0; j < table[i].length; j++){
                line.append(table[i][j]);
            }
            System.out.println(line);
        }
    }

}
