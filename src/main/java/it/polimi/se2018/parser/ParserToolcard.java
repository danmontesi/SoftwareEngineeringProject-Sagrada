package it.polimi.se2018.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.se2018.model.ToolCard;

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

    /* //TODO RIAGGIUNGI AL JSON
    MOMENTANEO REMOVE DELLE TOOLCARD:

    {
      "name": "Circular Cutter",
      "description": "After drafting, swap the drafted die with a die from the Round Track."
    },
    {
      "name": "Firm Pastry Brush",
      "description": "After drafting, re-roll che drafted die. If it cannot be placed, return it to the Draft Pool."
    },
    {
      "name": "Gavel",
      "description": "Re-roll all dice in the Draft Pool. This may only be used on your second turn before drafting."
    },
    {
      "name": "Wheels Pincher",
      "description": "After your first turn, immediately draft a die. Skip your next turn this round."
    },
    {
      "name": "Cork Line",
      "description": "After drafting, place the die in a spot that is not adjacent to another die. You must obey all other placement restrictions."
    },
    {
      "name": "Diamond Swab",
      "description": "After drafting, flip the die to its opposite side. 6 flips to 1, 5 to 2, 4 to 3, etc."
    },
    {
      "name": "Firm Pastry Thinner",
      "description": "After drafting, return the die to the Dice Bag and pull 1 die from the bag. Choose a value and place the nwe die, obeying all placement restrictions, or return it to the Draft Pool."
    },
     */
}
