package it.polimi.se2018.MatchTest;

import shared.commands.client_to_server_command.*;
import shared.exceptions.EmptyCellException;
import server.model.*;
import server.Controller;
import server.parser.ParserToolcard;
import org.junit.Test;

import java.io.IOException;
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
        setUpController();
        setUpWpcChoice();
        //       for (String user : players)
        //            controller.getUsernameTimerMap().get(user).cancel();


        assertEquals(controller.getCurrentPlayer(), "Nives");
        ClientToServerCommand command = new MoveChoiceDiePlacement(11, 1);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Nives");
    }

    @Test
    public void testRoundPassTurn() {
        //for (String user : players)
        //    controller.getUsernameTimerMap().get(user).cancel();
        //

        setUpController();
        setUpWpcChoice();

        ClientToServerCommand command = new MoveChoiceDiePlacement(11, 1);
        command.setUsername("Nives");
        controller.update(command);
        assertEquals(controller.getCurrentPlayer(), "Nives");
        command = new MoveChoicePassTurn();
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
    }


    @Test
    public void testUseRoughingForceps() throws EmptyCellException {
        setUpController();
        setUpWpcChoice();
        setKnownBoard();
        skipRound();

        ClientToServerCommand command = new MoveChoiceDiePlacement(11, 1);
        sendCommandToController(command, "Daniele");

        command = new MoveChoiceToolCard(0);
        command.setUsername("Daniele");
        controller.update(command);
        command = new ReplyPickDie(1);
        sendCommandToController(command, "Daniele");

        command = new ReplyIncreaseDecrease(true); //Increase the die
        sendCommandToController(command, "Daniele");
//todo        assertEquals(true, controller.isHasUsedTool());
    }

    @Test
    public void testUseFirmPastryThinner() throws EmptyCellException {
        setUpController();
        setUpWpcChoice();
        setKnownBoard();
        skipRound();

        ClientToServerCommand command;

        command = new MoveChoiceToolCard(2);
        command.setUsername("Daniele");
        controller.update(command);
        command = new ReplyPickDie(1);
        sendCommandToController(command, "Daniele");

        command = new ReplyDieValue(4); //Set this value for extracted die
        sendCommandToController(command, "Daniele");

        command = new ReplyPlaceDie(1); //Increase the die
        sendCommandToController(command, "Daniele");

//        assertEquals(true, controller.isHasUsedTool());
    }

    private void sendCommandToController(ClientToServerCommand command, String username){
        command.setUsername(username);
        controller.update(command);

    }

    private void skipRound(){
        ClientToServerCommand command;
        command = new MoveChoicePassTurn();
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
        WindowPatternCard card = new WindowPatternCard(fillWithColoredCells("rnnnnnnnnnnnnnnnnnnn", 1));
        card.setName("Prova_card");
        card.setDifficulty(5);
        controller.getOrderedPlayers().get(0).setWindowPatternCard(card);
        controller.getOrderedPlayers().get(1).setWindowPatternCard(card);
        controller.getOrderedPlayers().get(2).setWindowPatternCard(card);
        controller.getModel().getDraftPool().fillDraftPool( fillWithColoredDice("rrrrrrr", 1));

        ParserToolcard parserToolcard = new ParserToolcard();

        List<ToolCard> newToolCards = new ArrayList<>();
        try {
            List<ToolCard> toolCardDeck = null;
            toolCardDeck = parserToolcard.parseCards();
            for (int i = 0; i < 12; i++) {
                if (toolCardDeck.get(i).getName().equals("Roughing Forceps") || //num 0
                        toolCardDeck.get(i).getName().equals("Firm Pastry Thinner") || //num. 1
                        toolCardDeck.get(i).getName().equals("Manual Cutter")){ //num 2
                    newToolCards.add(toolCardDeck.get(i));
                }
            }
            controller.getModel().setExtractedToolCard(newToolCards);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("La toolcard 1 2 3 sono di size " + controller.getModel().getExtractedToolCard().size()+ " e sono " + controller.getModel().getExtractedToolCard().get(0).getName() +
                " - " + controller.getModel().getExtractedToolCard().get(1).getName() + " - " + controller.getModel().getExtractedToolCard().get(2).getName());

        //Find 3 toolcards I need
        //toolCardDeck.add;
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
