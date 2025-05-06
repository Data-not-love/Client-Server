package org.example.java_socket_network_programming.TCP.Encryption;

import io.github.cdimascio.dotenv.Dotenv;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DecryptKeyGenerator {
    private static String AES = Dotenv.configure().filename(".env").load().get("ENCRYPTION_TYPE");
    private static int keySize = Integer.parseInt(Dotenv.configure().filename(".env").load().get("KEY_SIZE"));

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    public static String encodeKey(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static SecretKey decodeKey(String encodedKey) {
        byte[] decoded = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decoded, AES);
    }



}

