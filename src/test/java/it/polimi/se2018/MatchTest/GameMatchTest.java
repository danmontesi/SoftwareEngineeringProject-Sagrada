package it.polimi.se2018.MatchTest;

import it.polimi.se2018.*;
import it.polimi.se2018.MVC.Controller;
import it.polimi.se2018.network.ClientConnection;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests Game operation.
 * @author Nives Migotto
 */
public class GameMatchTest {

   Model model;

   Player p1 = new Player("Gabriele");
   Player p2 = new Player("Antonio");
   Player p3 = new Player("Marta");
   ArrayList<Player> arrayPlayer = new ArrayList<>();

   @Before
    public void setUp(){
       arrayPlayer.add(p1);
       arrayPlayer.add(p2);
       arrayPlayer.add(p3);
   }

   @Test
    public void testConstructor(){
      model = Model.getInstance(arrayPlayer);
      assertEquals(3, model.getGamePlayers().size());
      assertEquals(3, model.getConnectedPlayers().size());
      assertEquals(3, model.getExtractedPublicObjectiveCard());
      assertEquals(3, model.getExtractedToolCard());
      assertEquals(10, model.getGameRounds());
   }

   /*Testing Player
   @Before
    public void assignCardsToPlayers(){

   }*/

}
