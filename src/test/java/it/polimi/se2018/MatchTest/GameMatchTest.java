package it.polimi.se2018.MatchTest;

import it.polimi.se2018.commands.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.model.Model;
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
       players.add("Nives");
       players.add("Alessio");
       players.add("Daniele");
       controller = new Controller(players, true);
       this.model = controller.getModel();
   }


   @Test
    public void checkUpController() {
      assertEquals(controller.getUninitializedOrderedPlayers().size(), 3);
      assertEquals(model.getObservers().size(), 3);
   }

    @Test
    public void setUpWpcChoice(){
        players = new ArrayList<>();
        players.add("Nives");
        players.add("Alessio");
        players.add("Daniele");
        controller = new Controller(players, true);
        this.model = controller.getModel();

        controller.initializeGame();

        System.out.println(controller.getUninitializedOrderedPlayers().get(0).getUsername());

        ClientToServerCommand chosenWPC1 = new ChosenWindowPatternCard("Virtus");
        chosenWPC1.setUsername(players.get(0));
        controller.update(new ChosenWindowPatternCard("Virtus"));
        chosenWPC1.setUsername("Nives");
        controller.update(new ChosenWindowPatternCard("Industria"));
        chosenWPC1.setUsername("Alessio");
        controller.update(new ChosenWindowPatternCard("Aurora Sagradis"));

        controller.getCheckBlockingTimer().cancel();
        /*for (String user : players)
            controller.getUsernameTimerMap().get(user).cancel();
            */
        assertEquals(controller.getOrderedPlayers().size(), 3);
    }
   @Test
    public void updateAllWpc(){
       assertEquals(controller.getOrderedPlayers().get(0).getWindowPatternCard().getCardName(), "Virtus");
       assertEquals(controller.getOrderedPlayers().get(0).getWindowPatternCard().getCardName(), "Industria");
       assertEquals(controller.getOrderedPlayers().get(0).getWindowPatternCard().getCardName(), "Aurora Sagradis");
   }

}
