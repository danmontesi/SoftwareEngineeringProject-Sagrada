package it.polimi.se2018.MatchTest;

import it.polimi.se2018.COLOR;
import it.polimi.se2018.DiceBag;
import it.polimi.se2018.Die;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDiceBag {

    private DiceBag diceBag;

    @Before
    public void setUp(){
        diceBag = DiceBag.getInstance();
    }

    @Test
    public void testExtractDie(){
        Die temp = diceBag.extractDie();
        Die temp2;
        assertNotNull(temp);
        for (int i=0; i<diceBag.getDice().size(); i++){
            temp2 = diceBag.getDice().get(i);
            assertNotEquals(temp, temp2);
        }
    }

    @Test
    public void testInsertDie(){
        Die die = diceBag.extractDie();
        int c=0;
        for(int i=0; i<diceBag.getDice().size(); i++){
            if (diceBag.getDice().get(i).getColor().equals(die.getColor())) {
                c++;
            }
        }
        diceBag.insertDie(die);
        int c2=0;
        for(int i=0; i<diceBag.getDice().size(); i++){
            if (diceBag.getDice().get(i).getColor().equals(die.getColor())) {
                c2++;
            }
        }
        if (c2<=c){
            fail();
        }
    }

    /*@Test
    public void testSwitchDie(){

    }
    */
}
