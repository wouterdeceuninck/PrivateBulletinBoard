package shared.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class KeyStoreUtil {

    public static final String pathname = "";

    public static KeyStore getKeyStore(String keystoreName) {
        File file = new File(pathname + keystoreName);
        KeyStore keyStore = getJceksInstance();
        if (file.exists()) {
            loadFile(file, keyStore);
        } else {
            createNewFile(file, keyStore);
        }
        return keyStore;
    }

    private static void loadFile(File file, KeyStore keyStore) {
        try {
            keyStore.load(new FileInputStream(file), "".toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createNewFile(File file, KeyStore keyStore) {
        try {
            keyStore.load(null, null);
            keyStore.store(new FileOutputStream(file), "".toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeyStore getJceksInstance() {
        try {
            return KeyStore.getInstance("JCEKS");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
}
