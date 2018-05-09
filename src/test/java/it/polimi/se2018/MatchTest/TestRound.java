package it.polimi.se2018.MatchTest;
import static org.junit.Assert.*;

import it.polimi.se2018.DiceBag;
import it.polimi.se2018.DraftPool;
import it.polimi.se2018.Player;
import it.polimi.se2018.Round;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestRound {

    private Round round;

    private Player a;
    private Player b;
    private Player c;
    private Player d;

    private int roundNumber;
    private Player currentPlayer;
    private Player firstPlayer;
    private HashMap<Player, Integer> countPlayersTurns;
    private int turnCount;
    private ArrayList<Player> gamePlayers;
    private DraftPool draftPool;
    private DiceBag diceBag;

    @Before
    public void setUp(){
        int roundNumber = 1;
        gamePlayers = new ArrayList<>();
        Player a = new Player("A");
        Player b = new Player("B");
        Player c = new Player("C");
        Player d = new Player("D");
        gamePlayers.add(a);
        gamePlayers.add(b);
        gamePlayers.add(c);
        gamePlayers.add(d);
        firstPlayer = a;
        currentPlayer = b;
        HashMap<Player, Integer> countPlayersTurns = new HashMap<>();
        int turnCount = 1;
        DiceBag diceBag = DiceBag.getInstance();
        DraftPool draftPool = new DraftPool(diceBag, 4);

        round = new Round(roundNumber, firstPlayer, gamePlayers, diceBag);
    }

    @Test
    public void testNextPlayer(){
        round.nextPlayer();
        assertEquals(gamePlayers.get(2), currentPlayer);
    }
}
