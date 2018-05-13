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

    public Cell(COLOR colorConstraint, int valueConstraint) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = valueConstraint;
        }

    public Cell(COLOR colorConstraint) {
        this.colorConstraint = colorConstraint;
        this.valueConstraint = null;
    }

    public Cell(int valueConstraint) {
        this.colorConstraint = null;
        this.valueConstraint = valueConstraint;
    }

    public Cell() {
        this.colorConstraint = null;
        this.valueConstraint = null;
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
            return Optional.empty();
        }
    }

    public Die switchDie(Die toSwitchDie){
        Die temp = this.associatedDie;
        associatedDie = toSwitchDie;
        return temp;
    }

    public Optional<Die> getAssociatedDie(){
        return Optional.of(associatedDie);
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
}
