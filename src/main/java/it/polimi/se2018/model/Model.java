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
import java.io.Serializable;
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
 * <p>
 * The controller directly modifies the Model.
 */
public class Model extends Observable implements Serializable, Cloneable { //Observable of View


    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    private DiceBag diceBag;

    private List<PublicObjectiveCard> extractedPublicObjectiveCard;

    private List<ToolCard> extractedToolCard;

    private List<WindowPatternCard> windowPatternCardDeck;

    private List<Player> gamePlayers;

    private RoundTrack roundTrack;

    private DraftPool draftPool;

    private int currentRound;

    private Player currentPlayer;

    //private ArrayList<Observer> observers; Already in the class thanks to Observable ->le virtual view!

    public void setDraftPool(List<Die> dice) {
        this.draftPool = new DraftPool(dice);
        notifyRefreshDraftPool();
    }

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
        ParserPrivateObjectiveCard parserPrivateObjectiveCard = new ParserPrivateObjectiveCard();
        ParserPublicObjectiveCard parserPublicObjectiveCard = new ParserPublicObjectiveCard();
        ParserToolcard parserToolcard = new ParserToolcard();
        ParserWindowPatternCard parserWindowPatternCard;

        try {
            parserWindowPatternCard = new ParserWindowPatternCard();
            windowPatternCardDeck = parserWindowPatternCard.parseAllCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<PrivateObjectiveCard> privateObjectiveCardDeck = new ArrayList<>();
        List<PublicObjectiveCard> publicObjectiveCardDeck = new ArrayList<>();

        try {
            privateObjectiveCardDeck = parserPrivateObjectiveCard.parseCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            publicObjectiveCardDeck = parserPublicObjectiveCard.parseCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extracting three cards
        Collections.shuffle(publicObjectiveCardDeck);
        extractedPublicObjectiveCard = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            extractedPublicObjectiveCard.add(publicObjectiveCardDeck.remove(0));
        }

        List<ToolCard> toolCardDeck = new ArrayList<>();
        try {
            toolCardDeck = parserToolcard.parseCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        extractedToolCard = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, toolCardDeck.size());
            extractedToolCard.add(i, toolCardDeck.remove(index));
        }

