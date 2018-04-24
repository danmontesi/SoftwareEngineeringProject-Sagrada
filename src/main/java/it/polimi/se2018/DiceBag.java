package it.polimi.se2018;

import java.util.ArrayList;

public class DiceBag {
    private ArrayList<Die> dice;

    public Die extractDie(){
        return dice.remove(0);
    }

    //insert a die in the diceBag
    public void insertDie(Die die){
        dice.add(die);
    }
    //DA IMPLEMENTARE
    public Die switchDie (Die toBeSwitched){
    }
}
