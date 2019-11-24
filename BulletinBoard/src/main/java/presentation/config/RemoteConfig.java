package presentation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import presentation.ports.BulletinBoardInterface;

@Configuration
public class RemoteConfig {

    @Bean
    RmiServiceExporter createBulletinBoardExporter(BulletinBoardInterface implementation) {
        Class<BulletinBoardInterface> serviceInterface = BulletinBoardInterface.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName("BulletinBoardService");
        exporter.setRegistryPort(1099);
        return exporter;
    }
}
