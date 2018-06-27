package it.polimi.se2018.MatchTest;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.server.Controller;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMatchTest {

   private Model model;

   private Controller controller;

    private List<String> players;
   @Before
   public void setUp(){
       players = new ArrayList<>();
       players.add("Antonio");
       players.add("Gabriele");
       players.add("Luca");
   }


   @Test
    public void checkUpController() {
      controller = new Controller(players, true);
      this.model = controller.getModel();
      assertEquals(controller.getUninitializedOrderedPlayers().size(), 3);
      assertEquals(model.getObservers().size(), 3);
   }


}
