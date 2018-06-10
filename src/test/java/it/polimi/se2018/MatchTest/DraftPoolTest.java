package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.DiceBag;
import it.polimi.se2018.Die;
import it.polimi.se2018.DraftPool;
import it.polimi.se2018.exceptions.EmptyCellException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests DraftPool methods.
 * @author Alessio Molinari
 */
public class DraftPoolTest {

    DraftPool dp;

    @Before
    public void setUp(){
        ArrayList<Die> dice = new ArrayList<>();
        dice.add(new Die(COLOR.BLUE));
        dice.add(new Die(COLOR.GREEN));
        dice.add(new Die(COLOR.BLUE));
        dice.add(new Die(COLOR.RED));
        dice.add(new Die(COLOR.BLUE));
        dp = new DraftPool(dice);
    }

    @Test
    public void draftPoolTest(){
        assertEquals(5, dp.draftPoolSize());
        //assertEquals(81, DiceBag.getInstance().size());
        ArrayList<Die> dice = new ArrayList<>();
        dice.add(new Die(COLOR.BLUE));
        dice.add(new Die(COLOR.GREEN));
        dice.add(new Die(COLOR.BLUE));
        dice.add(new Die(COLOR.RED));
        dice.add(new Die(COLOR.BLUE));
        DraftPool dp2 = new DraftPool(dice);
        //assertEquals(76, DiceBag.getInstance().size());
    }

    @Test
    public void testRoll() throws EmptyCellException {
        dp.rollDice();
        assertEquals(5, dp.draftPoolSize());
    }

    @Test
    public void testTakeDie() throws EmptyCellException {
        Die die = dp.takeDie(0);
        assertEquals(4, dp.draftPoolSize());
        Die die1 = dp.takeDie(2);
        assertEquals(3, dp.draftPoolSize());
    }

    @Test
    public void testSwitchDie() throws EmptyCellException {
        dp.switchDie(new Die(COLOR.YELLOW));
        assertEquals(3, dp.draftPoolSize());
        dp.switchDie(3, new Die(COLOR.GREEN));
        assertEquals(COLOR.GREEN, dp.getDie(4).getColor());
    }

    @Test
    public void testPlaceDie(){
        dp.placeDie(4, new Die(COLOR.GREEN));
        assertEquals(4, dp.draftPoolSize());
    }
}
