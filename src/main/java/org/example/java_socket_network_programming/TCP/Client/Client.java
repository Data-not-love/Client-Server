package org.example.java_socket_network_programming.TCP.Client;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.java_socket_network_programming.TCP.Controller.File_Handling;
import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class Client implements File_Handling{
    private String portNumber;
    private String serverAddress;
    private String OS;
    private static int id = 0;
    private File selectedFile;
    private InetAddress ip;
    private String IP;

    public Client() {
        Dotenv dotenv = Dotenv.configure().filename(".env").load();
        this.serverAddress = dotenv.get("SERVER_ADDRESS");
        this.portNumber = dotenv.get("PORT");
        this.OS = System.getProperty("os.name");
        this.id = ++id;

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();

                if (netInterface.isUp() && !netInterface.isLoopback() && !netInterface.isVirtual()) {
                    if (netInterface.getName().contains("wlan") || netInterface.getDisplayName().toLowerCase().contains("wi-fi")) {
                        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            InetAddress inet = addresses.nextElement();
                            if (inet instanceof Inet4Address) {
                                this.ip = inet;
                                this.IP = ip.getHostAddress();
                                break;
                            }
                        }
                    }
                }

                if (this.ip != null) break;
            }

            if (this.ip == null) {
                this.IP = "Unknown IP";
            }
        } catch (SocketException e) {
            this.IP = "Unknown IP";
            e.printStackTrace();
        }
    }

    @Override

    public void sendFile(File selectedFile) throws IOException {
        try (Socket clientSocket = new Socket(getServerAddress(), Integer.parseInt(getPortNumber()));
             FileInputStream fileInput = new FileInputStream(selectedFile);
             DataOutputStream fileOutPut = new DataOutputStream(clientSocket.getOutputStream())) {

            // Send file name and size
            fileOutPut.writeUTF(selectedFile.getName());
            fileOutPut.writeLong(selectedFile.length());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInput.read(buffer)) > 0) {
                fileOutPut.write(buffer, 0, bytesRead);
            }

            fileOutPut.flush();
            System.out.println("File is about to send");
        }

    }

    @Override
    public void receiveFile() {

    }

    @Override
    public void sendFolderAsZip() {

    }

    @Override
    public void extractZipAfterSend() {

    }


    public static int getId() {
        return id;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getOS() {
        return OS;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public InetAddress getIp() {
        return ip;
    }

    public String getIP() {
        return IP;
    }


}
