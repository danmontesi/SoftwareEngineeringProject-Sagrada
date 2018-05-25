package it.polimi.se2018.Parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ParserPublicObjectiveCard {
    private static final String PATH_NAME = "publicoc.json";

    public ArrayList<PublicObjectiveCard> parseCards() throws IOException {
        ParserSettings settings = new ParserSettings();
        JsonObject json = settings.extractJsonObject(PATH_NAME);
        JsonArray jcards = json.get("PublicObjectiveCards").getAsJsonArray();
        ArrayList<PublicObjectiveCard> cards = new ArrayList<>();
        for(int i = 0; i < jcards.size(); i++){
            JsonObject jcard = jcards.get(i).getAsJsonObject();
            String name = jcard.get("name").getAsString();
            String description = jcard.get("description").getAsString();
            int score = jcard.get("score").getAsInt();
            name = name.replaceAll("\\s","");
            name = "it.polimi.se2018.public_obj_cards." + name;

            try {
                Class<?> clazz = Class.forName(name);
                Constructor<?> constructor = clazz.getConstructor(String.class, String.class, Integer.class);
                PublicObjectiveCard instance = (PublicObjectiveCard)constructor.newInstance(name, description, score);
                cards.add(instance);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    return cards;
    }
}