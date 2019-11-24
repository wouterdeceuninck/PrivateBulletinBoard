package presentation.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import presentation.security.dto.GetMessageRequestDTO;
import presentation.security.dto.PostMessageRequestDTO;

import java.io.IOException;

import static utils.FileExtractor.getJsonFromFile;

@SpringBootTest
class RequestServiceTest {

    @Autowired
    HashFunction hashFunction;

    private DecryptService requestService;

    @BeforeEach
    void setUp() {
        requestService = new DecryptService(new ObjectMapper());
    }

    @Test
        //TODO should be updated with different exception type
    void decryptRequest() {
        Assertions.assertThrows(JsonParseException.class, () -> requestService.decryptGetMessageRequest("request"));
    }

    @Test
    void decryptJSON_getMessage() throws IOException {
        String message = getJsonFromFile("src/test/resources/jsonGetRequest.json");
        GetMessageRequestDTO getMessageRequestDTO = requestService.decryptGetMessageRequest(message);

        Assertions.assertEquals(3, getMessageRequestDTO.getCell());
        Assertions.assertEquals("tag", getMessageRequestDTO.getTag());
    }

    @Test
    void decryptJSON_postMessage() throws IOException {
        String message = getJsonFromFile("src/test/resources/jsonPostRequest.json");
        PostMessageRequestDTO postMessageRequestDTO = requestService.decryptPostMessageRequest(message);

        Assertions.assertEquals(3, postMessageRequestDTO.getCell());
        String tag = postMessageRequestDTO.getTag();
        System.out.println(tag);
        Assertions.assertEquals("2a1073a6e67f0e5f09a5957c659503c690efe7272be8313df872556a9a684d8c", tag);
        Assertions.assertEquals("value", postMessageRequestDTO.getMessage());
    }
}