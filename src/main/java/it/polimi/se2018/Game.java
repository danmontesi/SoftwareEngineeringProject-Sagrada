package it.polimi.se2018;

import it.polimi.se2018.toolcards.ToolCard;

import javax.tools.Tool;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
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
    public static Game instance;

    private Game game(){

    }

    public static Game getInstance(){

    }

    public Player calculateWinner(){

    }

    public void nextRound(){

    }

    public Round createGameRound(){

    }

    public ArrayList<PublicObjectiveCard> getExtractedPublicObjectiveCard{

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
