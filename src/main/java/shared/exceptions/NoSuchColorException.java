package shared.exceptions;

public class NoSuchColorException extends Exception{
    public NoSuchColorException() {
    }

    public NoSuchColorException(String message) {
        super(message);
    }
}
