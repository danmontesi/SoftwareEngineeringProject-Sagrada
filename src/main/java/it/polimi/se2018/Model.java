package it.polimi.se2018;

import it.polimi.se2018.parser.ParserPrivateObjectiveCard;
import it.polimi.se2018.parser.ParserPublicObjectiveCard;
import it.polimi.se2018.parser.ParserWindowPatternCard;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;
import it.polimi.se2018.toolcards.ToolCard;
import it.polimi.se2018.utils.Observable;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This is the model, the class that maintain the State of the game
 * It is an Observable from a VirtualView (the Observer).
 * The Virtual View just send "broadcast" all graphical changes of the board
 *
 * The controller directly modifies the Model.
 *
 */
public class Model extends Observable { //Observable of View

    private DiceBag diceBag;

    private ArrayList<PublicObjectiveCard> extractedPublicObjectiveCard;

    private ArrayList<ToolCard> extractedToolCard;

    private ArrayList<WindowPatternCard> windowPatternCardDeck;

    private ArrayList<PrivateObjectiveCard> privateObjectiveCardDeck;

    private ArrayList<Player> gamePlayers;

    private RoundTrack roundTrack;

    public DraftPool getDraftPool() {
        return draftPool;
    }

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
     * @param players list of game players
     */
    public Model(ArrayList<Player> players){
        gamePlayers = players;
        diceBag = new DiceBag();
        roundTrack = new RoundTrack();
        ParserPrivateObjectiveCard parserPrivateObjectiveCard = new ParserPrivateObjectiveCard();
        ParserPublicObjectiveCard parserPublicObjectiveCard = new ParserPublicObjectiveCard();
        ParserWindowPatternCard parserWindowPatternCard = new ParserWindowPatternCard();

        ArrayList<PublicObjectiveCard> publicObjectiveCardDeck = new ArrayList<>();
        privateObjectiveCardDeck = new ArrayList<>();

        try {
            windowPatternCardDeck = parserWindowPatternCard.parseCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        //TODO: Create the deck from a json file
        ArrayList<ToolCard> toolCardDeck = new ArrayList<>();

        extractedToolCard= new ArrayList<>();
        for (int i=0; i<3; i++){
            int index = ThreadLocalRandom.current().nextInt(0,  toolCardDeck.size());
            extractedToolCard.set(i, toolCardDeck.remove(index));
        }

    }

    public ArrayList<Die> extractDraftPoolDice(int numPlayers){
        ArrayList<Die> temp = new ArrayList<>();
        for (int i = 0; i < 2*numPlayers+1; i++) {
            temp.add(diceBag.extractDie());
        }
        return temp;
    }


    //Metodo boolean per fare una mossa alla casella X del giocatore Y con il dado D con le condizioni valore, colore, placement
    // che torna True o False

    //Metodo per get vari dei giocatori

    //...








    public ArrayList<PublicObjectiveCard> getExtractedPublicObjectiveCard(){
        return extractedPublicObjectiveCard;
    }

    /**
     * Returns an ArrayList of 4 WindowPatternCards
     * @return list of extracted cards
     */
    public ArrayList<WindowPatternCard> extractWindowPatternCard(){
        ArrayList<WindowPatternCard> toReturn = new ArrayList<>();
        for (int i=0; i<4; i++){
            int index = ThreadLocalRandom.current().nextInt(0,  windowPatternCardDeck.size());
            toReturn.add(i, windowPatternCardDeck.remove(index));
        }
        return toReturn;
    }

    /**
     * Returns an ArrayList of 3 ToolCard
     * @return list of extracted cards
     */
    public ArrayList<ToolCard> getExtractedToolCard(){
        return extractedToolCard;
    }

    public RoundTrack getRoundTrack(){
        return roundTrack;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    //Aready has
    //public void notify(Object event) {

}
