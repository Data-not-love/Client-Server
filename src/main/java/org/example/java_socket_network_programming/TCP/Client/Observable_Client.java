package org.example.java_socket_network_programming.TCP.Client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Observable_Client {
    private final StringProperty clientID;
    private final StringProperty clientOS;
    private final StringProperty clientIP;
    private final StringProperty clientFile;
    private final StringProperty clientFileSize;

    public Observable_Client(int id, String os, String ip, String clientFile, String clientFileSize) {
        this.clientID = new SimpleStringProperty(String.valueOf(id));
        this.clientOS = new SimpleStringProperty(os);
        this.clientIP = new SimpleStringProperty(ip);
        this.clientFile = new SimpleStringProperty(clientFile);
        this.clientFileSize = new SimpleStringProperty(clientFileSize);
    }

    // Getters for properties
//    public String getClientID() {
//        return clientID.get();
//    }
//    public String getClientOS() {
//        return clientOS.get();
//    }
//    public String getClientIP() {
//        return clientIP.get();
//    }
//    public String getClientFile() {
//        return clientFile.get();
//    }
//    public String getClientFileSize() {
//        return clientFileSize.get();
//    }


    public StringProperty clientIDProperty() {
        return clientID;
    }
    public StringProperty clientOSProperty() {
        return clientOS;
    }
    public StringProperty clientIPProperty() {
        return clientIP;
    }
    public StringProperty clientFileProperty() {
        return clientFile;
    }
    public StringProperty clientFileSizeProperty() {
        return clientFileSize;
    }


    // Setters for properties
//    public void setClientID(String clientID) {
//        this.clientID.set(clientID);
//    }
//
//    public void setClientOS(String clientOS) {
//        this.clientOS.set(clientOS);
//    }
//
//    public void setClientIP(String clientIP) {
//        this.clientIP.set(clientIP);
//    }
//
//    public void setClientFile(String clientFile) {
//        this.clientFile.set(clientFile);
//    }
}
