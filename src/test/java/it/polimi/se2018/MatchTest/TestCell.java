package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Cell;
import it.polimi.se2018.Die;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCell {

    private Cell cell;
    private Die die;

    @Before
    public void setUp(){
        die = Die.getInstance(COLOR.RED);
        cell = new Cell();
        cell.setAssociatedDie(die);
    }

    @Test
    public void testRemoveDie(){
        Die temp = cell.getAssociatedDie();
        Die temp2 = cell.removeDie();
        assertNull(cell.getAssociatedDie());
        assertEquals(temp, temp2);
    }

    @Test
    public void testSwitchDie(){
        Die toSwitchDie = Die.getInstance(COLOR.YELLOW);
        Die temp = cell.getAssociatedDie();
        Die temp2 = cell.switchDie(toSwitchDie);
        assertEquals(toSwitchDie, cell.getAssociatedDie());
        assertEquals(temp, temp2);
    }
}
