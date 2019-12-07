package application.security;

import application.messaging.forward.SecureGenerator;
import application.security.encryption.EncryptionService;
import application.security.keys.KeyService;
import application.security.utils.DefaultByteEncoder;
import application.security.utils.DefaultKeyEncoder;
import shared.HashFunction;

import javax.crypto.SecretKey;

public class SecurityService {

    private final KeyService keyService;
    private final EncryptionService encryptionService;
    private final HashFunction hashFunction;
    private final SecureGenerator secureGenerator;

    public SecurityService(KeyService keyService,
                           EncryptionService encryptionService,
                           HashFunction hashFunction,
                           SecureGenerator secureGenerator) {
        this.keyService = keyService;
        this.encryptionService = encryptionService;
        this.hashFunction = hashFunction;
        this.secureGenerator = secureGenerator;
    }

    public String encryptMessage(String message, String keyName) {
        SecretKey key = keyService.getKey(keyName);
        return encryptionService.encryptMessage(message, key);
    }

    public String decryptMessage(String message, String keyName) {
        SecretKey key = keyService.getKey(keyName);
        return encryptionService.decryptMessage(message, key);
    }

    public String generateKey(String keyName) {
        SecretKey secretKey = keyService.generateAndPersistKey(keyName);
        return DefaultByteEncoder.encodeToBase64(secretKey.getEncoded());
    }

    public void addKey(String base64EncodedKey, String keyName) {
        SecretKey secretKey = DefaultKeyEncoder.decodeToSecretKey(base64EncodedKey);
        keyService.addKey(keyName, secretKey);
    }

    public String hashString(String tag) {
        return hashFunction.hashString(tag);
    }

    public int generateCellIndex() {
        return secureGenerator.generateCellIndex();
    }

    public String generateTag() {
        return secureGenerator.generateTag();
    }
}
