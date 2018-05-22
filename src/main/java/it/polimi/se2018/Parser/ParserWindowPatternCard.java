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

    /**
     *
     * @return ArrayList of all WindowPatternCards contained in json file
     * @throws IOException
     */
    public ArrayList<WindowPatternCard> parseWindowPatternCards() throws IOException {
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
}
