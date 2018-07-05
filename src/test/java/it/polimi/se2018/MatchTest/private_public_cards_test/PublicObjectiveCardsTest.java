package it.polimi.se2018.MatchTest.private_public_cards_test;

import server.model.COLOR;
import server.model.Cell;
import server.model.Die;
import server.model.WindowPatternCard;
import server.parser.ParserPublicObjectiveCard;
import server.model.public_obj_cards.PublicObjectiveCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Alessio Molinari, danmontesi
 * Every test contains at least one empty cell to test the exception too
 * Methods to fill the cards (fillWithColoredDice and fillWithShadedDice)
 * do not need to respect placement restriction since they are used only for testing.
 */

public class PublicObjectiveCardsTest {
    private ArrayList<PublicObjectiveCard> cards = new ArrayList<>();
    //create wpc with no restriction
    private WindowPatternCard wpc = new WindowPatternCard();
    private ParserPublicObjectiveCard ppoc = new ParserPublicObjectiveCard();
    private final String[] names = {
            "RowColorVariety",
            "ColumnColorVariety",
            "ColumnShadeVariety",
            "RowShadeVariety",
            "ColorVariety",
            "ShadeVariety",
            "DeepShade",
            "MediumShade",
            "LightShade",
            "ColorDiagonals"
    };

