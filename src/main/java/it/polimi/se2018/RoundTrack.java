package it.polimi.se2018;

import java.util.ArrayList;

public class RoundTrack {

    private ArrayList<Cell> roundCells;

    public Die removeDie(int diePosition){
        return roundCells.get(diePosition).removeDie();
    }

    public Die switchDie(int diePosition, Die toBeSwitched){
        return roundCells.get(diePosition).switchDie(toBeSwitched);
    }

    public void placeDie(Die toBePlaced) {
        int i = 0;
        while (roundCells.get(i) != null) {
            i++;
        }
        roundCells.get(i).setAssociatedDie(toBePlaced);
    }

    public Die getDie(int cellNumber){
        return roundCells.get(cellNumber).getAssociatedDie();
    }

}
