package application.security.keys;

import application.exceptions.NoKeyFoundException;
import shared.utils.KeyStoreUtil;
import infrastructure.security.KeyStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyStore;

class KeyStorageTest {

    private KeyStore keyStore = KeyStoreUtil.getKeyStore("");
    private KeyStorage keyStorage = new KeyStorage(keyStore);

    @Test
    void getKey_expectNoKeyException() {
        Assertions.assertThrows(NoKeyFoundException.class, () -> keyStorage.getSecretKey("doesNotExist"));
    }
}