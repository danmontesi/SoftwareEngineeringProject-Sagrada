package shared.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import server.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that instantiates all the Tool Cards
 * @author Alessio Molinari
 */
public class ParserToolcard {

    private static final String TC_JSON = "tc.json";
    private static final int MAX_NUMBER_OF_FIELDS_IN_ACTION = 3;

    /**
     * @return ArrayList of all Tool Cards in json file
     * @throws IOException
     */
    public List<ToolCard> parseCards() throws IOException {
        ParserSettings settings = new ParserSettings();
        JsonObject jsonObject = settings.extractJsonObject(TC_JSON);
        JsonArray cards = jsonObject.get("ToolCards").getAsJsonArray();

        List<ToolCard> toolCards = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            JsonObject jCard = cards.get(i).getAsJsonObject();
            String name = jCard.get("name").getAsString();
            String description = jCard.get("description").getAsString();
            boolean reversible = jCard.get("reversible").getAsBoolean();
            JsonArray jActions = jCard.get("actions").getAsJsonArray();
            List<Action> actions = new ArrayList<>();
            for (int j = 0; j < jActions.size(); j++) {
                JsonArray jParameters = jActions.get(j).getAsJsonArray();
                String[] parameters = new String[MAX_NUMBER_OF_FIELDS_IN_ACTION];
                for (int k = 0; k < jParameters.size(); k++) {
                    parameters[k] = jParameters.get(k).getAsString();
                }
                //  !!!  Notice that this could throw an IlligalArgumentException if the json does not match the enum
                actions.add(new Action(ACTION_TYPE.valueOf(parameters[0]), parameters[1], parameters[2]));
            }
            toolCards.add(new ToolCard(name, description, actions, reversible));
        }
        return toolCards;
    }

}
