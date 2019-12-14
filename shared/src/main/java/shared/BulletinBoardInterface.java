package shared;

public interface BulletinBoardInterface {

    String getMessage(String request);

    boolean postMessage(String request);

    String getTicket();
}
