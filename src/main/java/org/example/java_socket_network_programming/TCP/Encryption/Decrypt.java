package org.example.java_socket_network_programming.TCP.Encryption;

import io.github.cdimascio.dotenv.Dotenv;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decrypt {
    private static final String KEY_TRANSFORMATION = Dotenv.configure().filename(".env").load().get("KEY_TRANSFORMATION");

    public static void decryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {

            byte[] iv = new byte[16];
            if (fileInputStream.read(iv) != iv.length) {
                throw new IOException("Unable to read IV from file.");
            }

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(KEY_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            try (CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
                 FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

            }

        }
    }
}
