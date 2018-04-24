package it.polimi.se2018;

public class Cell {
    private COLOR colorConstraint;
    private int numberConstraint;
    private Die associatedDie;

    public void setAssociatedDie(Die toBeSetDie){
        this.associatedDie = toBeSetDie;
    }

    public Die removeDie(){

    }

    public Die swipeDie(Die toSwipeDie){

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
