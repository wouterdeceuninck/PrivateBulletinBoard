package application.messaging;

import application.messaging.requests.ForwardMessage;
import application.messaging.requests.RequestService;
import application.security.SecurityService;
import application.security.keys.KeyService;
import application.security.ticket.TicketSolver;
import application.security.utils.DefaultKeyEncoder;
import application.users.UserService;
import application.users.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import presentation.config.RemoteProxyConfig;
import presentation.config.BeanConfig;
import shared.BulletinBoardInterface;

import static shared.utils.DefaultObjectMapper.mapToObject;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BeanConfig.class, RemoteProxyConfig.class})
class ITMessageServiceTest {

    @Autowired
    SecurityService securityService;

    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @Autowired
    KeyService keyService;

    @Autowired
    ObjectFactory<BulletinBoardInterface> bulletinBoardInterface;

    @Autowired
    TicketSolver ticketSolver;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageService(bulletinBoardInterface, requestService, securityService, userService, ticketSolver);
    }

    @Test
    void sendAndReceiveAMessage() {
        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateSendUser("Alice", new UserDto("keyName", 2, "tag"));
        messageService.sendMessage("message", "Alice");

        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateReceiveUser("Alice", new UserDto("keyName", 2, "tag"));
        String message = messageService.getMessage("Alice");

        ForwardMessage forwardMessage = mapToObject(ForwardMessage.class, message);
        Assertions.assertEquals("message", forwardMessage.getMessage());
    }

    @Test
    void send2Messages_receive2Messages() {
        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateSendUser("Alice", new UserDto("keyName", 2, "tag"));

        messageService.sendMessage("message", "Alice");
        messageService.sendMessage("message", "Alice");

        keyService.addKey("keyName", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        userService.updateReceiveUser("Alice", new UserDto("keyName", 2, "tag"));

        String message1 = messageService.getMessage("Alice");
        String message2 = messageService.getMessage("Alice");

        ForwardMessage forwardMessage1 = mapToObject(ForwardMessage.class, message1);
        ForwardMessage forwardMessage2 = mapToObject(ForwardMessage.class, message2);

        Assertions.assertEquals("message", forwardMessage1.getMessage());
        Assertions.assertEquals("message", forwardMessage2.getMessage());
    }
}