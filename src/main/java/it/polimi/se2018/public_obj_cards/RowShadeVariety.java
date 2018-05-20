package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.Exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class RowShadeVariety extends PublicObjectiveCard{
    int score;
    public RowShadeVariety(String name, String description, int score) {
        super(name, description, score);
    }

    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int i = 0; i < 4; i++){
            HashSet<Integer> numbers = new HashSet<>();
            for(int j = 0; j < 5; j++){

                try {
                    numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
                } catch (EmptyCellException e) {
                    continue;
                }
            }
            if(numbers.size() == 5){
                total += score;
            }
        }
        return total;
    }
}
