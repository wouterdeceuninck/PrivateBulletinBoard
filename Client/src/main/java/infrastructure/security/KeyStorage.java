package infrastructure.security;

import application.exceptions.NoKeyFoundException;
import application.security.utils.KeyStoreUtil;

import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;

public class KeyStorage {

    private KeyStore keyStore;
    private char[] password = "".toCharArray();

    public KeyStorage(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public void storeKey(String keyName, SecretKey key){
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(key);
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection("".toCharArray());

        try (FileOutputStream fileOutputStream = new FileOutputStream(KeyStoreUtil.pathname + keyName)) {
            keyStore.setEntry(keyName, secretKeyEntry, passwordProtection);
            keyStore.store(fileOutputStream, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SecretKey getSecretKey(String keyName) {
        try {
            return (SecretKey) getKeyFromStore(keyName);
        } catch (Exception e) {
            throw new NoKeyFoundException("Failed to get the requested key");
        }
    }

    private Key getKeyFromStore(String keyName) throws Exception {
        Key key = keyStore.getKey(keyName, password);
        if (key == null) throw new NoKeyFoundException("");
        return key;
    }
}
