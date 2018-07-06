package shared;

import com.google.gson.JsonObject;
import shared.parser.ParserSettings;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Constants loaded from json
 */
public class CONSTANTS {

    private static final String CONSTANTS_JSON = "constants.json";
    private static ParserSettings settings = new ParserSettings();
    private static JsonObject jConstants;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    static {
        try {
            jConstants = settings.extractJsonObject(CONSTANTS_JSON);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static final int LOBBY_TIMER = jConstants.get("LOBBY_TIMER").getAsInt();
    public static final int TURN_TIMER = jConstants.get("TURN_TIMER").getAsInt();
    public static final int WPC_TIMER = jConstants.get("WPC_TIMER").getAsInt();
    public static final int PING_TIMER = jConstants.get("PING_TIMER").getAsInt();
    public static final int RMI_PORT = jConstants.get("RMI_PORT").getAsInt();
}
