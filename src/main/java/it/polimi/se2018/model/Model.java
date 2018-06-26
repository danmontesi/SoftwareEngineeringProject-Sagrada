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
public class Model extends Observable implements Serializable { //Observable of View


    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    private DiceBag diceBag;

    private ArrayList<PublicObjectiveCard> extractedPublicObjectiveCard;

    private ArrayList<ToolCard> extractedToolCard;

    private ArrayList<WindowPatternCard> windowPatternCardDeck;

    private ArrayList<Player> gamePlayers;

    private RoundTrack roundTrack;

    private DraftPool draftPool;

    //private ArrayList<Observer> observers; Already in the class thanks to Observable ->le virtual view!

    public void setDraftPool(ArrayList<Die> dice) {
        this.draftPool = new DraftPool(dice);
    }

    /**
     * Constructor: generates a game by
     * uploading all WindowPatternCards, PublicObjectiveCards, PrivateObjectiveCards and ToolCards
     * extracting 3 PublicObjectiveCards, creating 10 rounds
     * initializing the diceBag, the game players list, the roundTrack
     *
     * @param players list of game players
     */
    public Model(ArrayList<Player> players) {
        gamePlayers = players;
        diceBag = new DiceBag();
        roundTrack = new RoundTrack();
        ParserPrivateObjectiveCard parserPrivateObjectiveCard = new ParserPrivateObjectiveCard();
        ParserPublicObjectiveCard parserPublicObjectiveCard = new ParserPublicObjectiveCard();
        ParserWindowPatternCard parserWindowPatternCard = null;
        ParserToolcard parserToolcard = new ParserToolcard();
        try {
            parserWindowPatternCard = new ParserWindowPatternCard();
        } catch (IOException e) {

        }
        ArrayList<PrivateObjectiveCard> privateObjectiveCardDeck = new ArrayList<>();
        ArrayList<PublicObjectiveCard> publicObjectiveCardDeck = new ArrayList<>();

        windowPatternCardDeck = parserWindowPatternCard.parseAllCards();

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

        ArrayList<ToolCard> toolCardDeck = new ArrayList<>();
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

    public ArrayList<Die> extractDraftPoolDice(int numPlayers) {
        ArrayList<Die> temp = new ArrayList<>();
        for (int i = 0; i < 2 * numPlayers + 1; i++) {
            temp.add(diceBag.extractDie());
        }
        return temp;
    }

    public ArrayList<PublicObjectiveCard> getExtractedPublicObjectiveCard() {
        return extractedPublicObjectiveCard;
    }

    /**
     * Returns an ArrayList of 4 WindowPatternCards
     *
     * @return list of extracted cards
     */
    public ArrayList<WindowPatternCard> extractWindowPatternCard() {
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

    public void setGamePlayers(ArrayList<Player> gamePlayers) {
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

    public void assignRefreshedPlayersCardsAndTokens(ArrayList<Player> players) {
        this.gamePlayers = players;
        notifyRefreshBoard();
    }

    public void increaseToolCardTokens(int toolCardNumber, int tokens) {
        extractedToolCard.get(toolCardNumber).increaseTokens(tokens);
        notifyRefreshTokens();
    }

    private void notifyRefreshDraftPool() {
        ArrayList<String> draftpool;
        draftpool = getDraftPool().draftpoolPathRepresentation();
        for (Observer observer : observers) {
            ((View) observer).updateDraftPool(new RefreshDraftPoolCommand(draftpool));
        }
    }

    private void notifyRefreshWpcs() {
        for (Observer observer : observers) {
            ArrayList<String> personalWpc = new ArrayList<>(); //Dice in the format: colorNumber/empty
            ArrayList<ArrayList<String>> otherPlayersWpcs = new ArrayList<>();
            View currentView = (View) observer;
            for (Player p : gamePlayers) {
                if (p.getUsername().equals(currentView.getUsername())) {
                    personalWpc = p.getWindowPatternCard().wpcPathRepresentation();
                } else {
                    otherPlayersWpcs.add(p.getWindowPatternCard().wpcPathRepresentation());
                }
            }
            ((View) observer).updateWpc(new RefreshWpcCommand(personalWpc, otherPlayersWpcs));
            LOGGER.log(Level.INFO,"Notificando una V.V. della new board -> new WPCards for " + currentView.getUsername());
        }
    }

    private void notifyRefreshRoundTrack() {
        for (Observer observer : observers) {
            ArrayList<String> roundTrackString = new ArrayList<>(); //Dice in the format: colorNumber/empty
            roundTrackString = getRoundTrack().roundtrackPathRepresentation();
            LOGGER.log(Level.INFO,"Notificando una V.V. della new board");
            ((View) observer).updateRoundTrack(new RefreshRoundTrackCommand(roundTrackString));
        }
    }

    private void notifyRefreshTokens() {
        LOGGER.log(Level.INFO,"Notificando una V.V. della new board");
        ArrayList<Integer> tokensToolCards = new ArrayList<>(); //Ordered
        for (ToolCard toolCard : extractedToolCard) {
            tokensToolCards.add(toolCard.getTokenCount());
        }

        for (Observer observer : observers) {
            View temp = (View) observer;
            ArrayList<Integer> otherPlayersTokens = new ArrayList<>();
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
    public void notifyRefreshBoard() {
        ArrayList<String> draftpool; //Dice in the format: colorNumber/empty
        draftpool = getDraftPool().draftpoolPathRepresentation();
        ArrayList<String> roundTrackString; //Dice in the format: colorNumber/empty
        roundTrackString = getRoundTrack().roundtrackPathRepresentation();

        ArrayList<String> publicObjectiveCards = new ArrayList<>();
        ArrayList<String> publicObjectiveDescription = new ArrayList<>();
        for (PublicObjectiveCard card : extractedPublicObjectiveCard) {
            publicObjectiveCards.add(card.getName());
            publicObjectiveDescription.add(card.getDescription());
        }

        ArrayList<String> toolCards = new ArrayList<>();
        ArrayList<String> toolCardDescription = new ArrayList<>();
        ArrayList<Integer> tokensToolCards = new ArrayList<>(); //Ordered
        for (ToolCard toolCard : extractedToolCard) {
            toolCards.add(toolCard.getName());
            toolCardDescription.add(toolCard.getDescription());
            tokensToolCards.add(toolCard.getTokenCount());
        }

        for (Observer observer : observers) {
            View currentView = (View) observer;
            String username = currentView.getUsername();

            //Private obj card
            String privateObjectiveCard = null;
            String privateObjectiveCardDescription = null;
            //WPC initialization
            ArrayList<String> personalWpc = new ArrayList<>(); //Dice in the format: colorNumber/empty
            ArrayList<ArrayList<String>> otherPlayersWpcs = new ArrayList<>();
            for (Player p : gamePlayers) {
                if (p.getUsername().equals(username)) {
                    personalWpc = p.getWindowPatternCard().wpcPathRepresentation();
                    privateObjectiveCard = p.getPrivateObjectiveCard().getName();
                    privateObjectiveCardDescription = p.getPrivateObjectiveCard().getDescription();
                } else {
                    otherPlayersWpcs.add(p.getWindowPatternCard().wpcPathRepresentation());
                }
            }
            ArrayList<Integer> otherPlayersTokens = new ArrayList<>();
            ArrayList<String> otherPlayersUsernames = new ArrayList<>();
            Integer myTokens = null;
            String playerUsr = username;
            for (Player p : gamePlayers) {
                if (p.getUsername().equals(username))
                    myTokens = p.getTokens();
                else {
                    otherPlayersTokens.add(p.getTokens());
                    otherPlayersUsernames.add(p.getUsername());
                }
            }

            observer.update(new RefreshBoardCommand(privateObjectiveCard, privateObjectiveCardDescription, publicObjectiveCards, publicObjectiveDescription, toolCards, toolCardDescription, tokensToolCards,
                    draftpool, roundTrackString, personalWpc, myTokens, playerUsr, otherPlayersWpcs, otherPlayersTokens, otherPlayersUsernames));
        }
    }

    public void putDieOnRoundTrack(Die die) {
        roundTrack.placeDie(die);
        notifyRefreshRoundTrack();
    }

    public Die getLastDie() throws EmptyCellException {
        Die toReturn;
        toReturn = draftPool.getLastDie();
        notifyRefreshDraftPool();
        return toReturn;
    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

}
