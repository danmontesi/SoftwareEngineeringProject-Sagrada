package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Die;
import it.polimi.se2018.RoundTrack;
import org.junit.Before;
import org.junit.Test;

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
    public void removeDieTest(){
        rt.removeDie(1);
        rt.removeDie(5);
        assertEquals(rt.getRoundCells().size(), 1);
    }

    @Test
    public void switchDieTest(){


    }
    @Test
    public void placeDieTest(){
        assertEquals(rt.getRoundCells().size(), 2);
    }

    @Test
    public void getDieTest(){

    }
}
