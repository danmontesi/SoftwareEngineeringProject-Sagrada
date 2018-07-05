package server.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import server.model.public_obj_cards.PublicObjectiveCard;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that instantiates all the Public Objective Cards
 * @author Alessio Molinari
 */
public class ParserPublicObjectiveCard {
    private static final String PATH_NAME = "publicoc.json";

    /**
     * @return ArrayList of all Public Objective Cards in json file
     * @throws IOException
     */
    public List<PublicObjectiveCard> parseCards() throws IOException {
        ParserSettings settings = new ParserSettings();
        JsonObject json = settings.extractJsonObject(PATH_NAME);
        JsonArray jcards = json.get("PublicObjectiveCards").getAsJsonArray();
        List<PublicObjectiveCard> cards = new ArrayList<>();
        for(int i = 0; i < jcards.size(); i++){
            JsonObject jcard = jcards.get(i).getAsJsonObject();
            String cardName = jcard.get("name").getAsString();
            String description = jcard.get("description").getAsString();
            int score = jcard.get("score").getAsInt();
            String name = cardName;
            name = name.replaceAll("\\s","");
            name = "server.model.public_obj_cards." + name;

            try {
                Class<?> clazz = Class.forName(name);
                Constructor<?> constructor = clazz.getConstructor(String.class, String.class, Integer.class);
                PublicObjectiveCard instance = (PublicObjectiveCard)constructor.newInstance(cardName, description, score);
                cards.add(instance);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    return cards;
    }
}