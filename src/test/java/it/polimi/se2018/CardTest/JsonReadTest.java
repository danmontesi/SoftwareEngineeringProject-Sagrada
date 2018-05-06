package it.polimi.se2018.CardTest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

public class JsonReadTest {


    /**
     * EXAMPLE: How to read from JSON
     */
    @Test
    public void testCardRead() {
        JSONParser parser = new JSONParser();
        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(new FileReader("/Users/danmontesi/Desktop/ing-sw-2018-ProvaFinale/src/resources/windowPatternCard.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Object o : a) {
            JSONObject card = (JSONObject) o;

            String name = (String) card.get("name");
            System.out.println(name);

            String difficulty = (String) card.get("difficulty");
            System.out.println(difficulty);

            JSONArray schema = (JSONArray) card.get("schema");

            for (Object c : schema) {
                System.out.println(c + "");
            }
        }


    }
}