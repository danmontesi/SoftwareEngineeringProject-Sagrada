package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Cell;
import it.polimi.se2018.Die;
import it.polimi.se2018.Exceptions.EmptyCellException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests Cell methods.
 * @author Nives Migotto
 */
public class CellTest {

    private Cell cell;
    private Die die;

    @Before
    public void setUp(){
        die = new Die(COLOR.RED);
        cell = new Cell(0);
        cell.setAssociatedDie(die);
    }

    @Test
    public void testConstructor1(){
        cell = new Cell(COLOR.VIOLET, 3, 0);
        assertEquals(new Integer(3), cell.getValueConstraint());
        assertEquals(COLOR.VIOLET, cell.getColorConstraint());
        assertEquals(0, cell.getIndex());
    }

    @Test
    public void testConstructor2(){
        cell = new Cell(COLOR.GREEN, 17);
        assertEquals(COLOR.GREEN, cell.getColorConstraint());
        assertEquals(17, cell.getIndex());
    }

    @Test
    public void testConstructor3(){
        cell = new Cell(4, 16);
        assertEquals(new Integer(4), cell.getValueConstraint());
        assertEquals(16, cell.getIndex());
    }

    @Test
    public void testRemoveDie(){
        try{
            Die temp = cell.getAssociatedDie();
            Die temp2 = cell.removeDie();
            assertNull(cell.getAssociatedDie());
            assertEquals(temp, temp2);
            cell.removeDie();
            fail();
        } catch (EmptyCellException e){
            assertTrue(true);
        }
    }

    @Test
    public void testSwitchDie(){
        try{
            Die toSwitchDie = new Die(COLOR.YELLOW);
            Die temp = cell.getAssociatedDie();
            Die temp2 = cell.switchDie(toSwitchDie);
            assertEquals(toSwitchDie, cell.getAssociatedDie());
            assertEquals(temp, temp2);
            cell.removeDie();
            cell.switchDie(new Die(COLOR.GREEN));
            fail();
        } catch (EmptyCellException e){
            assertTrue(true);
        }
    }
}
