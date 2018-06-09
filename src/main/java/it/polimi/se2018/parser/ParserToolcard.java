package it.polimi.se2018.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.se2018.toolcards.ToolCard;

import java.io.IOException;
import java.util.ArrayList;

public class ParserToolcard {

    private static final String TC_JSON = "tc.json";

    private ParserSettings settings;

    public ArrayList<ToolCard> parseCards() throws IOException {
        settings = new ParserSettings();
        JsonObject jsonObject = settings.extractJsonObject(TC_JSON);
        JsonArray cards = jsonObject.get("ToolCards").getAsJsonArray();

        ArrayList<ToolCard> toolCards = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++){
            JsonObject jcard =  cards.get(i).getAsJsonObject();
            String name = jcard.get("name").getAsString();
            String description = jcard.get("description").getAsString();
            toolCards.add(new ToolCard(name, description));
        }
        return toolCards;
    }

}
