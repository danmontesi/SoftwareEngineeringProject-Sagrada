package it.polimi.se2018.MatchTest.private_public_cards_test;

/**
 * @author danmontesi
 */

public class PublicObjectiveCardsTest {

 /*   private ArrayList<PublicObjectiveCard> cards = new ArrayList<>();
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
            schema.add(new Cell(i));
        }

        int counter=0;
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


        assertEquals(12, cards.get(0).calculateScore(myWPCard));
    //    assertEquals(correctPoints[0] , (Integer) cards.get(0).calculateScore(myWPCard) );

    }
*/

}
