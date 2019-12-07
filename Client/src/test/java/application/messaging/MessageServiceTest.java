package application.messaging;

import application.messaging.forward.SecureGenerator;
import application.messaging.requests.ForwardMessage;
import application.messaging.requests.RequestService;
import application.security.SecurityService;
import application.security.keys.KeyService;
import application.security.utils.DefaultKeyEncoder;
import application.users.UserService;
import application.users.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import presentation.config.RemoteProxyConfig;
import presentation.config.SecurityServiceConfig;
import shared.BulletinBoardInterface;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SecurityServiceConfig.class, RemoteProxyConfig.class})
class MessageServiceTest {

    @Autowired
    SecurityService securityService;

    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @Autowired
    KeyService keyService;

    @Autowired
    BulletinBoardInterface bulletinBoardInterface;

    @Autowired
    ObjectMapper objectMapper;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageService(bulletinBoardInterface, requestService, securityService, userService);
    }

    @Test
    void sendAndReceiveAMessage() throws JsonProcessingException {
        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateSendUser("Alice", new UserDto("keyName", 2, "tag"));
        messageService.sendMessage("message", "Alice");

        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateReceiveUser("Alice", new UserDto("keyName", 2, "tag"));
        String message = messageService.getMessage("Alice");

        ForwardMessage forwardMessage = objectMapper.readValue(message, ForwardMessage.class);
        Assertions.assertEquals("message", forwardMessage.getMessage());
    }

    @Test
    void send2Messages_receive2Messages() throws JsonProcessingException {
        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateSendUser("Alice", new UserDto("keyName", 2, "tag"));

        messageService.sendMessage("message", "Alice");
        messageService.sendMessage("message", "Alice");

        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateReceiveUser("Alice", new UserDto("keyName", 2, "tag"));

        String message1 = messageService.getMessage("Alice");
        String message2 = messageService.getMessage("Alice");

        ForwardMessage forwardMessage1 = objectMapper.readValue(message1, ForwardMessage.class);
        ForwardMessage forwardMessage2 = objectMapper.readValue(message2, ForwardMessage.class);

        Assertions.assertEquals("message", forwardMessage1.getMessage());
        Assertions.assertEquals("message", forwardMessage2.getMessage());
    }
}