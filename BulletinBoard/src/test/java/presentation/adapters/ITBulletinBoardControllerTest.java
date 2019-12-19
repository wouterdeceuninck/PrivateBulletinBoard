package presentation.adapters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shared.bulletinboard.BulletinBoardInfoDto;
import shared.utils.DefaultObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ITBulletinBoardControllerTest {

    @Test
    void name() {
        BulletinBoardInfoDto bulletinBoard1 = new BulletinBoardInfoDto(1099, 0, 29, "BulletinBoard");
        BulletinBoardInfoDto bulletinBoard2 = new BulletinBoardInfoDto(1100, 30, 59, "BulletinBoard");

        System.out.println(DefaultObjectMapper.mapToString(
                new BulletinBoardInfoDto[]{
                        bulletinBoard1,
                        bulletinBoard2
                }
        ));
    }
}