package it.polimi.se2018;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Describes DiceBag behavior. A die can be extracted from the bag, inserted in it or switch with another one
 * (not in the bag)
 * @author Alessio Molinari, Nives Migotto
 */
public class DiceBag {
    private ArrayList<Die> dice;

    /**
     * Constructor: generates a diceBag with 90 dice, 18 for each of the 5 colors
     * */
    public DiceBag(){
        dice = new ArrayList<>();
        for (int i=0; i<90; i+=5){
            dice.add(new Die(COLOR.RED));
            dice.add(new Die(COLOR.GREEN));
            dice.add(new Die(COLOR.VIOLET));
            dice.add(new Die(COLOR.BLUE));
            dice.add(new Die(COLOR.YELLOW));
        }
    }

    /**
     * Extracts a random die from the bag
     * @return extracted die
     * */
    public Die extractDie(){
        int index = ThreadLocalRandom.current().nextInt(0,  dice.size());
        return dice.remove(index);
    }

    /**
     * Inserts a die in the diceBag (queued in last position)
     * @param die to be inserted in the bag
    */
    public void insertDie(Die die){
        dice.add(die);
    }

    /**
     * Switches a die with a random die in diceBag
     * @param toBeSwitched to be put in the bag die
     * @return extracted from the bag die
     */
    public Die switchDie (Die toBeSwitched){
        int index = ThreadLocalRandom.current().nextInt(0,  dice.size());
        Die temp = dice.get(index);
        dice.set(index, toBeSwitched);
        return temp;
    }

    public ArrayList<Die> getDice() {
        return dice;
    }

    /**
     * Returns how many dice remain in the bag
     * @return DiceBag size
     */
    public int size(){
        return dice.size();
    }
}
