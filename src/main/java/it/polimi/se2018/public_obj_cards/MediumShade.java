package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class MediumShade extends PublicObjectiveCard{
    private int score = 2;
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        HashSet<Integer> numbers = new HashSet<>();
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
            }
            if(numbers.contains(3)&&(numbers.contains(4))){
                total += score;
            }
        }
        return total;
    }
}
