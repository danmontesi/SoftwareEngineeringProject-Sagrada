package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.Exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

public class MediumShade extends PublicObjectiveCard{
    public MediumShade(String name, String description, int score) {
        super(name, description, score);
    }

    /**
     * Sets of 3 & 4 values anywhere
     * @param w WindowPatternCard for which you want to calculate the score
     * @return mediumShade score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        int[] numbers = new int[2];
        numbers[0] = 0;
        numbers[1] = 0;
        //Check the number of 3 and 4 in window pattern card and save it in numbers
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++) {
                try {
                    if (w.getCell(i, j).getAssociatedDie().getValue() == 3) {
                        numbers[0] += 1;
                    } else if (w.getCell(i, j).getAssociatedDie().getValue() == 4) {
                        numbers[1] += 1;
                    }
                } catch (EmptyCellException e) {
                    continue;
                }

            }
            if(numbers[0] > numbers[1]){
                total += score*numbers[1];
            }
            else{
                total +=score*numbers[0];
            }
        }
        return total;
    }
}