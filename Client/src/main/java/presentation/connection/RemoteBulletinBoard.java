package presentation.connection;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import presentation.connection.dto.BulletinBoardInfo;
import shared.bulletinboard.BulletinBoardInfoDto;
import shared.bulletinboard.BulletinBoardInterface;
import shared.bulletinboard.dto.GetMessageRequestDTO;
import shared.bulletinboard.dto.PostMessageRequestDTO;
import shared.utils.DefaultObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static shared.utils.DefaultObjectMapper.mapToString;

public class RemoteBulletinBoard {

    private final Map<Integer, BulletinBoardInterface> sendMap;
    private final Map<Integer, BulletinBoardInterface> receiveMap;
    private BulletinBoardInfo bulletinBoardInfo;

    private int rootLocation = 1099;

    public RemoteBulletinBoard() {
        sendMap = new HashMap<>();
        receiveMap = new HashMap<>();
        bulletinBoardInfo = new BulletinBoardInfo();

        connectToRoot();
        updateBulletinBoardInfo();
    }

    private void updateBulletinBoardInfo() {
        BulletinBoardInterface rootBulletinBoard = getSendBulletinBoard(rootLocation);
        String bulletinBoardInfo = rootBulletinBoard.getBulletinBoardInfo();
        BulletinBoardInfoDto[] bulletinBoardInfoDtos = DefaultObjectMapper.mapToObject(BulletinBoardInfoDto[].class, bulletinBoardInfo);
        Arrays.stream(bulletinBoardInfoDtos)
                .filter(bulletinBoardInfoDto -> bulletinBoardInfoDto.getLocation() != 1099)
                .forEach(bulletinBoardInfoDto -> createRemoteConnection(bulletinBoardInfoDto.getLocation()));

        this.bulletinBoardInfo.updateInfo(Arrays.asList(bulletinBoardInfoDtos));
    }

    private void connectToRoot() {
        createRemoteConnection(rootLocation);
    }

    private void createRemoteConnection(int location) {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl(getUrl(location));
        rmiProxyFactory.setServiceInterface(BulletinBoardInterface.class);
        rmiProxyFactory.setCacheStub(true);
        rmiProxyFactory.setLookupStubOnStartup(true);
        rmiProxyFactory.setRefreshStubOnConnectFailure(true);
        rmiProxyFactory.afterPropertiesSet();
        sendMap.put(location, (BulletinBoardInterface) rmiProxyFactory.getObject());
        receiveMap.put(location, (BulletinBoardInterface) rmiProxyFactory.getObject());
        System.out.println("Connection made to port " + location);
    }

    private String getUrl(int location) {
        return "rmi://localhost:" + location + "/BulletinBoardService";
    }

    public boolean postMessage(PostMessageRequestDTO postRequest) {
        Integer location = bulletinBoardInfo.getLocation(postRequest.getCell());
        return getSendBulletinBoard(location).postMessage(mapToString(postRequest));
    }

    public String getTicket(PostMessageRequestDTO postRequest) {
        Integer location = bulletinBoardInfo.getLocation(postRequest.getCell());
        return getSendBulletinBoard(location).getTicket();
    }

    public String getMessage(GetMessageRequestDTO getRequest) {
        Integer location = bulletinBoardInfo.getLocation(getRequest.getCell());
        return getReceiveBulletinBoard(location).getMessage(mapToString(getRequest));
    }

    private BulletinBoardInterface getSendBulletinBoard(Integer location) {
        return sendMap.get(location);
    }

    private BulletinBoardInterface getReceiveBulletinBoard(Integer location) {
        return receiveMap.get(location);
    }
}
