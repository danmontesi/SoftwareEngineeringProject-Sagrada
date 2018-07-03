package it.polimi.se2018.MatchTest;

import it.polimi.se2018.model.DiceBag;
import it.polimi.se2018.model.Die;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests DiceBag methods.
 *
 * @author Nives Migotto
 */
public class DiceBagTest {

    private DiceBag diceBag;

    @Before
    public void setUp() {
        diceBag = new DiceBag();
    }

    @Test
    public void testExtractDie() {
        int initialSize = diceBag.size();
        Die outOfBag = diceBag.extractDie();
        assertEquals(initialSize - 1, diceBag.size());
        Die inBag;
        assertNotNull(outOfBag);
        for (int i = 0; i < diceBag.size(); i++) {
            inBag = diceBag.getDice().get(i);
            assertNotSame(outOfBag, inBag);
        }
    }

    @Test
    public void testInsertDie() {
        Die die = diceBag.extractDie();
        int c = 0;
        int size1 = diceBag.getDice().size();
        for (int i = 0; i < diceBag.getDice().size(); i++) {
            if (diceBag.getDice().get(i).getColor().equals(die.getColor())) {
                c++;
            }
        }
        diceBag.insertDie(die);
        int size2 = diceBag.getDice().size() - 1;
        assertEquals(size1, size2);
        int c2 = 0;
        for (int i = 0; i < diceBag.getDice().size(); i++) {
            if (diceBag.getDice().get(i).getColor().equals(die.getColor())) {
                c2++;
            }
        }
        if (c2 <= c) {
            fail();
        }
    }

    @Test
    public void testSwitchDie() {
        Die temp = diceBag.extractDie();
        int size1 = diceBag.getDice().size();
        diceBag.switchDie(temp);
        int size2 = diceBag.getDice().size();
        assertEquals(size1, size2);
    }
}
