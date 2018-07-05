package it.polimi.se2018.MatchTest;

import shared.exceptions.EmptyCellException;
import server.model.COLOR;
import server.model.Die;
import server.model.RoundTrack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Alessio Molinari
 */
public class RoundTrackTest {
    private RoundTrack rt;

    @Before
    public void setUp() {
        rt = new RoundTrack();
        rt.placeDie(new Die(COLOR.GREEN, 5));
        rt.placeDie(new Die(COLOR.RED, 3));
    }

    @Test
    public void setUpTest() {
        assertEquals(10, rt.getRoundCells().size());
        assertTrue(rt.getCell(2).isEmpty());
    }

    @Test
    public void removeDie() {
        try {
            rt.removeDie(1);
            rt.removeDie(5);
            assertEquals(1, rt.diceInTrack());
        } catch (EmptyCellException e) {
        }
    }

    @Test
    public void switchDie(){
        try {
            rt.switchDie(0, new Die(COLOR.BLUE, 1));
            assertFalse(rt.isPresent(COLOR.GREEN));
            assertEquals(new Die(COLOR.BLUE, 1), rt.getDie(0));
        } catch (EmptyCellException e) {
            Assert.fail();
        }
    }

    @Test
    public void wrongSwitchDie() {
        try {
            rt.switchDie(7, new Die(COLOR.BLUE, 1));
            Assert.fail();
        } catch (EmptyCellException e) {
            //correct catch
        }
    }

    @Test
    public void isPresent() {
        assertTrue(rt.isPresent(COLOR.RED));
    }

    @Test
    public void shouldNotBePresent(){
        assertFalse(rt.isPresent(COLOR.BLUE));
        assertFalse(rt.isPresent(COLOR.VIOLET));
    }

    @Test
    public void correctRepresentation(){
        List<String> representation;
        representation = rt.roundtrackPathRepresentation();
        assertEquals("GREEN_5", representation.get(0));
        assertEquals("empty", representation.get(3));
    }


    @Test
    public void placeDie() {
        assertEquals(2, rt.diceInTrack());
    }

    @Test
    public void getDie() {
        try {
            assertNotNull(rt.getDie(0));
            rt.getDie(4);
        } catch (EmptyCellException e) {
            assertTrue(true);
        }
    }
}
