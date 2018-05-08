package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class ColorVariety extends PublicObjectiveCard {
    private final int score = 4;

    public int calculateScore(WindowPatternCard w) {
        int total = 0;
        HashSet<COLOR> colors = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                colors.add(w.getCell(i, j).getAssociatedDie().getColor());
            }
            if (colors.size() == 5) {
                total += score;
            }
        }
        return total;
    }
}
