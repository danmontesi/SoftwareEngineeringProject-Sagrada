package it.polimi.se2018.view.CLI;

import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.exceptions.NoSuchColorException;
import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.model.Cell;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.parser.ParserWindowPatternCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLIModel {
    
    private String privateObjectiveCard;
    private String privateObjectiveCardDescription;

    private Map<String, String> publicObjectiveCards;
    private Map<String, ToolcardPair> toolcards;

    private List<String> draftpool; //Dice in the format: colorNumber/empty
    private List<String> roundTrack; //Dice in the format: colorNumber/empty

    /**
     * It is guaranteed that at index 0 there is personal player's data
     */
    private List<CLIPlayer> players;

    CLIModel() {
        publicObjectiveCards = new HashMap<>();
        players = new ArrayList<>();
    }

    void parseRefreshBoard(RefreshBoardCommand command){
        publicObjectiveCards = parsePublicObjectiveCards(command);
        privateObjectiveCard = command.getPrivateObjectiveCard();
        privateObjectiveCardDescription = command.getPrivateObjectiveCardDescription();
        toolcards = parseToolcards(command);
        draftpool = command.getDraftpool();
        roundTrack = command.getRoundTrack();
        parsePlayers(command);
    }

    void parseRefreshDraftPool(RefreshDraftPoolCommand command){
        draftpool = command.getDraftpool();
    }

    void parseRefreshRoundTrack(RefreshRoundTrackCommand command){
        roundTrack = command.getRoundTrack();
    }

    void parseRefreshWPC(RefreshWpcCommand command){
        players.get(0).setWpc(command.getPersonalWpc());
        for(int i = 1; i < command.getOtherPlayersWpcs().size(); i++){
            players.get(i).setWpc(command.getOtherPlayersWpcs().get(i-1));
        }
    }

    void parseRefreshTokens(RefreshTokensCommand command){
        players.get(0).setTokens(command.getPersonalTokens());
        for(int i = 1; i < command.getOtherPlayersTokens().size(); i++){
            players.get(i).setTokens(command.getOtherPlayersTokens().get(i-1));
        }
    }

    private void initPlayers(RefreshBoardCommand command){
        if (players.isEmpty()){
            for (int i = 0; i < 1 + command.getOtherPlayersUsernames().size(); i++)
            players.add(new CLIPlayer());
        }
    }

    private void parsePlayers(RefreshBoardCommand command){
        initPlayers(command);
        //set personal data
        players.get(0).setUsername(command.getUsername());
        players.get(0).setTokens(command.getPersonalTokens());
        players.get(0).setWpc(command.getPersonalWpc());

        //set other players data
        for (int i = 0; i < command.getOtherPlayersUsernames().size(); i++){
            int j = i + 1;
            players.get(j).setUsername(command.getOtherPlayersUsernames().get(i));
            players.get(j).setTokens(command.getOtherPlayersTokens().get(i));
            players.get(j).setWpc(command.getOtherPlayersWpcs().get(i));
        }
    }

    private HashMap<String, String> parsePublicObjectiveCards(RefreshBoardCommand command){
        HashMap<String, String> temp = new HashMap<>();
        ArrayList<String> cards =  command.getPublicObjectiveCards();
        ArrayList<String> description =command.getPublicObjectiveDescription();
        for (int i = 0; i < cards.size(); i++){
            temp.put(cards.get(i), description.get(i));
        }
        return temp;
    }

    private HashMap<String, ToolcardPair> parseToolcards(RefreshBoardCommand command){
        HashMap<String, ToolcardPair> temp = new HashMap<>();
        ArrayList<String> cards = command.getToolCards();
        ArrayList<String> toolcardsDescription = command.getToolCardDescription();
        ArrayList<Integer> toolcardsTokens = command.getTokensToolCards();

        for(int i = 0; i < temp.size(); i++){
            temp.put(cards.get(i), new ToolcardPair(toolcardsDescription.get(i), toolcardsTokens.get(i)));
        }
        return temp;
    }

    /**
     * If no WindowPatternCard is saved in the CliModel, parse the correct card
     * @param wpcString WidowPatternCard name
     */
    private WindowPatternCard initWPC(WindowPatternCard card, String wpcString){
        if (card == null){
            try {
                card = new ParserWindowPatternCard().parseCardByName(wpcString);
            } catch (IOException e) {
                //nothing
            }
        }
        return card;
    }

    private WindowPatternCard parseWPC(WindowPatternCard wpc, ArrayList<String> stringWPC){
        ArrayList<Cell> cells = new ArrayList<>();
        wpc = initWPC(wpc, stringWPC.get(0));

        //element 0 is name so i starts from 1
        for(int i = 1; i < stringWPC.size(); i++){
            int j = i - 1;
            cells.add(wpc.getCell(j));
            cells.get(j).setAssociatedDie(parseDie(stringWPC.get(i)));
        }
        wpc.setSchema(cells);
        return wpc;
    }

    /*private void parseDraftPool(RefreshBoardCommand command){
        initDraftPool(command);
        ArrayList<String> stringDraftPool = command.getDraftpool();
        for(int i = 0; i < stringDraftPool.size(); i++){
            draftpool.add(new Cell(i));
            draftpool.get(i).setAssociatedDie(parseDie(stringDraftPool.get(i)));
        }
    }

    private void initDraftPool(RefreshBoardCommand command){
        if (draftpool == null){
            draftpool = new ArrayList<>();
            for (int i = 0; i < command.getDraftpool().size(); i++){
                draftpool.add(new Cell(i));
            }
        }
    }

    private void parseRoundTrack(RefreshBoardCommand command){
        initRoundTrack(command);
        ArrayList<String> stringRoundTrack = command.getRoundTrack();
        for(int i = 0; i < stringRoundTrack.size(); i++){
            roundTrack.get(i).setAssociatedDie(parseDie(stringRoundTrack.get(i)));
        }
    }

    private void initRoundTrack(RefreshBoardCommand command){
        if (roundTrack == null){
            roundTrack = new ArrayList<>();
            for (int i = 0; i < command.getRoundTrack().size(); i++){
                roundTrack.add(new Cell(i));
            }
        }
    }
*/
    /**
     * Takes a die representation and turns it into a die
     * If the string represents a cell constraint instead of a die, returns null
     * @param stringDie Die representation
     * @return object Die
     */
    private Die parseDie(String stringDie){
        if(stringDie.matches("[A-Z]*_[0-6]")){
            String[] arrayDie = stringDie.split("_");
            try {
                return new Die(COLOR.stringToColor(arrayDie[0]), Integer.parseInt(arrayDie[1]));
            } catch (NoSuchColorException e) {
                //nothing
            }
        }
        return null;
    }

    public String getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public String getPrivateObjectiveCardDescription() {
        return privateObjectiveCardDescription;
    }

    public Map<String, String> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public Map<String, ToolcardPair> getToolcards() {
        return toolcards;
    }

    public List<String> getDraftpool() {
        return draftpool;
    }

    public List<String> getRoundTrack() {
        return roundTrack;
    }

    public List<CLIPlayer> getPlayers() {
        return players;
    }
}
