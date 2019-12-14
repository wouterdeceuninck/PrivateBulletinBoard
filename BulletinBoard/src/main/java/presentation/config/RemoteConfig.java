package presentation.config;

import application.bulletinBoard.ExceptionHandlingBulletinBoard;
import application.security.ticket.TicketGrantingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import presentation.adapters.BulletinBoardController;
import shared.BulletinBoardInterface;
import shared.HashFunction;

@Configuration
public class RemoteConfig {

    private int boardSize = 20;

    @Bean
    public BulletinBoardController createBulletinController(HashFunction hashFunction, TicketGrantingService ticketGrantingService) {
        return new BulletinBoardController(new ExceptionHandlingBulletinBoard(boardSize, hashFunction), ticketGrantingService);
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
