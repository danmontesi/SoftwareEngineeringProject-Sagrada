package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Cell;
import it.polimi.se2018.Die;
import it.polimi.se2018.Exceptions.EmptyCellException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    public void testRemoveDie(){
        try{
            Die temp = cell.getAssociatedDie();
            Die temp2 = cell.removeDie();
            assertNull(cell.getAssociatedDie());
            assertEquals(temp, temp2);
            cell.removeDie();
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
        } catch (EmptyCellException e){
            assertTrue(true);
        }
        }
}
