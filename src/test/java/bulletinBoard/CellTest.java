package bulletinBoard;

import bulletinBoard.cell.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.utils.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class CellTest {

    private Cell cell;
    private String value = "value";
    private String key = "key";
    private HashFunctionImpl hashFunction;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        hashFunction = new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
        cell = new Cell(hashFunction);
    }

    @Test
    void getMessage() {
        String message = cell.getMessage("");
        Assertions.assertNotNull(message);
    }

    @Test
    void putMessageWithHash_retrieveOnlyOnce() {
        String hashedKey = hashFunction.hashStringSHA256("key");
        cell.putMessage(hashedKey, value);

        Assertions.assertEquals(value, cell.getMessage(key));
        Assertions.assertEquals("", cell.getMessage(key));
    }

    @Test
    void getMessage_wrongKeyProvided_initialMessageNotDeleted() {
        String hashedKey = hashFunction.hashStringSHA256("key");
        cell.putMessage(hashedKey, value);

        Assertions.assertEquals("", cell.getMessage("key1"));
        Assertions.assertEquals(value, cell.getMessage(key));
    }


}