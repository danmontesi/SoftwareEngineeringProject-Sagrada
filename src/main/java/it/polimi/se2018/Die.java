package it.polimi.se2018;

import java.util.concurrent.ThreadLocalRandom;

public class Die {
    private int id;
    private COLOR color;
    private int value;

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

    public int getId(){
        return id;
    }

    public COLOR getColor(){
        return color;
    }

    public int getValue(){
        return value;
    }
}
