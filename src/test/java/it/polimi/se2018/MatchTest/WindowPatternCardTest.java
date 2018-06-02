package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Die;
import it.polimi.se2018.Parser.ParserWindowPatternCard;
import it.polimi.se2018.WindowPatternCard;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WindowPatternCardTest {

    private WindowPatternCard myWpc;
    private Die myDie;
    @Before
    public void setUp(){
        ArrayList<WindowPatternCard> mycards = new ArrayList<>();

        ParserWindowPatternCard pwpc = new ParserWindowPatternCard();
        try {
            mycards = pwpc.parseCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myWpc = mycards.get(0);
        myDie = new Die(COLOR.BLUE);
        myDie.setValue(4);
    }

    //TODO DA FARE: NON FUNZIONA BENE IL METODO DI CONTROLLO O IL METODO DI CELL 'getIndex'
    @Test
    public void testPlacement(){
        assertEquals(false, myWpc.placeDie(myDie, 0, 0, false, false, true));
    }

    @Test
    public void testValue(){
        assertEquals(true, myWpc.placeDie(myDie, 0, 0, false, true, false));
    }

    //TODO Da fare: in realtà deve dare True perchè sulla wp0 non ho alcun vincolo colore
    @Test
    public void testColor(){
        assertEquals(false, myWpc.placeDie(myDie, 0, 0, true, false, true));


    }
}
