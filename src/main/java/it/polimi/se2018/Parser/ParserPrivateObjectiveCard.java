package it.polimi.se2018.Parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.se2018.COLOR;
import it.polimi.se2018.Exceptions.NoSuchColorException;
import it.polimi.se2018.PrivateObjectiveCard;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Alessio
 * Class that instantiates all the PrivateObjectiveCards
 *
 */
public class ParserPrivateObjectiveCard {
    private static final String PATH_NAME = "privateoc.json";

    private ParserSettings settings;

    /**
     *
     * @return ArrayList of all PrivateObjectiveCards in json file
     * @throws IOException
     */
    public ArrayList<PrivateObjectiveCard> parseCards() throws IOException {
        settings = new ParserSettings();
        JsonObject json = settings.extractJsonObject(PATH_NAME);
        JsonArray jcards = json.get("PrivateObjectiveCards").getAsJsonArray();
        ArrayList<PrivateObjectiveCard> cards = new ArrayList<>();

        for (int i = 0; i < jcards.size(); i++){
            JsonObject jcard = jcards.get(i).getAsJsonObject();
            String name = jcard.get("name").getAsString();
            String description = jcard.get("description").getAsString();
            String color = jcard.get("color").getAsString();

            try {
                PrivateObjectiveCard card = new PrivateObjectiveCard(name, description, COLOR.stringToColor(color));
                cards.add(card);
            } catch (NoSuchColorException e) {
                System.out.println("Controllare ortograia colori: private obj cards");
            }
        }
        return cards;
    }
}
