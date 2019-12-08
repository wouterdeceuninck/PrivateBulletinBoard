package application.security.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class DefaultKeyEncoder {

    public static String encodeSecretKey(Key key) {
        return DefaultByteEncoder.encodeToBase64(key.getEncoded());
    }

    public static SecretKey decodeToSecretKey(String base64EncodedKey) {
        byte[] keyBytes = DefaultByteEncoder.decodeFromBase64(base64EncodedKey);
        return toSecretKey(keyBytes);
    }

    public static SecretKey toSecretKey(byte[] array) {
        return new SecretKeySpec(array, 0, 16, "AES");
    }
}
