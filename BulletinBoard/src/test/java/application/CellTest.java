package application;

import application.bulletinBoard.cell.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.security.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class CellTest {

    private Cell cell;
    private final String value = "value";
    private final String key = "key";

    @BeforeEach
    void setUp() {
        cell = new Cell();
    }

    @Test
    void getMessage() {
        String message = cell.getMessage("");
        Assertions.assertNotNull(message);
    }

    @Test
    void putMessageWithHash_retrieveOnlyOnce() {
        cell.putMessage(key, value);

        Assertions.assertEquals(value, cell.getMessage(key));
        Assertions.assertEquals("", cell.getMessage(key));
    }

    @Test
    void getMessage_wrongKeyProvided_initialMessageNotDeleted() {
        cell.putMessage(key, value);

        Assertions.assertEquals("", cell.getMessage("key1"));
        Assertions.assertEquals(value, cell.getMessage(key));
    }
}