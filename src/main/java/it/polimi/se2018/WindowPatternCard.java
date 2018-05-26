
package it.polimi.se2018;

import it.polimi.se2018.Exceptions.EmptyCellException;

import java.util.ArrayList;

/**
 * Describes WindowPatternCard behavior. Dice can be placed on it, after checking restrictions.
 * @author Alessio Molinari
 */
public class WindowPatternCard {
    private ArrayList<Cell> schema;
    private int difficulty;
    private String name;

    public WindowPatternCard(ArrayList<Cell> schema, int difficulty, String name) {
        this.schema = schema;
        this.difficulty = difficulty;
        this.name = name;
    }

    /**
     * Create an empty WindowPatternCard with plain numbered cells from 0 to 19
     */
    public WindowPatternCard() {
        this.difficulty = 0;
        this.name = null;
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cells.add(new Cell(i));
        }
        this.schema = cells;
    }

    public boolean isEmpty() {
        return this.schema.isEmpty();
    }

    /**
     * edit @danmontesi
     * placeDie will return boolean instead of void for usability.
     * In this way, if it returns true (= correct placement), automatically modify permanently the model and call next Turn
     * if it returns false (= incorrect placement), model doesn't change and i will be able to notify the view of the incorrect
     * move and restart the player's turn
     *
     * @param d                    to be placed die
     * @param row                  cell row number
     * @param column               cell column number
     * @param colorRestriction     cell color restriction
     * @param valueRestriction     cell value restriction
     * @param placementRestriction placement restriction
     * @return true if restrictions are respected and die is place, false otherwise
     */
    public boolean placeDie(Die d, int row, int column, boolean colorRestriction, boolean valueRestriction,
                            boolean placementRestriction) {
        if (this.getCell(row, column).hasDie()) {
            return false;
        }
        if (colorRestriction) {
            if (!checkColorRestricion(this.getCell(row, column), d)) {
                return false;
            }
        }
        if (valueRestriction) {
            if (!checkValueRestriction(this.getCell(row, column), d)) {
                return false;
            }
        }
        if (placementRestriction) {
            if (!checkPlacementRestriction(this, this.getCell(row, column))) {
                return false;
            }
        }
        this.getCell(row, column).setAssociatedDie(d);
        return true;
    }

    //What if the index is not valid? -> The controller checks it :)
    public Die removeDie(int row, int column) throws EmptyCellException {
        return schema.get(row * 5 + column).removeDie();
    }

    /**
     * edit @danmontesi
     * Instead of considering an Array of int, consider an ArrayList when I insert new numbers of adjacent
     * adjacent are 8 only if the cell is internal (and so the cell passes all the controls)
     *
     * if i find at least an adjacent -> return true
     */
    public boolean checkPlacementRestriction(WindowPatternCard w, Cell c) { // 'w' can be replaced with 'this'
        int index = c.getIndex();
        if (w.isEmpty()) {
            // if cell is internal -> false
            return !(((index % 5) > 0) && ((index % 5) < 4) && ((index / 5) > 0) && ((index / 5) < 3));
        }

        //in w isn't empty -> Check adjacency to other placed dice
        ArrayList<Integer> adjacentCells = new ArrayList<>();

        // 6 chained if clauses to determine if exists an adjacent cell

        if (index / 5 > 0) {
            adjacentCells.add(index / 5 - 1 + index % 5);
        }
        if (index / 5 > 0 && index % 5 > 0) {
            adjacentCells.add(index / 5 - 1 + index % 5 - 1);
            adjacentCells.add(index / 5 + index - 1);
        }
        if (index / 5 > 0 && index % 5 < 4) {
            adjacentCells.add(index / 5 - 1 + index % 5 + 1);
            adjacentCells.add(index / 5 + index % 5 + 1);
        }
        if (index / 5 < 3) {
            adjacentCells.add(index / 5 + 1 + index % 5);
        }
        if (index / 5 < 3 && index % 5 > 0) { // oss: sto inserendo stessi indici multipli, però non comporta errori (farò il controllo solamente 2 volte in + )
            adjacentCells.add(index / 5 + 1 + index % 5 - 1);
            adjacentCells.add(index / 5 + index - 1);
        }
        if (index / 5 < 3 && index % 5 < 4) { // vedi commento prec
            adjacentCells.add(index / 5 + 1 + index % 5 + 1);
            adjacentCells.add(index / 5 + index % 5 + 1);
        }

        for (int adjIndex : adjacentCells) {
            if (this.schema.get(adjIndex).hasDie()) {
                return true;
            }
        }
        return false;

    }

    public boolean checkColorRestricion(Cell c, Die d) {
        if (c.getColorConstraint() == null) {
            return true;
        }
        return (c.getColorConstraint().equals(d.getColor()));
    }

    public boolean checkValueRestriction(Cell c, Die d) {
        if (c.getValueConstraint() == null) {
            return true;
        }
        return (c.getValueConstraint() == d.getValue());

    }

/*    public boolean checkAllValueRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null)&&(temp.getAssociatedDie().get().getValue() != temp.getValueConstraint())){
                return false;
            }
        }
        return true;
    }*/

 /*   public boolean checkAllColorRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null) && !(temp.getAssociatedDie().get().getColor().equals(temp.getColorConstraint()))){
                return false;
            }
        }
        return true;
    }*/

    public String getCardName() {
        return this.name;
    }

    public ArrayList<Cell> getSchema() {
        return this.schema;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    //retrieve cell by row and column
    public Cell getCell(int row, int column) {
        return schema.get(row * 5 + column);
    }

    //retrieve cell by linear index
    public Cell getCell(int index) {
        return schema.get(index);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchema(ArrayList<Cell> schema) {
        this.schema = schema;
    }

    public String toString() {
        String printedCard = new String();
        for (int i = 0; i < schema.size(); i++) {
            try {
                if (schema.get(i).getAssociatedDie() == null) {
                    printedCard.concat("noDie");
                } else {
                    printedCard.concat(schema.get(i).getAssociatedDie().getColor() + "," + schema.get(i).getAssociatedDie().getValue());
                }
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
            if ((i + 1) % 5 == 0) {
                printedCard.concat("\n");
            }
            printedCard.concat("\t");
        }
        return printedCard;
    }

}
