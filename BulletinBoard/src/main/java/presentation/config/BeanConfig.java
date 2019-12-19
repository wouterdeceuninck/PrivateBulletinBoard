package presentation.config;

import application.security.ticket.TicketGrantingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.rmi.RmiServiceExporter;
import presentation.adapters.BulletinBoardController;
import shared.bulletinboard.BulletinBoardInfoDto;
import shared.bulletinboard.BulletinBoardInterface;
import shared.security.HashFunction;
import shared.security.HashFunctionImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Configuration
public class BeanConfig {

    private Logger logger = LoggerFactory.getLogger(BeanConfig.class);

    @Value("${registry.port.number}")
    private int registryPort;

    @Value("${amount.of.servers}")
    private int amountOfServers;

    @Value("${size.of.board}")
    private int sizeOfBoard;

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

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    RmiServiceExporter createBulletinBoardExporter(TicketGrantingService service, HashFunction hashFunction, BulletinBoardInfoDto bulletinBoardService, List<BulletinBoardInfoDto> infoDtoList) {
        BulletinBoardController implementation = new BulletinBoardController(service, bulletinBoardService, hashFunction, infoDtoList);
        Class<BulletinBoardInterface> serviceInterface = BulletinBoardInterface.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(bulletinBoardService.getServiceName());
        exporter.setRegistryPort(bulletinBoardService.getLocation());

        logger.info("created bulletin board on location " + bulletinBoardService.getLocation());
        return exporter;
    }

    @Bean
    RemoteConfig remoteConfig(TicketGrantingService service, HashFunction hashFunction, ObjectProvider<RmiServiceExporter> rmiServiceExporter) {
        return new RemoteConfig(service, hashFunction, rmiServiceExporter, amountOfServers, registryPort, sizeOfBoard);
    }
}
