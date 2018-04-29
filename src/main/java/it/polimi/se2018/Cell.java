package it.polimi.se2018;

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

    public Die removeDie(){
        Die temp = this.associatedDie;
        associatedDie = null;
        return temp;
    }

    public Die switchDie(Die toSwitchDie){
        Die temp = this.associatedDie;
        associatedDie = toSwitchDie;
        return temp;
    }

    public Die getAssociatedDie(){
        return associatedDie;
    }

    public COLOR getColorConstraint(){
        return colorConstraint;
    }

    public int getvalueConstraint(){
        return valueConstraint;
    }

    public int getIndex() {
        return index;
    }
}
