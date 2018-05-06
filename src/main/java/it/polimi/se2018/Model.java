package it.polimi.se2018;

import it.polimi.se2018.MVC.Controller;
import it.polimi.se2018.network.ClientConnection;
import it.polimi.se2018.toolcards.ToolCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This is the model, the class that maintain the State of the game
 * It is an Observable from a VirtualView (the Observer).
 * The Virtual View just send "broadcast" all graphical changes of the board
 *
 * The controller directly modifies the Model.
 *
 */

public class Model extends Observable{

    private ArrayList<Observer> observers;
    private DiceBag diceBag;

    private ArrayList<PrivateObjectiveCard> privateObjectiveCardDeck;

    private ArrayList<PublicObjectiveCard> publicObjectiveCardDeck;
    private ArrayList<PublicObjectiveCard> extractedPublicObjectiveCard;

    private ArrayList<ToolCard> toolCardDeck;
    private ArrayList<ToolCard> extractedToolCard;

    private ArrayList<WindowPatternCard> windowPatternCardDeck;

    /**
     * Changed from ClientConnection to Players (The conotroller knows to which player correspond its Client)
     */
    private ArrayList<Player> connectedPlayers;
    private ArrayList<Player> disconnectedPlayers;

    private ArrayList<Player> gamePlayers;
    private RoundTrack roundTrack;
    private Round currentRound;
    private ArrayList<Round> gameRounds;
    public static Model instance;


    /**
     * Constructor must
     *
     * - call createRound()
     * - initialize all attributes (creating new ones)
     * - NOT TODO: initialize the decks-> we will do it later in the project
     */
    private Model(ArrayList<Player> players){


        gamePlayers = players;
        gameRounds = createRounds();
        diceBag = DiceBag.getInstance();
        roundTrack = new RoundTrack();
        // TODO CREATE ALL CARDS
    }

    /**
     * Singleton
     */
    //TODO: FALLO BENE CIT. DANI
    public static Model getInstance(ArrayList<Player> players){
        if (instance==null){
            instance = new Model(players);
        }
        return instance;
    }

    /**
     * Calculates total score of a given player
     */

    public int playerScore(Player player) {
        int publicObjectiveScore = 0;
        for (int i=0; i<3; i++){
            publicObjectiveScore += extractedPublicObjectiveCard.get(i).calculateScore(player.getWindowPatternCard());
        }
        return player.calcuateTotalScore() + publicObjectiveScore;
    }

    /**
     * Calculates all scores of any player
     * saves them in a HashMap, and returns it
     *
     * Scores of any player are calculated summing each Player.calculateTotalScore() and every score of
     * the PublicObjectiveCards of extractedPublicObjectiveCard, calculated on each player
     */

    public HashMap<Player,Integer> playersScore() {
        HashMap<Player, Integer> playersScore = new HashMap<>();
        for (int i=0; i<gamePlayers.size(); i++){
            playersScore.put(gamePlayers.get(i), playerScore(gamePlayers.get(i)));
        }
        return playersScore;
    }

    public void nextRound() {
        if (gameRounds.size() == 0) {
            //notifyWinner();
        } else {
            currentRound = gameRounds.remove(0);
            currentRound.nextPlayer();
        }
    }

    /**

    /**
     * Initialize all 10 rounds with all attributes except Dice (they are extracted every time)
     */
    public ArrayList<Round> createRounds() {
        ArrayList<Round> roundList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            roundList.add(new Round(i+1, gamePlayers.get(((i+1)%4)-1), gamePlayers, diceBag));
        }
        return roundList;
    }

    public ArrayList<PublicObjectiveCard> getExtractedPublicObjectiveCard(){
        for (int i=0; i<3; i++){
            int index = ThreadLocalRandom.current().nextInt(0,  publicObjectiveCardDeck.size());
            extractedPublicObjectiveCard.set(i, publicObjectiveCardDeck.remove(index));
        }
        return extractedPublicObjectiveCard;
    }

    /**
     * return an ArrayList extracting first card of the windowPatternCardDeck
     * always need to extract 4 cards together, so no need for a single 'extractOneCard' method
     */
    public ArrayList<WindowPatternCard> extractWindowPatternCard(){
        for (int i=0; i<4; i++){
            int index = ThreadLocalRandom.current().nextInt(0,  windowPatternCardDeck.size());
            extractWindowPatternCard().set(i, windowPatternCardDeck.remove(index));
        }
        return extractWindowPatternCard();
    }

    public ArrayList<ToolCard> getExtractedToolCard(){
        for (int i=0; i<3; i++){
            int index = ThreadLocalRandom.current().nextInt(0,  toolCardDeck.size());
            extractedToolCard.set(i, toolCardDeck.remove(index));
        }
        return extractedToolCard;
    }

    public Round getCurrentRound(){
        return currentRound;
    }

    public Round getRound(int roundNumber){
        return gameRounds.get(roundNumber);
    }

    public RoundTrack getRoundTrack(){
        return roundTrack;
    }

    public void addDisconnectedPlayer(Player pl){
        disconnectedPlayers.add(pl);
        connectedPlayers.remove(pl);
    }

    public void addReconnectedPlayer(Player pl){
        disconnectedPlayers.remove(pl);
        connectedPlayers.add(pl);
    }

    /**
     * Notify methods for Controller
     */
    public void notifyModelChanges(){
        for (Observer o : observers)
            o.update(this, this);
    }

    public void addObserver(Observer o){
        observers.add(o);
    }


    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public ArrayList<Round> getGameRounds() {
        return gameRounds;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }


    public ArrayList<Player> getConnectedPlayers() {
        return connectedPlayers;
    }

    public ArrayList<Player> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }


}
