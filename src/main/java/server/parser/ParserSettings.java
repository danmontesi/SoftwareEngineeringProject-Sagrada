package server.parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * Class with common configuration settings for every json file
 * @author Alessio
 */
public class ParserSettings {
    private static final String PATH = System.getProperty("user.dir") + "/src/resources/json/";
    private BufferedReader br = null;
    private FileReader fr = null;

    /**
     * Extracts json object
     * @param jsonName name of json file without the whole path (e.g. "cards.json")
     * @return initial json object in json file
     * @throws IOException
     */
    public JsonObject extractJsonObject(String jsonName) throws IOException{

        String path = "/json/" + jsonName;
        //InputStream instr = new FileInputStream(new File(path));
        //File file = new File(this.getClass().getResource(path).getFile());
                //instr = this.getClass().getResource(path).openStream();
        InputStream stream = ParserSettings.class.getResourceAsStream(path);
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));

        //FileReader fileReader= new FileReader(file);
        //inputStream= CLASSECHEHAI.getClass().getClassLoader().getResourceAsStream(PERCORSORESOURCES);


            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(rd).getAsJsonObject();
            rd.close();
            stream.close();
            return object;



        //InputStreamReader stream = new InputStreamReader( instr);
        /*open(path);
        JsonParser parser = new JsonParser();

        JsonObject object = parser.parse(bufferedReader).getAsJsonObject();
        close();
        return object;
        */
    }



    /**
     * Instantiates new FileReader and BufferReader
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
