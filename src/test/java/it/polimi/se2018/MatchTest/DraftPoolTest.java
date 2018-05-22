package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.DiceBag;
import it.polimi.se2018.Die;
import it.polimi.se2018.DraftPool;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests DraftPool methods.
 * @author Alessio Molinari
 */
public class DraftPoolTest {

    DraftPool dp;
    @Before
    public void setUp(){
        dp = new DraftPool(DiceBag.getInstance(), 4);
    }

    @Test
    public void draftPoolTest(){
        assertEquals(9, dp.draftPoolSize());
        //assertEquals(81, DiceBag.getInstance().size());
        DraftPool dp2 = new DraftPool(DiceBag.getInstance(), 2);
        //assertEquals(76, DiceBag.getInstance().size());
    }

    @Test
    public void testRoll(){
        dp.rollDice();
        assertEquals(9, dp.draftPoolSize());
    }

    @Test
    public void testTakeDie(){
        Die die = dp.takeDie(5);
        assertEquals(8, dp.draftPoolSize());
        Die die1 = dp.takeDie(5);
        assertEquals(7, dp.draftPoolSize());
    }

    @Test
    public void testSwitchDie(){
        dp.switchDie(new Die(COLOR.YELLOW));
        assertEquals(9, dp.draftPoolSize());
        dp.switchDie(4, new Die(COLOR.GREEN));
        assertEquals(COLOR.GREEN, dp.getDie(4).getColor());
    }

    @Test
    public void testPlaceDie(){
        dp.placeDie(new Die(COLOR.GREEN));
        assertEquals(10, dp.draftPoolSize());
    }
}
