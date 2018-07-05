package server.model;

import shared.exceptions.EmptyCellException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Describes DraftPool behavior. A die can be placed in the draftPool, removed from it, rolled or switched with
 * another one (not in de draftPool). The number of present dice can be returned as well.
 * @author Alessio Molinari, Nives Migotto, Daniele Montesi
 */

public class DraftPool {

    private List<Cell> cells;

    /**
     * Constructor: generates a draftPool by taking from the dicebag 2 dice for each player + 1
     */
    public DraftPool(int numberOfPlayers) {
        this.cells = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers*2 +1; i++) {
            cells.add(new Cell(i));
        }
    }

    public void fillDraftPool(List<Die> dice){
        for(int i = 0; i < cells.size(); i++){
            cells.get(i).setAssociatedDie(dice.get(i));
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
     * Places a die in the draftPool in a given position
     * @param index position in which the die will be placed
     * @param toBePlaced to be placed in the draftPool die
     */
    public void placeDie(int index, Die toBePlaced){
        cells.get(index).setAssociatedDie(toBePlaced);
    }

    /**
     * Places a die in the first free position
     * @param toBePlaced to be placed in the draftPool die
     */
    public void placeDie(Die toBePlaced){
        for (Cell cell : cells) {
            if (cell.isEmpty()) {
                cell.setAssociatedDie(toBePlaced);
                return;
            }
        }
    }

    Die getLastDie() throws EmptyCellException {
        for (Cell c : cells){
            if (c.hasDie()){
                return c.removeDie();
            }
        }
        throw new EmptyCellException();
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
     * Returns the size of the Draft Pool
     * @return number of dice in the Draft Pool
     */
    public int draftPoolSize(){
        int counter=0;
        for (Cell cell: cells) {
            if (cell.hasDie())
                counter++;
        }
        return counter;
    }

    public Die getDie(int index) throws EmptyCellException {
        return cells.get(index).getAssociatedDie();
    }

    public Cell getCell(int index){
        return cells.get(index);
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
     * Returns a textual representation of Draft Pool dice
     * @return list of dice textual representations
     */
    public List<String> draftpoolPathRepresentation() {
        List<String> draftpoolString = new ArrayList<>();
        for (Cell cell : cells) {
            draftpoolString.add(cell.toString());
        }
        return draftpoolString;
    }
}
