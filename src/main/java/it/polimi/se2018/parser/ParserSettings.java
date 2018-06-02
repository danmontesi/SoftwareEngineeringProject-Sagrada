package it.polimi.se2018.parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Alessio
 * Class with common configuration settings for every json file
 */
public class ParserSettings {
    private static final String PATH = System.getProperty("user.dir") + "/src/resources/json/";

    private BufferedReader br = null;
    private FileReader fr = null;

    /**
     *
     * @param jsonname name of json file without the whole path (e.g. "cards.json")
     * @return initial json object in json file
     * @throws IOException
     */
    public JsonObject extractJsonObject(String jsonname) throws IOException{
        String path = PATH + jsonname;
        open(path);
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(br).getAsJsonObject();
        close();
        return object;
    }

    /**
     *Instantiates new FileReader and BufferReader
     * @param path entire path of json file to open
     * @throws IOException
     */
    private void open(String path) throws IOException{
        fr = new FileReader(path);
        br = new BufferedReader(fr);
    }

    /**
     * Closes FileReader and BufferedReader
     * @throws IOException
     */
    private void close() throws IOException {
        br.close();
        fr.close();
    }
}
