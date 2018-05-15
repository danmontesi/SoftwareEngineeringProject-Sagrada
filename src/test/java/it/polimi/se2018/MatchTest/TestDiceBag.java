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

    /*@Test
    public void testExtractDie(){
        Die temp = diceBag.extractDie();
        Die temp2;
        assertNotNull(temp);
        for (int i=0; i<90; i++){
            temp2 = diceBag.getDie(i);
            assertNotEquals(temp, temp2);
        }
    }

    @Test
    public void testInsertDie(){
        Die die = diceBag.extractDie();
        int c=0;
        for(int i=0; i<90; i++){
            if (diceBag.getDie(i)!=null){
                    if (diceBag.getDie(i).getColor().equals(die.getColor())) {
                        c++;
                    }
            }
        }
        diceBag.insertDie(die);
        int c2=0;
        for(int i=0; i<90; i++){
            if (diceBag.getDie(i)!=null){
                if (diceBag.getDie(i).getColor().equals(die.getColor())) {
                    c2++;
                }
            }
            if (c2<=c){
                fail();
            }
        }
    }

    /*@Test
    public void testSwitchDie(){
        Die toSwitchDie = diceBag.extractDie();

    }*/

}
