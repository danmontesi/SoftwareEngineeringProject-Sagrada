package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.EmptyCellException;

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

    //ATTENZIONE: E' NECESSARIO CONSIDERARE IL CONTROLLO DEI NUMERI SUI COSTRUTTORI?
    //AD ESEMPIO BISOGNA RESTITUIRE UN'ECCEZIONE SE IL value CONSTRAINT E' 7?

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

    /**
     *
     * @return Die in Cell
     * @throws EmptyCellException if the cell is empty
     */
    public Die getAssociatedDie() throws EmptyCellException {
        if (associatedDie == null){
            throw new EmptyCellException();
        }
            return this.associatedDie;
    }

    /**
     *
     * @return Color constraint for the cell, null if there is no constraint for color
     */
    public COLOR getColorConstraint(){
        return colorConstraint;
    }

    /**
     *
     * @return Value constraint for the cell, null if there is no constraint for value
     */
    public Integer getValueConstraint(){
        return valueConstraint;
    }

    /**
     *
     * @return Cell index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set Cell color constraint
     * @param colorConstraint cell color constraint
     */
    public void setColorConstraint(COLOR colorConstraint) {
        this.colorConstraint = colorConstraint;
    }

    /**
     * Set value constraint for the cell
     * @param valueConstraint cell value constraint
     */
    public void setValueConstraint(Integer valueConstraint) {
        this.valueConstraint = valueConstraint;
    }

    /**
     *
     * @return true if there is no Die on the cell, false otherwise
     */
    public boolean isEmpty(){
        return this.associatedDie == null;
    }

    /**
     *
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
