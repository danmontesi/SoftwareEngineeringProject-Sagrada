package it.polimi.se2018.model;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Describes Die behavior. A die can be flipped or rolled; its value can be increased or decreased by 1.
 * @author Alessio Molinari
 */
public class Die {
    private COLOR color;
    private int value;

    /**
     * Constructor: generates a die with a random value and a given color
     * @param color die color
     */
    public Die(COLOR color) {
        this.color = color;
        this.value = ThreadLocalRandom.current().nextInt(1, 7);
    }

    public Die(COLOR color, int value) {
        this.color = color;
        this.value = value;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public Die(int value) {
        this.value = value;
    }

    /**
     * Sets the die's value to 7 - the die's value (the value on the die's side opposed to the exposed one)
     */
    public void flip(){
        this.value = 7 - this.value;
    }

    /**
     * Rolls the die (gives the die a new random value)
     */
    public void roll(){
        this.value = ThreadLocalRandom.current().nextInt(1, 7);
    }

    /**
     * Increases the die's value by 1
     */
    public void increaseByOne(){
        if (this.value < 6){
            this.value +=1;
        }
    }

    /**
     * Decreases the die's value by 1
     */
    public void decreaseByOne(){
        if (this.value > 1){
            this.value -=1;
        }
    }

    public void setValue(int value){
        this.value = value;
    }

    public COLOR getColor(){
        return color;
    }

    public int getValue(){
        return value;
    }

    public String toString(){
        String toBePrinted = color.toString();
        Integer i = value;
        toBePrinted = toBePrinted + "num:" + i.toString();
        return toBePrinted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Die die = (Die) o;
        return value == die.value &&
                color == die.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }
}