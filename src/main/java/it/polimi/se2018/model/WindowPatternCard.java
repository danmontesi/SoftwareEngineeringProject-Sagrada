
package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.exceptions.WrongCellIndexException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Describes WindowPatternCard behavior. Dice can be placed on it, after checking restrictions.
 *
 * @author Alessio Molinari
 */
public class WindowPatternCard {
    private List<Cell> schema;
    private int difficulty;
    private String name;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public WindowPatternCard(List<Cell> schema, int difficulty, String name) {
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
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cells.add(new Cell(i));
        }
        this.schema = cells;
    }

    public WindowPatternCard(List<Cell> cells) {
        this.difficulty = 0;
        this.name = null;
        this.schema = cells;
    }

    public boolean isEmpty() {
        for (Cell cell : schema) {
            if (cell.hasDie()) {
                return false;
            }
        }
        return true;
    }

    /**
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
        int index = row * 5 + column;
        return placeDie(d, index, colorRestriction, valueRestriction, placementRestriction);
    }


    /**
     * Same of place die, but has index instead of row/column
     */
    public boolean placeDie(Die d, int index, boolean colorRestriction, boolean valueRestriction,
                            boolean placementRestriction) {
        if(checkDiePlacement(d, index, colorRestriction, valueRestriction, placementRestriction)){
            this.getCell(index).setAssociatedDie(d);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Overloaded method for ordinary moves (without toolcards)
     */
    public boolean placeDie(Die d, int row, int column) {
        return placeDie(d, row, column, true, true, true);
    }

    public boolean moveDie(int oldPosition, int newPosition, boolean colorRestriction, boolean valueRestriction,
                           boolean placementRestriction) throws EmptyCellException {
        Die d = this.removeDie(oldPosition);
        return placeDie(d, newPosition, colorRestriction, valueRestriction, placementRestriction);
        }


    public Die removeDie(int index) throws EmptyCellException {
        return schema.get(index).removeDie();
    }

    public boolean checkPlacementRestriction(Cell c, Die d) {
        int column = c.getColumn();
        int row = c.getRow();

        if (this.isEmpty()) {
            return row == 0 || row == 3 || column == 0 || column == 4;
        } else {
            return (checkAdjacents(c, d) && checkColorsAndValues(c, d));
        }
    }

    public boolean checkPlacementRestrictionNoAdjacents(Cell c, Die d) {
        int column = c.getColumn();
        int row = c.getRow();

        if (this.isEmpty()) {
            if (row == 0 || row == 3 || column == 0 || column == 4) {
                return true;
            } else {
                return false;
            }
        } else {
            return (checkColorsAndValues(c, d));
        }
    }


    public boolean checkAdjacents(Cell c, Die d) {
        int row = c.getRow();
        int column = c.getColumn();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    try {
                        if (this.getCell(row + i, column + j).hasDie()) {
                            return true;
                        }
                    } catch (WrongCellIndexException e) {
                        //nothing
                    }
                }
            }
        }
        return false;
    }


    public boolean checkColorsAndValues(Cell c, Die d) {
        int row = c.getRow();
        int column = c.getColumn();
        int value = d.getValue();
        COLOR color = d.getColor();

        for (int i = -1; i <= 1; i = i + 2) {
            try {
                Die check = this.getCell(row + i, column).getAssociatedDie();
                if (check.getValue() == (value) || check.getColor().equals(color)) {
                    return false;
                }
            } catch (EmptyCellException | WrongCellIndexException e) {
                //nothing
            }
        }
        for (int i = -1; i <= 1; i = i + 2) {
            try {
                Die check = this.getCell(row, column + i).getAssociatedDie();
                if (check.getValue() == (value) || check.getColor().equals(color)) {
                    return false;
                }
            } catch (EmptyCellException | WrongCellIndexException e) {
                //nothing
            }
        }
        return true;
    }

    private boolean checkColorRestriction(Cell c, Die d) {
        if (c.getColorConstraint() == null) {
            return true;
        }
        return (c.getColorConstraint().equals(d.getColor()));
    }

    private boolean checkValueRestriction(Cell c, Die d) {
        if (c.getValueConstraint() == null) {
            return true;
        }
        return (c.getValueConstraint() == d.getValue());

    }

    public String getCardName() {
        return this.name;
    }

    public List<Cell> getSchema() {
        return this.schema;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Retrieve cell by row andd column
     * Throws unchecked WrongCellIndexException
     *
     * @param row
     * @param column
     * @return
     * @throws WrongCellIndexException
     */
    public Cell getCell(int row, int column) {
        if (row < 0 || row > 3 || column < 0 || column > 4) {
            throw new WrongCellIndexException();
        }
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

    public void setSchema(List<Cell> schema) {
        this.schema = schema;
    }

    public String toString() {
        String printedCard = "\n - " + name + " - \n";
        for (int i = 0; i < schema.size(); i++) {
            try {
                if (schema.get(i).isEmpty()) {
                    if (schema.get(i).getColorConstraint() != null)
                        printedCard += schema.get(i).getColorConstraint().toString();
                    else if (schema.get(i).getValueConstraint() != null)
                        printedCard += schema.get(i).getValueConstraint().toString();
                    else
                        printedCard += "noDie";
                } else {
                    printedCard += schema.get(i).getAssociatedDie().getColor() + "," + schema.get(i).getAssociatedDie().getValue();
                }
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
            if ((i + 1) % 5 == 0) {
                printedCard += "\n";
            }
            printedCard += "\t";
        }
        return printedCard;
    }

    /**
     * Representation of the patch of the whole Wpc. Useful for gui
     *
     * @return List of path last name
     */
    public List<String> wpcPathRepresentation() {
        List<String> wpcString = new ArrayList<>();
        String pathName = name.replaceAll(" ", "_");
        wpcString.add(pathName);
        for (int i = 0; i < schema.size(); i++) {
            try {
                if (schema.get(i).isEmpty()) {
                    if (schema.get(i).getColorConstraint() != null) {
                        wpcString.add(schema.get(i).getColorConstraint().toString());   //COLOR constaint has just the "constr" color name. casn use ".contains"_" to know if there is a restriction
                    } else if (schema.get(i).getValueConstraint() != null) {
                        wpcString.add(schema.get(i).getValueConstraint().toString());   //VALUE constaint has just the "constr" + value name
                    } else {
                        wpcString.add("empty");
                    }
                } else {
                    wpcString.add(schema.get(i).getAssociatedDie().getColor().toString() + "_" + schema.get(i).getAssociatedDie().getValue());
                }
            } catch (EmptyCellException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return wpcString;
    }

    public boolean isPossibleToPlace(Die tempDieToCheckPlacement) {
        for (int i = 0; i < schema.size(); i++) {
            if (checkDiePlacement(tempDieToCheckPlacement, i, true, true, true)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiePlacement(Die d, int index, boolean colorRestriction, boolean valueRestriction, boolean placementRestriction) {
        if (this.getCell(index).hasDie()) {
            return false;
        }
        if ((colorRestriction) && (!checkColorRestriction(this.getCell(index), d))) {
            return false;
        }
        if ((valueRestriction) && (!checkValueRestriction(this.getCell(index), d))) {
            return false;
        }
        if ((placementRestriction) && (!checkPlacementRestriction(this.getCell(index), d))) {
            return false;
        }
        return true;}
}
