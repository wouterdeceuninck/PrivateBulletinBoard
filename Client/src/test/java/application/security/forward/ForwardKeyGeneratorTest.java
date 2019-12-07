package application.security.forward;

import application.security.utils.DefaultKeyEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Random;

class ForwardKeyGeneratorTest {

    @Test
    void generateNextKey() {
        ForwardKeyGenerator forwardKeyGenerator = new ForwardKeyGenerator(new Random());
        String base64EncodedKey = "qUm2zXQ1MFXOdeOXduM71RbSBI/lB6nUZKPhwBzUq5w=";
        SecretKey secretKey = forwardKeyGenerator.generateNextKey(DefaultKeyEncoder.decodeToSecretKey(base64EncodedKey));

        Assertions.assertNotNull(secretKey);
    }

    @Test
    void generateNextKey_mustBeEqualForSameInput() {
        ForwardKeyGenerator forwardKeyGenerator = new ForwardKeyGenerator(new Random());
        String base64EncodedKey = "qUm2zXQ1MFXOdeOXduM71RbSBI/lB6nUZKPhwBzUq5w=";
        SecretKey secretKey1 = forwardKeyGenerator.generateNextKey(DefaultKeyEncoder.decodeToSecretKey(base64EncodedKey));
        SecretKey secretKey2 = forwardKeyGenerator.generateNextKey(DefaultKeyEncoder.decodeToSecretKey(base64EncodedKey));

        Assertions.assertEquals(DefaultKeyEncoder.encodeSecretKey(secretKey1)
                , DefaultKeyEncoder.encodeSecretKey(secretKey2));
    }
}