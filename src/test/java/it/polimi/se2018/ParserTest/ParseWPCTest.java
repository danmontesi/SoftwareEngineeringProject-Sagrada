package it.polimi.se2018.ParserTest;

import com.google.gson.stream.MalformedJsonException;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Tests WindowPatternCardsParser methods.
 * @author Alessio Molinari
 */
public class ParseWPCTest {

    ParserWindowPatternCard pwpc;

    @Before
    public void instantiateParser(){
        try {
            pwpc = new ParserWindowPatternCard();
        } catch (IOException e) {
            Assert.fail();
        }

    }

    @Test
    public void parseEveryCard(){
        ArrayList<WindowPatternCard> theCards;
        theCards = (ArrayList<WindowPatternCard>) pwpc.parseAllCards();
        assertEquals(24, theCards.size());
        assertEquals("Virtus", theCards.get(0).getCardName());
        assertEquals("Via Lux", theCards.get(1).getCardName());

    }

    @Test
    public void parseSingleCard(){
        try {
            assertEquals("Via Lux", pwpc.parseCardByName("Via Lux").getCardName());
        } catch (MalformedJsonException e) {
            Assert.fail();
        }
    }

    @Test
    public void parseSingleCardWithWrongSpelling(){
        try {
            assertEquals("Via Lux", pwpc.parseCardByName("Via Luxa").getCardName());
        } catch (MalformedJsonException e) {
            assertTrue(true);
        }
    }

}
