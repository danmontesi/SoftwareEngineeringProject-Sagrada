package it.polimi.se2018.MatchTest.private_public_cards_test;

import it.polimi.se2018.*;
import it.polimi.se2018.Exceptions.EmptyCellException;
import it.polimi.se2018.Parser.ParserPrivateObjectiveCard;
import it.polimi.se2018.Parser.ParserPublicObjectiveCard;
import it.polimi.se2018.Parser.ParserWindowPatternCard;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PrivateObjectiveCardsTest {

    ParserPrivateObjectiveCard proc = new ParserPrivateObjectiveCard();
    ArrayList<PrivateObjectiveCard> testCards;

    private WindowPatternCard myWPCard;

    Integer[] correctPoints;

    @Before
    public void setUp() {
        ArrayList<Cell> schema = new ArrayList<>();

        ArrayList<WindowPatternCard> mycards = null;

        ParserWindowPatternCard pwpc = new ParserWindowPatternCard();
        try {
            mycards = pwpc.parseCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        schema = mycards.get(0).getSchema();

        int counter = 0;
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;

        //FIRST ROW
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;

        //SECOND ROW
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;

        //THIRD ROW
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        //FOURTH ROW

        for (int i = 0; i < 20; i++) {
            try {
                schema.get(i).getAssociatedDie().setValue(1);
            } catch (EmptyCellException e) {
                continue;
            }
        }

        myWPCard = mycards.get(0);

        System.out.println(mycards.get(0).toString());

    }


    @Before
    public void setTestCards() {
        try {
            testCards = proc.parseCards();
        } catch (IOException e) {
            System.out.println("problemino");
        }
    }

    @Test
    public void testAll(){

        correctPoints = new Integer[testCards.size()];
        int counter= 0;
        //System.out.println("Ok");

        //TODO Assegna i valori corretti all'array per il controllo del corretto calcolo score
        //Color purple
        correctPoints[counter]=0;
        counter++;
        //green
        correctPoints[counter]=3;
        counter++;
        //yellow
        correctPoints[counter]=6;
        counter++;
        //red
        correctPoints[counter]=4;
        counter++;
        //blu
        correctPoints[counter]=8;
        counter++;

        assertEquals(correctPoints[0], (Integer) testCards.get(0).calculateScore(myWPCard));


    }

    @Test
    public void testSecond(){

        assertEquals(correctPoints[1], (Integer) testCards.get(1).calculateScore(myWPCard));

    }

    @Test
    public void testThird(){

        assertEquals(correctPoints[2], (Integer) testCards.get(2).calculateScore(myWPCard));

    }

    @Test
    public void testFourth(){

        assertEquals(correctPoints[3], (Integer) testCards.get(3).calculateScore(myWPCard));

    }

    @Test
    public void testFirth(){

        assertEquals(correctPoints[4], (Integer) testCards.get(4).calculateScore(myWPCard));

    }



}
