package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.Exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class ShadeVariety extends PublicObjectiveCard{
    int score;

    public ShadeVariety(String name, String description, int score) {
        super(name, description, score);
    }

    /**
     * Sets of one of each value anywhere
     * @param w WindowPatternCard for which you want to calculate the score
     * @return shadeVariety score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        HashSet<Integer> numbers = new HashSet<>();
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                try {
                    numbers.add(w.getCell(i, j).getAssociatedDie().getValue());
                } catch (EmptyCellException e) {
                    continue;
                }
            }
            if(numbers.size() == 6){
                total += score;
            }
        }
        return total;
    }
}
