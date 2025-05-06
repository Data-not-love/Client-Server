package org.example.java_socket_network_programming.TCP.Encryption;

import javax.crypto.SecretKey;
import java.io.File;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws Exception {
        SecretKey key = DecryptKeyGenerator.generateKey();
        String encodeKey = DecryptKeyGenerator.encodeKey(key);
        System.out.println("Key : " + encodeKey);


        SecretKey decodeKey = DecryptKeyGenerator.decodeKey(encodeKey);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter path for encryption : ");
        String filePath = scanner.nextLine();
        File encryptFile = new File(filePath);

        File encrypted = new File(encryptFile+".encrypted");
        Encrypt.encryptFile(encryptFile,encrypted, decodeKey);



    }
}
