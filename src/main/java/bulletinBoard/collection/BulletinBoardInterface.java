package bulletinBoard.collection;

public interface BulletinBoardInterface {

    void add(int cell, String tag, String value);

    String get(int cell, String tag);
}
