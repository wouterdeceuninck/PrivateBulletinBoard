package presentation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import presentation.adapters.BulletinBoardController;
import presentation.ports.BulletinBoardInterface;
import presentation.security.DecryptService;
import presentation.security.HashFunction;

@Configuration
public class RemoteConfig {

    private int boardSize = 20;

    @Bean
    public BulletinBoardController createBulletinController(HashFunction hashFunction, DecryptService decryptService) {
        return new BulletinBoardController(boardSize, hashFunction, decryptService);
    }

    @Bean
    RmiServiceExporter createBulletinBoardExporter(BulletinBoardController implementation) {
        Class<BulletinBoardInterface> serviceInterface = BulletinBoardInterface.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName("BulletinBoardService");
        exporter.setRegistryPort(1099);
        return exporter;
    }
}
