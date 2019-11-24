package application.security;

import application.security.utils.KeyStoreUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

public class EncryptionService {

    private final KeyStore keyStore;

    public EncryptionService(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public String generateKey(String keyName) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey key = keyGen.generateKey();

            storeKey(keyName, key);

            Base64.Encoder encoder = Base64.getEncoder();
            String keyString = encoder.encodeToString(key.getEncoded());
            System.out.println(keyString);
            return keyString;
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return keyName;
    }

    private void storeKey(String keyName, SecretKey key) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(KeyStoreUtil.pathname)) {
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(key);
            KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection("".toCharArray());

            keyStore.setEntry(keyName, secretKeyEntry, passwordProtection);

            keyStore.store(fileOutputStream, "".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
    }
}
