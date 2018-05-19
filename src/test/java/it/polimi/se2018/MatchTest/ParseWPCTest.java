package it.polimi.se2018.MatchTest;

import it.polimi.se2018.Parser.ParserWindowPatternCard;
import it.polimi.se2018.WindowPatternCard;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParseWPCTest {

    ParserWindowPatternCard pwpc = new ParserWindowPatternCard();
    ArrayList<WindowPatternCard> mycards;

    @Test
    public void iHopeItWorks(){
        try {
            mycards = pwpc.parseWindowPatternCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //You must write, instead of 2, the number of cards in wpc.json
        //The class works correctly
        //Thank God
        assertEquals(24, mycards.size());
    }

}
