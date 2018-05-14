package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Die;
import it.polimi.se2018.RoundTrack;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RoundTrackTest{
    RoundTrack rt;

    @Before
    public void setUp(){
        rt = new RoundTrack();
        rt.placeDie(new Die(COLOR.GREEN));
        rt.placeDie(new Die(COLOR.RED));
    }

    @Test
    public void setUpTest(){
        assertEquals(10, rt.getRoundCells().size());
        assertEquals(Optional.empty(), rt.getDie(3));
    }

    @Test
    public void removeDieTest(){
        rt.removeDie(1);
        rt.removeDie(5);
        assertEquals(1, rt.diceInTrack());
    }

    @Test
    public void switchDieTest(){


    }
    @Test
    public void placeDieTest(){
        assertEquals(2, rt.diceInTrack());
    }

    @Test
    public void getDieTest(){

    }
}
