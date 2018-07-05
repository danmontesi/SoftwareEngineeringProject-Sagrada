package shared.exceptions;

public class WrongCellIndexException extends RuntimeException {
    public WrongCellIndexException() {
    }

    public WrongCellIndexException(String message) {
        super(message);
    }
}
