package it.polimi.se2018.MatchTest;

import it.polimi.se2018.commands.client_to_server_command.ChosenWindowPatternCard;
import it.polimi.se2018.commands.client_to_server_command.ClientToServerCommand;
import it.polimi.se2018.commands.client_to_server_command.MoveChoiceDicePlacement;
import it.polimi.se2018.commands.client_to_server_command.MoveChoicePassTurn;
import it.polimi.se2018.model.*;
import it.polimi.se2018.network.server.Controller;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameMatchTest {

   private Model model;

   private Controller controller;

    private List<String> players;

    private void setUpController(){
        players = new ArrayList<>();
        players.add("Nives");
        players.add("Alessio");
        players.add("Daniele");
        controller = new Controller(players, true);
        this.model = controller.getModel();
    }


   @Test
    public void testCheckUpController() {
        setUpController();
      assertEquals(controller.getUninitializedOrderedPlayers().size(), 3);
      assertEquals(model.getObservers().size(), 3);
   }

   public void setUpWpcChoice(){
        controller.initializeGame();

        ClientToServerCommand chosenWPC1 = new ChosenWindowPatternCard("Virtus");
        chosenWPC1.setUsername(players.get(0));
        controller.update(chosenWPC1);
        chosenWPC1.setUsername(players.get(2));
        controller.update(chosenWPC1);
        chosenWPC1.setUsername(players.get(1));
        controller.update(chosenWPC1);
        controller.getCheckBlockingTimer().cancel();
    }

    @Test
    public void testWpcChoice(){
        setUpController();
        setUpWpcChoice();
        assertEquals(controller.getUninitializedOrderedPlayers().size(), 0);
        assertEquals(controller.getOrderedPlayers().size(), 3);
        assertEquals("Daniele", controller.getOrderedPlayers().get(1).getUsername());
    }


    @Test
    public void testRound(){
        /*for (String user : players)
            controller.getUsernameTimerMap().get(user).cancel();
            */
        setUpController();
        setUpWpcChoice();
        assertEquals(controller.getCurrentPlayer(), "Nives");
        ClientToServerCommand command = new MoveChoiceDicePlacement(2, 2, 1);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Nives");
    }

    @Test
    public void testRoundPassTurn() {
        /*for (String user : players)
            controller.getUsernameTimerMap().get(user).cancel();
            */
        setUpController();
        setUpWpcChoice();

        ClientToServerCommand command = new MoveChoiceDicePlacement(2, 2, 1);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Nives");
        command = new MoveChoicePassTurn("Nives");
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Daniele");
        assertEquals(controller.getCurrentRoundOrderedPlayers().size(), 5);
        controller.update(command);
        assertNotEquals(controller.getCurrentPlayer(), "Daniele");
        assertEquals(controller.getCurrentRoundOrderedPlayers().size(), 5);
    }

    private void setKnownBoard(){
        controller.getOrderedPlayers().get(0).setWindowPatternCard(new WindowPatternCard());
        controller.getOrderedPlayers().get(0).setWindowPatternCard(new WindowPatternCard());
        controller.getOrderedPlayers().get(0).setWindowPatternCard(new WindowPatternCard());
        controller.getModel().setDraftPool((ArrayList<Die>) fillWithColoredDice("rrrrrr",1));

    }

    private List<Cell> fillWithColoredCells(String colors, int valueToSet){
        ArrayList<Cell> cells = new ArrayList<>();
        colors = colors.toLowerCase();
        for(int i = 0; i < colors.length(); i++){
            cells.add(new Cell(i));
            COLOR color = null;
            switch(colors.charAt(i)){
                case 'r':
                    color = COLOR.RED;
                    break;
                case 'b':
                    color = COLOR.BLUE;
                    break;
                case 'y':
                    color = COLOR.YELLOW;
                    break;
                case 'g':
                    color = COLOR.GREEN;
                    break;
                case 'v':
                    color = COLOR.VIOLET;
                    break;
                case 'n':
                    continue;
                default:
                    System.out.println("Something's wrong with the initialization of colored dice");
            }
            //values are always 1 and 2 to avoid conflicts with placement restriction
            cells.get(i).setAssociatedDie(new Die(color, valueToSet));
        }
        return cells;
    }

    private List<Die> fillWithColoredDice(String colors, int valueToSet){
        ArrayList<Die> dice = new ArrayList<>();
        colors = colors.toLowerCase();
        for(int i = 0; i < colors.length(); i++){
            dice.add(new Die(valueToSet));
            COLOR color = null;
            switch(colors.charAt(i)){
                case 'r':
                    color = COLOR.RED;
                    break;
                case 'b':
                    color = COLOR.BLUE;
                    break;
                case 'y':
                    color = COLOR.YELLOW;
                    break;
                case 'g':
                    color = COLOR.GREEN;
                    break;
                case 'v':
                    color = COLOR.VIOLET;
                    break;
                case 'n':
                    continue;
                default:
                    System.out.println("Something's wrong with the initialization of colored dice");
            }
            //values are always 1 and 2 to avoid conflicts with placement restriction
            dice.get(i).setColor(color);
        }
        return dice;
    }
   /*
   @Test
    public void updateAllWpc(){
       assertEquals(controller.getOrderedPlayers().get(0).getWindowPatternCard().getCardName(), "Virtus");
       assertEquals(controller.getOrderedPlayers().get(0).getWindowPatternCard().getCardName(), "Industria");
       assertEquals(controller.getOrderedPlayers().get(0).getWindowPatternCard().getCardName(), "Aurora Sagradis");
   }
   */

}
