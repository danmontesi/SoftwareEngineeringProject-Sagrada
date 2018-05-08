package it.polimi.se2018;

import java.util.HashSet;

public abstract class PublicObjectiveCard { //Probably has to be an Interface for correcting binding methods

    private String description;
    private int score;
    private String name;

    public int calculateScore(WindowPatternCard w){
        return 0;
    }
}


class ColorDiagonals extends PublicObjectiveCard {
    public int calculateScore(WindowPatternCard w) {
        int countScore = 0;
        COLOR currentColor;
        for (int row = 0; row < 3; row++) { //eccetto l'ultima riga
            for (int col = 0; col < 5; col++) {
                // checking if there's a die in the current cell
                if (w.getSchema().get(row * 5 + col).getAssociatedDie() != null) {
                    currentColor = w.getSchema().get(row * 5 + col).getAssociatedDie().getColor();
                    if (col > 0) { //checking sud-ovest cell die color
                        if (w.getSchema().get((row + 1) * 5 + (col - 1)).getAssociatedDie() != null) {
                            if (w.getSchema().get((row + 1) * 5 + (col - 1)).getAssociatedDie().getColor() == currentColor) {
                                countScore++;
                            }
                        }
                    }
                    if (col < 4) { //checking sud-est cell die color
                        if (w.getSchema().get((row + 1) * 5 + (col + 1)).getAssociatedDie() != null) {
                            if (w.getSchema().get((row + 1) * 5 + (col + 1)).getAssociatedDie().getColor() == currentColor) {
                                countScore++;
                            }
                        }
                    }
                }
            }
        }
        return countScore;
    }
}

class RowColorVariety extends PublicObjectiveCard{
    private int score = 6;

    //what if a cell is empty?
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int i = 0; i < 4; i++){
            HashSet<COLOR> colors = new HashSet<>();
            for(int j = 0; j < 5; j++){
                colors.add(w.getCell(i, j).getAssociatedDie().getColor());
            }
            if(colors.size() == 5){
                total += score;
            }
        }
        return total;
    }
}

class ColumnColorVariety extends PublicObjectiveCard{
    private int score = 5;
    //what if a cell is empty?
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int j = 0; j < 5; j++){
            HashSet<COLOR> colors = new HashSet<>();
            for(int i = 0; i < 4; i++){
                colors.add(w.getCell(i, j).getAssociatedDie().getColor());
            }
            if(colors.size() == 4){
                total += score;
            }
        }
        return total;
    }
}

class ColumnShadeVariety extends PublicObjectiveCard{
    private int score = 4;
    //what if a cell is empty?
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int j = 0; j < 5; j++){
            HashSet<Integer> numbers = new HashSet<>();
            for(int i = 0; i < 4; i++){
                numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
            }
            if(numbers.size() == 4){
                total += score;
            }
        }
        return total;
    }
}

class ColorVariety extends PublicObjectiveCard{
    private int score = 4;
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        HashSet<COLOR> colors = new HashSet<>();
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                colors.add(w.getCell(i, j).getAssociatedDie().getColor());
            }
            if(colors.size() == 5){
                total += score;
            }
        }
        return total;
    }
}

class RowShadeVariety extends PublicObjectiveCard{
    private int score = 5;
    //what if a cell is empty? --> Has to be checked
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int i = 0; i < 4; i++){
            HashSet<Integer> numbers = new HashSet<>();
            for(int j = 0; j < 5; j++){
                numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
            }
            if(numbers.size() == 5){
                total += score;
            }
        }
        return total;
    }
}

class ShadeVariety extends PublicObjectiveCard{
    private int score = 5;
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        HashSet<Integer> numbers = new HashSet<>();
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
            }
            if(numbers.size() == 6){
                total += score;
            }
        }
        return total;
    }
}

class DeepShade extends PublicObjectiveCard{
    private int score = 2;
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        int[] numbers = new int[2];
        numbers[0] = 0;
        numbers[1] = 0;
        //Check the number of 5 and 6 in window pattern card and save it in numbers
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                if (w.getCell(i, j).getAssociatedDie().getValue() == 5){
                    numbers[0]+=1;
                }
                else if (w.getCell(i, j).getAssociatedDie().getValue() == 6){
                    numbers[1]+=1;
                }
            }
            if(numbers[0] > numbers[1]){
                total += score*numbers[1];
            }
            else{
                total +=score*numbers[0];
            }
        }
        return total;
    }
}

class MediumShade extends PublicObjectiveCard{
    private int score = 2;
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        int[] numbers = new int[2];
        numbers[0] = 0;
        numbers[1] = 0;
        //Check the number of 3 and 4 in window pattern card and save it in numbers
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                if (w.getCell(i, j).getAssociatedDie().getValue() == 3){
                    numbers[0]+=1;
                }
                else if (w.getCell(i, j).getAssociatedDie().getValue() == 4){
                    numbers[1]+=1;
                }
            }
            if(numbers[0] > numbers[1]){
                total += score*numbers[1];
            }
            else{
                total +=score*numbers[0];
            }
        }
        return total;
    }
}

class  LightShade extends PublicObjectiveCard{
    private int score = 2;
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        int[] numbers = new int[2];
        numbers[0] = 0;
        numbers[1] = 0;
        //Check the number of 1 and 2 in window pattern card and save it in numbers
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                if (w.getCell(i, j).getAssociatedDie().getValue() == 1){
                    numbers[0]+=1;
                }
                else if (w.getCell(i, j).getAssociatedDie().getValue() == 2){
                    numbers[1]+=1;
                }
            }
            if(numbers[0] > numbers[1]){
                total += score*numbers[1];
            }
            else{
                total +=score*numbers[0];
            }
        }
        return total;
    }
}
