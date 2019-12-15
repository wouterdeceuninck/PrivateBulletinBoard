package presentation.ui.controller.startup;

import application.messaging.forward.SecureGenerator;
import application.security.forward.ForwardKeyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

class StartupServiceTest {

    @Test
    void twoNames() throws NoSuchAlgorithmException {
        StartupService startupService = new StartupService(new SecureGenerator(SecureRandom.getInstance("SHA1PRNG"), new ForwardKeyGenerator(new Random())), 2);
        List<UserInfos> users = startupService.createUsers();

        Assertions.assertEquals(2, users.size());

        users.forEach(userInfos -> {
            Assertions.assertEquals(1, userInfos.getUserInfoListSend().size());
            Assertions.assertEquals(1, userInfos.getUserInfoListReceive().size());
        });
    }

    @Test
    void threeNames() throws NoSuchAlgorithmException {
        StartupService startupService = new StartupService(new SecureGenerator(SecureRandom.getInstance("SHA1PRNG"), new ForwardKeyGenerator(new Random())), 3);
        List<UserInfos> users = startupService.createUsers();

        Assertions.assertEquals(3, users.size());

        users.forEach(userInfos -> {
            Assertions.assertEquals(2, userInfos.getUserInfoListSend().size());
            Assertions.assertEquals(2, userInfos.getUserInfoListReceive().size());
        });
    }
}