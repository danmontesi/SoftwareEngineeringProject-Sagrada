package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class RowColorVariety extends PublicObjectiveCard{
    private int score = 6;

    //what if a cell is empty?
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int i = 0; i < 4; i++){
            HashSet<COLOR> colors = new HashSet<>();
            for(int j = 0; j < 5; j++){
                if (w.getCell(i, j).getAssociatedDie() != null)
                    colors.add(w.getCell(i, j).getAssociatedDie().getColor());
            }
            if(colors.size() == 5){
                total += score;
            }
        }
        return total;
    }
}
