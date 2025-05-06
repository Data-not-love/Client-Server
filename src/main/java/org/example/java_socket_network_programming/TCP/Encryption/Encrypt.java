package org.example.java_socket_network_programming.TCP.Encryption;


import io.github.cdimascio.dotenv.Dotenv;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.SecureRandom;

public class Encrypt {
    private static final String KEY_TRANSFORMATION = Dotenv.configure().filename(".env").load().get("KEY_TRANSFORMATION");

    public static void encryptFile(File inputFile, File outputFile, SecretKey decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_TRANSFORMATION);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, decryptKey, ivSpec);

        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream,cipher);
        FileInputStream fileInputStream = new FileInputStream(inputFile))
        {
            fileOutputStream.write(iv);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }

        }
    }
}
