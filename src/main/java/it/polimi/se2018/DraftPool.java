package it.polimi.se2018;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class DraftPool {
    private ArrayList<Die> dice;

    //DA RIVEDERE
    public DraftPool() {
        this.dice = null;
    }

    public Die takeDie(int diePosition){
        return dice.remove(diePosition);
    }

    public Die switchDie(int diePosition, Die toBeSwitched){
        int index = ThreadLocalRandom.current().nextInt(0,  dice.size());
        Die temp = dice.get(index);
        dice.set(index, toBeSwitched);
        return temp;
    }

    public void placeDie(Die toBePlaced){
        dice.add(toBePlaced);
    }

    //roll all dice in the DraftPool
    public void rollDice(){
        Iterator<Die> iterator = dice.iterator();
        while(iterator.hasNext()){
            iterator.next().roll();
        }
    }
}
