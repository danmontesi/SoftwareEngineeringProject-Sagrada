
package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.exceptions.WrongCellIndexException;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes Window Pattern Card behavior. Dice can be placed on it, after checking restrictions.
 * @author Alessio Molinari
 */
public class WindowPatternCard {
    private List<Cell> schema;
    private int difficulty;
    private String name;

    public WindowPatternCard(List<Cell> schema, int difficulty, String name) {
        this.schema = schema;
        this.difficulty = difficulty;
        this.name = name;
    }

    /**
     * Constructor: creates an empty Window Pattern Card with plain numbered cells from 0 to 19
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

    /**
     * Checks whether there are dice on the Window Pattern Card or not
     * @return true if there are no dice, false if there are dice
     */
    public boolean isEmpty() {
        for (Cell cell : schema) {
            if (cell.hasDie()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Places a given die in a given position, checking restrictions
     * @param d to be placed die
     * @param row row index
     * @param column column index
     * @param colorRestriction indicates whether the color restrictions have to be checked
     * @param valueRestriction indicates whether the value restrictions have to be checked
     * @param placementRestriction indicates whether the placement restrictions have to be checked
     * @return true if restrictions are respected and die is placed, false otherwise
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
        if (checkCorrectDiePlacement(d, index, colorRestriction, valueRestriction, placementRestriction)) {
            this.getCell(index).setAssociatedDie(d);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Overloaded method for ordinary moves (without Tool Cards)
     */
    public boolean placeDie(Die d, int row, int column) {
        return placeDie(d, row, column, true, true, true);
    }

    /**
     * Moves a die on the Window Pattern Card, checking restrictions
     * @param oldPosition old die position
     * @param newPosition new die position
     * @param colorRestriction indicates whether the color restrictions have to be checked
     * @param valueRestriction indicates whether the value restrictions have to be checked
     * @param placementRestriction indicates whether the placement restrictions have to be checked
     * @return true if restrictions are respected and die is placed, false otherwise
     * @throws EmptyCellException if the old position is empty
     */
    public boolean moveDie(int oldPosition, int newPosition, boolean colorRestriction, boolean valueRestriction,
                           boolean placementRestriction) throws EmptyCellException {
        Die d = this.removeDie(oldPosition);
        if (checkCorrectDiePlacement(d, newPosition, colorRestriction, valueRestriction, placementRestriction)) {
            return placeDie(d, newPosition, colorRestriction, valueRestriction, placementRestriction);
        } else {
            this.setDie(d, oldPosition);
            return false;
        }
    }

    /**
     * Removes the die from a given index
     * @param index index
     * @return removed die
     * @throws EmptyCellException if the cell is empty
     */
    public Die removeDie(int index) throws EmptyCellException {
        return schema.get(index).removeDie();
    }

    /**
     * Checks placement restrictions
     * @param c to be checked cell
     * @param d to be placed die
     * @return true if restrictions are respected, false otherwise
     */
    private boolean checkPlacementRestriction(Cell c, Die d) {
        int column = c.getColumn();
        int row = c.getRow();
        if (this.isEmpty()) {
            return row == 0 || row == 3 || column == 0 || column == 4;
        } else {
            return (checkAdjacents(c) && checkAdjacentsColorsAndValues(c, d));
        }
    }

    public boolean checkPlacementRestrictionNoAdjacents(Cell c, Die d) {
        int column = c.getColumn();
        int row = c.getRow();

        if (this.isEmpty()) {
            return row == 0 || row == 3 || column == 0 || column == 4;
        } else {
            return (checkAdjacentsColorsAndValues(c, d));
        }
    }

    /**
     * Checks whether the destination cell is adjacent to the source one or not
     * @param c destination cell
     * @return true if the destination cell is next to the source one (vertically, horizontally or diagonally), false otherwise
     */
    private boolean checkAdjacents(Cell c) {
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

    /**
     * Checks whether the destination cell respects the adjacent color and value restrictions
     * @param c destination cell
     * @param d to be placed die
     * @return true if the destination cell (if is next to the source one) doesn't have the same color or value as the die, false otherwise
     */
    private boolean checkAdjacentsColorsAndValues(Cell c, Die d) {
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

    /**
     * Checks whether the destination cell has the same color as the die or not
     * @param c destination cell
     * @param d to be place die
     * @return true if the destination cell does not have the same color as the die, false otherwise
     */
    private boolean checkColorRestriction(Cell c, Die d) {
        if (c.getColorConstraint() == null) {
            return true;
        }
        return (c.getColorConstraint().equals(d.getColor()));
    }

    /**
     * Checks whether the destination cell has the same value as the die or not
     * @param c destination cell
     * @param d to be place die
     * @return true if the destination cell does not have the same value as the die, false otherwise
     */
    private boolean checkValueRestriction(Cell c, Die d) {
        if (c.getValueConstraint() == null) {
            return true;
        }
        return (c.getValueConstraint() == d.getValue());

    }

    /**
     * Checks all restrictions for every cell in the Window Pattern Card
     * @param tempDieToCheckPlacement die to be placed
     * @return true if there is at least one possible placement, false otherwise
     */
    public boolean isPossibleToPlace(Die tempDieToCheckPlacement) {
        for (int i = 0; i < schema.size(); i++) {
            if (checkCorrectDiePlacement(tempDieToCheckPlacement, i, true, true, true)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks all restrictions for a given cell
     * @param die to be placed die
     * @param index destination cell position
     * @param colorRestriction indicates whether the color restrictions have to be checked
     * @param valueRestriction indicates whether the value restrictions have to be checked
     * @param placementRestriction indicates whether the placement restrictions have to be checked
     * @return true if all restrictions are respected, false otherwise
     */
    private boolean checkCorrectDiePlacement(Die die, int index, boolean colorRestriction, boolean valueRestriction, boolean placementRestriction) {
        Cell cell = this.getCell(index);
        if (cell.hasDie()) {
            return false;
        }
        return (!colorRestriction || checkColorRestriction(cell, die)) &&
                (!valueRestriction || checkValueRestriction(cell, die)) &&
                (!placementRestriction || checkPlacementRestriction(cell, die));
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

    public Cell getCell(int row, int column) {
        if (row < 0 || row > 3 || column < 0 || column > 4) {
            throw new WrongCellIndexException();
        }
        return schema.get(row * 5 + column);
    }

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

    private void setDie(Die die, int index) {
        this.schema.get(index).setAssociatedDie(die);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                sb.append(getCell(i, j).toString());
                sb.append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a textual representation of Window Pattern Card dice
     * @return list of dice textual representations
     */
    public List<String> wpcPathRepresentation() {
        List<String> wpcString = new ArrayList<>();
        String pathName = name.replaceAll(" ", "_");
        wpcString.add(pathName);
        for (Cell aSchema : schema) {
            wpcString.add(aSchema.toString());
        }
        return wpcString;
    }
}
