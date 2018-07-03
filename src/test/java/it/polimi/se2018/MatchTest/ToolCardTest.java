package it.polimi.se2018.MatchTest;

import it.polimi.se2018.model.ACTION_TYPE;
import it.polimi.se2018.model.Action;
import it.polimi.se2018.model.ToolCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ToolCardTest {
    private ToolCard toolCard;
    private List<Action> actions = new ArrayList<>();


    @Before
    public void setUp(){
        actions.add(new Action(ACTION_TYPE.ASK_DIE_VALUE, null, null));
        toolCard = new ToolCard("Prova", "Toolcard di prova", actions, true);
    }

    @Test
    public void correctSetUp(){
        assertEquals("Prova", toolCard.getName());
        assertEquals("Toolcard di prova", toolCard.getDescription());
        assertEquals(0, toolCard.getTokenCount());
        assertTrue(toolCard.isReversible());
        assertEquals(1, toolCard.getActions().size());
        assertEquals(ACTION_TYPE.ASK_DIE_VALUE, toolCard.getActions().get(0).getType());
    }
}
