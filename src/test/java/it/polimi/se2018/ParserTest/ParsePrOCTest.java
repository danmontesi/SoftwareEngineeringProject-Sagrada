package it.polimi.se2018.ParserTest;

import it.polimi.se2018.Parser.ParserPrivateObjectiveCard;
import it.polimi.se2018.PrivateObjectiveCard;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class ParsePrOCTest {
    ParserPrivateObjectiveCard ppoc = new ParserPrivateObjectiveCard();
    ArrayList<PrivateObjectiveCard> testCards;

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