        Collections.shuffle(privateObjectiveCardDeck);
        for (Player p : gamePlayers)
            p.setPrivateObjectiveCard(privateObjectiveCardDeck.remove(0));
    }

    public Model(Model oldModel){
        this.gamePlayers = oldModel.getGamePlayers();
        this.setCurrentRound(oldModel.getCurrentRound());
        this.setDraftPool(oldModel.getDraftPool());
        this.setDiceBag(oldModel.getDiceBag());
        this.setExtractedToolCard(oldModel.getExtractedToolCard());
        this.setExtractedPublicObjectiveCard(oldModel.getExtractedPublicObjectiveCard());
        this.setRoundTrack(oldModel.getRoundTrack());
    }

    public List<Die> extractDraftPoolDice(int numPlayers) {
        ArrayList<Die> temp = new ArrayList<>();
        for (int i = 0; i < 2 * numPlayers + 1; i++) {
            temp.add(diceBag.extractDie());
        }
        return temp;
    }

    public List<PublicObjectiveCard> getExtractedPublicObjectiveCard() {
        return extractedPublicObjectiveCard;
    }

    /**
     * Returns an ArrayList of 4 WindowPatternCards
     *
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

    /**
     * Returns an ArrayList of 3 ToolCard
     *
     * @return list of extracted cards
     */
    public List<ToolCard> getExtractedToolCard() {
        return extractedToolCard;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public void insertDieInDiceBag(Die die) {
        getDiceBag().insertDie(die);
    }

    public Die extractDieFromDiceBag() {
        return getDiceBag().extractDie();
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
            e.printStackTrace();
        }
        notifyRefreshDraftPool();
        return temp;
    }

    public void changeDieValueOnDraftPool(int index, int value) {
        try {
            draftPool.getDie(index).setValue(value);
        } catch (EmptyCellException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
        draftpool = getDraftPool().draftpoolPathRepresentation();

        for (Observer observer : observers) {
            ((View) observer).updateDraftPool(new RefreshDraftPoolCommand(draftpool));
        }

    }

    private void notifyRefreshWpcs() {

        for (Observer observer : observers) {
            List<String> personalWpc = new ArrayList<>(); //Dice in the format: colorNumber/empty
            List<List<String>> otherPlayersWpcs = new ArrayList<>();
            View currentView = (View) observer;

            for (Player p : gamePlayers) {
                if (p.getUsername().equals(currentView.getUsername())) {
                    personalWpc = p.getWindowPatternCard().wpcPathRepresentation();
                } else {
                    otherPlayersWpcs.add(p.getWindowPatternCard().wpcPathRepresentation());
                }
            }

            ((View) observer).updateWpc(new RefreshWpcCommand(personalWpc, otherPlayersWpcs));
            LOGGER.log(Level.INFO, "Notificando una V.V. della new board -> new WPCards for " + currentView.getUsername());

        }
    }

    private void notifyRefreshRoundTrack() {

        for (Observer observer : observers) {
            List<String> roundTrackString = new ArrayList<>(); //Dice in the format: colorNumber/empty
            roundTrackString = getRoundTrack().roundtrackPathRepresentation();
            LOGGER.log(Level.INFO, "Notificando una V.V. della new board");
            ((View) observer).updateRoundTrack(new RefreshRoundTrackCommand(roundTrackString));
        }

    }

    private void notifyRefreshTokens() {
        LOGGER.log(Level.INFO, "Notificando una V.V. della new board");
        List<Integer> tokensToolCards = new ArrayList<>(); //Ordered


        for (ToolCard toolCard : extractedToolCard) {
            tokensToolCards.add(toolCard.getTokenCount());
        }


        for (Observer observer : observers) {
            View temp = (View) observer;
            List<Integer> otherPlayersTokens = new ArrayList<>();
            Integer myTokens = null;
            for (Player p : gamePlayers) {
                if (p.getUsername().equals(temp.getUsername()))
                    myTokens = p.getTokens();
                else {
                    otherPlayersTokens.add(p.getTokens());
                }
            }


            ((View) observer).updateTokens(new RefreshTokensCommand(otherPlayersTokens, tokensToolCards, myTokens));
        }
    }

    /**
     * Method for initial setting of the board
     */
    public void notifyRefreshBoard(String playerUsername, List<Player> orderedPlayers) {
        if (orderedPlayers!=null){
            this.gamePlayers=orderedPlayers;
        }

        List<String> draftpool = new ArrayList<>(); //Dice in the format: colorNumber/empty

        if (this.draftPool == null) { //not istantiated yet
            for (int i = 0; i < 9; i++) {
                draftpool.add("empty");
            }
        } else {
            draftpool = getDraftPool().draftpoolPathRepresentation();
        }


        List<String> roundTrackString; //Dice in the format: colorNumber/empty
        roundTrackString = getRoundTrack().roundtrackPathRepresentation();

        List<String> publicObjectiveCards = new ArrayList<>();
        List<String> publicObjectiveDescription = new ArrayList<>();


        for (PublicObjectiveCard card : extractedPublicObjectiveCard) {
            publicObjectiveCards.add(card.getName());
            publicObjectiveDescription.add(card.getDescription());
        }

        List<String> toolCards = new ArrayList<>();
        List<String> toolCardDescription = new ArrayList<>();
        List<Integer> tokensToolCards = new ArrayList<>(); //Ordered


        for (ToolCard toolCard : extractedToolCard) {
            toolCards.add(toolCard.getName());
            toolCardDescription.add(toolCard.getDescription());
            tokensToolCards.add(toolCard.getTokenCount());
        }


        for (Observer observer : observers) {
            View currentView = (View) observer;
            String username = currentView.getUsername();
            String privateObjectiveCard = null;
            String privateObjectiveCardDescription = null;
            List<String> personalWpc = new ArrayList<>(); //Dice in the format: colorNumber/empty
            List<List<String>> otherPlayersWpcs = new ArrayList<>();


            for (Player p : gamePlayers) {
                if (p.getUsername().equals(username)) {
                    personalWpc = p.getWindowPatternCard().wpcPathRepresentation();
                    privateObjectiveCard = p.getPrivateObjectiveCard().getName();
                    privateObjectiveCardDescription = p.getPrivateObjectiveCard().getDescription();
                } else {
                    otherPlayersWpcs.add(p.getWindowPatternCard().wpcPathRepresentation());
                }
            }


            List<Integer> otherPlayersTokens = new ArrayList<>();
            List<String> otherPlayersUsernames = new ArrayList<>();
            Integer myTokens = null;


            for (Player p : gamePlayers) {
                if (p.getUsername().equals(username))
                    myTokens = p.getTokens();
                else {
                    otherPlayersTokens.add(p.getTokens());
                    otherPlayersUsernames.add(p.getUsername());
                }
            }
            //TODO con 15 costruttori
            if (playerUsername != null) {
                if (playerUsername.equals(((View) observer).getUsername())) {
                    ((View) observer).updateBoard(new RefreshBoardCommand(privateObjectiveCard, privateObjectiveCardDescription, publicObjectiveCards, publicObjectiveDescription, toolCards, toolCardDescription, tokensToolCards,
                            draftpool, roundTrackString, personalWpc, myTokens, username, otherPlayersWpcs, otherPlayersTokens, otherPlayersUsernames));
                    //TODO: avvisa che è il suo turno if (currentPlayer==getPlayerFromUsername(playerUsername)){
                    return;
                }
            } else {
                ((View) observer).updateBoard(new RefreshBoardCommand(privateObjectiveCard, privateObjectiveCardDescription, publicObjectiveCards, publicObjectiveDescription, toolCards, toolCardDescription, tokensToolCards,
                        draftpool, roundTrackString, personalWpc, myTokens, username, otherPlayersWpcs, otherPlayersTokens, otherPlayersUsernames));
            }

        }
    }

    public void putDieOnRoundTrack(Die die) {
        roundTrack.placeDie(die);
        notifyRefreshRoundTrack();
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
        System.out.println("Error: returning a random one");
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

    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    public void setExtractedPublicObjectiveCard(List<PublicObjectiveCard> extractedPublicObjectiveCard) {
        this.extractedPublicObjectiveCard = extractedPublicObjectiveCard;
    }

    public void setExtractedToolCard(List<ToolCard> extractedToolCard) {
        this.extractedToolCard = extractedToolCard;
    }

    public void setWindowPatternCardDeck(List<WindowPatternCard> windowPatternCardDeck) {
        this.windowPatternCardDeck = windowPatternCardDeck;
    }

    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
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
