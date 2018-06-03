package it.polimi.se2018.MatchTest;

import it.polimi.se2018.*;
import org.junit.Test;

import java.util.ArrayList;

public class GameMatchTest {

   Model model;

   Player p1 = new Player("Gabriele");
   Player p2 = new Player("Antonio");

   @Test
    public void setUp() {
      ArrayList<Player> arrayPlayer = new ArrayList<>();
      arrayPlayer.add(p1);
      arrayPlayer.add(p2);
   }
       //model = new Model(arrayPlayer);
/*
       assertEquals(2, model.getGamePlayers().size());
       assertEquals(2, model.getConnectedPlayers().size());
       assertEquals(3, model.getExtractedPublicObjectiveCard());
       assertEquals(3, model.getExtractedToolCard());
       assertEquals(10, model.getGameRounds());
   }

   /*@Test
    public void newTurn(){
        model.nextRound();

        assertEquals(9, model.getGameRounds().size());
   }

   @Test
    public void newTurn2(){
        model.nextRound();

        assertEquals(8, model.getGameRounds().size());
   }

   //Testing Player
   @Before
    public void assignCardsToPlayers(){

   }
*/
}
