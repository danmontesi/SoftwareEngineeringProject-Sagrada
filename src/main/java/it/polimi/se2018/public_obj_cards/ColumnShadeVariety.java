package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class ColumnShadeVariety extends PublicObjectiveCard{
    private int score = 4;
    //what if a cell is empty?
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int j = 0; j < 5; j++){
            HashSet<Integer> numbers = new HashSet<>();
            for(int i = 0; i < 4; i++){
                if (w.getCell(i, j).getAssociatedDie() != null)
                    numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
            }
            if(numbers.size() == 4){
                total += score;
            }
        }
        return total;
    }
}
