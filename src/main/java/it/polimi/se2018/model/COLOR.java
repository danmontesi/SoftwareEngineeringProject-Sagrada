package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.NoSuchColorException;

/**
 * Die colors enumeration.
 * @author Alessio Molinari
 */
public enum COLOR {
    VIOLET, RED, YELLOW, GREEN, BLUE;

    public static COLOR stringToColor(String string) throws NoSuchColorException {
        if (string == null){
            return null;
        }
        string = string.toLowerCase();
        switch (string){
            case "violet":
                return COLOR.VIOLET;
            case "red":
                return COLOR.RED;
            case "yellow":
                return COLOR.YELLOW;
            case "green":
                return COLOR.GREEN;
            case "blue":
                return COLOR.BLUE;
            default:
                throw new NoSuchColorException("You may want to check COLOR orthography");
        }
    }

}
