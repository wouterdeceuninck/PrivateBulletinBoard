package presentation.connection.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.bulletinboard.BulletinBoardInfoDto;

import java.util.Arrays;
import java.util.Collections;

class BulletinBoardInfoTest {

    @Test
    void singleBoard() {
        BulletinBoardInfo bulletinBoardInfo = new BulletinBoardInfo();
        bulletinBoardInfo.updateInfo(Collections.singletonList(new BulletinBoardInfoDto(1099, 0, 29, "BulletinBoard")));

        int location = bulletinBoardInfo.getLocation(5);
        Assertions.assertEquals(1099, location);
    }

    @Test
    void doubleBoard() {
        BulletinBoardInfoDto bulletinBoard1 = new BulletinBoardInfoDto(1099, 0, 29, "BulletinBoard");
        BulletinBoardInfoDto bulletinBoard2 = new BulletinBoardInfoDto(1100, 30, 59, "BulletinBoard");

        BulletinBoardInfo bulletinBoardInfo = new BulletinBoardInfo();
        bulletinBoardInfo.updateInfo(Arrays.asList(bulletinBoard1, bulletinBoard2));
        Assertions.assertEquals(1099, bulletinBoardInfo.getLocation(0));
        Assertions.assertEquals(1099, bulletinBoardInfo.getLocation(29));
        Assertions.assertEquals(1100, bulletinBoardInfo.getLocation(30));
        Assertions.assertEquals(1100, bulletinBoardInfo.getLocation(59));
    }
}