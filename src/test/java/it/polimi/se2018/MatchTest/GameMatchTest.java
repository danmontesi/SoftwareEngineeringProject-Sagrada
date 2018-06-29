package it.polimi.se2018.MatchTest;

import it.polimi.se2018.commands.client_to_server_command.*;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.network.server.Controller;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        ClientToServerCommand command = new MoveChoiceDicePlacement(11, 1);
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

        ClientToServerCommand command = new MoveChoiceDicePlacement(11, 1);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Nives");
        command = new MoveChoicePassTurn("Nives");
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Daniele");
        assertEquals(controller.getCurrentRoundOrderedPlayers().size(), 4);
        command.setUsername("Daniele");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Alessio");
        assertEquals(controller.getCurrentRoundOrderedPlayers().size(), 3);
        command.setUsername("Alessio");
        controller.update(command);
        command.setUsername("Alessio");
        controller.update(command);
        command.setUsername("Daniele");
        controller.update(command);
        command.setUsername("Nives");
        controller.update(command);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentRoundOrderedPlayers().size(), 5);
        assertEquals(controller.getOrderedRoundPlayers().size(), 8);
    }


    @Test
    public void testToolCards1() throws EmptyCellException {
        setUpController();
        setUpWpcChoice();
        setKnownBoard();
        skipRound();

        System.out.println("Current è " + controller.getCurrentPlayer());
        ClientToServerCommand command = new MoveChoiceToolCard(0);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(false, controller.isHasUsedTool());

        Die tempDraft = model.getDraftPool().getDie(0);
        System.out.println("draft prima:" +tempDraft);
        Die tempRound = model.getRoundTrack().getDie(0);
        System.out.println("roundtr prima:" +tempRound);
        command = new UseToolCircularCutter(0, 0);
        command.setUsername("Daniele");
        controller.update(command);
        assertEquals(tempRound, model.getDraftPool().getDie(0));
        assertEquals(tempDraft, model.getRoundTrack().getDie(0));
        command = new MoveChoicePassTurn("Daniele");
        controller.update(command);


        System.out.println("Current è " + controller.getCurrentPlayer());
        Die tempDie  = controller.getOrderedPlayers().get(2).getWindowPatternCard().getCell(0).getAssociatedDie();
        System.out.println("Die to move" + tempDie);

        command = new UseToolMoveDieNoRestriction("Copper Foil Reamer", 0, 2);
        command.setUsername("Alessio");
        controller.update(command);
        assertEquals(tempDie, controller.getOrderedPlayers().get(2).getWindowPatternCard().getCell(2).getAssociatedDie());
        command = new MoveChoicePassTurn("Alessio");
        controller.update(command);



        System.out.println("Current è " + controller.getCurrentPlayer());
        Die tempDieCork  = model.getDraftPool().getDie(4);
        System.out.println("Die to move" + tempDieCork);
        command = new UseToolCorkLine(0, 4);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(tempDieCork, controller.getOrderedPlayers().get(2).getWindowPatternCard().getCell(0).getAssociatedDie());
        command = new MoveChoicePassTurn("Nives");
        controller.update(command);
    }




    private void skipRound(){
        ClientToServerCommand command;
        command = new MoveChoicePassTurn("Nives");
        command.setUsername("Nives");
        controller.update(command);
        command.setUsername("Daniele");
        controller.update(command);
        command.setUsername("Alessio");
        controller.update(command);
        command.setUsername("Alessio");
        controller.update(command);
        command.setUsername("Daniele");
        controller.update(command);
        command.setUsername("Nives");
        controller.update(command);
    }



    private void setKnownBoard(){
        WindowPatternCard card = new WindowPatternCard((ArrayList<Cell>) fillWithColoredCells("rnnnnnnnnnnnnnnnnnnn", 1));
        card.setName("Prova_card");
        card.setDifficulty(5);
        controller.getOrderedPlayers().get(0).setWindowPatternCard(card);
        controller.getOrderedPlayers().get(1).setWindowPatternCard(card);
        controller.getOrderedPlayers().get(2).setWindowPatternCard(card);
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
            if (color == null){
                continue;
            }
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
