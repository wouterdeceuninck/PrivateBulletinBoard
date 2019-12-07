package application.security.forward;

import com.google.common.primitives.Longs;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Random;

public class ForwardKeyGenerator {

    private final Random random;

    public ForwardKeyGenerator(Random random) {
        this.random = random;
    }

    public SecretKey generateNextKey(Key key) {
        byte[] encoded = key.getEncoded();
        byte[] bytes = new byte[20];

        random.setSeed(Longs.fromByteArray(encoded));
        random.nextBytes(bytes);
        return new SecretKeySpec(bytes, 0, 16, "AES");
    }
}
