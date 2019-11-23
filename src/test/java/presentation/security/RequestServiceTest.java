package presentation.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.security.dto.GetMessageRequestDTO;
import presentation.security.dto.PostMessageRequestDTO;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static utils.FileExtractor.getJsonFromFile;

class RequestServiceTest {

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
        Assertions.assertEquals("⨐玦\uE67F\u0E5Fথ镼斕φ郯\uE727\u2BE8ㄽ\uF872啪驨䶌", postMessageRequestDTO.getTag());
        Assertions.assertEquals("value", postMessageRequestDTO.getMessage());
    }

    @Test
    void name() throws NoSuchAlgorithmException {
        HashFunction hashFunction = new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
        String tag = hashFunction.hashString("tag");
        System.out.println(tag);
    }
}