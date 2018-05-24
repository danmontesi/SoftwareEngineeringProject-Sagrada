package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class RowColorVariety extends PublicObjectiveCard{
    public RowColorVariety(String name, String description, Integer score) {
        super(name, description, score);
    }

    /**
     * Rows with no repeated colors
     * @param w WindowPatternCard for which you want to calculate the score
     * @return rowColorVariety score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int i = 0; i < 4; i++){
            HashSet<COLOR> colors = new HashSet<>();
            for(int j = 0; j < 5; j++){
                try {
                    colors.add(w.getCell(i, j).getAssociatedDie().getColor());
                } catch (EmptyCellException e) {
                    continue;
                }
            }
            if(colors.size() == 5){
                total += score;
            }
        }
        return total;
    }
}
