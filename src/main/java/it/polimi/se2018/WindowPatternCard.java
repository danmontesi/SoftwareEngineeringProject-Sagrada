
package it.polimi.se2018;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class WindowPatternCard {
    private ArrayList<Cell> schema;
    private int difficulty;
    private String name;

    public WindowPatternCard(ArrayList<Cell> schema, int difficulty, String name) {
        this.schema = schema;
        this.difficulty = difficulty;
        this.name = name;
    }

    public boolean isEmpty() {
        return this.schema.isEmpty();
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
    public Optional<Die> removeDie(int row, int column){
        return schema.get(row*5+column).removeDie();
    }

    public boolean checkPlacementRestriction(WindowPatternCard w, Cell c){ // 'w' can be replaced with 'this'
        int index = c.getIndex();
        if (w.isEmpty()) {
            // if cell is internal -> false
            if (((index % 5) > 0) && ((index % 5) < 4)
                    && ((index / 5) > 0) && ((index / 5) < 3)) {
                return false;
            } else
                return true;

        }
        /**
         * edit @danmontesi
         * Instead of considering an Array of int, consider an ArrayList when I insert new numbers of adjacent
         * adjacent are 8 only if the cell is internal (and so the cell passes all the controls)
         *
         * if i find at least an adjacent -> return true
         */
        ArrayList<Integer> adjacentCells = new ArrayList<>();

        // 6 chained if clauses to determine if exists an adjacent cell

        if (index/5 > 0){
            adjacentCells.add(index/5 -1 + index%5);
        }
        if (index/5 > 0 && index%5 > 0){
            adjacentCells.add(index/5 -1 + index%5 -1 );
            adjacentCells.add(index/5  + index - 1 );
        }
        if (index/5 > 0 && index%5 < 4 ){
            adjacentCells.add(index/5 -1 + index%5 + 1 );
            adjacentCells.add(index/5  + index%5 + 1 );
        }
        if (index/5 < 3 ){
            adjacentCells.add(index/5 +1 + index%5);
        }
        if (index/5 < 3 && index%5 > 0){ // oss: sto inserendo stessi indici multipli, però non comporta errori (farò il controllo solamente 2 volte in + )
            adjacentCells.add(index/5 +1 + index%5 -1 );
            adjacentCells.add(index/5  + index - 1 );
        }
        if (index/5 < 3 && index%5 < 4 ){ // vedi commento prec
            adjacentCells.add(index/5 +1 + index%5 + 1 );
            adjacentCells.add(index/5  + index%5 + 1 );
        }

        for (int adjIndex : adjacentCells){
            if(this.schema.get(adjIndex).getAssociatedDie() != null){
                return true;
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
        if (c.getValueConstraint() == null) { return true; }
        if(c.getValueConstraint() == d.getValue()){
            return true;
        }
        else
            return false;
    }

    public boolean checkAllValueRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null)&&(temp.getAssociatedDie().get().getValue() != temp.getValueConstraint())){
                return false;
            }
        }
        return true;
    }

    public boolean checkAllColorRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null) && !(temp.getAssociatedDie().get().getColor().equals(temp.getColorConstraint()))){
                return false;
            }
        }
        return true;
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
