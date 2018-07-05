package it.polimi.se2018.ParserTest;

import server.model.ToolCard;
import server.parser.ParserToolcard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ParseToolCardsTest {
    ParserToolcard parserToolcard;

    @Before
    public void setUp(){
        parserToolcard = new ParserToolcard();
    }

    @Test
    public void parseEveryToolCard(){
        List<ToolCard> theCards;
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
