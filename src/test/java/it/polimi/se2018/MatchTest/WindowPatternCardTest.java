package it.polimi.se2018.MatchTest;

import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Author: Alessio
 */
public class WindowPatternCardTest {

    private WindowPatternCard wpc;

    @Before
    public void setUp(){
        List<WindowPatternCard> myCards;

        ParserWindowPatternCard pwpc;
        try {
            pwpc = new ParserWindowPatternCard();
            //tests on WindowPatternCard: Via Lux
            myCards = pwpc.parseAllCards();
            wpc = myCards.get(1);
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void wpcConstructorWith(){
        List<Cell> cellSchema = new ArrayList<>();
        cellSchema.add(new Cell(0));
        wpc = new WindowPatternCard(cellSchema, 4, "WPC di Prova");
        assertEquals(1, wpc.getSchema().size());
    }

    @Test
    public void printRepresentation(){
        System.out.println(wpc.wpcPathRepresentation());
    }

    @Test
    public void setOnSide(){
        assertTrue(wpc.placeDie(new Die(COLOR.YELLOW, 3), 0, 0));
    }

    @Test
    public void wrongFirstSet(){
        assertFalse(wpc.placeDie(new Die(COLOR.YELLOW, 3), 1, 3));
    }

    @Test
    public void setDieAdjacentToAnother(){
        wpc.placeDie(new Die(COLOR.YELLOW, 3), 0, 3);
        assertTrue(wpc.placeDie(new Die(COLOR.RED, 4), 1, 3));
    }

    @Test
    public void setDieNotAdjacentToAnother(){
        wpc.placeDie(new Die(COLOR.YELLOW, 3), 0, 3);
        assertFalse(wpc.placeDie(new Die(COLOR.VIOLET, 3), 2, 3));
    }

    @Test
    public void setDieAdjacentToAnotherButSameColor(){
        wpc.placeDie(new Die(COLOR.YELLOW, 3), 0, 4);
        assertFalse(wpc.placeDie(new Die(COLOR.YELLOW, 4), 0, 3));
    }

    @Test
    public void setDieAdjacentToAnotherButSameValue(){
        wpc.placeDie(new Die(COLOR.YELLOW, 3), 0, 4);
        assertFalse(wpc.placeDie(new Die(COLOR.RED, 3), 0, 3));
    }

    @Test
    public void setDieOntoAnotherOne(){
        wpc.placeDie(new Die(COLOR.YELLOW, 3), 1, 4);
        assertFalse(wpc.placeDie(new Die(COLOR.YELLOW, 3), 1, 4));
    }

    @Test
    public void setWrongColor(){
        assertFalse(wpc.placeDie(new Die(COLOR.RED, 3), 0, 0));
    }

    @Test
    public void setWrongValue(){
        assertFalse(wpc.placeDie(new Die(COLOR.RED, 3), 1, 1));
    }

    @Test
    public void setDieOnSideButNotAtFirstTurn(){
        wpc.placeDie(new Die(COLOR.YELLOW, 3), 0, 4);
        assertFalse(wpc.placeDie(new Die(COLOR.RED, 3), 1, 0));
    }

    @Test
    public void justABunchOfMovesToVerifyItWorks(){
        assertFalse(wpc.placeDie(new Die(COLOR.RED, 3), 0, 0));
        assertTrue(wpc.placeDie(new Die(COLOR.YELLOW, 3), 0, 0));
        assertTrue(wpc.placeDie(new Die(COLOR.VIOLET, 1), 1, 1));
        assertTrue(wpc.placeDie(new Die(COLOR.YELLOW, 6), 2, 1));
        assertFalse(wpc.placeDie(new Die(COLOR.YELLOW, 5), 2, 0));

    }
}

