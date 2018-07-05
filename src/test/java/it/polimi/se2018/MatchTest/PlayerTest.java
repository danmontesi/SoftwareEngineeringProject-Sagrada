package it.polimi.se2018.MatchTest;

import server.model.Player;
import server.model.WindowPatternCard;
import server.parser.ParserWindowPatternCard;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class PlayerTest {

    private Player player;
    private WindowPatternCard wpc;

    @Before
    public void setUp() throws IOException {
        ParserWindowPatternCard parser = new ParserWindowPatternCard();
        wpc = parser.parseCardByName("Batllo");
        player = new Player("Alessio");
        player.setWindowPatternCard(wpc);
    }

    @Test
   public void correctAssignment(){
        assertEquals(wpc.getCardName(), player.getWindowPatternCard().getCardName());
    }

    @Test
    public void correctTokens(){
        assertEquals(wpc.getDifficulty(), player.getTokens());
    }

    @Test
    public void decreaseTokens(){
        player.decreaseTokens(2);
        assertEquals(wpc.getDifficulty() - 2, player.getTokens());
    }

}
