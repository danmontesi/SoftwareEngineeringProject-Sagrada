package it.polimi.se2018.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParserSettings {
    private static final String PATH = System.getProperty("user.dir") + "/src/resources/json/";

    private BufferedReader br = null;
    private FileReader fr = null;

    public JsonObject extractJsonObject(String jsonname) throws IOException{
        String path = PATH + jsonname;
        open(path);
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(br).getAsJsonObject();
        close();
        return object;
    }

    private void open(String path) throws IOException{
        fr = new FileReader(path);
        br = new BufferedReader(fr);
    }

    private void close() throws IOException {
        br.close();
        fr.close();
    }
}
