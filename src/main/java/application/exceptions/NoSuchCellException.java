package application.exceptions;

public class NoSuchCellException extends RuntimeException {
    public NoSuchCellException(String message) {
        super(message);
    }
}
