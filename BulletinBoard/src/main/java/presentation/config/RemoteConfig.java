package presentation.config;

import application.security.ticket.TicketGrantingService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.remoting.rmi.RmiServiceExporter;
import shared.bulletinboard.BulletinBoardInfoDto;
import shared.security.HashFunction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RemoteConfig {

    private final int amountOfServers;
    private final int registryPort;
    private final int sizeOfBoard;

    public RemoteConfig(TicketGrantingService service,
                        HashFunction hashFunction,
                        ObjectProvider<RmiServiceExporter> objectProvider,
                        int amountOfServers,
                        int registryPort,
                        int sizeOfBoard) {
        this.amountOfServers = amountOfServers;
        this.registryPort = registryPort;
        this.sizeOfBoard = sizeOfBoard;

        createInstances(objectProvider, service, hashFunction);
    }

    public void createInstances(ObjectProvider<RmiServiceExporter> objectProvider, TicketGrantingService service, HashFunction hashFunction) {
        List<BulletinBoardInfoDto> infoDtoList = createInfoDtoList();
        for (BulletinBoardInfoDto infoDto : infoDtoList) {
            objectProvider.getObject(service, hashFunction, infoDto, infoDtoList);
        }
    }

    private List<BulletinBoardInfoDto> createInfoDtoList() {
        return IntStream.range(0, amountOfServers)
                .mapToObj(counter -> new BulletinBoardInfoDto(registryPort + counter, sizeOfBoard * counter, sizeOfBoard * (counter + 1), "BulletinBoardService"))
                .collect(Collectors.toList());
    }

}
