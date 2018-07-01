package it.polimi.se2018.ParserTest;

import it.polimi.se2018.model.PrivateObjectiveCard;
import it.polimi.se2018.parser.ParserPrivateObjectiveCard;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests PrivateObjectiveCardsParser methods.
 * @author Alessio Molinari
 */
public class ParsePrOCTest {
    ParserPrivateObjectiveCard ppoc = new ParserPrivateObjectiveCard();
    List<PrivateObjectiveCard> testCards;

    @Test
    public void testCards(){
        try{
            testCards = ppoc.parseCards();
            assertEquals(5, testCards.size());
        } catch (IOException e){
            System.out.println("Parsing Error: PrivateOC");
        }

    }
}
