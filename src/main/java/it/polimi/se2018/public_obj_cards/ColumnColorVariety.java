package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class ColumnColorVariety extends PublicObjectiveCard{
    private int score = 5;
    //what if a cell is empty?
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        for (int j = 0; j < 5; j++){
            HashSet<COLOR> colors = new HashSet<>();
            for(int i = 0; i < 4; i++){
                if (w.getCell(i, j).getAssociatedDie() != null);
                colors.add(w.getCell(i, j).getAssociatedDie().getColor());
            }
            if(colors.size() == 4){
                total += score;
            }
        }
        return total;
    }
}
