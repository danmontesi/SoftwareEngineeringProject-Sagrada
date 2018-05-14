package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.WindowPatternCard;

public class LightShade extends PublicObjectiveCard{
    private int score = 2;
    public int calculateScore(WindowPatternCard w){
        int total = 0;
        int[] numbers = new int[2];
        numbers[0] = 0;
        numbers[1] = 0;
        //Check the number of 1 and 2 in window pattern card and save it in numbers
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++) {
                if (w.getCell(i, j).getAssociatedDie().isPresent()) {
                    if (w.getCell(i, j).getAssociatedDie().get().getValue() == 1) {
                        numbers[0] += 1;
                    } else if (w.getCell(i, j).getAssociatedDie().get().getValue() == 2) {
                        numbers[1] += 1;
                    }
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
