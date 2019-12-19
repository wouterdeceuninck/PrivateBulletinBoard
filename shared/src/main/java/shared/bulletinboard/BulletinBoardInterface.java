package shared.bulletinboard;

public interface BulletinBoardInterface {

    String getMessage(String request);

    boolean postMessage(String request);

    String getTicket();

    String getBulletinBoardInfo();
}
