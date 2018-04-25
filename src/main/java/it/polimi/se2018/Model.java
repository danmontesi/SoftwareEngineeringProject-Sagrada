package it.polimi.se2018;

import it.polimi.se2018.toolcards.ToolCard;

import javax.tools.Tool;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


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

    private Model game(){

    }

    public static Model getInstance(){

    }

    public Player calculateWinner(){

    }

    public void nextRound() {
        if (gameRounds.size() == 0) {
            //notifyWinner();
        } else {
            currentRound = gameRounds.remove(0);
            currentRound.nextPlayer();
        }
    }

    public Round createGameRound(){

    }

    public ArrayList<PublicObjectiveCard> getExtractedPublicObjectiveCard(){

    }

    public ArrayList<WindowPatternCard> getExtractedWindowPatternCard(int toBeExtracted){

    }

    public ArrayList<ToolCard> getExtractedToolCard{

    }

    public DiceBag getDiceBag{

    }

    public Player getPlayer(int playersNumber){

    }

    public Round getCurrentRound{

    }

    public Round getRound(int roundNumber){

    }

    public RoundTrack getRoundTrack(){

    }
}
