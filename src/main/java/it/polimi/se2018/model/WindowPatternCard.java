
package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.exceptions.WrongCellIndexException;

import java.util.ArrayList;

/**
 * Describes WindowPatternCard behavior. Dice can be placed on it, after checking restrictions.
 * @author Alessio Molinari
 */
public class WindowPatternCard {
    private ArrayList<Cell> schema;
    private int difficulty;
    private String name;

    public WindowPatternCard(ArrayList<Cell> schema, int difficulty, String name) {
        this.schema = schema;
        this.difficulty = difficulty;
        this.name = name;
    }

    /**
     * Create an empty WindowPatternCard with plain numbered cells from 0 to 19
     */
    public WindowPatternCard() {
        this.difficulty = 0;
        this.name = null;
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cells.add(new Cell(i));
        }
        this.schema = cells;
    }

    public boolean isEmpty() {
        for (Cell cell : schema){
            if (cell.hasDie()){
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
     * @param d                    to be placed die
     * @param row                  cell row number
     * @param column               cell column number
     * @param colorRestriction     cell color restriction
     * @param valueRestriction     cell value restriction
     * @param placementRestriction placement restriction
     * @return true if restrictions are respected and die is place, false otherwise
     */
    public boolean placeDie(Die d, int row, int column, boolean colorRestriction, boolean valueRestriction,
                            boolean placementRestriction) {
        if (this.getCell(row, column).hasDie()) {
            return false;
        }
        if (colorRestriction) {
            if (!checkColorRestriction(this.getCell(row, column), d)) {
                return false;
            }
        }
        if (valueRestriction) {
            if (!checkValueRestriction(this.getCell(row, column), d)) {
                return false;
            }
        }
        if (placementRestriction) {
            if (!checkPlacementRestriction(getCell(row, column), d)) {
                return false;
            }
        }
        this.getCell(row, column).setAssociatedDie(d);
        return true;
    }

    /**
     * Overloaded method for ordinary moves (without toolcards)
     * @param d Die to be placed
     * @param row Cell row
     * @param column Cell column
     * @return true if the move is valid
     */
    public boolean placeDie(Die d, int row, int column){
        Cell c = this.getCell(row, column);
        if(c.hasDie()){
            return false;
        }
        if(checkColorRestriction(c, d) && checkPlacementRestriction(c, d) && checkValueRestriction(c, d)){
            c.setAssociatedDie(d);
            return true;
        } else {
            return false;
        }
    }

    public boolean switchDie(int oldPosition, int newPosition, boolean colorRestriction, boolean valueRestriction,
                            boolean placementRestriction) throws EmptyCellException {
        Die d = this.getCell(oldPosition).getAssociatedDie();
        if (this.getCell(newPosition).hasDie()) {
            return false;
        }
        if (colorRestriction) {
            if (!checkColorRestriction(this.getCell(newPosition), d)) {
                return false;
            }
        }
        if (valueRestriction) {
            if (!checkValueRestriction(this.getCell(newPosition), d)) {
                return false;
            }
        }
        if (placementRestriction) {
            if (!checkPlacementRestriction(getCell(newPosition), d)) {
                return false;
            }
        }
        this.getCell(newPosition).setAssociatedDie(this.getCell(oldPosition).removeDie());
        return true;
    }

    public boolean move2Dice(int oldPosition1, int newPosition1, int oldPosition2, int newPosition2, boolean colorRestriction, boolean valueRestriction,
                             boolean placementRestriction) throws EmptyCellException {
        Die d = this.getCell(oldPosition1).getAssociatedDie();
        if (this.getCell(newPosition1).hasDie()) {
            return false;
        }
        if (colorRestriction) {
            if (!checkColorRestriction(this.getCell(newPosition1), d)) {
                return false;
            }
        }
        if (valueRestriction) {
            if (!checkValueRestriction(this.getCell(newPosition1), d)) {
                return false;
            }
        }
        if (placementRestriction) {
            if (!checkPlacementRestriction(getCell(newPosition1), d)) {
                return false;
            }
        }

        Die d2 = this.getCell(oldPosition2).getAssociatedDie();
        if (this.getCell(newPosition2).hasDie()) {
            return false;
        }
        if (colorRestriction) {
            if (!checkColorRestriction(this.getCell(newPosition2), d2)) {
                return false;
            }
        }
        if (valueRestriction) {
            if (!checkValueRestriction(this.getCell(newPosition2), d2)) {
                return false;
            }
        }
        if (placementRestriction) {
            if (!checkPlacementRestriction(getCell(newPosition2), d2)) {
                return false;
            }
        }

        this.getCell(newPosition1).setAssociatedDie(this.getCell(oldPosition1).removeDie());
        this.getCell(newPosition2).setAssociatedDie(this.getCell(oldPosition2).removeDie());
        return true;
    }

    public Die removeDie(int row, int column) throws EmptyCellException {
        return schema.get(row * 5 + column).removeDie();
    }

    public boolean checkPlacementRestriction(Cell c, Die d) {
        int column = c.getColumn();
        int row = c.getRow();
        COLOR color = d.getColor();
        int value = d.getValue();
        if (this.isEmpty()){
            if (row == 0 || row == 3 || column == 0 || column == 4){
                return true;
            } else {
                return false;
            }
        } else {
            return (checkAdjacents(c, d) && checkColorsAndValues(c, d));
        }
    }

    public boolean checkAdjacents(Cell c, Die d){
        int row = c.getRow();
        int column = c.getColumn();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(i!= 0 || j != 0){
                    try{
                        if(this.getCell(row + i, column + j).hasDie()){
                            return true;
                        }
                    } catch (WrongCellIndexException e){
                        continue;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkColorsAndValues(Cell c, Die d){
        int row = c.getRow();
        int column = c.getColumn();
        int value = d.getValue();
        COLOR color = d.getColor();

        for(int i = -1; i <= 1; i = i+2){
            try{
                Die check = this.getCell(row + i, column).getAssociatedDie();
                if (check.getValue() == (value) || check.getColor().equals(color)){
                    return false;
                }
            } catch (EmptyCellException | WrongCellIndexException e){
                continue;
            }
        }
        for(int i = -1; i <= 1; i = i+2){
            try{
                Die check = this.getCell(row, column + i).getAssociatedDie();
                if (check.getValue() == (value) || check.getColor().equals(color)){
                    return false;
                }
            } catch (EmptyCellException | WrongCellIndexException e){
                continue;
            }
        }
        return true;
    }



    public boolean checkColorRestriction(Cell c, Die d) {
        if (c.getColorConstraint() == null) {
            return true;
        }
        return (c.getColorConstraint().equals(d.getColor()));
    }

    public boolean checkValueRestriction(Cell c, Die d) {
        if (c.getValueConstraint() == null) {
            return true;
        }
        return (c.getValueConstraint() == d.getValue());

    }

/*    public boolean checkAllValueRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null)&&(temp.getAssociatedDie().get().getValue() != temp.getValueConstraint())){
                return false;
            }
        }
        return true;
    }*/

 /*   public boolean checkAllColorRestriction(){
        Iterator<Cell> iterator = schema.iterator();
        while(iterator.hasNext()){
            Cell temp = iterator.next();
            if ((temp.getAssociatedDie() != null) && !(temp.getAssociatedDie().get().getColor().equals(temp.getColorConstraint()))){
                return false;
            }
        }
        return true;
    }*/

    public String getCardName() {
        return this.name;
    }

    public ArrayList<Cell> getSchema() {
        return this.schema;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     *
     * Retrieve cell by row andd column
     * Throws unchecked WrongCellIndexException
     * @throws WrongCellIndexException
     * @param row
     * @param column
     * @return
     */
    public Cell getCell(int row, int column) {
        if (row < 0 || row > 3 || column < 0 || column > 4){
            throw new WrongCellIndexException();
        }
        return schema.get(row * 5 + column);
    }

    //retrieve cell by linear index
    public Cell getCell(int index) {
        return schema.get(index);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchema(ArrayList<Cell> schema) {
        this.schema = schema;
    }

    public String toString() {
        String printedCard = "\n - " + name + " - \n";
        for (int i = 0; i < schema.size(); i++) {
            try {
                if (schema.get(i).isEmpty()) {
                    if (schema.get(i).getColorConstraint()!=null)
                        printedCard+=schema.get(i).getColorConstraint().toString();
                    else if (schema.get(i).getValueConstraint()!=null)
                        printedCard+=schema.get(i).getValueConstraint().toString();
                    else
                        printedCard+= "noDie";
                } else {
                    printedCard += schema.get(i).getAssociatedDie().getColor() + "," + schema.get(i).getAssociatedDie().getValue();
                }
            } catch (EmptyCellException e) {
                e.printStackTrace();
            }
            if ((i + 1) % 5 == 0) {
                printedCard += "\n";
            }
            printedCard += "\t";
        }
        return printedCard;
    }

    /**
     * Representation of the patch of the whole Wpc. Useful for GUI
     * @return List of path last name
     */
    public ArrayList<String> wpcPathRepresentation(){
            ArrayList<String> wpcString = new ArrayList<>();
            String pathName = name.replaceAll(" ", "_");
            wpcString.add(pathName);
            for (int i = 0; i < schema.size(); i++) {
                try {
                    if (schema.get(i).isEmpty()) {
                        if (schema.get(i).getColorConstraint()!=null){
                            wpcString.add( schema.get(i).getColorConstraint().toString());   //COLOR constaint has just the "constr" color name. casn use ".contains"_" to know if there is a restriction
                        }
                        else if (schema.get(i).getValueConstraint()!=null){
                            wpcString.add( schema.get(i).getValueConstraint().toString());   //VALUE constaint has just the "constr" + value name
                        }
                        else {
                            wpcString.add("empty");
                        }
                    } else {
                        wpcString.add(schema.get(i).getAssociatedDie().getColor().toString() + "_" + schema.get(i).getAssociatedDie().getValue());
                    }
                } catch (EmptyCellException e) {
                    e.printStackTrace();
                    System.out.println("Error: cell is empty");
                }
            }
            return wpcString;
        }

}
