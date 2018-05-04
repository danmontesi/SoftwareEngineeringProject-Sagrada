package it.polimi.se2018;

import it.polimi.se2018.MVC.VirtualView;
import it.polimi.se2018.toolcards.ToolCard;

import javax.tools.Tool;
import java.awt.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;


/** TODO: Problemi nel capire come interagisce il client con il server.
 *
 * TODO Il server passa il Model al client, per permettere di chiamare anche i metodi toString del model?
 *
 * TODO: Capire dove viene creato il model, guarda come funziona Tris, guarda gli Event-based, pensa ai problemi di concorrenza
 *
 *
 * CHANGES: Chiamato model al posto di Game per coerenza del pattern
 * Aggiunte alcune classi model-view-controller
 *
 */

public class Model extends Observable{

    private Observer virtualView;
    private DiceBag diceBag;
    private DraftPool draftPool;
    private ArrayList<PrivateObjectiveCard> privateObjectiveCardDeck;

    private ArrayList<PublicObjectiveCard> publicObjectiveCardDeck;
    private ArrayList<PublicObjectiveCard> extractedPublicObjectiveCard;

    private ArrayList<ToolCard> toolCardDeck;
    private ArrayList<ToolCard> extractedToolCard;

    private ArrayList<WindowPatternCard> windowPatternCardDeck;

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
    private Model(ArrayList<Player> players, ArrayList<Connection> connections, ){
        gamePlayers = players;
        gameRounds = createRound();
        currentRound.setFirstPlayer(gamePlayers.get(0));
        diceBag = new DiceBag();
        roundTrack = new RoundTrack();
        draftPool = new DraftPool();

        virtualView= new VirtualView();
    }

    /**
     * Singleton
     */
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
     * Assign the draftPool at the very beginning of the round
     * <p>
     * meanwhile, the round is built when the game starts
     */
    public void setDice() {
        int n = 2*gamePlayers.size()+1;
        for (int i=0; i<n; i++){
            draftPool.placeDie(diceBag.extractDie());
        }
    }

    /**
     * Initialize all 10 rounds with all attributes except Dice (they are extracted every time)
     */
    public ArrayList<Round> createRound() {
        ArrayList<Round> roundList = null;
        for (int i = 0; i < 10; i++) {
            roundList.set(i, new Round());
            roundList.get(i).setRoundNumber(i+1);
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

    public DiceBag getDiceBag(){
        return diceBag;
    }

    /**
     * get the Player of ArrayList "players" of the @param(playerNumber) position
     */
    public Player getPlayer(int playerNumber){
        return gamePlayers.get(playerNumber);
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

    public void notifyChanges(){
        virtualView.update(this, this);

    }
}
