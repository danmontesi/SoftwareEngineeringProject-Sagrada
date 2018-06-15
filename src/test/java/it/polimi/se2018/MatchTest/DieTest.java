package it.polimi.se2018.MatchTest;

import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.model.Die;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Tests Die methods.
 * @author Alessio Molinari
 */
public class DieTest {
    Die die;

    @Before
    public void setUp(){
        die = new Die(COLOR.RED);
    }

    @Test
    public void testRoll(){
        die.roll();
        assertTrue((die.getValue() <= 6) &&(die.getValue()) >=1);
        assertEquals(die.getColor(), COLOR.RED);
    }

    @Test
    public void testFlip(){
        int value = die.getValue();
        die.flip();
        int value2 = die.getValue();
        assertEquals(7-value, value2);
    }

    @Test
    public  void testIncreaseByOne(){
        die.setValue(6);
        die.increaseByOne();
        assertEquals(6, die.getValue());
        die.setValue(1);
        die.increaseByOne();
        assertEquals(2, die.getValue());
    }

    @Test
    public  void testDecreaseByOne(){
        die.setValue(1);
        die.decreaseByOne();
        assertEquals(1, die.getValue());
        die.setValue(6);
        die.decreaseByOne();
        assertEquals(5, die.getValue());
    }
}
