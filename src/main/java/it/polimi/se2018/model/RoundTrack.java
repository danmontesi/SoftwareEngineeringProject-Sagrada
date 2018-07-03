package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.EmptyCellException;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes RoundTrack behavior. A die can be placed in the roundTrack, removed from it or switched with another one
 * (not on the roundTrack). The number of present dice can be returned as well.
 * @author Nives Migotto
 */
public class RoundTrack {

    private List<Cell> roundCells;

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
     * @throws EmptyCellException if there is no die in diePosition
     */
    public Die removeDie(int diePosition) throws EmptyCellException {
            return roundCells.get(diePosition).removeDie();
    }

    /**
     * Switches a die with a given one in the RoundTrack
     * @param diePosition position from which the die is taken
     * @param toBeSwitched new die in roundTrack
     * @return old die from roundTrack
     * @throws EmptyCellException if there is no die in diePosition
     */
    public Die switchDie(int diePosition, Die toBeSwitched) throws EmptyCellException {
        return roundCells.get(diePosition).switchDie(toBeSwitched);
    }

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

    /**
     * Returns associated die
     * @param cellNumber cell number
     * @return associated die
     * @throws EmptyCellException if the cell is empty
     */
    public Die getDie(int cellNumber) throws EmptyCellException {
        return roundCells.get(cellNumber).getAssociatedDie();
    }

    public Cell getCell(int cellNumber){
        return roundCells.get(cellNumber);
    }

    public List<Cell> getRoundCells() {
        return roundCells;
    }

    /**
     * Representation of the patch of the whole roundtrack. Useful for gui and cli
     * @return List of path last name
     */
    public List<String> roundtrackPathRepresentation() {
        List<String> roundtrackString = new ArrayList<>();
        for (Cell roundCell : roundCells) {
            roundtrackString.add(roundCell.toString());
        }
        return roundtrackString;
    }

    public boolean isPresent(COLOR color) {
        for (Cell cell : roundCells){
            if (cell.hasDie()){
                try {
                    if (cell.getAssociatedDie().getColor()==color)
                        return true;
                } catch (EmptyCellException e) {
                    //cell must have die, since it is true cell.hasDie()
                }
            }
        }
        return false;
    }
}
