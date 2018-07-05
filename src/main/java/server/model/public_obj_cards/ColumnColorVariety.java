package server.model.public_obj_cards;

import server.model.COLOR;
import shared.exceptions.EmptyCellException;
import server.model.WindowPatternCard;

import java.util.HashSet;

public class ColumnColorVariety extends PublicObjectiveCard{
    public ColumnColorVariety(String name, String description, Integer score) {
        this.name = name;
        this.description = description;
        this.score = score;
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * Columns with no repeated colors
     * @param w WindowPatternCard for which you want to calculate the score
     * @return columnColorVariety score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int j = 0; j < 5; j++){
            HashSet<COLOR> colors = new HashSet<>();
            for(int i = 0; i < 4; i++){
                if (w.getCell(i, j).hasDie());
                try {
                    colors.add(w.getCell(i, j).getAssociatedDie().getColor());
                } catch (EmptyCellException e) {
                    //nothing
                }
            }
            if(colors.size() == 4){
                total += score;
            }
        }
        return total;
    }
}
