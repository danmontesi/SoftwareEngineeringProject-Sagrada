package it.polimi.se2018.ParserTest;

import it.polimi.se2018.parser.ParserToolcard;
import it.polimi.se2018.model.ToolCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ParseToolCardsTest {
    ParserToolcard parserToolcard;

    @Before
    public void setUp(){
        parserToolcard = new ParserToolcard();
    }

    @Test
    public void parseEveryToolCard(){
        ArrayList<ToolCard> theCards;
        try {
            theCards = parserToolcard.parseCards();
            assertEquals(12, theCards.size());
            assertEquals("Roughing Forceps", theCards.get(0).getName());
            assertEquals("After drafting, increase or decrease the value of the drafted die by 1. 1 may not change to 6, or 6 to 1.",
                    theCards.get(0).getDescription());
            assertEquals("Eglomise Brush", theCards.get(1).getName());

        } catch (IOException e) {
            Assert.fail();
        }
    }
}
