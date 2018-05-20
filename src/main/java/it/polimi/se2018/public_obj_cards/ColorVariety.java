package it.polimi.se2018.public_obj_cards;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Exceptions.EmptyCellException;
import it.polimi.se2018.WindowPatternCard;

import java.util.HashSet;

public class ColorVariety extends PublicObjectiveCard {
    int score;
    public ColorVariety(String name, String description, int score) {
        super(name, description, score);
    }

    public int calculateScore(WindowPatternCard w) {
        int total = 0;
        HashSet<COLOR> colors = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (w.getCell(i, j).hasDie()) {
                    try {
                        colors.add(w.getCell(i, j).getAssociatedDie().getColor());
                    } catch (EmptyCellException e) {

                    }
                }
            }
            if (colors.size() == 5) {
                total += score;
            }
        }
        return total;
    }
}


