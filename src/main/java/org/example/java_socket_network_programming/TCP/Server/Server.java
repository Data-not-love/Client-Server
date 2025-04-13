package org.example.java_socket_network_programming.TCP.Server;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import org.example.java_socket_network_programming.TCP.Client.Client;
import org.example.java_socket_network_programming.TCP.Client.Observable_Client;
import org.example.java_socket_network_programming.TCP.Controller.File_Handling;
import org.example.java_socket_network_programming.TCP.Controller.Notification;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;
import java.util.concurrent.BlockingQueue;


public class Server implements File_Handling {
    private final String portNumber = Dotenv.configure().filename(".env").load().get("PORT");
    private final ServerSocket serverSocket;
    private Consumer<Observable_Client> clientListener;
    private volatile boolean running = false;


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(Integer.parseInt(portNumber));
        serverSocket.setSoTimeout(1000000);
    }

    public void setClientListener(Consumer<Observable_Client> listener) {
        this.clientListener = listener;
    }
    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("\u001B[31mServer stopped.\u001B[0m");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendFile(File selectedFile) {

    }

    @Override
    public void receiveFile() {
        while (true) {
            System.out.println("\u001B[33mWaiting for client...\u001B[0m");

            try (Socket clientSocket = serverSocket.accept();
                 DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream())) {

                String fileName = inputStream.readUTF();
                long fileSize = inputStream.readLong();
                String sizeInMB = String.format("%.2f", fileSize / Math.pow(1024.0, 2));

                if (fileName.isBlank()) {
                    System.err.println("Received empty filename. Skipping.");
                    continue;
                }

                // --- Show GUI popup to ask user if they want to accept ---
                BlockingQueue<Boolean> decisionQueue = new ArrayBlockingQueue<>(1);
                Platform.runLater(() -> Notification.askUserForFile(fileName, sizeInMB, decisionQueue));

                boolean accepted = decisionQueue.take(); // blocks until user clicks

                if (!accepted) {
                    System.out.println("User refused the transfer for file: " + fileName);
                    continue; // skip receiving
                }

                File saveFile = new File(System.getProperty("user.home") + "/Downloads", fileName);
                try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalRead = 0;

                    while (totalRead < fileSize && (bytesRead = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                        totalRead += bytesRead;
                    }
                }

                System.out.println("File saved to: " + saveFile.getAbsolutePath());

                if (clientListener != null) {
                    Client client = new Client();
                    Observable_Client observableClient = new Observable_Client(Client.getId(), client.getOS(), client.getIP(), fileName, sizeInMB + "MB");
                    Platform.runLater(() -> clientListener.accept(observableClient));
                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void sendFolderAsZip() {

    }

    @Override
    public void extractZipAfterSend() {

    }

}
