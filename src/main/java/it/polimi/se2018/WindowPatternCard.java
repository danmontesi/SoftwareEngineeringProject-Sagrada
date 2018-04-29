
package it.polimi.se2018;

import java.util.ArrayList;
import java.util.Iterator;

public class WindowPatternCard {
    private ArrayList<Cell> schema;
    private int difficulty;
    private String name;
    private int cardID;

    public boolean isEmpty() {
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getAssociatedDie() != null){
                return false;
            }
        }
        return true;
    }

    /**
     * edit @danmontesi
     * placeDie will return boolean instead of void for usability.
     * In this way, if it returns true (= correct placement), automatically modify permanently the model and call next Turn
     * if it returns false (= incorrect placement), model doesn't change and i will be able to notify the view of the incorrect
     * move and restart the player's turn
     *
     * @param d
     * @param row
     * @param column
     * @param colorRestriction
     * @param valueRestriction
     * @param placementRestriction
     * @return
     */
    public boolean placeDie(Die d, int row, int column, boolean colorRestriction, boolean valueRestriction,
    boolean placementRestriction){
        if (this.getCell(row,column).getAssociatedDie()!= null) { return false; }
        if (colorRestriction) {
            if (!checkColorRestricion(this.getCell(row, column), d)) {
                return false;
            }
        }
        if (valueRestriction){
            if (!checkValueRestriction(this.getCell(row, column), d)) {
                return false;
            }
        }
        if (placementRestriction){
            if (!checkPlacementRestriction(this, this.getCell(row, column))) {
                return false;
            }
        }
        this.getCell(row, column).setAssociatedDie(d);
        return true;
    }

    //What if the index is not valid? -> The controller checks it :)
    public Die removeDie(int row, int column){
        return schema.get(row*5+column).removeDie();
    }

    //TODO: funziona solo per le caselle interne...
    public boolean checkPlacementRestriction(WindowPatternCard w, Cell c){ // 'w' can be replaced with 'this'
        if (w.isEmpty()){
            int index = c.getIndex();
            // if cell is internal -> false
            if( ( (index%5) >= 1 ) && ((index%5) <= 3)
                    && ((index/5) >= 1 ) && ((index/5)<= 4) ){
                return false;
            }
            else
                return true;
        }

        int adjacents[] = new int[8];
        int index = c.getIndex();
        adjacents[0] = index-6;
        adjacents[1] = index-5;
        adjacents[2] = index-4;
        adjacents[3] = index-1;
        adjacents[4] = index+1;
        adjacents[5] = index+4;
        adjacents[6] = index+5;
        adjacents[7] = index+6;

        for (int i = 0; i < 8; i++){
            if((adjacents[i] >= 0)&&(adjacents[i] <=19)){
                if(this.schema.get(i).getAssociatedDie() != null){
                    return true;
                }
            }
        }
        return false;
        
    }

    public boolean checkColorRestricion(Cell c, Die d){
        if (c.getColorConstraint()==null) { return true; }
        if(c.getColorConstraint().equals(d.getColor())){
            return true;
        }
        else
            return false;
    }

    public boolean checkValueRestriction(Cell c, Die d){
        if (c.getvalueConstraint() == null) { return true; }
        if(c.getvalueConstraint() == d.getValue()){
            return true;
        }
        else
            return false;
    }

    public boolean checkAllValueRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null)&&(temp.getAssociatedDie().getValue() != temp.getvalueConstraint())){
                return false;
            }
        }
        return true;
    }

    public boolean checkAllColorRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null) && !(temp.getAssociatedDie().getColor().equals(temp.getColorConstraint()))){
                return false;
            }
        }
        return true;
    }

    public int getCardID(){
        return this.cardID;
    }

    public String getCardName(){
        return this.name;
    }

    public ArrayList<Cell> getSchema(){
        return this.schema;
    }

    public int getDifficulty(){
        return this.difficulty;
    }

    //retrieve cell by row and column
    public Cell getCell(int row, int column){
        return schema.get(row*5 + column);
    }

    //retrieve cell by linear index
    public Cell getCell(int index){
        return schema.get(index);
    }
}
