package it.polimi.se2018.ParserTest;

import it.polimi.se2018.parser.ParserPublicObjectiveCard;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests PublicObjectiveCardsParser methods.
 * @author Alessio Molinari
 */
public class ParsePuOCTest {
    ParserPublicObjectiveCard ppoc = new ParserPublicObjectiveCard();
    ArrayList<PublicObjectiveCard> testCards;

    @Test
    public void setCardsTest() {
        try {
            testCards = ppoc.parseCards();
            assertEquals(12, testCards.size());
        } catch (IOException e) {
            System.out.println("problemino");
        }

    }
}
