package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class RowShadeVariety extends PublicObjectiveCard{
    public RowShadeVariety(String name, String description, Integer score) {
        super(name, description, score);
    }

    /**
     * Rows with no repeated values
     * @param w WindowPatternCard for which you want to calculate the score
     * @return rowShadeVariety score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int i = 0; i < 4; i++){
            HashSet<Integer> numbers = new HashSet<>();
            for(int j = 0; j < 5; j++){

                try {
                    numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
                } catch (EmptyCellException e) {
                    //nothing
                }
            }
            if(numbers.size() == 5){
                total += score;
            }
        }
        return total;
    }
}
