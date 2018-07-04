package it.polimi.se2018.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.se2018.exceptions.NoSuchColorException;
import it.polimi.se2018.model.COLOR;
import it.polimi.se2018.model.PrivateObjectiveCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that instantiates all the Private Objective Cards
 * @author Alessio Molinari
 */
public class ParserPrivateObjectiveCard {
    private static final String PATH_NAME = "privateoc.json";

    private ParserSettings settings;

    /**
     * @return ArrayList of all Private Objective Cards in json file
     * @throws IOException
     */
    public List<PrivateObjectiveCard> parseCards() throws IOException {
        settings = new ParserSettings();
        JsonObject json = settings.extractJsonObject(PATH_NAME);
        JsonArray jcards = json.get("PrivateObjectiveCards").getAsJsonArray();
        List<PrivateObjectiveCard> cards = new ArrayList<>();

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
