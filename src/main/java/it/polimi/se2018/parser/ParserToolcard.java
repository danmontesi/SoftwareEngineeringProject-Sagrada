package it.polimi.se2018.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.se2018.model.ACTION_TYPE;
import it.polimi.se2018.model.Action;
import it.polimi.se2018.model.ToolCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserToolcard {

    private static final String TC_JSON = "tc.json";
    private static final int MAX_NUMBER_OF_FIELDS_IN_ACTION = 3;

    private ParserSettings settings;

    public ArrayList<ToolCard> parseCards() throws IOException {
        settings = new ParserSettings();
        JsonObject jsonObject = settings.extractJsonObject(TC_JSON);
        JsonArray cards = jsonObject.get("ToolCards").getAsJsonArray();

        ArrayList<ToolCard> toolCards = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            JsonObject jcard = cards.get(i).getAsJsonObject();
            String name = jcard.get("name").getAsString();
            String description = jcard.get("description").getAsString();
            JsonArray jActions = jcard.get("actions").getAsJsonArray();
            List<Action> actions = new ArrayList<>();
            for (int j = 0; j < jActions.size(); j++) {
                JsonArray jParameters = jActions.get(j).getAsJsonArray();
                String[] parameters = new String[MAX_NUMBER_OF_FIELDS_IN_ACTION];
                for (int k = 0; k < jParameters.size(); k++) {
                    parameters[k] = jParameters.get(k).getAsString();
                }
                //  !!!  Notice that this could throw an IlligalArgumentException if the json does not mactch the enum
                actions.add(new Action(ACTION_TYPE.valueOf(parameters[0]), parameters[1], parameters[2]));
            }
            toolCards.add(new ToolCard(name, description, actions));
        }
        return toolCards;
    }
}
