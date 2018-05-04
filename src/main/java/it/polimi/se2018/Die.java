package it.polimi.se2018;

import java.util.concurrent.ThreadLocalRandom;

public class Die {
    private COLOR color;
    private int value;
    private static int[] counts = new int[5];


    //build a die only if permitted by rules
    //returns null if a die cannot be created
    //TODO: THIS METHOD HAS TO THROW A BIG EXCEPTION :)
    public static Die getInstance(COLOR color){
        if (counts[color.ordinal()] < 18){
            counts[color.ordinal()] += 1;
            return new Die(color);
        }
        return null;
    }

    //generate a random value die with given color
    private Die(COLOR color) {
        this.color = color;
        this.value = ThreadLocalRandom.current().nextInt(1, 7);
    }

    public void flip(){
        this.value = 7 - this.value;
    }

    public void roll(){
        this.value = ThreadLocalRandom.current().nextInt(1, 7);
    }

    public void increaseByOne(){
        if (this.value < 6){
            this.value +=1;
        }
    }

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