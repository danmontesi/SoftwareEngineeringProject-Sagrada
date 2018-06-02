package it.polimi.se2018.Parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.se2018.COLOR;
import it.polimi.se2018.Exceptions.NoSuchColorException;
import it.polimi.se2018.WindowPatternCard;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Alessio
 * Class that instantiates Window Pattern Cards
 */
public class ParserWindowPatternCard {
    private static final String WPC_JSON = "wpc.json";

    private ParserSettings settings;

    private ArrayList<WindowPatternCard> allCards;

    /**
     *
     * @return ArrayList of all WindowPatternCards contained in json file
     * @throws IOException
     */

    public ParserWindowPatternCard(){
        try {
            this.allCards = parseCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<WindowPatternCard> parseCards() throws IOException {
        settings = new ParserSettings();
        JsonObject WPCCards = settings.extractJsonObject(WPC_JSON);
        JsonArray cards = WPCCards.get("WindowPatternCards").getAsJsonArray();
        //ArrayList which will contain every WindowPatternCard
        ArrayList<WindowPatternCard> windowPatternCards = new ArrayList<>();

        for (int index = 0; index < cards.size(); index++) {
            //create an empty WindowPatternCard with 20 numbered plain cells
            WindowPatternCard wpc = new WindowPatternCard();
            JsonObject card = cards.get(index).getAsJsonObject();

            String name = card.get("name").getAsString();
            wpc.setName(name);

            int difficulty = card.get("difficulty").getAsInt();
            wpc.setDifficulty(difficulty);

            JsonArray cells = card.get("schema").getAsJsonArray();

            //set constraints for each non-plain cell
            for (int i = 0; i < cells.size(); i++) {
                JsonObject cell = cells.get(i).getAsJsonObject();
                //set valueConstraint
                int row = cell.get("row").getAsInt();
                int column = cell.get("column").getAsInt();
                try{
                    Integer valueConstraint = cell.get("valueConstraint").getAsInt();
                    wpc.getCell(row, column).setValueConstraint(valueConstraint);
                } catch (NullPointerException e){
                    wpc.getCell(row, column).setValueConstraint(null);
                }
                //set colorConstraint
                try {
                    String tempstring = cell.get("colorConstraint").getAsString();
                    COLOR colorConstraint = COLOR.stringToColor(tempstring);
                    wpc.getCell(row, column).setColorConstraint(colorConstraint);
                } catch (NoSuchColorException e) {
                    System.out.println("Controllare l'ortografia dei colori nel file json");
                } catch (NullPointerException e){
                    wpc.getCell(row, column).setColorConstraint(null);
                }
            }
            windowPatternCards.add(wpc);
        }
        return windowPatternCards;
    }

    public WindowPatternCard getCardFromName(String cardName) {
        for (WindowPatternCard card : allCards) {
            if (card.getCardName() == cardName)
                return card;
        }
        //DEFAULT (non deve accadere
        System.out.println("Problema: nome carda scorretto, torno la prima");
        return allCards.get(0);
    }
}
