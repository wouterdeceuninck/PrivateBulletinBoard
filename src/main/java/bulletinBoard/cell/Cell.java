package bulletinBoard.cell;

import security.utils.HashFunction;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private final HashFunction hashFunction;
    private Map<String, String> messages;

    public Cell(HashFunction hashFunction) {
        messages = new HashMap<>();
        this.hashFunction = hashFunction;
    }

    public String getMessage(String key) {
        String hashedKey = hashFunction.hashStringSHA256(key);
        String message = messages.remove(hashedKey);
        return message == null ? "" : message;
    }

    public void putMessage(String hashedKey, String value) {
        messages.put(hashedKey, value);
    }
}
