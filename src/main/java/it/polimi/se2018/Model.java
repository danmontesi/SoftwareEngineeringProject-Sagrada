package it.polimi.se2018;

import it.polimi.se2018.Parser.ParserPrivateObjectiveCard;
import it.polimi.se2018.Parser.ParserPublicObjectiveCard;
import it.polimi.se2018.Parser.ParserWindowPatternCard;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;
import it.polimi.se2018.toolcards.ToolCard;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;


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

    //Changed from ClientConnection to Players (The conotroller knows to which player correspond its Client)
    private ArrayList<Player> connectedPlayers;

    private ArrayList<Player> gamePlayers;

    private RoundTrack roundTrack;

    public DraftPool getDraftPool() {
        return draftPool;
    }

    private DraftPool draftPool;

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
        // TODO CREATE ALL CARDS FROM JSON FILES
        ParserPrivateObjectiveCard parserPrivateObjectiveCard = new ParserPrivateObjectiveCard();
        ParserPublicObjectiveCard parserPublicObjectiveCard = new ParserPublicObjectiveCard();
        ParserWindowPatternCard parserWindowPatternCard = new ParserWindowPatternCard();

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

        // ora aspetto che il controller esegua comandi
    }


    public ArrayList<PublicObjectiveCard> getExtractedPublicObjectiveCard(){
        for (int i=0; i<3; i++){
            int index = ThreadLocalRandom.current().nextInt(0,  publicObjectiveCardDeck.size());
            extractedPublicObjectiveCard.set(i, publicObjectiveCardDeck.remove(index));
        }
        return extractedPublicObjectiveCard;
    }

    public ArrayList<Die> extractDraftPoolDice(int numPlayers){
        ArrayList<Die> temp = new ArrayList<>();
        for (int i = 0; i < 2*numPlayers+1; i++) {
            temp.add(diceBag.extractDie());
        }
        return temp;
    }

    /**
     * Returns an ArrayList of 4 WindowPatternCards
     * @return list of extracted cards
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

    public RoundTrack getRoundTrack(){
        return roundTrack;
    }

    /**
     * Notifies Controller of Model changes
     */
    public void notifyModelChanges(){
        for (Observer o : observers)
            o.update(this, this);
    }

    /**
     * Adds an observer
     * @param o to be added observer
     */
    public void addObserver(Observer o){
        observers.add(o);
    }


    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }



    public String stringModelRepresentationForPlayer(Player player){
        String string = "";
        //4 blocks
        //1- DraftPool
        //2- toolcard
        //3- publicobjective
        //4- private Objective(dependent to Player)
        //5- WindowPatternCard
        //6- roundtrack
        //7- number of players (2,3,4)
        //8- each player's roundtrack (first is the player's)
        //different cards separated by comma",". sections separated by "-"

        //DraftPool
        string += "";



        string += "";

        return string;


    }

}
