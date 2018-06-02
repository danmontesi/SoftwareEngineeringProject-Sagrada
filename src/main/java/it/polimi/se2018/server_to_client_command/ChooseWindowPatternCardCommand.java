package it.polimi.se2018.server_to_client_command;

public class ChooseWindowPatternCardCommand extends ServerToClientCommand{
/**
 * Request of ToolCard
 * String with only NameClass
 */
    /**
     * Contains nameClass + three names of Toolcards
     */
    private String message;

    public ChooseWindowPatternCardCommand(String message) {
        this.message = "ChooseWindowPatternCardCommand "+ message;
    }
}