    @Before
    public void setUp(){
        try {
            cards.addAll(ppoc.parseCards());
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void correctCards(){
        for(int i = 0; i < names.length; i++){
            String cardName = cards.get(i).getClass().getSimpleName();
            assertEquals(names[i], cardName);
        }
    }

    @Test
    public void rowColorVariety(){
        PublicObjectiveCard currentCard = cards.get(0);
        //2 righe con colori diversi (seconda e terza)
        fillWithColoredDice(wpc, "YrgrbRgvbyBrygvYgvbg");
        assertEquals(6*2, cards.get(0).calculateScore(wpc));
    }

    @Test
    public void rowColorVarietyWithEmptyCells(){
        PublicObjectiveCard theCard = cards.get(0);
        //2 righe con colori diversi (seconda e terza)
        fillWithColoredDice(wpc, "YrnrbRgvbyBrygvYgvnn");
        assertEquals(theCard.getScore()*2, theCard.calculateScore(wpc));
    }

    @Test
    public void columnColorVariety(){
        PublicObjectiveCard theCard = cards.get(1);
        fillWithColoredDice(wpc, "RgbvyBvngrYgvgvVybrg");
        assertEquals(theCard.getScore()*2, theCard.calculateScore(wpc));
    }

    @Test
    public void columnShadeVariety(){
        PublicObjectiveCard theCard = cards.get(2);
        fillWithShadedDice(wpc, "12345" +
                "23451" +
                "3451n" +
                "45123");
        assertEquals(theCard.getScore()*4, theCard.calculateScore(wpc));
    }

    @Test
    public void rowShadeVariety(){
        PublicObjectiveCard theCard = cards.get(3);
        fillWithShadedDice(wpc, "12345" +
                "23431" +
                "34512" +
                "4512n");
        assertEquals(theCard.getScore()*2, theCard.calculateScore(wpc));
    }

    @Test
    public void colorVariety(){
        PublicObjectiveCard theCard = cards.get(4);
        fillWithColoredDice(wpc, "RbyngByrnnYbgrgNbrgv");
        assertEquals((int)theCard.getScore(), theCard.calculateScore(wpc));
    }

    @Test
    public void colorVarietyButNeverViolet(){
        PublicObjectiveCard theCard = cards.get(4);
        fillWithColoredDice(wpc, "RbyngByrnnYbgrgNbrgg");
        assertEquals(0, theCard.calculateScore(wpc));
    }

    @Test
    public void shadeVariety(){
        PublicObjectiveCard theCard = cards.get(5);
        fillWithShadedDice(wpc, "n2345" +
                "n4563" +
                "1n543" +
                "52621");
        assertEquals((int)theCard.getScore(), theCard.calculateScore(wpc));
    }
    @Test
    public void shadeVarietyButNeverSix(){
        PublicObjectiveCard theCard = cards.get(5);
        fillWithShadedDice(wpc, "12345" +
                "2n513" +
                "125nn" +
                "52121");
        assertEquals(0, theCard.calculateScore(wpc));
    }

    @Test
    public void deepShadeWithTwoSets(){
        PublicObjectiveCard theCard = cards.get(6);
        fillWithShadedDice(wpc, "1256n" +
                "n5421" +
                "53243" +
                "6n541");
        assertEquals(theCard.getScore()*2, theCard.calculateScore(wpc));
    }

    @Test
    public void deepShadeWithNoSix(){
        PublicObjectiveCard theCard = cards.get(6);
        fillWithShadedDice(wpc, "1255n" +
                "n5421" +
                "53243" +
                "5n541");
        assertEquals(0, theCard.calculateScore(wpc));
    }

    @Test
    public void mediumShadeWithOneSet(){
        PublicObjectiveCard theCard = cards.get(7);
        fillWithShadedDice(wpc, "n4651" +
                "15263" +
                "5n462" +
                "4552n");
        assertEquals((int)theCard.getScore(), theCard.calculateScore(wpc));
    }

    @Test
    public void mediumShadeWithNoThree(){
        PublicObjectiveCard theCard = cards.get(7);
        fillWithShadedDice(wpc, "n4651" +
                "15261" +
                "5n462" +
                "4552n");
        assertEquals(0, theCard.calculateScore(wpc));
    }

    @Test
    public void lightShadeWithFourSets(){
        PublicObjectiveCard theCard = cards.get(8);
        fillWithShadedDice(wpc, "n4651" +
                "15261" +
                "52462" +
                "15522");
        assertEquals(theCard.getScore()*4, theCard.calculateScore(wpc));
    }

    @Test
    public void lightShadeWithNoTwo(){
        PublicObjectiveCard theCard = cards.get(8);
        fillWithShadedDice(wpc, "n4651" +
                "15n31" +
                "n5463" +
                "4554n");
        assertEquals(0, theCard.calculateScore(wpc));
    }

    @Test
    public void colorDiagonals(){
        //This test is hard to visualize but it is supposed to have:
        //5 red diagonally adjacent dice, 4 blue, 3 green and 3 yellow, 15 in total.
        PublicObjectiveCard theCard = cards.get(9);
        fillWithColoredDice(wpc, "RbrbrBrgybGgrygGryyy");
        assertEquals(15, theCard.calculateScore(wpc));
    }

    private void fillWithColoredDice(WindowPatternCard wpc, String colors){
        ArrayList<Cell> cells = new ArrayList<>();
        colors = colors.toLowerCase();
        for(int i = 0; i < 20; i++){
            cells.add(new Cell(i));
            COLOR color;
            switch(colors.charAt(i)){
                case 'r':
                    color = COLOR.RED;
                    break;
                case 'b':
                    color = COLOR.BLUE;
                    break;
                case 'y':
                    color = COLOR.YELLOW;
                    break;
                case 'g':
                    color = COLOR.GREEN;
                    break;
                case 'v':
                    color = COLOR.VIOLET;
                    break;
                case 'n':
                    continue;
                default:
                    System.out.println("Something's wrong with the initialization of colored dice");
                    return;
            }
            //values are always 1 and 2 to avoid conflicts with placement restriction
            cells.get(i).setAssociatedDie(new Die(color, (i%2)+1));
        }
        wpc.setSchema(cells);
    }

    private void fillWithShadedDice(WindowPatternCard wpc, String numbers){
    ArrayList<Cell> cells = new ArrayList<>();
    COLOR color;
        for(int i = 0; i < 20; i++){
        cells.add(new Cell(i));
        if(numbers.charAt(i) == 'n'){
            continue;
        } else if (i%2 == 0) {
            color = COLOR.GREEN;
        } else {
            color = COLOR.RED;
        }
        //colors are always green and red to avoid placement conflicts
            cells.get(i).setAssociatedDie(new Die(color,Character.getNumericValue(numbers.charAt(i))));
        }
        wpc.setSchema(cells);
    }
}
