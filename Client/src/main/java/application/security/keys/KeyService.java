package application.security.keys;

import application.exceptions.KeyGenerationFailedException;
import infrastructure.security.KeyStorage;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class KeyService {
    private final KeyStorage keyStorage;
    private String algorithm = "AES";
    private int keysize = 256;

    public KeyService(KeyStorage keyStorage) {
        this.keyStorage = keyStorage;
    }

    public SecretKey generateAndPersistKey(String keyName) {
        SecretKey key = generateSecretKey();
        keyStorage.storeKey(keyName, key);
        return key;
    }

    public SecretKey getKey(String keyName) {
        return keyStorage.getSecretKey(keyName);
    }

    public SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(keysize);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new KeyGenerationFailedException("failed exception");
        }
    }

    public void addKey(String keyName, SecretKey secretKey) {
        keyStorage.storeKey(keyName, secretKey);
    }
}
