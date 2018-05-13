package it.polimi.se2018;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DiceBag {
    private ArrayList<Die> dice;
    private static DiceBag instance = null;

    //Singleton
    public static DiceBag getInstance(){
        if (instance == null) {
            instance = new DiceBag();
        }
            return instance;
    }

    private DiceBag(){
        dice = new ArrayList<>();
        for (int i=0; i<90; i+=5){
            dice.add(new Die(COLOR.RED));
            dice.add(new Die(COLOR.GREEN));
            dice.add(new Die(COLOR.VIOLET));
            dice.add(new Die(COLOR.BLUE));
            dice.add(new Die(COLOR.YELLOW));
        }
    }

    //extract a random die from the bag
    public Die extractDie(){
        int index = ThreadLocalRandom.current().nextInt(0,  dice.size());
        return dice.remove(index);
    }

    //insert a die in the diceBag (queued in last position)
    public void insertDie(Die die){
        dice.add(die);
    }

    //switch a toBeSwitched die with a random die in diceBag
    public Die switchDie (Die toBeSwitched){
        int index = ThreadLocalRandom.current().nextInt(0,  dice.size());
        Die temp = dice.get(index);
        dice.set(index, toBeSwitched);
        return temp;
    }
}
