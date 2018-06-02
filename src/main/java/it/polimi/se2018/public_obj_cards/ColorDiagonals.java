package it.polimi.se2018.public_obj_cards;


import it.polimi.se2018.COLOR;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

public class ColorDiagonals extends PublicObjectiveCard {

    public ColorDiagonals(String name, String description, Integer score) {
        super(name, description, score);
    }

    /**
     * Count of diagonally adjacent same color dice
     * @param w WindowPatternCard for which you want to calculate score
     * @return number of diagonally adjacent dice of the same color
     */
    public int calculateScore(WindowPatternCard w){
        COLOR currentColor;
        boolean checked[] = new boolean[20];
        for (int i = 0; i < 20; i++) {
            checked[i] = false;
        }
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 5; col++){
                int index = row*5+col;
                int indexDx = (row+1)*5+col+1;
                int indexSx = (row+1)*5+col-1;
                    try {
                        currentColor = w.getSchema().get(index).getAssociatedDie().getColor();
                        if ((col > 0)&&(w.getSchema().get(indexSx).getAssociatedDie().getColor().equals(currentColor))){
                            score+=2;
                            if (checked[index]){
                                score-=1;
                            }
                            if (checked[indexSx]){
                                score -=1;
                            }
                            checked[index] = true;
                            checked[indexSx] = true;
                        }
                        if ((col < 4)&&(w.getSchema().get(indexDx).getAssociatedDie().getColor().equals(currentColor))){
                            score+=2;
                            if (checked[index]){
                                score-=1;
                            }
                            if (checked[indexDx]){
                                score -=1;
                            }
                            checked[index] = true;
                            checked[indexDx] = true;
                        }
                    } catch (EmptyCellException ignored) {
                    }

                }
            }
            return score;
        }
}
