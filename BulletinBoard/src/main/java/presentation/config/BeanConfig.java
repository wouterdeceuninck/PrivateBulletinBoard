package presentation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import presentation.adapters.BulletinBoardController;
import presentation.ports.BulletinBoardInterface;
import presentation.security.DecryptService;
import presentation.security.HashFunction;
import presentation.security.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class BeanConfig {

    @Bean
    public HashFunction getHashFunction() throws NoSuchAlgorithmException {
        return new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
    }

    @Bean
    public DecryptService getDecryptService(ObjectMapper objectMapper) {
        return new DecryptService(objectMapper);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
