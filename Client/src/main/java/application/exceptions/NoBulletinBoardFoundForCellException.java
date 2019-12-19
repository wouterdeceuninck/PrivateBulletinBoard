package application.exceptions;

public class NoBulletinBoardFoundForCellException extends RuntimeException {
    public NoBulletinBoardFoundForCellException() {
    }

    public NoBulletinBoardFoundForCellException(String message) {
        super(message);
    }
}

