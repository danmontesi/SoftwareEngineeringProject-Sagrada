package it.polimi.se2018.model;

import it.polimi.se2018.commands.server_to_client_command.*;
import it.polimi.se2018.exceptions.EmptyCellException;
import it.polimi.se2018.model.public_obj_cards.PublicObjectiveCard;
import it.polimi.se2018.parser.ParserPrivateObjectiveCard;
import it.polimi.se2018.parser.ParserPublicObjectiveCard;
import it.polimi.se2018.parser.ParserToolcard;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is the model, the class that maintain the State of the game
 * It is an Observable from a VirtualView (the Observer).
 * The Virtual View just send "broadcast" all graphical changes of the board
 * The controller directly modifies the Model.
 */
public class Model extends Observable { //Observable of View
    private DiceBag diceBag;
    private List<PublicObjectiveCard> extractedPublicObjectiveCard = new ArrayList<>();
    private List<ToolCard> extractedToolCard = new ArrayList<>();
    private List<WindowPatternCard> windowPatternCardDeck;
    private List<Player> gamePlayers;
    private RoundTrack roundTrack;
    private DraftPool draftPool;
    private int currentRound;
    private Player currentPlayer;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    /**
     * Constructor: generates a game by
     * uploading all WindowPatternCards, PublicObjectiveCards, PrivateObjectiveCards and ToolCards
     * extracting 3 PublicObjectiveCards, creating 10 rounds
     * initializing the diceBag, the game players list, the roundTrack
     *
     * @param players list of game players
     */
    public Model(List<Player> players) {
        gamePlayers = players;
        diceBag = new DiceBag();
        roundTrack = new RoundTrack();
        draftPool = new DraftPool(gamePlayers.size());

        List<PrivateObjectiveCard> privateObjectiveCardDeck;
        List<PublicObjectiveCard> publicObjectiveCardDeck;
        List<ToolCard> toolCardDeck;

        try {
            //instantiate Parsers
            ParserPrivateObjectiveCard parserPrivateObjectiveCard = new ParserPrivateObjectiveCard();
            ParserPublicObjectiveCard parserPublicObjectiveCard = new ParserPublicObjectiveCard();
            ParserToolcard parserToolcard = new ParserToolcard();
            ParserWindowPatternCard parserWindowPatternCard = new ParserWindowPatternCard();

            //Parse cards
            windowPatternCardDeck = parserWindowPatternCard.parseAllCards();
            toolCardDeck = parserToolcard.parseCards();
            privateObjectiveCardDeck = parserPrivateObjectiveCard.parseCards();
            publicObjectiveCardDeck = parserPublicObjectiveCard.parseCards();

            //Shuffle decks
            Collections.shuffle(publicObjectiveCardDeck);
            Collections.shuffle(toolCardDeck);
            Collections.shuffle(privateObjectiveCardDeck);

            //extract cards
            for (int i = 0; i < 3; i++) {
                extractedPublicObjectiveCard.add(publicObjectiveCardDeck.remove(0));
                extractedToolCard.add(toolCardDeck.remove(0));
            }

            for (Player p : gamePlayers){
                p.setPrivateObjectiveCard(privateObjectiveCardDeck.remove(0));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public Model(Model oldModel){
        this.gamePlayers = oldModel.getGamePlayers();
        this.currentRound = oldModel.getCurrentRound();
        this.draftPool = oldModel.getDraftPool();
        this.diceBag = oldModel.getDiceBag();
        this.extractedToolCard = oldModel.getExtractedToolCard();
        this.extractedPublicObjectiveCard = oldModel.getExtractedPublicObjectiveCard();
        this.roundTrack = oldModel.getRoundTrack();
        }

    public List<Die> extractDraftPoolDice(int numPlayers) {
        return diceBag.extractDice(numPlayers);
    }

    /**
     * Returns an ArrayList of 4 WindowPatternCards
     * @return list of extracted cards
     */
    public List<WindowPatternCard> extractWindowPatternCard() {
        ArrayList<WindowPatternCard> toReturn = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, windowPatternCardDeck.size());
            toReturn.add(i, windowPatternCardDeck.remove(index));
        }
        return toReturn;
    }

    public List<ToolCard> getExtractedToolCard() {
        return extractedToolCard;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public List<PublicObjectiveCard> getExtractedPublicObjectiveCard() {
        return extractedPublicObjectiveCard;
    }

    public void insertDieInDiceBag(Die die) {
        this.diceBag.insertDie(die);
    }

    public Die extractDieFromDiceBag() {
        return this.diceBag.extractDie();
    }

    public void setGamePlayers(List<Player> gamePlayers) {
        this.gamePlayers = gamePlayers;
        notifyRefreshWpcs();
    }

    public Die removeDieFromDraftPool(int index) {
        Die temp = null;
        try {
            temp = getDraftPool().takeDie(index);
        } catch (EmptyCellException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        notifyRefreshDraftPool();
        return temp;
    }

    public void changeDieValueOnDraftPool(int index, int value) {
        try {
            this.draftPool.getDie(index).setValue(value);
        } catch (EmptyCellException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        notifyRefreshDraftPool();
    }

    public void setDieOnDraftPool(Die die, int index) {
        this.draftPool.placeDie(index, die);
        notifyRefreshDraftPool();
    }

    public void rollDraftpoolDice() {
        this.getDraftPool().rollDice();
        notifyRefreshDraftPool();
    }

    public Die swapDieOnRoundTrack(Die die, int index) {
        Die toReturn = null;

        try {
            toReturn = getRoundTrack().switchDie(index, die);
        } catch (EmptyCellException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        notifyRefreshRoundTrack();
        return toReturn;
    }

    public void increaseToolCardTokens(int toolCardNumber, int tokens) {
        extractedToolCard.get(toolCardNumber).increaseTokens(tokens);
        notifyRefreshTokens();
    }

    public void notifyRefreshDraftPool() {
        List<String> draftpool;
        draftpool = draftPool.draftpoolPathRepresentation();

        for (Observer observer : observers) {
            ((View) observer).updateDraftPool(new RefreshDraftPoolCommand(draftpool));
        }
    }

    private void notifyRefreshWpcs() {
        for (Observer observer : observers) {
            View view = (View) observer;
            List<List<String>> otherPlayersWpcs = refreshWPCs(view);
            List<String> personalWpc = otherPlayersWpcs.remove(0);
            view.updateWpc(new RefreshWpcCommand(personalWpc, otherPlayersWpcs));
            LOGGER.log(Level.INFO, "Notificando una V.V. delle nuove WPC: " + view.getUsername());
        }
    }

    public List<List<String>> refreshWPCs(View view){
        List<List<String>> wpcList = new ArrayList<>();

        for (Player p : gamePlayers) {
            if (p.getUsername().equals(view.getUsername())) {
                //my wpc is in first place
                wpcList.add(0, p.getWindowPatternCard().wpcPathRepresentation());
            } else {
                wpcList.add(p.getWindowPatternCard().wpcPathRepresentation());
            }
        }
        return wpcList;
    }

    private void notifyRefreshRoundTrack() {
        List<String> roundTrackString = refreshRoundTrack();
        for (Observer observer : observers) {
            LOGGER.log(Level.INFO, "Notificando una V.V. del nuovo round track");
            ((View) observer).updateRoundTrack(new RefreshRoundTrackCommand(roundTrackString));
        }
    }

    public List<String> refreshRoundTrack(){
        List<String> roundTrackString; //Dice in the format: colorNumber/empty
        roundTrackString = this.roundTrack.roundtrackPathRepresentation();
        return roundTrackString;
    }

    private void notifyRefreshTokens() {
        LOGGER.log(Level.INFO, "Notificando una V.V. dei nuovi tokens");
        List<Integer> tokensToolCards = refreshToolCardTokens(); //Ordered

        for (Observer observer : observers) {
            View player = (View) observer;
            List<Integer> otherPlayersTokens = refreshPlayerTokens(player);
            int myTokens = otherPlayersTokens.remove(0);
            ((View) observer).updateTokens(new RefreshTokensCommand(otherPlayersTokens, tokensToolCards, myTokens));
        }
    }

    public List<Integer> refreshToolCardTokens(){
        List<Integer> tokensToolCards = new ArrayList<>();
        for (ToolCard toolCard : extractedToolCard) {
            tokensToolCards.add(toolCard.getTokenCount());
        }
        return tokensToolCards;
    }

    public List<Integer> refreshPlayerTokens(View view){
        List<Integer> tokenList = new ArrayList<>();
        for (Player p : gamePlayers) {
            if (p.getUsername().equals(view.getUsername())) {
                //my tokens is in first place
                tokenList.add(0, p.getTokens());
            }
            else {
                tokenList.add(p.getTokens());
            }
        }
        return tokenList;
    }

    /**
     * Method for initial setting of the board
     */
    public void notifyRefreshBoard(List<Player> players ) {
        gamePlayers = players;
        for (Observer observer : observers) {
            View currentView = (View) observer;
            currentView.updateBoard(new RefreshBoardCommand(this, currentView));
        }
    }

    public void notifyBoardSinglePlayer(View view){
        view.updateBoard(new RefreshBoardCommand(this, view));
    }

    public void putDieOnRoundTrack(Die die) {
        roundTrack.placeDie(die);
        notifyRefreshRoundTrack();
    }

    public void setDraftPool(int numberOfPlayers) {
        this.draftPool.fillDraftPool(diceBag.extractDice(numberOfPlayers));
        notifyRefreshDraftPool();
    }

    public void flipDraftPoolDie(int index) throws EmptyCellException {
        draftPool.getDie(index).flip();
        notifyRefreshDraftPool();
    }

    public void rollDraftpoolDie(int index) throws EmptyCellException {
        draftPool.getDie(index).roll();
        notifyRefreshDraftPool();
    }

    public void increaseDraftpoolDieValue(int index, boolean increase) throws EmptyCellException {
        if (increase)
            draftPool.getDie(index).increaseByOne();
        else
            draftPool.getDie(index).decreaseByOne();
        notifyRefreshDraftPool();
    }

    public Die getLastDie() throws EmptyCellException {
        Die toReturn;
        toReturn = draftPool.getLastDie();
        return toReturn;
    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public Player getPlayerFromUsername(String username) {
        for (Player p : gamePlayers){
            if (p.getUsername().equals(username))
                return p;
        }
        LOGGER.log(Level.INFO, "Error: returning a random one");
        return gamePlayers.get(0);
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getGamePlayers() {
        return gamePlayers;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setGamePlayersNoRefresh(List<Player> players) {
        this.gamePlayers = players;
    }
}
