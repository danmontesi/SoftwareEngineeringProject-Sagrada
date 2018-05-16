package it.polimi.se2018;

import java.util.Optional;

public class Cell {
    private COLOR colorConstraint;
    private Integer valueConstraint;
    private Die associatedDie;
    private int index;

    //Ale:
    //ATTENZIONE: Ho cambiato numberConstraint con valueConstraint per coerenza con le altre classi

    //ATTENZIONE: E' NECESSARIO CONSIDERARE IL CONTROLLO DEI NUMERI SUI COSTRUTTORI
    //AD ESEMPIO BISOGNA RESTITUIRE UN'ECCEZIONE SE IL value CONSTRAINT E' 7

    /**
     * Constructor: generates a cell with color and value constraints
     * @param colorConstraint cell color constraint
     * @param valueConstraint cell value constraint
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
     * */
    public Cell(int valueConstraint, int index) {
        this.colorConstraint = null;
        this.valueConstraint = valueConstraint;
        this.associatedDie = null;
        this.index = index;
    }


    /**
     * Constructor: generates a cell with no constraints
     * */
    public Cell(int index) {
        this.colorConstraint = null;
        this.valueConstraint = null;
        this.associatedDie = null;
        this.index = index;
    }

    public void setAssociatedDie(Die toBeSetDie){
        this.associatedDie = toBeSetDie;
    }

    /**
     * Removes the associated die from the cell
     * @return old cell's associated die
     * @throws NullPointerException if the removed die is null
     * */
    public Optional<Die> removeDie(){
        try{
            Die temp = this.associatedDie;
            associatedDie = null;
            return Optional.of(temp);
        }
        catch(NullPointerException e){
            return Optional.empty();
        }
    }
    /**
     * Switches the cell's associated die with another one
     * @param toSwitchDie new cell's associated die
     * @return old cell's associated die
     * */
    public Die switchDie(Die toSwitchDie){
        Die temp = this.associatedDie;
        associatedDie = toSwitchDie;
        return temp;
    }

    public Optional<Die> getAssociatedDie(){
        try{
            return Optional.of(associatedDie);
        }
        catch (NullPointerException e){
            return Optional.empty();
        }
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
}
