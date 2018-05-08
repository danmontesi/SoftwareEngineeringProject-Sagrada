package it.polimi.se2018;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReaderClass {

    // Read based on the argument received
    public void JsonReaderClass(ArrayList<WindowPatternCard> cards) throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        //ATTENZIONE: POTREBBE ESSERE NECESSARIO MODIFICARE IL PERCORSO RELATIVO
        JSONArray a = (JSONArray) parser.parse(new FileReader("/../../../../../resources/windowPatternCard.json"));

        for (Object o : a)
        {
            JSONObject card = (JSONObject) o;

            String name = (String) card.get("name");
            System.out.println(name);

            String difficulty = (String) card.get("difficulty");
            System.out.println(difficulty);

            JSONArray schema = (JSONArray) card.get("schema");

            for (Object c : schema)
            {
                if (c == "no")
                    System.out.println("Non ho restrizioni -> Cella vuota");
                if (c == "RED")
                    System.out.println("Restrizione - colore");
                if (c == "GREEN")
                    System.out.println("Restrizione - colore");
                if (c == "VIOLET")
                    System.out.println("Restrizione - colore");
                if (c == "YELLOW")
                    System.out.println("Restrizione - colore");
                if (c == "BLUE")
                    System.out.println("Restrizione - colore");

                if (c == "1")
                    System.out.println("Restrizione - num");
                if (c == "2")
                    System.out.println("Restrizione - num");
                if (c == "3")
                    System.out.println("Restrizione - num");
                if (c == "4")
                    System.out.println("Restrizione - num");
                if (c == "5")
                    System.out.println("Restrizione - num");
                if (c == "6")
                    System.out.println("Restrizione - num");
            }
        }
    }



}
