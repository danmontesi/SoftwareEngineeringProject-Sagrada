package it.polimi.se2018;

import java.util.concurrent.ThreadLocalRandom;

public class Die {
    private COLOR color;
    private int value;

    /**
     * Constructor: generates a random value die with given color
     * @param color die color
     */
    public Die(COLOR color) {
        this.color = color;
        this.value = ThreadLocalRandom.current().nextInt(1, 7);
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
}