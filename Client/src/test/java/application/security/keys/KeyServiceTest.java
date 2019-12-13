package application.security.keys;

import shared.utils.KeyStoreUtil;
import infrastructure.security.KeyStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.KeyStore;

class KeyServiceTest {

    private KeyStore keyStore;
    private KeyService keyService;

    @BeforeEach
    void setUp() {
        keyStore = KeyStoreUtil.getKeyStore("userKeyStore.jceks");
        keyService = new KeyService(new KeyStorage(keyStore));
    }

    @Test
    void verifyKeyPersistance() {
        SecretKey key1 = keyService.generateAndPersistKey("test");
        SecretKey key2 = keyService.getKey("test");

        Assertions.assertEquals(key1, key2);
    }

    @Test
    void addKey() {
        SecretKey key = keyService.generateAndPersistKey("key");
        keyService.addKey("keyname", key);
        SecretKey persistedKey = keyService.getKey("keyname");

        Assertions.assertEquals(key, persistedKey);
    }
}