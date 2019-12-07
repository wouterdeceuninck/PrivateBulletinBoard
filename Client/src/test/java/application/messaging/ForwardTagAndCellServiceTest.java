package application.messaging;

import application.messaging.forward.SecureGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import presentation.config.SecurityServiceConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SecurityServiceConfig.class)
class ForwardTagAndCellServiceTest {

    @Autowired
    SecureGenerator secureGenerator;

    @Test
    void forwardTagAndCellService() {
        ForwardTagAndCellService forwardTagAndCellService = new ForwardTagAndCellService(secureGenerator, new ObjectMapper());
    }
}