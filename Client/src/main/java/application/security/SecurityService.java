package application.security;

import application.messaging.forward.SecureGenerator;
import application.security.encryption.EncryptionService;
import application.security.keys.KeyService;
import application.security.utils.DefaultByteEncoder;
import application.security.utils.DefaultKeyEncoder;
import presentation.ui.controller.startup.UserInfos;
import shared.HashFunction;

import javax.crypto.SecretKey;
import java.security.Key;

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
        SecretKey key = keyService.getKey(sendKeyName(keyName));
        return encryptionService.encryptMessage(message, key);
    }

    private String sendKeyName(String keyName) {
        return "send_" + keyName;
    }

    public String decryptMessage(String message, String keyName) {
        SecretKey key = keyService.getKey(receiveKeyName(keyName));
        return encryptionService.decryptMessage(message, key);
    }

    private String receiveKeyName(String keyName) {
        return "receive_" + keyName;
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

    public void updateSecurityKey(String keyName) {
        SecretKey key = keyService.getKey(keyName);
        Key forwardKey = secureGenerator.generateNextKey(key);
        keyService.addKey(keyName, (SecretKey) forwardKey);
    }

    public void addKey(UserInfos userInfos) {
        userInfos.getUserInfoListReceive().forEach(userInfo -> addKey(userInfo.getKey(), receiveKeyName(userInfo.getName())));
        userInfos.getUserInfoListSend().forEach(userInfo -> addKey(userInfo.getKey(), sendKeyName(userInfo.getName())));
    }
}
