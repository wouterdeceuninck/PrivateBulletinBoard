package application.security.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import static application.security.utils.DefaultByteEncoder.*;

public class EncryptionService {

    private final String cipherName;

    public EncryptionService(String cipherName) {
        this.cipherName = cipherName;
    }

    public String encryptMessage(String utf8EncodedMessage, SecretKey key) {
        try {
            byte[] encryptedMessage = executeCipher(decodeFromUTF8(utf8EncodedMessage), key, Cipher.ENCRYPT_MODE);
            return encodeToBase64(encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decryptMessage(String base64EncodedMessage, SecretKey key) {
        try {
            byte[] encryptedMessage = executeCipher(decodeFromBase64(base64EncodedMessage), key, Cipher.DECRYPT_MODE);
            return encodeToUTF8(encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private byte[] executeCipher(byte[] message, SecretKey key, int encryptMode) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherName);
        cipher.init(encryptMode, key);
        return cipher.doFinal(message);
    }
}
