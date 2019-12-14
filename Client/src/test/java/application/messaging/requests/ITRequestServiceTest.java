package application.messaging.requests;

import application.security.keys.KeyService;
import application.security.utils.DefaultKeyEncoder;
import application.users.dto.UserDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import presentation.config.BeanConfig;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BeanConfig.class)
class ITRequestServiceTest {

    @Autowired
    RequestService requestService;

    @Autowired
    KeyService keyService;


    @Test
    @Disabled
    void createPostRequest() {
        keyService.addKey("test", DefaultKeyEncoder.decodeToSecretKey("7ZH4SS/MNPDhs51p9XMLAo9tTjNbsTVnGULyhOYO8eo="));
        requestService.createPostRequest(new UserDto("test", 2, "tag"), "message", 5, "newTag");
    }
}