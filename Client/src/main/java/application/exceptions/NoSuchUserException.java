package application.exceptions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String s) {
        super(s);
    }
}
