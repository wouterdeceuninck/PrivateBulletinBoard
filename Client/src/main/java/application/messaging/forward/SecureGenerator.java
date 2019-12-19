package application.messaging.forward;

import application.security.forward.ForwardKeyGenerator;
import application.security.utils.DefaultByteEncoder;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.SecureRandom;

public class SecureGenerator {

    private final int cell_size = 40;
    private final SecureRandom secureRandom;
    private final ForwardKeyGenerator forwardKeyGenerator;

    public SecureGenerator(SecureRandom secureRandom, ForwardKeyGenerator forwardKeyGenerator) {
        this.secureRandom = secureRandom;
        this.forwardKeyGenerator = forwardKeyGenerator;
    }

    public int generateCellIndex() {
        byte[] bytes = new byte[1];
        secureRandom.nextBytes(bytes);
        return new BigInteger(bytes)
                .abs()
                .intValue() % cell_size;
    }

    public String generateTag() {
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        return DefaultByteEncoder.encodeToBase64(bytes);
    }

    public SecretKey generateNextKey(SecretKey key) {
        return forwardKeyGenerator.generateNextKey(key);
    }
}
