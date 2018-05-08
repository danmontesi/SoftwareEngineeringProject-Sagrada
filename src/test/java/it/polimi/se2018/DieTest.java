package it.polimi.se2018;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class DieTest {
    Die die;
    @Before
    public void setUp(){
        die = Die.getInstance(COLOR.RED);
    }

    @Test
    public void testRoll(){
        die.roll();
        assertTrue((die.getValue() <= 6) &&(die.getValue()) >=1);
        assertTrue(die.getColor().equals(COLOR.RED));
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

    @Test
    public void testGetInstance(){
        for (int i = 0; i < 13; i++){
            assertNotNull(die = Die.getInstance(COLOR.RED));
        }
        assertNull(die = Die.getInstance(COLOR.RED));
    }
}
