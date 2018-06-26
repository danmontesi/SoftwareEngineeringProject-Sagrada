package it.polimi.se2018.exceptions;

public class WrongCellIndexException extends RuntimeException {
    public WrongCellIndexException() {
    }

    public WrongCellIndexException(String message) {
        super(message);
    }
}
