package server.model;

import shared.exceptions.EmptyCellException;

/**
 * Describes Cell behavior. A cell can be created with or without color and/or value restrictions.
 * A die can be associated with a cell, removed from it or switched with another die.
 * @author Alessio Molinari
 */
public class Cell {
    private COLOR colorConstraint;
    private Integer valueConstraint;
    private Die associatedDie;
    private int index;

    /**
     * Constructor: generates a cell with color and value constraints
     * @param colorConstraint cell color constraint
     * @param valueConstraint cell value constraint
     * @param index cell index (between 0 and 19)
     * */
    public Cell(COLOR colorConstraint, int valueConstraint, int index) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = valueConstraint;
        this.associatedDie = null;
        this.index = index;
        }

    /**
     * Constructor: generates a cell with color constraint
     * @param colorConstraint cell color constraint
     * @param index cell index (between 0 and 19)
     * */
    public Cell(COLOR colorConstraint, int index) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = null;
        this.associatedDie = null;
        this.index = index;
    }


    /**
     * Constructor: generates a cell with value constraint
     * @param valueConstraint cell value constraint
     * @param index cell index (between 0 and 19)
     * */
    public Cell(int valueConstraint, int index) {
        this.colorConstraint = null;
        this.valueConstraint = valueConstraint;
        this.associatedDie = null;
        this.index = index;
    }


    /**
     * Constructor: generates a cell with no constraints
     * @param index cell index (between 0 and 19)
     * */
    public Cell(int index) {
        this.colorConstraint = null;
        this.valueConstraint = null;
        this.associatedDie = null;
        this.index = index;
    }

    /**
     * Constructor: generates a cell with no constraints
     * @param index cell index (between 0 and 19)
     * */
    public Cell(Die associatedDie, int index) {
        this.colorConstraint = null;
        this.valueConstraint = null;
        this.associatedDie = associatedDie;
        this.index = index;
    }

    public void setAssociatedDie(Die toBeSetDie){
        this.associatedDie = toBeSetDie;
    }

    /**
     * Removes the associated die from the cell
     * @return old cell's associated die
     * @throws EmptyCellException if the cell is empty
     * */
    public Die removeDie() throws EmptyCellException{
        if (associatedDie == null){
            throw new EmptyCellException();
        }
        Die temp = this.associatedDie;
        this.associatedDie = null;
        return temp;

    }
    /**
     * Switches the cell's associated die with another one
     * You cannot switch a die with an empty cell
     * @param toSwitchDie new cell's associated die
     * @return old cell's associated die
     * @throws  EmptyCellException if the cell is empty
     * */
    public Die switchDie(Die toSwitchDie) throws EmptyCellException {
        if (associatedDie == null){
            throw new EmptyCellException();
        }
        Die temp = this.associatedDie;
        associatedDie = toSwitchDie;
        return temp;
    }

    @Override
    public String toString(){
        try{
            Die d = getAssociatedDie();
            return d.getColor().toString() + "_" + d.getValue();
        } catch (EmptyCellException e) {
            if (getColorConstraint() != null){
                return getColorConstraint().toString();
            }
            if (getValueConstraint() != null){
                return getValueConstraint().toString();
            }
            return "empty";
        }
    }

    public Die getAssociatedDie() throws EmptyCellException {
        if (associatedDie == null){
            throw new EmptyCellException();
        }
            return this.associatedDie;
    }

    public COLOR getColorConstraint(){
        return colorConstraint;
    }

    public Integer getValueConstraint(){
        return valueConstraint;
    }

    public int getIndex() {
        return index;
    }

    public void setColorConstraint(COLOR colorConstraint) {
        this.colorConstraint = colorConstraint;
    }

    public void setValueConstraint(Integer valueConstraint) {
        this.valueConstraint = valueConstraint;
    }

    /**
     * Checks if the Cell is empty
     * @return true if there is no Die on the cell, false otherwise
     */
    public boolean isEmpty(){
        return this.associatedDie == null;
    }

    /**
     * Checks if there is a die on the Cell
     * @return true if there is a die on the cell, false otherwise
     */
    public boolean hasDie(){
        return this.associatedDie != null;
    }

    public int getRow(){
        return index/5;
    }

    public int getColumn(){
        return index%5;
    }
}
