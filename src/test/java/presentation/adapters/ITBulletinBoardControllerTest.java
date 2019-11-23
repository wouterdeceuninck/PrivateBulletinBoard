package presentation.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static utils.FileExtractor.getJsonFromFile;

class ITBulletinBoardControllerTest {

    private BulletinBoardController bulletinBoard;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        bulletinBoard = new BulletinBoardController();
    }

    @Test
    void postAndReceiveAMessage() {
        String postRequest = getJsonFromFile("src/test/resources/jsonPostRequest.json");
        String getRequest = getJsonFromFile("src/test/resources/jsonGetRequest.json");

        bulletinBoard.postMessage(postRequest);
        String message = bulletinBoard.getMessage(getRequest);

        Assertions.assertEquals("value", message);
    }
}