package it.polimi.se2018;

public class Cell {
    private COLOR colorConstraint;
    private Integer numberConstraint;
    private Die associatedDie;

    //ATTENZIONE: E' NECESSARIO CONSIDERARE IL CONTROLLO DEI COLORI E DEI NUMERI SUI COSTRUTTORI
    //AD ESEMPIO BISOGNA RESTITUIRE UN'ECCEZIONE SE IL NUMBER CONSTRAINT E' 7 O SE IL COLOR E' ORANGE

    public Cell(COLOR colorConstraint, int numberConstraint) {
        this.colorConstraint = colorConstraint;
        this.numberConstraint = numberConstraint;
        }

    public Cell(COLOR colorConstraint) {
        this.colorConstraint = colorConstraint;
        this.numberConstraint = null;
    }

    public Cell(int numberConstraint) {
        this.colorConstraint = null;
        this.numberConstraint = numberConstraint;
    }

    public Cell() {
        this.colorConstraint = null;
        this.numberConstraint = null;
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

    public int getNumberConstraint(){
        return numberConstraint;
    }
}
