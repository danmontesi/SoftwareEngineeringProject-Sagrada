package it.polimi.se2018.exceptions;

/**
 * TimeUpException is like a TimeoutException but it is unchecked
 */
public class TimeUpException extends RuntimeException {

    public TimeUpException(String message) {
        super(message);
    }
}
