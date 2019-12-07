package application.security.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DefaultByteEncoder {

    public static String encodeToBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }

    public static byte[] decodeFromBase64(String message) {
        return Base64.getDecoder().decode(message);
    }

    public static String encodeToUTF8(byte[] array) {
        return new String(array, StandardCharsets.UTF_8);
    }

    public static byte[] decodeFromUTF8(String message) {
        return message.getBytes(StandardCharsets.UTF_8);
    }
}
