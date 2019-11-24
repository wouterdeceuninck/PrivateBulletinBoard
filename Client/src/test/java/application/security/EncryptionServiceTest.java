package application.security;

import application.security.utils.KeyStoreUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;

class EncryptionServiceTest {

    private EncryptionService encryptionService;
    private KeyStore keyStore;

    @BeforeEach
    void createEncryptionService() {
        keyStore = KeyStoreUtil.getKeyStore();
        encryptionService = new EncryptionService(keyStore);
    }

    @Test
    void generateKeyPair() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        String key = encryptionService.generateKey("test");

        SecretKey key1 = (SecretKey) keyStore.getKey("test", "".toCharArray());

        System.out.println(key1);
        Base64.Encoder encoder = Base64.getEncoder();
        String savedKey = encoder.encodeToString(key1.getEncoded());

        Assertions.assertEquals(key, savedKey);
    }
}