package it.polimi.se2018;

import java.util.ArrayList;
import java.util.Optional;

public class RoundTrack {

    private ArrayList<Cell> roundCells;

    /**
     * Constructor: generates a roundTrack by creating a list of 10 cells
     */
    public RoundTrack() {
        this.roundCells = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            roundCells.add(new Cell(i));
        }
    }

    /**
     * Removes a die from the roundTrack
     * @param diePosition position from which the die is taken
     * @return removed die
     * @throws IndexOutOfBoundsException if there is no die in diePosition
     */
    public Optional<Die> removeDie(int diePosition){
        try{
            return roundCells.get(diePosition).removeDie();
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("There is no die at such index");
            return Optional.empty();
        }
    }

    /**
     * Switches a die with a given one
     * @param diePosition position from which the die is taken
     * @param toBeSwitched new die in roundTrack
     * @return old die from roundTrack
     */
    public Die switchDie(int diePosition, Die toBeSwitched){
        return roundCells.get(diePosition).switchDie(toBeSwitched);
    }

    //Cosa succede se ci sono due dadi da mettere contemporaneamente sul roundTrack? Si mette prima uno e poi l'altro

    /**
     * Places a die on the roundTrack (in last position)
     * @param toBePlaced to be placed on the roundTrack die
     */
    public void placeDie(Die toBePlaced) {
        for (int i = 0; i < 10; i++) {
            if (!roundCells.get(i).getAssociatedDie().isPresent()) {
                roundCells.get(i).setAssociatedDie(toBePlaced);
                return;
            }
        }
    }

    /**
     * Returns the number of dice on the roundTrack
     * @return dice number
     */
    public int diceInTrack(){
        int n = 0;
        for (int i = 0; i < 10; i++){
            if(roundCells.get(i).getAssociatedDie().isPresent()){
                n +=1;
            }
        }
        return n;
    }

    public Optional<Die> getDie(int cellNumber){
        return roundCells.get(cellNumber).getAssociatedDie();
    }

    public ArrayList<Cell> getRoundCells() {
        return roundCells;
    }
}
