package it.polimi.se2018.MatchTest.private_public_cards_test;

import shared.exceptions.EmptyCellException;
import server.model.*;
import shared.parser.ParserPrivateObjectiveCard;
import shared.parser.ParserWindowPatternCard;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PrivateObjectiveCardsTest {

    private ParserPrivateObjectiveCard proc = new ParserPrivateObjectiveCard();
    private List<PrivateObjectiveCard> testCards;

    private WindowPatternCard myWPCard;

    Integer[] correctPoints;

    @Before
    public void setUp() {
        List<Cell> schema;

        List<WindowPatternCard> mycards;

        ParserWindowPatternCard pwpc = null;
        try {
            pwpc = new ParserWindowPatternCard();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mycards = pwpc.parseAllCards();

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
                //nothing
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


        //Color purple
        correctPoints[counter]=0;
        counter++;
        //red
        correctPoints[counter]=3;
        counter++;
        //yellow
        correctPoints[counter]=6;
        counter++;
        //green
        correctPoints[counter]=4;
        counter++;
        //blu
        correctPoints[counter]=8;
        counter++;

        correctPoints[2]=6;

        assertEquals(0, testCards.get(0).calculateScore(myWPCard));


    }

    @Test
    public void testSecond(){

        assertEquals(4, testCards.get(1).calculateScore(myWPCard));

    }

    @Test
    public void testThird(){
        assertEquals(6, testCards.get(2).calculateScore(myWPCard));

    }

    @Test
    public void testFourth(){

        assertEquals(3 , testCards.get(3).calculateScore(myWPCard));

    }

    @Test
    public void testFirth(){

        assertEquals(7 , testCards.get(4).calculateScore(myWPCard));

    }



}
