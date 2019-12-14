package presentation.config;

import application.security.ticket.TicketGrantingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shared.HashFunction;
import shared.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class BeanConfig {

    @Bean
    public HashFunction getHashFunction() throws NoSuchAlgorithmException {
        return new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public SecureRandom secureRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG");
    }

    @Bean
    public TicketGrantingService createTicketGrantingService(SecureRandom secureRandom, HashFunction hashFunction) {
        return new TicketGrantingService(secureRandom, hashFunction);
    }
}
