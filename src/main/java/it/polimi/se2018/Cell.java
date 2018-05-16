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

    public Cell(COLOR colorConstraint, int valueConstraint, int index) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = valueConstraint;
        this.associatedDie = null;
        this.index = index;
        }

    public Cell(COLOR colorConstraint, int index) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = null;
        this.associatedDie = null;
        this.index = index;
    }

    public Cell(int valueConstraint, int index) {
        this.colorConstraint = null;
        this.valueConstraint = valueConstraint;
        this.associatedDie = null;
        this.index = index;
    }

    public Cell(int index) {
        this.colorConstraint = null;
        this.valueConstraint = null;
        this.associatedDie = null;
        this.index = index;
    }

    public void setAssociatedDie(Die toBeSetDie){
        this.associatedDie = toBeSetDie;
    }

    public Optional<Die> removeDie(){
        try{
            Die temp = this.associatedDie;
            associatedDie = null;
            return Optional.of(temp);
        }
        catch(NullPointerException e){
            return null;
        }
    }

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
