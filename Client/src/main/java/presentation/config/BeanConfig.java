package presentation.config;


import application.messaging.MessageService;
import application.messaging.forward.SecureGenerator;
import application.security.SecurityService;
import application.security.encryption.EncryptionService;
import application.security.forward.ForwardKeyGenerator;
import application.security.ticket.TicketSolver;
import application.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import presentation.connection.RemoteBulletinBoard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import presentation.ui.controller.startup.StartupService;
import shared.security.HashFunction;
import shared.security.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

@Configuration
public class BeanConfig {

    private String keystoreName = "userKeyStore.jceks";

    @Value("${amount.of.users}")
    public int amountOfUsers;

    private String cipherName = "AES";
    @Bean
    EncryptionService encryptionService() {
        return new EncryptionService(cipherName);
    }

    @Bean
    ForwardKeyGenerator forwardKeyGenerator() {
        return new ForwardKeyGenerator(new Random());
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SecurityService securityService(EncryptionService encryptionService,
                                    HashFunction hashFunction,
                                    SecureGenerator secureGenerator) {
        return new SecurityService(encryptionService, hashFunction, secureGenerator);
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
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    UserService userService(HashFunction hashFunction) {
        return new UserService(new HashMap<>(), new HashMap<>(), hashFunction);
    }

    @Bean
    HashFunction hashFunction() throws NoSuchAlgorithmException {
        return new HashFunctionImpl(MessageDigest.getInstance("sha-256"));
    }

    @Bean
    TicketSolver createTicketSolver(HashFunction hashFunction) {
        return new TicketSolver(hashFunction);
    }

    @Bean
    StartupService createStartupService(SecureGenerator secureGenerator) {
        return new StartupService(secureGenerator, amountOfUsers);
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    RemoteBulletinBoard remoteBulletinBoard() {
        return new RemoteBulletinBoard();
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    MessageService messageService(RemoteBulletinBoard remoteBulletinBoard,
                                  SecurityService securityService,
                                  UserService userService,
                                  TicketSolver ticketSolver) {
        return new MessageService(remoteBulletinBoard, securityService, userService, ticketSolver);
    }
}
