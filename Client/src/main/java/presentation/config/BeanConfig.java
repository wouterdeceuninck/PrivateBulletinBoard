package presentation.config;


import application.messaging.forward.SecureGenerator;
import application.messaging.requests.RequestService;
import application.security.SecurityService;
import application.security.encryption.EncryptionService;
import application.security.forward.ForwardKeyGenerator;
import application.security.keys.KeyService;
import application.security.ticket.TicketSolver;
import shared.utils.KeyStoreUtil;
import application.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import infrastructure.security.KeyStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shared.HashFunction;
import shared.HashFunctionImpl;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

@Configuration
public class BeanConfig {

    private String keystoreName = "userKeyStore.jceks";

    private String cipherName = "AES";
    @Bean
    EncryptionService encryptionService() {
        return new EncryptionService(cipherName);
    }

    @Bean
    KeyService keyService() {
        KeyStore keyStore = KeyStoreUtil.getKeyStore(keystoreName);
        KeyStorage keyStorage = new KeyStorage(keyStore);
        return new KeyService(keyStorage);
    }

    @Bean
    ForwardKeyGenerator forwardKeyGenerator() {
        return new ForwardKeyGenerator(new Random());
    }

    @Bean
    SecurityService securityService(KeyService keyService,
                                    EncryptionService encryptionService,
                                    HashFunction hashFunction,
                                    SecureGenerator secureGenerator) {
        return new SecurityService(keyService, encryptionService, hashFunction, secureGenerator);
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    SecureGenerator cellIndexGenerator(ForwardKeyGenerator forwardKeyGenerator) {
        return new SecureGenerator(new SecureRandom(), forwardKeyGenerator);
    }

    @Bean
    UserService userService(HashFunction hashFunction) {
        return new UserService(new HashMap<>(), new HashMap<>(), hashFunction);
    }

    @Bean
    HashFunction hashFunction() throws NoSuchAlgorithmException {
        return new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
    }

    @Bean
    RequestService requestService(SecurityService securityService) {
        return new RequestService(securityService);
    }

    @Bean
    TicketSolver createTicketSolver(HashFunction hashFunction) {
        return new TicketSolver(hashFunction);
    }
}
