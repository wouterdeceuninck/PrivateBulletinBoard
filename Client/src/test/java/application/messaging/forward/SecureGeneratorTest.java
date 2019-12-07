package application.messaging.forward;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

class SecureGeneratorTest {

    @Test
    void createNewIndex() {
        SecureGenerator secureGenerator = new SecureGenerator(new SecureRandom());
        int index = secureGenerator.generateCellIndex();

        Assertions.assertTrue(index >= 0);
        Assertions.assertTrue(index <= 15);
    }

    @Test
    void createNewTag() {
        SecureGenerator secureGenerator = new SecureGenerator(new SecureRandom());
        String tag = secureGenerator.generateTag();

        Assertions.assertTrue(tag.length() > 0);
    }
}