package org.example.java_socket_network_programming.TCP;

public class File_To_Send {
    private int id;
    private String fileName;
    private byte [] data;
    private String fiLeFormat;


    public File_To_Send(int id,String fileName, byte[] data, String fiLeFormat) {
        this.id = id;
        this.fileName = fileName;
        this.data = data;
        this.fiLeFormat = fiLeFormat;

    }

    public String getFiLeFormat() {
        return fiLeFormat;
    }

    public void setFiLeFormat(String fiLeFormat) {
        this.fiLeFormat = fiLeFormat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
