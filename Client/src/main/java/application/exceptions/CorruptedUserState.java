package application.exceptions;

public class CorruptedUserState extends RuntimeException {
    public CorruptedUserState(String s) {
        super(s);
    }
}
