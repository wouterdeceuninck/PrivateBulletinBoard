package security.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static org.apache.commons.lang3.StringUtils.toEncodedString;

public class HashFunctionImpl implements HashFunction {
    private MessageDigest messageDigest;

    public HashFunctionImpl(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    @Override
    public String hashStringSHA256(String message) {
        byte[] digest = digestMessage(message);
        return toEncodedString(digest, StandardCharsets.UTF_8);
    }

    private synchronized byte[] digestMessage(String message) {
        messageDigest.update(message.getBytes());
        return messageDigest.digest();
    }
}
