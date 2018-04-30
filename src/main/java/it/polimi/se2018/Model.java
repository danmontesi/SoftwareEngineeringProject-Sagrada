package it.polimi.se2018;

import it.polimi.se2018.toolcards.ToolCard;

import javax.tools.Tool;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


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

public class Model {
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
    private Model(){

    }

    public static Model getInstance(){

    }

    /**
     * Calculates all scores of any player
     * saves them in a HashMap, and returns it
     *
     * Scores of any player are calculated summing each Player.calculateTotalScore() and every score of
     * the PublicObjectiveCards of extractedPublicObjectiveCard, calculated on each player
     *
     * @return HashMap
     */

    public HashMap<Player,Integer> playersScore(){

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
     * Initialize all 10 rounds with all attributes except Dices (they are extracted every time)
     *
     * @return
     */
    public Round createGameRound(){

    }

    public ArrayList<PublicObjectiveCard> getExtractedPublicObjectiveCard(){

    }

    /**
     * return an ArrayList extracting first card of the windowPatternCardDeck
     * always need to extract 4 cards together, so no need for a single 'extractOneCard' method
     *
     * @param toBeExtracted
     * @return
     */
    public ArrayList<WindowPatternCard> extractWindowPatternCard(int toBeExtracted){

    }

    public ArrayList<ToolCard> getExtractedToolCard(){

    }

    public DiceBag getDiceBag(){

    }

    /**
     * get the Player of ArrayList "players" of the @param(playerNumber) position
     * @param playerNumber
     * @return
     */
    public Player getPlayer(int playerNumber){

    }

    public Round getCurrentRound(){

    }

    public Round getRound(int roundNumber){

    }

    public RoundTrack getRoundTrack(){

    }
}
