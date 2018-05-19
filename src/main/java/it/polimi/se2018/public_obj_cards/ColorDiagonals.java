package it.polimi.se2018.public_obj_cards;


public class ColorDiagonals extends PublicObjectiveCard {

        public ColorDiagonals(){
        }

/*        public int calculateScore(WindowPatternCard w) {
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
