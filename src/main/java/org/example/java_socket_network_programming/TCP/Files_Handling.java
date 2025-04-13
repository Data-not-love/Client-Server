package org.example.socket_network_programming.TCP;

import java.io.*;
import java.net.Socket;

public abstract class Files_Handling {
    public void receiveFile(Socket socket, String saveDirectory) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        // Read the file name from the client
        String fileName = dataInputStream.readUTF();
        long fileSize = dataInputStream.readLong();

        // Save file with the same name in the specified directory
        FileOutputStream fileOutputStream = new FileOutputStream(saveDirectory + fileName);
        byte[] buffer = new byte[4096];
        int bytesRead;
        long totalRead = 0;

        while (totalRead < fileSize && (bytesRead = dataInputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, bytesRead);
            totalRead += bytesRead;
        }

        fileOutputStream.close();
        System.out.println("File received: " + fileName);
    }

    public void sendFile(Socket socket, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);
            return;
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        // Send file name and size first
        dataOutputStream.writeUTF(file.getName());
        dataOutputStream.writeLong(file.length());

        // Send file data
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) > 0) {
            dataOutputStream.write(buffer, 0, bytesRead);
        }

        fileInputStream.close();
        dataOutputStream.flush();
        System.out.println("File sent: " + file.getName());
    }
}