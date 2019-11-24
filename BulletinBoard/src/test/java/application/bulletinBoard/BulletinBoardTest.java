package application.bulletinBoard;

import application.exceptions.NoSuchCellException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.security.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class BulletinBoardTest {

    private BulletinBoard bulletinBoard;
    private HashFunctionImpl hashFunction;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        hashFunction = new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
        bulletinBoard = new BulletinBoard(5, hashFunction);
    }

    @Test
    void putAMessageInAllCells_boardIs5CellsLarge() {
        bulletinBoard.add(0, "tag", "value");
        bulletinBoard.add(1, "tag", "value");
        bulletinBoard.add(2, "tag", "value");
        bulletinBoard.add(3, "tag", "value");
        bulletinBoard.add(4, "tag", "value");

        Assertions.assertThrows(NoSuchCellException.class, () ->bulletinBoard.add(5, "tag", "value"));
        Assertions.assertThrows(NoSuchCellException.class, () ->bulletinBoard.add(-1, "tag", "value"));
    }

    @Test
    void putAMessageInACell_retrievePostedMessage() {
        String tag = "tag";
        String value = "value";
        String hashedTag = hashFunction.hashString(tag);

        bulletinBoard.add(1 , hashedTag, value);
        String receivedMessage = bulletinBoard.get(1, tag);

        Assertions.assertEquals(value, receivedMessage);
    }
}