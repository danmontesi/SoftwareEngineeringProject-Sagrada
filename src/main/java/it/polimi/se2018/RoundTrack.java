package it.polimi.se2018;

import java.util.ArrayList;
import java.util.Optional;

public class RoundTrack {

    private ArrayList<Cell> roundCells;

    public RoundTrack() {
        this.roundCells = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            roundCells.add(new Cell());
        }
    }

    public Optional<Die> removeDie(int diePosition){
        try{
            return roundCells.get(diePosition).removeDie();
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("There is no die at such index");
            return Optional.empty();
        }
    }

    public Die switchDie(int diePosition, Die toBeSwitched){
        return roundCells.get(diePosition).switchDie(toBeSwitched);
    }

    //Cosa succede se ci sono due dadi da mettere contemporaneamente sul roundTrack?
    public void placeDie(Die toBePlaced) {
        int i = 0;
        while (!roundCells.get(i).getAssociatedDie().equals(null)) {
            i++;
        }
        roundCells.get(i).setAssociatedDie(toBePlaced);
    }

    public Optional<Die> getDie(int cellNumber){
        return roundCells.get(cellNumber).getAssociatedDie();
    }

    public ArrayList<Cell> getRoundCells() {
        return roundCells;
    }
}
