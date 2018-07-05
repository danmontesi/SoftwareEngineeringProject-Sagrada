package server.model.public_obj_cards;

import shared.exceptions.EmptyCellException;
import server.model.WindowPatternCard;

import java.util.HashSet;

public class ColumnShadeVariety extends PublicObjectiveCard{
    public ColumnShadeVariety(String name, String description, Integer score) {
        super(name, description, score);
    }

    @Override
    public String getName() {
        return name;
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
                        //nothing
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
