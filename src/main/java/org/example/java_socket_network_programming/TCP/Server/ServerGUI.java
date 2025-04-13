package org.example.java_socket_network_programming.TCP.Server;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.java_socket_network_programming.TCP.Client.Observable_Client;
import java.io.IOException;
import java.util.Objects;


public class ServerGUI extends Application {

    private ObservableList<Observable_Client> clientData = FXCollections.observableArrayList();
    private Thread serverThread;
    private boolean isServerRunning = false;
    private Server server;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Server Side");

        Button startServer = new Button("Start Server");
        Label labelStatus = new Label("Server is Closed");


        startServer.setOnAction(event -> {
            if (!isServerRunning) {
                try {
                    server = new Server();
                    labelStatus.setText("Server is Open");
                    startServer.setText("Stop Server");
                    isServerRunning = true;

                    server.setClientListener(observableClient -> clientData.add(observableClient));

                    serverThread = new Thread(server::receiveFile);
                    serverThread.setDaemon(true);
                    serverThread.start();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                server.stopServer();
                labelStatus.setText("Server is Closed");
                startServer.setText("Start Server");
                isServerRunning = false;
            }
        });


        HBox firstRow = new HBox(10);
        firstRow.getChildren().addAll(startServer, labelStatus);
        firstRow.setStyle("-fx-alignment: center-left;");

        Label fileSentFromClient = new Label("Receive File From Clients");
        TableView<Observable_Client> tableView = new TableView<>();

        TableColumn<Observable_Client, String> clientID = new TableColumn<>("Client ID");
        clientID.setCellValueFactory(cellData -> cellData.getValue().clientIDProperty());

        TableColumn<Observable_Client, String> clientOS = new TableColumn<>("Client OS");
        clientOS.setCellValueFactory(cellData -> cellData.getValue().clientOSProperty());

        TableColumn<Observable_Client, String> clientIP = new TableColumn<>("Client IP");
        clientIP.setCellValueFactory(cellData -> cellData.getValue().clientIPProperty());

        TableColumn<Observable_Client, String> FileName = new TableColumn<>("File Name");
        FileName.setCellValueFactory(clientData -> clientData.getValue().clientFileProperty());

        TableColumn<Observable_Client, String> FileSize = new TableColumn<>("File Size");
        FileSize.setCellValueFactory(clientData -> clientData.getValue().clientFileSizeProperty());



        clientID.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        clientOS.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        clientIP.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        FileName.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        FileSize.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        
        tableView.setItems(clientData);
        tableView.getColumns().addAll(clientID, clientOS,clientIP,FileName,FileSize);

        VBox layout = new VBox(15, firstRow, fileSentFromClient, tableView);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Receive Files", layout);
        tab1.setClosable(false);

        Tab tab2 = new Tab("Send Files", new Label("Settings tab content goes here..."));
        tab2.setClosable(false);

        tabPane.getTabs().addAll(tab1, tab2);

        Scene scene = new Scene(tabPane, 750, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/style.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
