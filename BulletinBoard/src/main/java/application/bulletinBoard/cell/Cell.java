package application.bulletinBoard.cell;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private final Map<String, String> messages;

    public Cell() {
        messages = new HashMap<>();
    }

    public String getMessage(String hashedKey) {
        String message = messages.remove(hashedKey);
        return message == null ? "" : message;
    }

    public void putMessage(String hashedKey, String value) {
        messages.put(hashedKey, value);
    }
}
