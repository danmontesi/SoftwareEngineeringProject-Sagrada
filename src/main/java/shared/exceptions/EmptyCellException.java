package shared.exceptions;

public class EmptyCellException extends Exception{
    public EmptyCellException() {
    }

    public EmptyCellException(String message) {
        super(message);
    }
}
