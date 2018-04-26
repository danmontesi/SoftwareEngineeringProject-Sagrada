
package it.polimi.se2018;

import java.util.ArrayList;

public class WindowPatternCard {
    private ArrayList<Cell> schema;
    private int difficulty;
    private String name;
    private int cardID;

    public boolean isEmply() {

    }

    /**
     * edit @danmontesi
     * placeDie will return boolean instead of void for usability.
     * In this way, if it returns true (= correct placement), automatically modify permanently the model and call next Turn
     * if it returns false (= incorrect plaement), model doesn't change and i will be able to notify the view of the incorrect
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

    }

    public Die removeDie(int row, int column){

    }

    public Die removeDie(int row, int column){

    }

    public boolean checkPlacementRestriction(){

    }

    public boolean checkValueRestriction(){

    }

    public boolean checkColorRestriction(){

    }

    public int getCardID(){

    }

    public String getCardName(){

    }

    public ArrayList<Cell> getSchema(){

    }

    public int getDifficulty(){

    }


}
