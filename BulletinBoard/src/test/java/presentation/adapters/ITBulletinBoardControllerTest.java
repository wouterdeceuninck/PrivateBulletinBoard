package presentation.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static utils.FileExtractor.getJsonFromFile;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ITBulletinBoardControllerTest {

    @Autowired
    private BulletinBoardController bulletinBoard;

    @Test
    void postAndReceiveAMessage() {
        String postRequest = getJsonFromFile("src/test/resources/jsonPostRequest.json");
        String getRequest = getJsonFromFile("src/test/resources/jsonGetRequest.json");

        bulletinBoard.postMessage(postRequest);
        String message = bulletinBoard.getMessage(getRequest);

        Assertions.assertEquals("value", message);
    }
}