package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Cell;
import it.polimi.se2018.Die;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CellTest {

    private Cell cell;
    private Die die;

    @Before
    public void setUp(){
        die = new Die(COLOR.RED);
        cell = new Cell();
        cell.setAssociatedDie(die);
    }

    @Test
    public void testRemoveDie(){
        Die temp = cell.getAssociatedDie().get();
        Optional<Die> temp2 = cell.removeDie();
        assertNull(cell.getAssociatedDie());
        assertEquals(temp, temp2);
    }

    @Test
    public void testSwitchDie(){
        Die toSwitchDie =new Die(COLOR.YELLOW);
        Die temp = cell.getAssociatedDie().get();
        Die temp2 = cell.switchDie(toSwitchDie);
        assertEquals(toSwitchDie, cell.getAssociatedDie());
        assertEquals(temp, temp2);
    }
}
