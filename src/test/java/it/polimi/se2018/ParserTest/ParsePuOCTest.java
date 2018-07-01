package it.polimi.se2018.ParserTest;

import it.polimi.se2018.model.public_obj_cards.PublicObjectiveCard;
import it.polimi.se2018.parser.ParserPublicObjectiveCard;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests PublicObjectiveCardsParser methods.
 * @author Alessio Molinari
 */
public class ParsePuOCTest {
    ParserPublicObjectiveCard ppoc = new ParserPublicObjectiveCard();
    List<PublicObjectiveCard> testCards;

    @Test
    public void setCardsTest() {
        try {
            testCards = ppoc.parseCards();
            System.out.println(testCards.get(1).getName());
            assertEquals(10, testCards.size());
        } catch (IOException e) {
            System.out.println("problemino");
        }

    }
}
