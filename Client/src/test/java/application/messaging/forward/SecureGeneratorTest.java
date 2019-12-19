package application.messaging.forward;

import application.security.forward.ForwardKeyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

class SecureGeneratorTest {

    private ForwardKeyGenerator forwardKeyGenerator = new ForwardKeyGenerator(new Random());

    @Test
    void createNewIndex() {
        SecureGenerator secureGenerator = new SecureGenerator(new SecureRandom(), forwardKeyGenerator);
        int index = secureGenerator.generateCellIndex();

        Assertions.assertTrue(index >= 0);
        Assertions.assertTrue(index <= 39);
    }

    @Test
    void createNewTag() {
        SecureGenerator secureGenerator = new SecureGenerator(new SecureRandom(), forwardKeyGenerator);
        String tag = secureGenerator.generateTag();

        Assertions.assertTrue(tag.length() > 0);
    }
}