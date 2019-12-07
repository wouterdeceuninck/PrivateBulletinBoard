package application.security.encryption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

class EncryptionServiceTest {

    @Test
    void encryptMessage() {
        EncryptionService encryptionService = new EncryptionService();
        encryptionService.encryptMessage("message", decodeStringToKey("+L9SeEdYDVSLokj/+U2LVQ=="));
    }

    @Test
    void encryptAndDecrypt() {
        EncryptionService encryptionService = new EncryptionService();
        SecretKey key = decodeStringToKey("qUm2zXQ1MFXOdeOXduM71RbSBI/lB6nUZKPhwBzUq5w=");

        String message = encryptionService.encryptMessage("message", key);
        String decryptMessage = encryptionService.decryptMessage(message, key);

        Assertions.assertEquals("message", decryptMessage);
    }

   private SecretKey decodeStringToKey(String key) {
       Base64.Decoder decoder = Base64.getDecoder();
       byte[] keyBytes = decoder.decode(key);
       return new SecretKeySpec(keyBytes, 0, 16, "AES");
   }
}