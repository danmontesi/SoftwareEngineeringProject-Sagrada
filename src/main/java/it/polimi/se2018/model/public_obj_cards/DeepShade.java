package it.polimi.se2018.model.public_obj_cards;

import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.model.WindowPatternCard;

public class DeepShade extends PublicObjectiveCard{
    public DeepShade(String name, String description, Integer score) {
        this.name = name;
        this.description = description;
        this.score = score;
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * Sets of 5 & 6 values anywhere
     * @param w WindowPatternCard for which you want to calculate the score
     * @return deepShade score
     */
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        int[] numbers = new int[2];
        numbers[0] = 0;
        numbers[1] = 0;
        //Check the number of 5 and 6 in window pattern card and save it in the array numbers
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                try {
                    if (w.getCell(i, j).getAssociatedDie().getValue() == 5) {
                        numbers[0] += 1;
                    } else if (w.getCell(i, j).getAssociatedDie().getValue() == 6) {
                        numbers[1] += 1;
                    }
                } catch (EmptyCellException e) {
                    //nothing
                }
            }
        }
        if(numbers[0] > numbers[1]){
            total += score*numbers[1];
        }
        else{
            total += score*numbers[0];
        }
        return total;
    }
}