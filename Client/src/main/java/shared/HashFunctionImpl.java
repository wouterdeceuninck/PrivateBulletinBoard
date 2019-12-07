package shared;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class HashFunctionImpl implements HashFunction {
    private final MessageDigest messageDigest;

    public HashFunctionImpl(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    @Override
    public String hashString(String message) {
        byte[] digest = digestMessage(message);
        return Hex.encodeHexString(digest);
    }

    private synchronized byte[] digestMessage(String message) {
        messageDigest.update(message.getBytes());
        return messageDigest.digest();
    }
}
