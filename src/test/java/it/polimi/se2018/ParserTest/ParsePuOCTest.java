package it.polimi.se2018.ParserTest;

import it.polimi.se2018.Parser.ParserPublicObjectiveCard;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Tests PublicObjectiveCardsParser methods.
 * @author Alessio Molinari
 */
public class ParsePuOCTest {
    ParserPublicObjectiveCard ppoc = new ParserPublicObjectiveCard();
    ArrayList<PublicObjectiveCard> testCards;

    @Test
    public void setTestCards() {
        try {
            testCards = ppoc.parseCards();
        } catch (IOException e) {
            System.out.println("problemino");
        }
    }
}
