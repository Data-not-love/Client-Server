package org.example.java_socket_network_programming.TCP.Controller;

import java.io.File;
import java.io.IOException;

public interface File_Handling {
    void sendFile(File selectedFile) throws IOException;
    void receiveFile();

    // send Folder
    // Step 1 : zip a Folder first
    // Step 2 : Send zip to the server
    // Step 3 : Unzip again

    void sendFolderAsZip();
    void extractZipAfterSend();
}
