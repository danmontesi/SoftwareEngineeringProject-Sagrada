package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.Exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class ColumnShadeVariety extends PublicObjectiveCard{
    public ColumnShadeVariety(String name, String description, Integer score) {
        super(name, description, score);
    }

    /**
     * Columns with no repeated values
     * @param w WindowPatternCard for which you want to calculate the score
     * @return columnShadeVariety score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int j = 0; j < 5; j++){
            HashSet<Integer> numbers = new HashSet<>();
            for(int i = 0; i < 4; i++){
                if (w.getCell(i, j).hasDie()) {
                    try {
                        numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
                    } catch (EmptyCellException e) {

                    }
                }
            }
            if(numbers.size() == 4){
                total += score;
            }
        }
        return total;
    }
}
