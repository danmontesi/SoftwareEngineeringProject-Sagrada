package it.polimi.se2018;

import it.polimi.se2018.Exceptions.EmptyCellException;

import java.util.ArrayList;

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
    public Die removeDie(int diePosition) throws EmptyCellException {
            return roundCells.get(diePosition).removeDie();
    }

    /**
     * Switches a die with a given one in the RoundTrack
     * @param diePosition position from which the die is taken
     * @param toBeSwitched new die in roundTrack
     * @return old die from roundTrack
     */
    public Die switchDie(int diePosition, Die toBeSwitched) throws EmptyCellException {
        return roundCells.get(diePosition).switchDie(toBeSwitched);
    }

    //Cosa succede se ci sono due dadi da mettere contemporaneamente sul roundTrack? Si mette prima uno e poi l'altro

    /**
     * Places a die on the roundTrack (in last position)
     * @param toBePlaced to be placed on the roundTrack die
     */
    public void placeDie(Die toBePlaced) {
        for (int i = 0; i < 10; i++) {
            if (roundCells.get(i).isEmpty()){
                roundCells.get(i).setAssociatedDie(toBePlaced);
                return;
            }
        }
    }

    /**
     * Returns the number of dice on the roundTrack
     * @return number of dice in round track
     */
    public int diceInTrack(){
        int n = 0;
        for (int i = 0; i < 10; i++) {
            if (roundCells.get(i).hasDie()){
                n+=1;
            }
        }
        return n;
    }

    public Die getDie(int cellNumber) throws EmptyCellException {
        return roundCells.get(cellNumber).getAssociatedDie();
    }

    public Cell getCell(int cellNumber){
        return roundCells.get(cellNumber);
    }

    public ArrayList<Cell> getRoundCells() {
        return roundCells;
    }
}
