package it.polimi.se2018.public_obj_cards;


import it.polimi.se2018.COLOR;
import it.polimi.se2018.Exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

public class ColorDiagonals extends PublicObjectiveCard {

        public ColorDiagonals(){
        }

        public int calculateScore(WindowPatternCard w){
            int score = 0;
            COLOR currentColor;
            boolean checked[] = new boolean[20];
            for (boolean i : checked){
                i = false;
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

    /*    public int calculateScore(WindowPatternCard w) {

            int countScore = 0;
            COLOR currentColor;
            for (int row = 0; row < 3; row++) { //eccetto l'ultima riga
                for (int col = 0; col < 5; col++) {
                    // checking if there's a die in the current cell
                    if (w.getSchema().get(row * 5 + col).hasDie()) {
                        try {
                            currentColor = w.getSchema().get(row * 5 + col).getAssociatedDie().getColor();
                        } catch (EmptyCellException e) {
                        }
                        if (col > 0) { //checking sud-ovest cell die color
                            if (w.getSchema().get((row + 1) * 5 + (col - 1)).hasDie()) {
                                try {
                                    if (w.getSchema().get((row + 1) * 5 + (col - 1)).getAssociatedDie().getColor() == currentColor) {
                                        countScore++;
                                    }
                                } catch (EmptyCellException e) {

                                }
                            }
                        }
                        if (col < 4) { //checking sud-est cell die color
                            if (w.getSchema().get((row + 1) * 5 + (col + 1)).hasDie()) {
                                try {
                                    if (w.getSchema().get((row + 1) * 5 + (col + 1)).getAssociatedDie().getColor() == currentColor) {
                                        countScore++;
                                    }
                                } catch (EmptyCellException e) {

                                }
                            }
                        }
                    }
                }
            }
            return countScore;
        }
        */
}
