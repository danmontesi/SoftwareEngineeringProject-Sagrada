package it.polimi.se2018.MatchTest.private_public_cards_test;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.Cell;
import it.polimi.se2018.Die;
import it.polimi.se2018.WindowPatternCard;
import it.polimi.se2018.public_obj_cards.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * @author danmontesi
 */

public class PublicObjectiveCardsTest {

    private ArrayList<PublicObjectiveCard> cards = new ArrayList<>();
    private WindowPatternCard myWPCard;

    @Before
    public void setUp(){
        cards.add(new ColorDiagonals());
        cards.add(new ColorVariety());
        cards.add(new ColumnColorVariety());
        cards.add(new ColumnShadeVariety());
        cards.add(new DeepShade());
        cards.add(new LightShade());
        cards.add(new MediumShade());
        cards.add(new RowColorVariety());
        cards.add(new RowShadeVariety());
        cards.add(new ShadeVariety());

        ArrayList<Cell> schema = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            schema.add(new Cell());
        }

        int counter=0;
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.VIOLET));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.VIOLET));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.VIOLET));
        counter++;

        //FIRST ROW
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        counter++;

        //SECOND ROW
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;

        //THIRD ROW
        schema.get(counter).setAssociatedDie(new Die(COLOR.RED));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.YELLOW));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.BLUE));
        counter++;
        schema.get(counter).setAssociatedDie(new Die(COLOR.GREEN));
        //FOURTH ROW

        for (int i = 0; i < 20; i++) {
            schema.get(i).getAssociatedDie().get().setValue(1);
        }

        myWPCard = new WindowPatternCard(schema, 2, "WP1");

        //TODO: print della WindowPatternCard (fare un metodo toString)
    }

    @Test
    public void testAll(){
        Integer[] correctPoints = new Integer[cards.size()];
        int counter= 0;
        //System.out.println("Ok");

        //TODO Assegna i valori corretti all'array per il controllo del corretto calcolo score
        //ColorDiagonals
        correctPoints[counter]=0;
        counter++;
        //ColorVariety
        correctPoints[counter]=0;
        counter++;
        //ColumnColorVariety
        correctPoints[counter]=0;
        counter++;
        //ColumnShadeVariety
        correctPoints[counter]=0;
        counter++;
        //DeepShade
        correctPoints[counter]=0;
        counter++;
        //LightShade
        correctPoints[counter]=0;
        counter++;
        //MediumShade
        correctPoints[counter]=0;
        counter++;
        //RowColorVariety
        correctPoints[counter]=0;
        counter++;
        //RowShadeVariety
        correctPoints[counter]=0;
        counter++;
        //ShadeVariety
        correctPoints[counter]=0;
        counter++;

        assertEquals(correctPoints[0] , (Integer) cards.get(0).calculateScore(myWPCard) );

    }


}
