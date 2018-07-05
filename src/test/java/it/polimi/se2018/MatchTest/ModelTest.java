package it.polimi.se2018.MatchTest;

import server.model.Model;
import server.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ModelTest {
    private List<Player> players = new ArrayList<>();
    private Model model;

    @Before
    public void setUp(){
        players.add(new Player("A"));
        players.add(new Player("B"));
        model = new Model(players);
    }

    @Test
    public void testSetUp(){
        assertEquals(2, model.getGamePlayers().size());
        assertEquals(90, model.getDiceBag().size());
        //RoundTrack and DraftPool must exist even though they are empty
        assertEquals(0, model.getRoundTrack().diceInTrack());
        assertEquals(0, model.getDraftPool().draftPoolSize());
        assertEquals(3, model.getExtractedToolCard().size());
        assertEquals(3, model.getExtractedPublicObjectiveCard().size());
    }

}
