package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.EmptyCellException;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Describes DraftPool behavior. A die can be placed in the draftPool, removed from it, rolled or switched with
 * another one (not in de draftPool). The number of present dice can be returned as well.
 * @author Alessio Molinari, Nives Migotto, Daniele Montesi
 */

public class DraftPool {
    /**
     * The arraylist is a List of Die.
     * If a Die is picked, the value has to remain NULL in order to let the Graphic to remain the same when a die is removed
     */
    private ArrayList<Cell> cells;

    /**
     * Constructor: generates a draftPool by taking from the dicebag 2 dice for each player + 1
     * @param dice dice to assign
     */
    public DraftPool(ArrayList<Die> dice) {
        this.cells = new ArrayList<>();
        for (int i = 0; i < dice.size(); i++) {
            cells.add(new Cell(dice.get(i), i));
        }
    }

    /**
     * Removes a die from the draftPool
     * @param diePosition position from which the die is taken
     * @return removed die
     */
    public Die takeDie(int diePosition) throws EmptyCellException {
        //I know it does not make much sense to just rename an exception
        //It just sounds better to call it like this
        try{
            return cells.get(diePosition).removeDie();
        } catch (IndexOutOfBoundsException e){
            throw new EmptyCellException();
        }
    }

    /**
     * Switches a die with a random one
     * @param toBeSwitched new die in draftPool
     * @return old die from draftPool
     */
    public Die switchDie(Die toBeSwitched) {
        while(true){
            try{
                int index = ThreadLocalRandom.current().nextInt(0, cells.size());
                Die temp = cells.get(index).removeDie();
                cells.get(index).setAssociatedDie(toBeSwitched);
                return temp;
            } catch (EmptyCellException e){
                //just keep going
                //DraftPool is guaranteed to be never empty during turn
            }
        }
    }

    /**
     * Switches a die with a given one
     * @param diePosition position from which the die is taken
     * @param toBeSwitched new die in draftPool
     * @return old die from draftPool
     */
    public Die switchDie(int diePosition, Die toBeSwitched) throws EmptyCellException {
        Die temp;
        temp = cells.get(diePosition).removeDie();
        cells.get(diePosition).setAssociatedDie(toBeSwitched);
        return temp;
    }

    /**
     * Places a die in the draftPool
     * @param toBePlaced to be placed in the draftPool die
     */
    public void placeDie(int index, Die toBePlaced){
        cells.get(index).setAssociatedDie(toBePlaced);
    }

    /**
     * Place a die
     * @param toBePlaced
     */
    public void placeDie(Die toBePlaced){
        for(int i = 0; i < cells.size(); i++){
            if(cells.get(i).isEmpty()){
                cells.get(i).setAssociatedDie(toBePlaced);
                return;
            }
        }
    }


    /**
     * Rolls all dice in the draftPool (gives all dice a new random value)
     */
    public void rollDice() {
        for (Cell cell : cells) {
            if (!cell.isEmpty()){
                try {
                    cell.getAssociatedDie().roll();
                } catch (EmptyCellException e) {
                    //nothing
                }
            }
        }
    }

    /**
     *
     * @return numbers of dice in Draft Pool
     */
    public int draftPoolSize(){
        int counter=0;
        for (Cell cell: cells) {
            if (cell.hasDie())
                counter++;
        }
        return counter;
    }

    /**
     *
     * @param index draftPool index
     * @return Die at given index
     */
    public Die getDie(int index) throws EmptyCellException {
        return cells.get(index).getAssociatedDie();
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cells.size(); i++) {
            try {
                if (!cells.get(i).hasDie()) {
                    builder.append(i).append(":- ").append("noDie");
                } else {
                    builder.append(i).append(":- ").append(cells.get(i).getAssociatedDie().toString());
                }
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
            builder.append("\t");
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Representation of the patch of the whole draftpool. Useful for GUI
     * @return List of path last name
     */
    public ArrayList<String> draftpoolPathRepresentation() {
        ArrayList<String> draftpoolString = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            try {
                if (cells.get(i).isEmpty()) {
                    draftpoolString.add("empty");
                } else {
                    draftpoolString.add(cells.get(i).getAssociatedDie().getColor().toString() + "_" + cells.get(i).getAssociatedDie().getValue());
                }
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
        }
        return draftpoolString;
    }

}
