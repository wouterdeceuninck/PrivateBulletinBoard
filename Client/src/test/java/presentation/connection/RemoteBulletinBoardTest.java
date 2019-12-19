package presentation.connection;

import org.junit.jupiter.api.Test;
import shared.bulletinboard.dto.PostMessageRequestDTO;
class RemoteBulletinBoardTest {

    @Test
    void name() {
        RemoteBulletinBoard remoteBulletinBoard = new RemoteBulletinBoard();
        remoteBulletinBoard.getTicket(new PostMessageRequestDTO(1, "tag", "message"));
    }
}