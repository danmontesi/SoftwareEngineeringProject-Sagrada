package shared.parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * Class with common configuration settings for every json file
 * @author Alessio
 */
public class ParserSettings {

    /**
     * Extracts json object
     * @param jsonName name of json file without the whole path (e.g. "cards.json")
     * @return initial json object in json file
     * @throws IOException
     */
    public JsonObject extractJsonObject(String jsonName) throws IOException{

        String path = "/json/" + jsonName;
        InputStream stream = ParserSettings.class.getResourceAsStream(path);
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));

        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(rd).getAsJsonObject();
        rd.close();
        stream.close();
        return object;
    }

}
