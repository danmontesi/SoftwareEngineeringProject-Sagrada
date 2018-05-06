package it.polimi.se2018;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DraftPool {
    private ArrayList<Die> dice = new ArrayList<>(10);
    private DiceBag diceBag;

    public DraftPool(DiceBag diceBag, int playersNumber) {
        this.diceBag = diceBag;
        for (int i=0; i<playersNumber; i++){
            this.dice.add(diceBag.extractDie());
        }
    }

    public Die takeDie(int diePosition){
        return dice.remove(diePosition);
    }

    //switch a die with a random one
    public Die switchDie(Die toBeSwitched){
        int index = ThreadLocalRandom.current().nextInt(0,  dice.size());
        Die temp = dice.get(index);
        dice.set(index, toBeSwitched);
        return temp;
    }

    //switch a die with a given one
    public Die switchDie(int diePosition, Die toBeSwitched){
        Die temp = dice.get(diePosition);
        dice.set(diePosition, toBeSwitched);
        return temp;
    }

    public void placeDie(Die toBePlaced){
        dice.add(toBePlaced);
    }

    //roll all dice in the DraftPool
    public void rollDice(){
        for (Die aDice : dice) {
            aDice.roll();
        }
    }
}
