package application.messaging.requests;

import application.users.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import presentation.config.SecurityServiceConfig;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SecurityServiceConfig.class)
class RequestServiceTest {

    @Autowired
    RequestService requestService;


    @Test
    void createPostRequest() {
        requestService.createPostRequest(new UserDto("test", 2, "tag"), "message", 5, "newTag");
    }
}