package application.messaging.forward;

import application.security.utils.DefaultByteEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SecureGenerator {

    private final int cell_size = 16;
    private final SecureRandom secureRandom;

    public SecureGenerator(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public int generateCellIndex() {
        byte[] bytes = new byte[4];
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
}
