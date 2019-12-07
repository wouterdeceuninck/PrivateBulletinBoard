package application.security.keys;

import application.exceptions.NoKeyFoundException;
import application.security.utils.KeyStoreUtil;
import infrastructure.security.KeyStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyStore;

class KeyStorageTest {

    private KeyStore keyStore = KeyStoreUtil.getKeyStore("userKeyStore.jceks");
    private KeyStorage keyStorage = new KeyStorage(keyStore);

    @Test
    void getKey_expectNoKeyException() {
        Assertions.assertThrows(NoKeyFoundException.class, () -> keyStorage.getSecretKey("doesNotExist"));
    }
}