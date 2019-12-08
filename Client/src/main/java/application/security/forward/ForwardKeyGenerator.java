package application.security.forward;

import com.google.common.primitives.Longs;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Random;

import static application.security.utils.DefaultKeyEncoder.toSecretKey;

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
        return toSecretKey(bytes);
    }
}
