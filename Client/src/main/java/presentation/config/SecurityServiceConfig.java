package presentation.config;


import application.messaging.ForwardTagAndCellService;
import application.messaging.forward.SecureGenerator;
import application.messaging.requests.RequestService;
import application.security.SecurityService;
import application.security.encryption.EncryptionService;
import application.security.forward.ForwardKeyGenerator;
import application.security.keys.KeyService;
import application.security.utils.KeyStoreUtil;
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
public class SecurityServiceConfig {

    @Bean
    EncryptionService encryptionService() {
        return new EncryptionService();
    }

    @Bean
    KeyService keyService() {
        KeyStore keyStore = KeyStoreUtil.getKeyStore("userKeyStore.jceks");
        KeyStorage keyStorage = new KeyStorage(keyStore);
        return new KeyService(keyStorage);
    }

    @Bean
    SecureRandom secureRandom() {
        return new SecureRandom();
    }

    @Bean
    ForwardKeyGenerator forwardKeyGenerator() {
        return new ForwardKeyGenerator(new Random());
    }

    @Bean
    SecurityService securityService(KeyService keyService,
                                    EncryptionService encryptionService,
                                    HashFunction hashFunction,
                                    SecureGenerator secureGenerator,
                                    ForwardKeyGenerator forwardKeyGenerator) {
        return new SecurityService(keyService, encryptionService, hashFunction, secureGenerator, forwardKeyGenerator);
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    SecureGenerator cellIndexGenerator() {
        return new SecureGenerator(new SecureRandom());
    }

    @Bean
    ForwardTagAndCellService forwardTagAndCellService(SecureGenerator secureGenerator, ObjectMapper objectMapper) {
        return new ForwardTagAndCellService(secureGenerator, objectMapper);
    }

    @Bean
    UserService userService() {
        return new UserService(new HashMap<>(), new HashMap<>());
    }

    @Bean
    HashFunction hashFunction() throws NoSuchAlgorithmException {
        return new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
    }

    @Bean
    RequestService requestService(ObjectMapper objectMapper,
                                  ForwardTagAndCellService forwardTagAndCellService,
                                  SecurityService securityService) {
        return new RequestService(objectMapper, forwardTagAndCellService, securityService);
    }
}
